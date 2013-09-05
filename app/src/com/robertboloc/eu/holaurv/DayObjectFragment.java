package com.robertboloc.eu.holaurv;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.robertboloc.eu.holaurv.lib.Evalos;

public class DayObjectFragment extends Fragment {

    public static final String ARG_OBJECT = "object";

    private TextView mFirstAccumulate;
    private TextView mFirstEntry;
    private TextView mFirstExit;
    private TextView mSecondAccumulate;
    private TextView mSecondEntry;
    private TextView mSecondExit;
    private LinearLayout mDisplay;

    private LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        this.mInflater = inflater;

        View rootView = inflater.inflate(R.layout.fragment_collection_object,
                container, false);

        mDisplay = (LinearLayout) rootView.findViewById(R.id.display);

        Bundle args = getArguments();

        refresh(args.getInt(ARG_OBJECT));

        return rootView;
    }

    protected void refresh(int day) {
        // Obtain the application state
        HoLaURV appState = ((HoLaURV) getActivity().getApplicationContext());
        Evalos eva = appState.getEva();

        // First Entry
        String firstEntry = eva.getFirstEntry(day);
        if (!firstEntry.isEmpty()) {
            mDisplay.addView(mInflater.inflate(R.layout.first_entry, mDisplay,
                    false));
            mFirstEntry = (TextView) mDisplay.findViewById(R.id.firstEntry);
            mFirstEntry.setText(firstEntry);
        }

        // First Exit
        String firstExit = eva.getFirstExit(day);
        if (!firstExit.isEmpty()) {
            mDisplay.addView(mInflater.inflate(R.layout.first_exit, mDisplay,
                    false));
            mFirstExit = (TextView) mDisplay.findViewById(R.id.firstExit);
            mFirstExit.setText(firstExit);

            // Compute the first accumulated
            String[] firstEntryTimeList = firstEntry.split(":");
            int firstEntryHour = Integer.parseInt(firstEntryTimeList[0]);
            int firstEntryMinute = Integer.parseInt(firstEntryTimeList[1]);

            String[] firstExitTimeList = firstExit.split(":");
            int firstExitHour = Integer.parseInt(firstExitTimeList[0]);
            int firstExitMinute = Integer.parseInt(firstExitTimeList[1]);

            DateTime firstEntryDateTime = new DateTime(2000, 1, 1,
                    firstEntryHour, firstEntryMinute);
            DateTime firstExitDateTime = new DateTime(2000, 1, 1,
                    firstExitHour, firstExitMinute);

            Period period = new Period(firstEntryDateTime, firstExitDateTime);
            PeriodFormatter HHMMSSFormater = new PeriodFormatterBuilder()
                    .printZeroAlways().minimumPrintedDigits(2).appendHours()
                    .appendSeparator("h").appendMinutes().appendLiteral("m")
                    .toFormatter();

            mFirstAccumulate = (TextView) mDisplay
                    .findViewById(R.id.firstAccumulated);

            mFirstAccumulate.setText(HHMMSSFormater.print(period));
        }

        //
        // // Second Entry
        // mSecondEntry.setText(eva.getSecondEntry());
        //
        // // Second Exit
        // mSecondExit.setText(eva.getSecondExit());
    }
}