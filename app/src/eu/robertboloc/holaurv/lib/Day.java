package eu.robertboloc.holaurv.lib;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;

public class Day {

    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY = 3;
    public static final int FRIDAY = 4;
    public static final int SATURDAY = 5;
    public static final int SUNDAY = 6;

    String shiftRaw;

    /**
     * List of entries.
     */
    final List<Entry> entries = new ArrayList<Entry>();

    public String getShiftDisplay() {
        return shiftRaw.replace("Festiu", "").replace("G-", "");
    }

    public void setShiftRaw(String shiftRaw) {
        this.shiftRaw = shiftRaw;
    }

    /**
     * Adds an entry.
     * 
     * @param entry
     *            Entry
     */
    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    /**
     * Obtains an entry if it exists.
     * 
     * @param entry
     *            Entry.FIRST_ENTRY | Entry.SECOND_ENTRY | Entry.FIRST_EXIT |
     *            Entry.SECOND_EXIT
     * @return Entry
     */
    public Entry getEntry(int entry) {
        if (!entries.isEmpty() && entry < entries.size()) {
            return entries.get(entry);
        }
        return null;
    }

    /**
     * Calculates the accumulated time between an entry and an exit.
     * 
     * @param entry
     *            Entry.FIRST_ENTRY | Entry.SECOND_ENTRY
     * @param exit
     *            Entry.SECOND_EXIT | Entry.SECOND_EXIT
     * @return Period
     */
    public Period getAccumulate(int entry, int exit) {
        if (getEntry(entry) != null && getEntry(exit) != null) {

            DateTime entryDateTime = new DateTime(2000, 1, 1, getEntry(entry)
                    .getHour(), getEntry(entry).getMinute());
            DateTime exitDateTime = new DateTime(2000, 1, 1, getEntry(exit)
                    .getHour(), getEntry(exit).getMinute());

            return new Period(entryDateTime, exitDateTime);
        }

        return null;
    }

    /**
     * With no arguments returns the daily accumulate.
     * 
     * @return Period
     */
    public Period getAccumulate() {

        Period firstAccumulate = getAccumulate(Entry.FIRST_ENTRY,
                Entry.FIRST_EXIT);

        if (firstAccumulate != null) {
            Period secondAccumulate = getAccumulate(Entry.SECOND_ENTRY,
                    Entry.SECOND_EXIT);

            if (secondAccumulate != null) {
                firstAccumulate.plusHours(secondAccumulate.getHours())
                        .plusMinutes(secondAccumulate.getMinutes());
            }

            return firstAccumulate;
        }

        return null;
    }

    /**
     * Returns the current day of the week in an integer format, shifted by one
     * so that MONDAY is 0.
     * 
     * @return int
     */
    public static int today() {
        DateTime date = DateTime.now();
        return date.getDayOfWeek() - 1;
    }
}
