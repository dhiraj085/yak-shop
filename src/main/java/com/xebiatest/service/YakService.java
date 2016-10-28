package com.xebiatest.service;

import com.xebiatest.io.YakOutput;
import com.xebiatest.model.LabYak;
import com.xebiatest.store.YakYield;

import java.util.List;

/**
 * Created by kamlendra on 18/07/15.
 */
public interface YakService {

    public YakYield getTotalYakYield(int elapsedTimeInDays);
    public YakYield getYakYieldForAYak(LabYak yak, int elapsedTimeInDays);
    public void calculateAndSaveYieldForDay(LabYak yak,int elapsedTimeInDays);

}
