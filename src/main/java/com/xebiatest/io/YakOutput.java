package com.xebiatest.io;


public class YakOutput {

    public double getTotalMilkLiters() {
        return totalMilkLiters;
    }

    public void setTotalMilkLiters(double totalMilkLiters) {
        this.totalMilkLiters = totalMilkLiters;
    }

    private double totalMilkLiters = 0;
    private int totalSkins = 0;
    public YakOutput(double totalMilkLiters,int totalSkins){
        this.totalMilkLiters = totalMilkLiters;
        this.totalSkins = totalSkins;
    }
    public int getTotalSkins() {
        return totalSkins;
    }

    public void setTotalSkins(int totalSkins) {
        this.totalSkins = totalSkins;
    }

}
