package com.tr.sarala;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


/**
 * Created by S on 2/10/16.
 */
public class RunnerTest {

    Runner testObject;

    @Before
    public void setUp() {
        testObject = new Runner("Flash");
    }

    @Test
    public void executeJob_ReturnsANumberWithinAGivenRange() {
        double actualResult = testObject.executeJob();
        assertNotNull(testObject.executeJob());
        assert (actualResult > 9.0);
        assert (actualResult < 10.5);
    }
}
