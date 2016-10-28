package com.xebiatest.controller;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonObject;
import com.xebiatest.io.XmlReader;
import com.xebiatest.model.CustomerOrder;
import com.xebiatest.model.LabYak;
import com.xebiatest.service.YakService;
import com.xebiatest.service.YakServiceImpl;
import com.xebiatest.store.YakYield;

@Controller
public class BaseController extends BaseWSController{

	private static int counter = 0;
	private static final String VIEW_INDEX = "index";
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(ModelMap model) {

		model.addAttribute("message", "Welcome");
		model.addAttribute("counter", ++counter);
		logger.debug("[welcome] counter : {}", counter);

		// Spring uses InternalResourceViewResolver and return back index.jsp
		return VIEW_INDEX;

	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String welcomeName(@PathVariable String name, ModelMap model) {

		model.addAttribute("message", "Welcome " + name);
		model.addAttribute("counter", ++counter);
		logger.debug("[welcomeName] counter : {}", counter);
		return VIEW_INDEX;

	}
	@RequestMapping(value = "/stock/{t}", method = RequestMethod.GET)
	public @org.springframework.web.bind.annotation.ResponseBody String getStock(@PathVariable Integer t, ModelMap model) {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("input/herd.xml").getFile());
        int elapsedTimeInDays = t;//Integer.parseInt(args[1]);
        ArrayList<LabYak> yakList = new XmlReader(file).read();


        YakService service = new YakServiceImpl();
        for(LabYak yak : yakList){
            service.calculateAndSaveYieldForDay(yak, elapsedTimeInDays);
        }
        YakYield totalYakYield = service.getTotalYakYield(elapsedTimeInDays);
        System.out.println("T = "+elapsedTimeInDays+"\n\n");

        System.out.println("In Stock:");
        System.out.println("\t\t"+totalYakYield.getMilk()+" liters of milk");
        System.out.println("\t\t"+totalYakYield.getSkin()+" skins of wool");
        System.out.println("Herd:\n\n");
        for(LabYak yak : yakList){
            System.out.println(yak.display(elapsedTimeInDays));
        }
		logger.debug("[welcomeName] counter : {}", counter);
		return convertIntoJson(totalYakYield);

	}
	@RequestMapping(value = "/herd/{t}", method = RequestMethod.GET)
	public @org.springframework.web.bind.annotation.ResponseBody String getHerd(@PathVariable Integer t, ModelMap model) {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("input/herd.xml").getFile());
        int elapsedTimeInDays = t;//Integer.parseInt(args[1]);
        ArrayList<LabYak> yakList = new XmlReader(file).read();

        for(LabYak yak : yakList){
        	if(yak.isAlive(elapsedTimeInDays)){
        		yak.setAge((yak.getAge()*100+elapsedTimeInDays)/100);
        		yak.setAgeAtLastShave(yak.getAgeAtLastShave()/100);
        	}
        }
   		return convertIntoJson(yakList);

	}
	@RequestMapping(value = "/order/{t}", method = RequestMethod.POST)
	public @org.springframework.web.bind.annotation.ResponseBody String postOrder(@PathVariable Integer t,@RequestBody String body, ModelMap model) {
		System.out.println("body "+body);
		CustomerOrder customerOrder=gson.fromJson(body, CustomerOrder.class);
		System.out.println("customer "+customerOrder.getCustomer());
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("input/herd.xml").getFile());
        int elapsedTimeInDays = t;//Integer.parseInt(args[1]);
        ArrayList<LabYak> yakList = new XmlReader(file).read();

        Integer milk=customerOrder.getOrder().getMilk();
        Integer skin=customerOrder.getOrder().getSkins();
        YakService service = new YakServiceImpl();
        for(LabYak yak : yakList){
            service.calculateAndSaveYieldForDay(yak, elapsedTimeInDays);
        }
        JsonObject respose=new JsonObject();
        YakYield totalYakYield = service.getTotalYakYield(elapsedTimeInDays);

        if(totalYakYield.getMilk()>milk && totalYakYield.getSkin()>skin){
        	respose.addProperty("status", 201);
        	JsonObject order=new JsonObject();
        	order.addProperty("milk", milk);
        	order.addProperty("skins", skin);
        	respose.add("order", order);
        }
        if(totalYakYield.getMilk()>milk && totalYakYield.getSkin()<skin){
        	respose.addProperty("status", 206);
        	JsonObject order=new JsonObject();
        	order.addProperty("milk", milk);
        	respose.add("order", order);
        	
        }
        if(totalYakYield.getMilk()<milk && totalYakYield.getSkin()>skin){
        	respose.addProperty("status", 206);
        	JsonObject order=new JsonObject();
        	order.addProperty("skin", skin);
        	respose.add("order", order);
        }
        if(totalYakYield.getMilk()<milk && totalYakYield.getSkin()<skin){
        	respose.addProperty("status", 404);
        }
        
   		return convertIntoJson(respose);

	}


}