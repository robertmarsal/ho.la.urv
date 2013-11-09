package eu.robertboloc.holaurv.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.ViewById;

import eu.robertboloc.holaurv.HoLaURV;
import eu.robertboloc.holaurv.R;
import eu.robertboloc.holaurv.helpers.Evalos;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    public static final String HOLAURV_PREFS = "holaurvprefs";

    SharedPreferences appSettings;

    @ViewById(R.id.screenLogger)
    TextView mScreenLogger;

    @ViewById(R.id.brand)
    TextView mBrand;

    @ViewById(R.id.username)
    EditText mUsername;

    @ViewById(R.id.password)
    EditText mPassword;

    @SystemService
    ConnectivityManager connMgr;

    @AfterViews
    void styleBrandText() {
        // Set brand text
        Spannable wordToSpan = new SpannableString(getText(R.string.brand));
        wordToSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 3, 6,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBrand.setText(wordToSpan);

        // Set logo font
        Typeface font = Typeface.createFromAsset(getAssets(),
                "Exo-ExtraBold.ttf");
        mBrand.setTypeface(font);
    }

    @AfterViews
    void restorePreferences() {
        appSettings = getSharedPreferences(HOLAURV_PREFS, 0);

        // Restore the last used username
        mUsername.setText(appSettings.getString("username", ""));
    }

    private class EvalosLoginTask extends AsyncTask<String, Void, Evalos> {

        @Override
        protected Evalos doInBackground(String... credentials) {
            return new Evalos(credentials[0], credentials[1]).login();
        }

        @Override
        protected void onPostExecute(Evalos result) {
            if (result.loginSuccessful()) {

                // Store the 'eva' in the application context
                HoLaURV appState = ((HoLaURV) getApplicationContext());
                appState.setEva(result);

                // Store the username if it has changed
                if (!(appSettings.getString("username", "").equals(result
                        .getUsername()))) {
                    SharedPreferences.Editor prefEditor = appSettings.edit();
                    prefEditor.putString("username", result.getUsername());
                    prefEditor.commit();
                }

                // Clear the screen logger
                mScreenLogger.setText("");

                // Launch the main activity
                startActivity(new Intent(LoginActivity.this,
                        DisplayActivity.class));
            } else {

                if (result.connectionProblem()) {
                    // There is a problem with the connection.
                    mScreenLogger
                            .setText(getText(R.string.alert_connection_problem));
                } else {
                    // The credentials are wrong.
                    mScreenLogger
                            .setText(getText(R.string.alert_wrong_credentials));
                }
            }
        }
    }

    public void loginClickHandler(View view) {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        // Check for empty credentials
        if (username.isEmpty() || password.isEmpty()) {
            mScreenLogger.setText(getText(R.string.alert_empty_credentials));
            return;
        }

        // Check for network connection
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Inform of the start of the loading action
            mScreenLogger.setText(getText(R.string.info_loading_data));
            new EvalosLoginTask().execute(username, password);
        } else {
            mScreenLogger.setText(getText(R.string.alert_no_network));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }
}
