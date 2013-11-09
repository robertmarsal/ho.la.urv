package eu.robertboloc.holaurv.lib.test;

import junit.framework.TestCase;
import eu.robertboloc.holaurv.lib.Evalos;

public class EvalosTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetUsername() {
        String testUsername = "testUsername";
        Evalos testEva = new Evalos(testUsername, "testPassword");
        assertEquals(testUsername, testEva.getUsername());
    }

    public void testComputeBalance() {
        Evalos testEva = new Evalos("test", "test");
        long computedBalance = testEva.computeBalance("09:00", "06:40");

        // The difference is -2 hours and 20 minutes which is -8400000 milis
        assertEquals(-8400000, computedBalance);
    }
}
