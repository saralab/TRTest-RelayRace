package com.tr.sarala;

import java.util.concurrent.Callable;

/**
 * Created by S on 2/10/16.
 */
public class Runner implements Callable<Double> {

    private String runnerName;
    private static final double MIN_RACE_TIME = 9.0;
    private static final double MAX_RACE_TIME = 10.5;


    public Runner(String runnerName) {
        this.runnerName = runnerName;
    }

    public String getRunnerName() {
        return runnerName;
    }

    @Override
    public Double call() throws Exception {
        return executeJob();
    }

    public double executeJob() {
        double range = (MAX_RACE_TIME - MIN_RACE_TIME);
        System.out.println(" Executing Runner :: " + this.runnerName);
        return (Math.random() * range) + MIN_RACE_TIME;
    }

    @Override
    public String toString() {
        return runnerName;
    }
}
