package eu.robertboloc.holaurv.lib;

public class Entry {

    public static final int FIRST_ENTRY = 0;
    public static final int FIRST_EXIT = 1;
    public static final int SECOND_ENTRY = 2;
    public static final int SECOND_EXIT = 3;

    String code;
    String hourRaw;
    String minuteRaw;

    public String getHourRaw() {
        return hourRaw;
    }

    public void setHourRaw(String hourRaw) {
        this.hourRaw = hourRaw;
    }

    public int getHour() {
        return Integer.parseInt(hourRaw);
    }

    public String getMinuteRaw() {
        return minuteRaw;
    }

    public void setMinuteRaw(String minuteRaw) {
        this.minuteRaw = minuteRaw;
    }

    public int getMinute() {
        return Integer.parseInt(minuteRaw);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayHourAndMinute() {
        if (!hourRaw.isEmpty() && !minuteRaw.isEmpty()) {
            return hourRaw + ":" + minuteRaw;
        }
        return null;
    }
}
