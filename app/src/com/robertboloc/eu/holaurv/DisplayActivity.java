package com.robertboloc.eu.holaurv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.robertboloc.eu.holaurv.lib.Evalos;

public class DisplayActivity extends Activity {

	private TextView mCode;
	private TextView mFirstAccumulateDesc;
	private TextView mFirstEntry;
	private TextView mFirstExit;
	private TextView mFistEntryCode;
	private TextView mFistExitCode;
	private TextView mName;
	private TextView mSecondAccumulateDesc;
	private TextView mSecondEntry;
	private TextView mSecondEntryCode;
	private TextView mSecondExit;
	private TextView mSecondExitCode;
	private TextView mShift;
	private TextView mUsername;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);

		mName = (TextView) findViewById(R.id.name);
		mShift = (TextView) findViewById(R.id.shift);
		mCode = (TextView) findViewById(R.id.code);
		mUsername = (TextView) findViewById(R.id.username);
		mFirstEntry = (TextView) findViewById(R.id.firstEntry);
		mFistEntryCode = (TextView) findViewById(R.id.firstEntryCode);
		mFirstExit = (TextView) findViewById(R.id.firstExit);
		mFistExitCode = (TextView) findViewById(R.id.firstExitCode);
		mSecondEntry = (TextView) findViewById(R.id.secondEntry);
		mSecondEntryCode = (TextView) findViewById(R.id.secondEntryCode);
		mSecondExit = (TextView) findViewById(R.id.secondExit);
		mSecondExitCode = (TextView) findViewById(R.id.secondExitCode);
		mFirstAccumulateDesc = (TextView) findViewById(R.id.firstAccumulatedDesc);
		mSecondAccumulateDesc = (TextView) findViewById(R.id.secondAccumulatedDesc);

		refresh();
	}

	protected void refresh() {
		// Obtain the application state
		HoLaURV appState = ((HoLaURV) getApplicationContext());
		Evalos eva = appState.getEva();

		// Update the name
		mName.setText(eva.getName());

		// Update the shift
		mShift.setText(eva.getShift());

		// Update the username
		mUsername.setText(eva.getUsername());

		// Update the code
		mCode.setText(eva.getCode());

		// First Entry
		mFistEntryCode.setText(eva.getFirstEntryCode());
		mFirstEntry.setText(eva.getFirstEntry());

		// First Exit
		String firstExitCode = eva.getFirstExitCode();
		if (!firstExitCode.isEmpty()) {
			mFistExitCode.setText(firstExitCode);
			mFirstExit.setText(eva.getFirstExit());
			mFirstAccumulateDesc.setText(getText(R.string.accumulated));
		}

		// Second Entry
		mSecondEntryCode.setText(eva.getSecondEntryCode());
		mSecondEntry.setText(eva.getSecondEntry());

		// Second Exit
		String secondExitCode = eva.getSecondExitCode();
		if (!secondExitCode.isEmpty()) {
			mSecondExitCode.setText(secondExitCode);
			mSecondExit.setText(eva.getSecondExit());
			mSecondAccumulateDesc.setText(getText(R.string.accumulated));
		}
	}

}
