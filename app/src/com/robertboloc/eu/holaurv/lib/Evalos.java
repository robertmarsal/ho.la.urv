package com.robertboloc.eu.holaurv.lib;

import java.util.List;

import org.joda.time.DateTime;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.html.HtmlDocument;
import com.gistlabs.mechanize.document.html.HtmlElement;
import com.gistlabs.mechanize.document.html.HtmlElements;
import com.gistlabs.mechanize.document.html.form.Form;
import com.gistlabs.mechanize.document.html.query.HtmlQueryBuilder;

public class Evalos {

	private MechanizeAgent agent;
	private HtmlDocument response;
	private HtmlElements htmlElements;
	private List<HtmlElement> weekActivity;

	private final String webAppBaseUrl = "http://gestiodelapresencia.urv.cat/evalos/login.html";

	private String username;
	private String password;

	public Evalos(String username, String password) {
		this.username = username;
		this.password = password;

		this.agent = new MechanizeAgent();
	}

	public Evalos login() {
        try {
            Document page = agent.get(webAppBaseUrl);

            Form form = page.form("form1");
            form.get("username").set(username);
            form.get("password").set(password);

            response = form.submit();
    		htmlElements = response.htmlElements();

        } catch (Exception e) {
            e.printStackTrace();
        }
 
		return this;
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
	 * Obtains the full name of the user.
	 * 
	 * @return String
	 */
	public String getName() {
		return htmlElements.get(HtmlQueryBuilder.byWidth("306").and.
												 byHeight("19").and.
												 byClass("verdana10bold")).getText().replace("Nom:", "")
												 								    .replace("\n", "")
												 								    .replace("\r", "")
												 									.trim();
	}

	/**
	 * Obtains the internal code assigned to the user.
	 * 
	 * @return String
	 */
	public String getCode() {
		return htmlElements.get(HtmlQueryBuilder.byWidth("143").and.
											     byHeight("19").and.
											     byClass("verdana10bold")).getText().replace("Codi:", "");
	}

	/**
	 * Obtains todays' shift.
	 * 
	 * @return String
	 */
	public String getShift() {
		HtmlElement today = getWeekActivity().get(getCustomIntDayOfTheWeek());
		return today.get(HtmlQueryBuilder.byClass("verdana12").and.
										  byWidth("75").and.
									      byHeight("40")).get(HtmlQueryBuilder.byTag("div")).getInnerHtml();
	}

	/**
	 * Returns the current day of the week shifted with one position.
	 * 
	 * @return int
	 */
	public int getCustomIntDayOfTheWeek()
	{
		DateTime date = org.joda.time.DateTime.now();
		return date.getDayOfWeek() - 1;
	}

	/**
	 * Returns the week activity.
	 * 
	 * @return List<HtmlElement>
	 */
	private List<HtmlElement> getWeekActivity() {
		if(weekActivity != null) {
			return weekActivity;
		} else {

		weekActivity = htmlElements.getAll(HtmlQueryBuilder.byTag("table").and.
												   byWidth("680").and.
												   byHeight("41").and.
												   by("cellspacing", "0").and.
												   by("cellpadding", "0"));
		}

		return weekActivity;
	}
}
