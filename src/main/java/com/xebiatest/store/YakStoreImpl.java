package com.xebiatest.store;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kamlendra on 18/07/15.
 */
public class YakStoreImpl  implements  YakStore{

    //private Map dayYieldMap = new HashMap<Integer,YakYield>();
    private Map yakStore = new HashMap<Integer,Map<Integer,YakYield>>();
    public YakYield getYieldForYak(int id, int elapsedTimeInDays) {
        //get the yield map for this yak from store
        Map<Integer,YakYield> dayYieldMap = (Map<Integer, YakYield>) yakStore.get(id);
        //return yield
        return dayYieldMap.get(elapsedTimeInDays);
    }

    public YakYield getTotalYield(int elapsedTimeInDays) {
        double totalMilk = 0;
        int totalSkins = 0;
        Iterator it = yakStore.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            YakYield yield = ((Map<Integer, YakYield>) pair.getValue()).get(elapsedTimeInDays);
            totalMilk += yield.getMilk();
            totalSkins += yield.getSkin();
        }
        return  new YakYield(totalMilk,totalSkins);
    }
    public void saveYieldForDay(int id, int elapsedTimeInDays, YakYield yield) {
        //get the yield map for this yak
        Map<Integer,YakYield> dayYieldMap = (Map<Integer, YakYield>) yakStore.get(id);
        if(dayYieldMap == null) {
            dayYieldMap = new HashMap<Integer, YakYield>();
            yakStore.put(id,dayYieldMap);

        }
        //store the yield
        dayYieldMap.put(elapsedTimeInDays,yield);
    }
}
