package eu.robertboloc.holaurv.lib.test;

import junit.framework.TestCase;

import org.joda.time.Period;

import eu.robertboloc.holaurv.lib.Day;
import eu.robertboloc.holaurv.lib.Entry;

public class DayTest extends TestCase {

    Day testDay;

    Entry firstEntry = new Entry();
    Entry firstExit = new Entry();
    Entry secondEntry = new Entry();
    Entry secondExit = new Entry();

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        testDay = new Day();

        // Set up first entry
        firstEntry.setHourRaw("08");
        firstEntry.setMinuteRaw("15");

        // Set up first exit
        firstExit.setHourRaw("14");
        firstExit.setMinuteRaw("01");

        // Set up second entry
        secondEntry.setHourRaw("15");
        secondEntry.setMinuteRaw("01");

        // Set up second exit
        secondExit.setHourRaw("18");
        secondExit.setMinuteRaw("01");
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

    public void testGetAccumulateWithParamsFirst() {
        testDay.addEntry(firstEntry);
        testDay.addEntry(firstExit);

        Period firstPeriod = testDay.getAccumulate(Entry.FIRST_ENTRY,
                Entry.FIRST_EXIT);

        // From 08:15 to 14:01 -> 5 hours and 46 minutes
        assertEquals(5, firstPeriod.getHours());
        assertEquals(46, firstPeriod.getMinutes());
    }

    public void testGetAccumulateWithParamsSecond() {
        // Entries position is sequential
        testDay.addEntry(firstEntry);
        testDay.addEntry(firstExit);
        testDay.addEntry(secondEntry);
        testDay.addEntry(secondExit);

        Period secondPeriod = testDay.getAccumulate(Entry.SECOND_ENTRY,
                Entry.SECOND_EXIT);

        // From 15:01 to 18:01 -> 3 hours and 0 minutes
        assertEquals(3, secondPeriod.getHours());
        assertEquals(0, secondPeriod.getMinutes());
    }

    public void testGetAccumulateNoParams() {
        // Entries position is sequential
        testDay.addEntry(firstEntry);
        testDay.addEntry(firstExit);
        testDay.addEntry(secondEntry);
        testDay.addEntry(secondExit);

        Period dayAccumulatePeriod = testDay.getAccumulate();

        // From 08:15 to 18:01 -> 8 hours and 46 minutes
        assertEquals(8, dayAccumulatePeriod.getHours());
        assertEquals(46, dayAccumulatePeriod.getMinutes());
    }
}
