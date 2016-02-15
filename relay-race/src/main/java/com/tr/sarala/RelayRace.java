package com.tr.sarala;

import java.util.concurrent.*;

/**
 * Created by S on 2/10/16.
 */
public class RelayRace implements  Race{

    //Use a ConcurrentMap to log team standings!
    public final static ConcurrentMap<Double, Team> teamStandings = new ConcurrentHashMap<Double, Team>();

    int teamSize;
    int numberOfTeams;
    Team[] teams;
    private ExecutorService executorService;

    @Override
    public String toString() {
        return teamSize + ":" + numberOfTeams + ":" + teams + ":" + teamStandings;
    }

    public RelayRace(int teamSize, int numberOfTeams, ExecutorService executorService) {
        this.teamSize = teamSize;
        this.numberOfTeams = numberOfTeams;
        this.executorService = executorService;
        initTeams();
    }


    /**
     * This method uses a Cyclic Barrier to start the race at the same time for all the teams
     * After all the teams finish at the barrier, it saves the team standings in a ConcurrentMap
     *
     * @throws InterruptedException
     */
    public void startRace() throws InterruptedException {

        System.out.println("Starting the race with " + teams);

        final CyclicBarrier raceBarrier = new CyclicBarrier(teamSize);
        final CyclicBarrier raceEndBarrier = new CyclicBarrier(teamSize);

        final CountDownLatch startLatch = new CountDownLatch(teamSize);
        final CountDownLatch endLatch = new CountDownLatch(teamSize);

        try{
        for (final Team team : teams) {
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println("Starting team : " + team.teamName);
                        //Make sure all the teams start at the same time
                        startLatch.countDown();
                        startLatch.await();

                        System.out.println("RelayRace in progress");

                        teamStandings.put(team.startTeamRaceAndGetTime(), team);

                        //Wait for all the teams to finish
                        endLatch.countDown();
                        endLatch.await();
                        System.out.println("Done");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread.sleep(10);
        }
        }
        finally {
            executorService.shutdown();
        }
    }

    /**
     * This is actually just a util method to initialize teams and assign racers to each team
     *
     * @return
     */

    private Team[] initTeams() {

        //Initialize teams based on number of teams
        teams = new Team[numberOfTeams];

        for (int teamIndex = 1; teamIndex <= numberOfTeams; teamIndex++) {
            Team team = new Team(new StringBuffer().append(teamIndex).toString());

            //Initialize Runners per each team
            team.runners = new Runner[teamSize];

            for (int racerIndex = 1; racerIndex <= teamSize; racerIndex++) {
                Runner raceRunner = new Runner(buildRunnerName(teamIndex, racerIndex));
                team.runners[racerIndex - 1] = raceRunner;
            }
            teams[teamIndex - 1] = team;
        }

        return teams;
    }


    /**
     * Builds a runnerName: Team1Runner1, Team1Runner2 ...
     *
     * @param teamIndex
     * @param racerIndex
     * @return
     */
    String buildRunnerName(int teamIndex, int racerIndex) {
        StringBuffer name = new StringBuffer();
        name.append("Team").append(teamIndex).append("Runner").append(racerIndex);
        return name.toString();
    }
}
