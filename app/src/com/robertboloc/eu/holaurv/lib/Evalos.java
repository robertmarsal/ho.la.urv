package com.robertboloc.eu.holaurv.lib;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.html.HtmlDocument;
import com.gistlabs.mechanize.document.html.HtmlElements;
import com.gistlabs.mechanize.document.html.form.Form;
import com.gistlabs.mechanize.document.html.query.HtmlQueryBuilder;

public class Evalos {

	private MechanizeAgent agent;
	private HtmlDocument response;
	private HtmlElements htmlElements;

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

	public String getName() {
		return htmlElements.get(HtmlQueryBuilder.byWidth("306").and.byHeight("19")).getText().replace("Nom:", "");
	}
}
