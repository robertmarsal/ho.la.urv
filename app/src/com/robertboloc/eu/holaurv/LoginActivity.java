package com.robertboloc.eu.holaurv;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		final TextView brandTextView = (TextView) findViewById(R.id.brand);
		final Button loginButton = (Button) findViewById(R.id.loginButton);

		// Set brand text
		Spannable wordToSpan = new SpannableString(getText(R.string.brand));
		wordToSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 3, 6,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		brandTextView.setText(wordToSpan);

		// Set logo font
		Typeface font = Typeface.createFromAsset(getAssets(),
				"Exo-ExtraBold.ttf");
		brandTextView.setTypeface(font);

		// Set log in button listener
		loginButton.setOnClickListener(loginButtonListener);

	}

	public OnClickListener loginButtonListener = new View.OnClickListener() {
		public void onClick(View v) {

			final EditText usernameEditText = (EditText) findViewById(R.id.username);
			final EditText passwordEditText = (EditText) findViewById(R.id.password);

			String username = usernameEditText.getText().toString();
			String password = passwordEditText.getText().toString();

			AlertDialog emptyCredentialsAlert = new AlertDialog.Builder(
					v.getContext()).create();

			if (username.isEmpty() || password.isEmpty()) {
				// Missing credentials, just display the alert
				emptyCredentialsAlert
						.setMessage(getText(R.string.alert_empty_credentials));
				emptyCredentialsAlert.show();
			} else {

				// Launch the main activity providing it with the credentials
				Intent i = new Intent(v.getContext(), MainActivity.class);
				i.putExtra("username", username);
				i.putExtra("password", password);
				startActivity(i);
			}
		}
	};
}
