package eu.robertboloc.holaurv.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.html.HtmlDocument;
import com.gistlabs.mechanize.document.html.HtmlElement;
import com.gistlabs.mechanize.document.html.HtmlElements;
import com.gistlabs.mechanize.document.html.form.Form;
import com.gistlabs.mechanize.document.html.query.HtmlQueryBuilder;

public class Evalos {

    private final MechanizeAgent agent;
    private final Map dayActivity = new HashMap<Integer, List<Entry>>();
    private List<HtmlElement> weekActivity;
    private final String[] dailyAccumulates = new String[7];

    private HtmlElements response;

    private String title;
    private final String username;
    private final String password;

    /**
     * Evalos public base url.
     */
    private final String WEB_APP_BASE_URL = "http://gestiodelapresencia.urv.cat/evalos/login.html";
    private final String LOGIN_ERROR = "Error de Login";
    /**
     * While testing replace PLACEHOLDER with value.
     */
    private final String PLACEHOLDER = "";

    public Evalos(String username, String password) {
        this.username = username;
        this.password = password;

        this.agent = new MechanizeAgent();
    }

    /**
     * Obtains the internal code assigned to the user.
     * 
     * @return String
     */
    public String getCode() {
        return response
                .get(HtmlQueryBuilder.byWidth("143").and.byHeight("19").and
                        .byClass("verdana10bold")).getText()
                .replace("Codi:", "");
    }

    /**
     * Returns the current day of the week shifted with one position.
     * 
     * @return int
     */
    public int getCustomIntDayOfTheWeek() {
        DateTime date = org.joda.time.DateTime.now();
        return date.getDayOfWeek() - 1;
    }

    /**
     * Returns the day activity.
     * 
     * @return List<Entry>
     */
    private List<Entry> getDayActivity(int day) {
        if (dayActivity.containsKey(day)) {
            return (List<Entry>) dayActivity.get(day);
        } else {

            HtmlElement today = getWeekActivity().get(day);
            String rawActivity = today
                    .get(HtmlQueryBuilder.byClass("verdana10").and
                            .byWidth("149").and.byHeight("40"))
                    .get(HtmlQueryBuilder.byTag("div")).getInnerHtml();

            String[] fragments = rawActivity.split(" ");

            List<Entry> dayActivity = new ArrayList<Entry>();

            for (int i = 0; i <= (fragments.length - 2); i = i + 2) {

                Entry entryPrototype = new Entry();
                entryPrototype.setTime(fragments[i]);
                entryPrototype.setCode(fragments[i + 1]);

                dayActivity.add(entryPrototype);
            }

            this.dayActivity.put(day, dayActivity);

            return dayActivity;
        }
    }

    /**
     * Obtains the time of the first entry.
     * 
     * @return String
     */
    public String getFirstEntry(int day) {
        List<Entry> dayActivity = getDayActivity(day);
        if (dayActivity.size() > 0) {
            return dayActivity.get(0).getTime();
        } else
            return PLACEHOLDER;
    }

    /**
     * Obtains the code of the first entry.
     * 
     * @return String
     */
    // public String getFirstEntryCode() {
    // List<Entry> dayActivity = getDayActivity(day);
    // if (getDayActivity().size() > 0) {
    // return getDayActivity().get(0).getCode();
    // } else
    // return PLACEHOLDER;
    // }

    /**
     * Obtains the time of the first exit.
     * 
     * @return String
     */
    public String getFirstExit(int day) {
        List<Entry> dayActivity = getDayActivity(day);
        if (dayActivity.size() > 1) {
            return dayActivity.get(1).getTime();
        } else
            return PLACEHOLDER;
    }

    /**
     * Obtains the code of the first exit.
     * 
     * @return String
     */
    // public String getFirstExitCode() {
    // if (getDayActivity().size() > 1) {
    // return getDayActivity().get(1).getCode();
    // } else
    // return PLACEHOLDER;
    // }

    /**
     * Obtains the full name of the user.
     * 
     * @return String
     */
    public String getName() {
        return response
                .get(HtmlQueryBuilder.byWidth("306").and.byHeight("19").and
                        .byClass("verdana10bold")).getText()
                .replace("Nom:", "").replace("\n", "").replace("\r", "").trim();
    }

    public HtmlElements getResponse() {
        return response;
    }

    /**
     * Obtains the time of the second entry.
     * 
     * @return String
     */
    public String getSecondEntry(int day) {
        List<Entry> dayActivity = getDayActivity(day);
        if (dayActivity.size() > 2) {
            return dayActivity.get(2).getTime();
        } else
            return PLACEHOLDER;
    }

