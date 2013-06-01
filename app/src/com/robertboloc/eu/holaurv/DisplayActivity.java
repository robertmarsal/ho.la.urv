package com.robertboloc.eu.holaurv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.robertboloc.eu.holaurv.lib.Evalos;

public class DisplayActivity extends Activity {

	private TextView mName;
	private TextView mShift;
	private TextView mUsername;
	private TextView mCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		mName = (TextView)findViewById(R.id.name);
		mShift = (TextView)findViewById(R.id.shift);
		mCode = (TextView)findViewById(R.id.code);
		mUsername = (TextView)findViewById(R.id.username);
		
		refresh();
	}

	protected void refresh() {
		// Obtain the application state
		HoLaURV appState = ((HoLaURV)getApplicationContext());
		Evalos eva = appState.getEva();

		// Update the name
		mName.setText(eva.getName());

		// Update the shift
		mShift.setText(eva.getShift());

		// Update the username
		mUsername.setText(eva.getUsername());

		// Update the code
		mCode.setText(eva.getCode());
	}

}
