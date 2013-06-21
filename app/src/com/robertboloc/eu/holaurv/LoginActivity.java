package com.robertboloc.eu.holaurv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.robertboloc.eu.holaurv.lib.Evalos;

@EActivity
public class LoginActivity extends Activity {

	@ViewById
	TextView screenLogger;

	@ViewById(R.id.brand)
	TextView brandTextView;

	private class EvalosLoginTask extends AsyncTask<String, Void, Evalos> {

		@Override
		protected Evalos doInBackground(String... credentials) {
			return new Evalos(credentials[0], credentials[1]).login();
		}

		@Override
		protected void onPostExecute(Evalos result) {
			// Store the 'eva' in the application context
			HoLaURV appState = ((HoLaURV) getApplicationContext());
			appState.setEva(result);
			// Launch the main activity
			startActivity(new Intent(LoginActivity.this, DisplayActivity.class));
		}
	}

	public void loginClickHandler(View view) {
		// Obtain user credentials from the views
		final EditText usernameEditText = (EditText) findViewById(R.id.username);
		final EditText passwordEditText = (EditText) findViewById(R.id.password);

		String username = usernameEditText.getText().toString();
		String password = passwordEditText.getText().toString();

		// Check for empty credentials
		if (username.isEmpty() || password.isEmpty()) {
			screenLogger.setText(getText(R.string.alert_empty_credentials));
			return;
		}

		// Check for network connection
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			new EvalosLoginTask().execute(username, password);
		} else {
			screenLogger.setText(getText(R.string.alert_no_network));
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Set brand text
		Spannable wordToSpan = new SpannableString(getText(R.string.brand));
		wordToSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 3, 6,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		brandTextView.setText(wordToSpan);

		// Set logo font
		Typeface font = Typeface.createFromAsset(getAssets(),
				"Exo-ExtraBold.ttf");
		brandTextView.setTypeface(font);
	}
}
