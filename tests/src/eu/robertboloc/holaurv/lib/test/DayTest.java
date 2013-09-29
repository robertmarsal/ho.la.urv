package eu.robertboloc.holaurv.lib.test;

import junit.framework.TestCase;
import eu.robertboloc.holaurv.lib.Day;

public class DayTest extends TestCase {

    Day testDay;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testDay = new Day();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetShiftDisplay() {
        // Test empty case
        testDay.setShiftRaw("");
        assertEquals("", testDay.getShiftDisplay());

        // Test what happens on an off day
        testDay.setShiftRaw("Festiu");
        assertEquals("", testDay.getShiftDisplay());

        // Test what happens on a normal day
        testDay.setShiftRaw("G-09:00");
        assertEquals("09:00", testDay.getShiftDisplay());
    }

    public void testSetShiftRaw() {
        testDay.setShiftRaw("test");
        assertEquals("test", testDay.getShiftDisplay());
    }
}
