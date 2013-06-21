package com.robertboloc.eu.holaurv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robertboloc.eu.holaurv.lib.Evalos;

public class DayObjectFragment extends Fragment {

	public static final String ARG_OBJECT = "object";

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_collection_object,
				container, false);

		mName = (TextView) rootView.findViewById(R.id.name);
		mShift = (TextView) rootView.findViewById(R.id.shift);
		mCode = (TextView) rootView.findViewById(R.id.code);
		mUsername = (TextView) rootView.findViewById(R.id.username);
		mFirstEntry = (TextView) rootView.findViewById(R.id.firstEntry);
		mFistEntryCode = (TextView) rootView.findViewById(R.id.firstEntryCode);
		mFirstExit = (TextView) rootView.findViewById(R.id.firstExit);
		mFistExitCode = (TextView) rootView.findViewById(R.id.firstExitCode);
		mSecondEntry = (TextView) rootView.findViewById(R.id.secondEntry);
		mSecondEntryCode = (TextView) rootView
				.findViewById(R.id.secondEntryCode);
		mSecondExit = (TextView) rootView.findViewById(R.id.secondExit);
		mSecondExitCode = (TextView) rootView.findViewById(R.id.secondExitCode);
		mFirstAccumulateDesc = (TextView) rootView
				.findViewById(R.id.firstAccumulatedDesc);
		mSecondAccumulateDesc = (TextView) rootView
				.findViewById(R.id.secondAccumulatedDesc);

		refresh();

		return rootView;
	}

	protected void refresh() {
		// Obtain the application state
		HoLaURV appState = ((HoLaURV) getActivity().getApplicationContext());
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