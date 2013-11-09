package eu.robertboloc.holaurv.lib;

import java.util.List;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.html.HtmlDocument;
import com.gistlabs.mechanize.document.html.HtmlElement;
import com.gistlabs.mechanize.document.html.HtmlElements;
import com.gistlabs.mechanize.document.html.form.Form;
import com.gistlabs.mechanize.document.html.query.HtmlQueryBuilder;

public class Evalos {

    /**
     * Evalos public base url.
     */
    static final String WEB_APP_BASE_URL = "http://gestiodelapresencia.urv.cat/evalos/login.html";

    /**
     * Title of the error of login page.
     */
    static final String LOGIN_ERROR = "Error de Login";

    /**
     * While testing replace PLACEHOLDER with value.
     */
    static final String PLACEHOLDER = "";

    /**
     * If the login was OK this will be true.
     */
    boolean loginSuccess = false;

    /**
     * If the connection fails this will be true.
     */
    private boolean connectionProblem = false;

    /**
     * Stores the week activity.
     */
    final Week week = new Week();

    /**
     * Username.
     */
    final String username;

    /**
     * Password.
     */
    final String password;

    public Evalos(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Obtains the raw html of the data.
     * 
     * @return Evalos
     */
    public Evalos login() {
        try {
            final MechanizeAgent agent = new MechanizeAgent();
            final Document page = agent.get(WEB_APP_BASE_URL);

            Form form = page.form("form1");
            form.get("username").set(username);
            form.get("password").set(password);

            HtmlDocument document = form.submit();

            parseRawHtmlResponse(document);

        } catch (Exception e) {
            connectionProblem = true;
        }

        return this;
    }

    /**
     * Returns true if login was successful.
     * 
     * @return boolean
     */
    public boolean loginSuccessful() {
        return loginSuccess;
    }

    /**
     * Returns true if there was a connection problem.
     * 
     * @return boolean
     */
    public boolean connectionProblem() {
        return connectionProblem;
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
     * Returns the requested day object. All days will exist (and be empty if
     * the case does not proceed).
     * 
     * @param day
     *            int
     * @return Day
     */
    public Day getDay(int day) {
        return week.getDay(day);
    }

    /**
     * Does the html parsing heavy-lifting.
     * 
     * @param document
     *            HtmlDocument
     * @return boolean
     */
    private boolean parseRawHtmlResponse(HtmlDocument document) {

        // Check if the response was OK.
        if (!document.getTitle().equals(LOGIN_ERROR)) {
            loginSuccess = true;
        } else {
            // Do nothing else.
            return false;
        }

        // We have a correct response, parse it.
        HtmlElements response = document.htmlElements();

        List<HtmlElement> weekHtmlElements = response.getAll(HtmlQueryBuilder
                .byTag("table").and.byWidth("680").and.byHeight("41").and.by(
                "cellspacing", "0").and.by("cellpadding", "0"));

        // Parse the week html and store it as Day objects in the week attribute
        if (!weekHtmlElements.isEmpty()) {
            Day dayPrototype = new Day();
            int dayOfWeek = 0;

            // Iterate over the days until the current day.
            for (HtmlElement dayHtmlElement : weekHtmlElements) {
                // If today is greater than the current iteration do nothing
                // but store an empty day in the week.
                if (dayOfWeek <= Day.today()) {
                    dayPrototype = new Day();

                    // Parse shift data
                    dayPrototype.setShiftRaw(dayHtmlElement
                            .get(HtmlQueryBuilder.byClass("verdana12").and
                                    .byWidth("75").and.byHeight("40"))
                            .get(HtmlQueryBuilder.byTag("div")).getInnerHtml());

                    // Parse entries data
                    String rawActivity = dayHtmlElement
                            .get(HtmlQueryBuilder.byClass("verdana10").and
                                    .byWidth("149").and.byHeight("40"))
                            .get(HtmlQueryBuilder.byTag("div")).getInnerHtml();

                    String[] fragments = rawActivity.split(" ");

                    Entry entryPrototype = new Entry();

                    // Iterate over the entries and add them to the day
                    for (int i = 0; i <= (fragments.length - 2); i = i + 2) {

                        entryPrototype = new Entry();

                        String[] entryTimeList = fragments[i].split(":");
                        entryPrototype.setHourRaw(entryTimeList[0]);
                        entryPrototype.setMinuteRaw(entryTimeList[1]);

                        entryPrototype.setCode(fragments[i + 1]);

                        dayPrototype.addEntry(entryPrototype);
                    }
                }

                week.setDay(dayOfWeek, dayPrototype);
                dayOfWeek++;
            }
        }

        return true;
    }

    public long computeBalance(String theorical, String real) {

        String[] theoricalTimeList = theorical.split(":");
        // Convert hours and minutes to milis
        int theoricalMilis = (Integer.parseInt(theoricalTimeList[0]) * 3600000)
                + (Integer.parseInt(theoricalTimeList[1]) * 60000);

        String[] realTimeList = real.split(":");
        // Convert hours and minutes to milis
        int realMilis = (Integer.parseInt(realTimeList[0]) * 3600000)
                + (Integer.parseInt(realTimeList[1]) * 60000);

        return (realMilis - theoricalMilis);
    }
}
