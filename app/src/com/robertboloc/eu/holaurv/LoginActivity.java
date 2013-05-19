package com.robertboloc.eu.holaurv;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.widget.TextView;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); 

        // Set brand text
        TextView brandTextView = (TextView)findViewById(R.id.brand);
        Spannable wordToSpan = new SpannableString(getText(R.string.brand));
        wordToSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 3, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        brandTextView.setText(wordToSpan);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
}
