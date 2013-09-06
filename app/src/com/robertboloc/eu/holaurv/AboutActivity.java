package com.robertboloc.eu.holaurv;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class AboutActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView mAboutBrand = (TextView) findViewById(R.id.aboutBrand);

        // Set brand text
        Spannable wordToSpan = new SpannableString(getText(R.string.brand));
        wordToSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 3, 6,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mAboutBrand.setText(wordToSpan);

        // Set logo font
        Typeface font = Typeface.createFromAsset(getAssets(),
                "Exo-ExtraBold.ttf");
        mAboutBrand.setTypeface(font);
    }
}