    /**
     * Obtains the code of the second entry.
     * 
     * @return String
     */
    // public String getSecondEntryCode() {
    // if (getDayActivity().size() > 2) {
    // return getDayActivity().get(2).getCode();
    // } else
    // return PLACEHOLDER;
    // }

    /**
     * Obtains the time of the second exit.
     * 
     * @return String
     */
    public String getSecondExit(int day) {
        List<Entry> dayActivity = getDayActivity(day);
        if (dayActivity.size() > 3) {
            return dayActivity.get(3).getTime();
        } else
            return PLACEHOLDER;
    }

    /**
     * Obtains the code of the second exit.
     * 
     * @return String
     */
    // public String getSecondExitCode() {
    // if (getDayActivity().size() > 3) {
    // return getDayActivity().get(3).getCode();
    // } else
    // return PLACEHOLDER;
    // }

    /**
     * Obtains todays' shift.
     * 
     * @return String
     */
    public String getShift(int day) {
        HtmlElement today = getWeekActivity().get(day);
        String shift = today
                .get(HtmlQueryBuilder.byClass("verdana12").and.byWidth("75").and
                        .byHeight("40")).get(HtmlQueryBuilder.byTag("div"))
                .getInnerHtml().replaceFirst("G-", "0")
                .replaceFirst("Festiu", "");

        if (shift.isEmpty()) {
            return "";
        }

        return shift;
    }

    /**
     * Obtains the username.
     * 
     * @return String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the week activity.
     * 
     * @return List<HtmlElement>
     */
    private List<HtmlElement> getWeekActivity() {
        if (weekActivity != null) {
            return weekActivity;
        } else {

            weekActivity = response.getAll(HtmlQueryBuilder.byTag("table").and
                    .byWidth("680").and.byHeight("41").and.by("cellspacing",
                    "0").and.by("cellpadding", "0"));
        }

        return weekActivity;
    }

    public Evalos login() {
        try {
            Document page = agent.get(WEB_APP_BASE_URL);

            Form form = page.form("form1");
            form.get("username").set(username);
            form.get("password").set(password);

            HtmlDocument document = form.submit();

            title = document.getTitle();
            response = document.htmlElements();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public boolean loginSuccessful() {

        if (title.equals(this.LOGIN_ERROR)) {
            return false;
        }

        return true;
    }

    public void setDailyAccumulate(int day, String accumulate) {
        this.dailyAccumulates[day] = accumulate;
    }

    public String getDailyAccumulate(int day) {
        if (this.dailyAccumulates[day] != null) {
            return this.dailyAccumulates[day];
        } else {
            if (!getFirstEntry(day).isEmpty()) {

                PeriodFormatter DailyAccumulateFormatter = new PeriodFormatterBuilder()
                        .printZeroAlways().minimumPrintedDigits(2)
                        .appendHours().appendSeparator(":").appendMinutes()
                        .toFormatter();

                Period accumulate = this.computePartialAcumulate(
                        getFirstEntry(day), getFirstExit(day));

                if (!getSecondEntry(day).isEmpty()) {
                    Period secondAccumulate = this.computePartialAcumulate(
                            getSecondEntry(day), getSecondExit(day));

                    accumulate = accumulate.plusHours(
                            secondAccumulate.getHours()).plusMinutes(
                            secondAccumulate.getMinutes());
                }

                return this.dailyAccumulates[day] = DailyAccumulateFormatter
                        .print(accumulate);
            }
        }

        return "-";
    }

    public Period computePartialAcumulate(String entry, String exit) {

        String[] entryTimeList = entry.split(":");
        int entryHour = Integer.parseInt(entryTimeList[0]);
        int entryMinute = Integer.parseInt(entryTimeList[1]);

        String[] exitTimeList = exit.split(":");
        int exitHour = Integer.parseInt(exitTimeList[0]);
        int exitMinute = Integer.parseInt(exitTimeList[1]);

        DateTime entryDateTime = new DateTime(2000, 1, 1, entryHour,
                entryMinute);
        DateTime exitDateTime = new DateTime(2000, 1, 1, exitHour, exitMinute);

        return new Period(entryDateTime, exitDateTime);
    }

    public long computeBalance(String theorical, String real) {

        String[] theoricalTimeList = theorical.split(":");
        // Convert hours and seconds to milis
        int theoricalMilis = (Integer.parseInt(theoricalTimeList[0]) * 3600000)
                + (Integer.parseInt(theoricalTimeList[1]) * 60000);

        String[] realTimeList = real.split(":");
        // Convert hours and seconds to milis
        int realMilis = (Integer.parseInt(realTimeList[0]) * 3600000)
                + (Integer.parseInt(realTimeList[1]) * 60000);

        return (realMilis - theoricalMilis);
    }
}
