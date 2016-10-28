package com.xebiatest.service;

import com.xebiatest.model.LabYak;
import com.xebiatest.store.YakYield;

public interface YakService {

    public YakYield getTotalYakYield(int elapsedTimeInDays);
    public YakYield getYakYieldForAYak(LabYak yak, int elapsedTimeInDays);
    public void calculateAndSaveYieldForDay(LabYak yak,int elapsedTimeInDays);

}
