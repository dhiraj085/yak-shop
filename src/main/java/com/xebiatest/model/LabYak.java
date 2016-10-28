package com.xebiatest.model;


import com.google.gson.annotations.Expose;
import com.xebiatest.exception.YakException;
import com.xebiatest.store.YakYield;
import com.xebiatest.util.Constants;

public class LabYak {

    private String name;
    private double age = 0; // In years
    @Expose(serialize = false, deserialize = false)
    private Sex yakSex;
    private double ageAtLastShave = 0;
    @Expose(serialize = false, deserialize = false)
    private int id = 0;

    public int getId() {
        return id;
    }

    public enum Sex {
        MALE, FEMALE
    }


    public String display(int elapsedTimeInDays) {
        return isAlive(elapsedTimeInDays) ? name + " " + (age*100+elapsedTimeInDays)/100 + " years old" : Constants.YAK_DIED_MSG;
    }

    public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}

	public double getAgeAtLastShave() {
		return ageAtLastShave;
	}

	public void setAgeAtLastShave(double ageAtLastShave) {
		this.ageAtLastShave = ageAtLastShave;
	}

	public LabYak(int id,String name, double age, Sex yakSex) {
        this.name = name;
        this.age = age;
        this.yakSex = yakSex;
        ageAtLastShave = age*100;
        this.id = id;
    }
    public int getSkinCount(int elapsedTimeInDays){
        int skinCount = 1; // for day 0
        for(int day=1;day<elapsedTimeInDays;day++){
          int currentAgeInDays = calculateCurrentAgeInDays(day);
            try {
                if(canShaveToday(currentAgeInDays)){
                    skinCount++;
                    ageAtLastShave = currentAgeInDays;
                }
            }
            catch (YakException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        return skinCount;
    }


    //At most every 8+D*0.01 days you can again shave a LabYak (D = age in days).
    // can shave if -> difference in today's age in days and age in days at last shave is more than allowed gap

    public YakYield calculateYakYieldForDay(int elapsedTimeInDays){
        return new YakYield(getTotalMilkQuantity(elapsedTimeInDays),getSkinCount(elapsedTimeInDays));
    }

    private boolean canShaveToday(int currentAgeInDays)throws YakException{
        boolean isEligibleForShave = false;
        double allowedGapInShave = (8+ageAtLastShave*0.01);
        if(isAlive(currentAgeInDays)) {
            isEligibleForShave = (currentAgeInDays - ageAtLastShave > allowedGapInShave) ? true : false;
        }
        else
            throw new YakException(Constants.YAK_DIED_MSG);

        return isEligibleForShave;
    }
    public double getTotalMilkQuantity(int elapsedTimeInDays){
        double totalMilkQuantity = 0;
        for(int day=0;day<elapsedTimeInDays;day++){
            try {
                totalMilkQuantity = totalMilkQuantity + getMilkQuantityForDay(calculateCurrentAgeInDays(day));
            }
            catch(YakException ex){
                System.out.println(ex.getMessage());
                break;
            }
        }

        return totalMilkQuantity;
    }
    public  double getMilkQuantityForDay(int currentAgeInDays) throws YakException{
        double milkForToday = 0;
        if (isAliveByAge(currentAgeInDays)) {
            milkForToday = (50 - currentAgeInDays * 0.03);
        }
        else{
            throw new YakException(Constants.YAK_DIED_MSG);
        }
        return milkForToday;
    }

    private boolean isAliveByAge(int ageInDays){
        return ageInDays < 1000;
    }
    public boolean isAlive(int elapsedTimeInDays){
        return calculateCurrentAgeInDays(elapsedTimeInDays) < 1000;
    }

    private int calculateCurrentAgeInDays(int elapsedTimeInDays) {
        return (int)(age * 100 + elapsedTimeInDays);
    }
}
