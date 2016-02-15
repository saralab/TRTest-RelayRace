package com.tr.sarala;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by S on 2/10/16.
 */
public class Team {

    String teamName;

    Runner[] runners;

    private ExecutorService executor;

    public Team(String teamName) {
        this.teamName = teamName;
    }

    /**
     * This method uses a SingleThreadExecutor to ensure 1 runner runs at a given time
     * It uses a Future.get which is a blocking call to make sure runners per team run in order, after one another.
     *
     * This method should ideally belong here and not in the RelayRace class.
     * @return
     */
    public Double startTeamRaceAndGetTime() {
        //Make sure the executor runs one Thread/Runner per team a time
        executor = Executors.newSingleThreadExecutor();

        List<Future<Double>> runningTimes = new ArrayList();
        double totalTime = 0.0;

        for (Runner runner : runners) {
            // Get ready to run: Add each runner to the executor
            runningTimes.add(executor.submit(runner));
        }

        //Using a Future and Future.get to make sure it's a blocking call per runner per team
        for (Future runner : runningTimes) {
            try {
                totalTime += ((Double) runner.get()).doubleValue();
            }
            //TODO Exit graciously here
            catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Team " + this.teamName + "::  time :: " + totalTime);

        return totalTime;
    }

    @Override
    public String toString() {
        return teamName + ":" + runners;
    }

}
