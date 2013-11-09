package eu.robertboloc.holaurv.test.models;

import junit.framework.TestCase;

import org.joda.time.Period;

import eu.robertboloc.holaurv.models.Day;
import eu.robertboloc.holaurv.models.Entry;

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
        firstEntry.setHourRaw("07");
        firstEntry.setMinuteRaw("52");

        // Set up first exit
        firstExit.setHourRaw("14");
        firstExit.setMinuteRaw("28");

        // Set up second entry
        secondEntry.setHourRaw("14");
        secondEntry.setMinuteRaw("56");

        // Set up second exit
        secondExit.setHourRaw("17");
        secondExit.setMinuteRaw("40");
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

        // From 07:52 to 14:28 -> 6 hours and 36 minutes
        assertEquals(6, firstPeriod.getHours());
        assertEquals(36, firstPeriod.getMinutes());
    }

    public void testGetAccumulateWithParamsSecond() {
        // Entries position is sequential
        testDay.addEntry(firstEntry);
        testDay.addEntry(firstExit);
        testDay.addEntry(secondEntry);
        testDay.addEntry(secondExit);

        Period secondPeriod = testDay.getAccumulate(Entry.SECOND_ENTRY,
                Entry.SECOND_EXIT);

        // From 14:56 to 17:40 -> 2 hours and 44 minutes
        assertEquals(2, secondPeriod.getHours());
        assertEquals(44, secondPeriod.getMinutes());
    }

    public void testGetAccumulateNoParams() {
        // Entries position is sequential
        testDay.addEntry(firstEntry);
        testDay.addEntry(firstExit);
        testDay.addEntry(secondEntry);
        testDay.addEntry(secondExit);

        Period dayAccumulatePeriod = testDay.getAccumulate();

        // From 07:52 to 17:40 -> 9 hours and 20 minutes
        assertEquals(9, dayAccumulatePeriod.getHours());
        assertEquals(20, dayAccumulatePeriod.getMinutes());
    }
}
