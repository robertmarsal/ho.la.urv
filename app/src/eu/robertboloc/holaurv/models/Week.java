package eu.robertboloc.holaurv.models;


public class Week {

    final Day[] days = new Day[7];

    public void setDay(int dayOfWeek, Day day) {
        days[dayOfWeek] = day;
    }

    public Day getDay(int dayOfWeek) {
        return days[dayOfWeek];
    }
}
