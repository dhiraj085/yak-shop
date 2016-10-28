package com.xebiatest.store;

/**
 * Created by kamlendra on 18/07/15.
 */
public interface YakStore {

    /**
     * Created by kamlendra on 18/07/15.
     */

     public YakYield getYieldForYak(int id, int elapsedTimeInDays);
     public YakYield getTotalYield(int elapsedTimeInDays);
     public void saveYieldForDay(int id,int elapsedTimeInDays,YakYield yield);
}
