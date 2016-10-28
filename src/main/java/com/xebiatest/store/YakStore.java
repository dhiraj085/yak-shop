package com.xebiatest.store;

public interface YakStore {


     public YakYield getYieldForYak(int id, int elapsedTimeInDays);
     public YakYield getTotalYield(int elapsedTimeInDays);
     public void saveYieldForDay(int id,int elapsedTimeInDays,YakYield yield);
}
