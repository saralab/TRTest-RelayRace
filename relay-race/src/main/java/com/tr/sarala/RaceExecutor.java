package com.tr.sarala;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;

/**
 * RaceExecutor strarts a new RelayRace with a given team size and number of team members
 * It then sorts the final team standings using a TreeMap and prints them
 */
public class RaceExecutor {

    final static int TEAM_SIZE = 4;
    final static int NUMBER_OF_TEAMS = 6;

    public static void main(String[] args) throws InterruptedException {
        RaceExecutor relayRace = new RaceExecutor();

        RelayRace race = new RelayRace(TEAM_SIZE, NUMBER_OF_TEAMS, Executors.newFixedThreadPool(TEAM_SIZE));

        race.startRace();

        relayRace.printStandigs(race);

        System.exit(0);

    }

    private void printStandigs(RelayRace relayRace) {
        ConcurrentMap finalResults = relayRace.teamStandings;
        //Use a TreeMap to copy the final results to sort them by time
        //Couldn't use the TreeMap in the RelayRace class , as it' NOT concurrent!
        SortedMap<Double, Team> teamStandings = new TreeMap();

        teamStandings.putAll(finalResults);

        System.out.println(" Final Team Standings : ");

        for (Double teamTime : teamStandings.keySet()) {
            System.out.println(" Team : " + finalResults.get(teamTime) + " Total Time: " + Math.round(teamTime * 100.0) / 100.0);
        }
    }

}
