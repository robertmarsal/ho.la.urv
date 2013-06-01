package com.robertboloc.eu.holaurv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.robertboloc.eu.holaurv.lib.Evalos;

public class DisplayActivity extends Activity {

	private TextView mName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		mName = (TextView)findViewById(R.id.name);
		
		refresh();
	}

	protected void refresh() {
		// Obtain the application state
		HoLaURV appState = ((HoLaURV)getApplicationContext());
		Evalos eva = appState.getEva();
		
		// Update the name
		mName.setText(eva.getName());
	}

}
