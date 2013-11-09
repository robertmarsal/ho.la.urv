package eu.robertboloc.holaurv.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

import eu.robertboloc.holaurv.R;

@EActivity(R.layout.activity_about)
public class AboutActivity extends Activity {

    @ViewById(R.id.aboutBrand)
    TextView mAboutBrand;

    @AfterViews
    void styleBrand() {
        // Set brand text
        Spannable wordToSpan = new SpannableString(getText(R.string.brand));
        wordToSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 3, 6,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mAboutBrand.setText(wordToSpan);

        // Set brand font
        mAboutBrand.setTypeface(Typeface.createFromAsset(getAssets(),
                "Exo-ExtraBold.ttf"));
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
