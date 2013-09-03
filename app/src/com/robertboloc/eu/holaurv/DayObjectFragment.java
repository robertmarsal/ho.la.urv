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

    private TextView mFirstAccumulateDesc;
    private TextView mFirstEntry;
    private TextView mFirstExit;
    private TextView mSecondAccumulateDesc;
    private TextView mSecondEntry;
    private TextView mSecondExit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_collection_object,
                container, false);

        mFirstEntry = (TextView) rootView.findViewById(R.id.firstEntry);
        mFirstExit = (TextView) rootView.findViewById(R.id.firstExit);
        mSecondEntry = (TextView) rootView.findViewById(R.id.secondEntry);
        mSecondExit = (TextView) rootView.findViewById(R.id.secondExit);
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

        // First Entry
        String firstEntry = eva.getFirstEntry();
        String[] firstEntryTimeList = firstEntry.split(":");
        int firstEntryHour = Integer.parseInt(firstEntryTimeList[0]);
        int firstEntryMinute = Integer.parseInt(firstEntryTimeList[1]);
        mFirstEntry.setText(firstEntry);

        // First Exit
        String firstExit = eva.getFirstExit();
        String[] firstExitTimeList = firstExit.split(":");
        int firstExitHour = Integer.parseInt(firstExitTimeList[0]);
        int firstExitMinute = Integer.parseInt(firstExitTimeList[1]);
        mFirstExit.setText(firstExit);

        // Compute the first accumulated
        // DateTime firstEntryDateTime = new DateTime(2000, 1, 1,
        // firstEntryHour,
        // firstEntryMinute);
        // DateTime firstAccumulated = firstEntryDateTime.plusHours(
        // firstExitHour - firstEntryHour - 1).plusMinutes(
        // firstEntryMinute + firstExitMinute);
        // Period period = new Period(firstEntryDateTime, firstAccumulated);
        // PeriodFormatter HHMMSSFormater = new PeriodFormatterBuilder()
        // .printZeroAlways().minimumPrintedDigits(2).appendHours()
        // .appendSeparator(":").appendMinutes().toFormatter();
        //
        // String test = HHMMSSFormater.print(period);

        // Second Entry
        mSecondEntry.setText(eva.getSecondEntry());

        // Second Exit
        mSecondExit.setText(eva.getSecondExit());
    }
}