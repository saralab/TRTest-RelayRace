package com.tr.sarala;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertEquals;

/**
 * Created by S on 2/10/16.
 */
public class RaceTest {


    @Mock
    ExecutorService executor;

    @InjectMocks
    RelayRace testObject;

    @Before
    public void setUp() {
        testObject = new RelayRace(1, 1, executor);
        MockitoAnnotations.initMocks(this);
        testObject = new RelayRace(1, 1, executor);
    }

    @Test
    public void buildRunnerName_returnsExpectedName() {
        String expectedName = testObject.buildRunnerName(1, 1);
        assertEquals("Team1Runner1", expectedName);

        expectedName = testObject.buildRunnerName(2, 1);
        assertEquals("Team2Runner1", expectedName);
    }

    @Test
    public void initTeams_returnsExpectedTeams() {
        assertEquals(1, testObject.teams.length);
        assertEquals(1, testObject.teams.length);
        assertEquals("1", testObject.teams[0].teamName);
        assertEquals("Team1Runner1", testObject.teams[0].runners[0].getRunnerName());

        testObject = new RelayRace(2, 2, executor);

        assertEquals(2, testObject.teams.length);
        assertEquals(2, testObject.teams.length);
        assertEquals("2", testObject.teams[1].teamName);
        assertEquals("Team1Runner2", testObject.teams[0].runners[1].getRunnerName());
    }

}
