package com.robertboloc.eu.holaurv.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

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
    private final String password;
    /**
     * While testing replace PLACEHOLDER with value.
     */
    private final String PLACEHOLDER = "";

    private HtmlElements response;

    private final String username;

    /**
     * Evalos public base url.
     */
    private final String WEB_APP_BASE_URL = "http://gestiodelapresencia.urv.cat/evalos/login.html";
    private List<HtmlElement> weekActivity;

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
    // public String getSecondEntry() {
    // if (getDayActivity().size() > 2) {
    // return getDayActivity().get(2).getTime();
    // } else
    // return PLACEHOLDER;
    // }

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
     * Obtains the time of the second entry.
     * 
     * @return String
     */
    // public String getSecondExit() {
    // if (getDayActivity().size() > 3) {
    // return getDayActivity().get(3).getTime();
    // } else
    // return PLACEHOLDER;
    // }

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
    public String getShift() {
        HtmlElement today = getWeekActivity().get(getCustomIntDayOfTheWeek());
        return today
                .get(HtmlQueryBuilder.byClass("verdana12").and.byWidth("75").and
                        .byHeight("40")).get(HtmlQueryBuilder.byTag("div"))
                .getInnerHtml();
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
            response = document.htmlElements();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }
}
