package eu.robertboloc.holaurv.test;

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
}
