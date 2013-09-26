package eu.robertboloc.holaurv;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import eu.robertboloc.holaurv.lib.Evalos;

public class DayObjectFragment extends Fragment {

    public static final String ARG_OBJECT = "object";

    private TextView mFirstEntry;
    private TextView mFirstExit;
    private TextView mSecondEntry;
    private TextView mSecondExit;
    private TextView mFirstAccumulate;
    private TextView mSecondAccumulate;

    private LinearLayout mDisplay;

    private LayoutInflater mInflater;

    private AdView adView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        this.mInflater = inflater;

        View rootView = inflater.inflate(R.layout.fragment_collection_object,
                container, false);

        mDisplay = (LinearLayout) rootView.findViewById(R.id.display);
        adView = (AdView) rootView.findViewById(R.id.adView);

        Bundle args = getArguments();

        // Obtain the application state
        HoLaURV appState = ((HoLaURV) getActivity().getApplicationContext());
        Evalos eva = appState.getEva();

        // In case the app was killed by the OS
        if (!(eva instanceof Evalos)) {
            Intent intent = new Intent(getActivity(), LoginActivity_.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            refresh(eva, args.getInt(ARG_OBJECT));
        }

        return rootView;
    }

    protected void refresh(Evalos eva, int day) {

        Period accumulate = null;

        // Period formatter instance
        PeriodFormatter HHMMSSFormater = new PeriodFormatterBuilder()
                .printZeroAlways().minimumPrintedDigits(2).appendHours()
                .appendSeparator("h").appendMinutes().appendLiteral("m")
                .toFormatter();

        // Accumulate formatter instance
        PeriodFormatter DailyAccumulateFormatter = new PeriodFormatterBuilder()
                .printZeroAlways().minimumPrintedDigits(2).appendHours()
                .appendSeparator(":").appendMinutes().toFormatter();

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
        if (!firstEntry.isEmpty() && !firstExit.isEmpty()) {
            mDisplay.addView(mInflater.inflate(R.layout.first_exit, mDisplay,
                    false));
            mFirstExit = (TextView) mDisplay.findViewById(R.id.firstExit);
            mFirstExit.setText(firstExit);

            // Compute the first accumulated
            accumulate = eva.computePartialAcumulate(firstEntry, firstExit);

            mFirstAccumulate = (TextView) mDisplay
                    .findViewById(R.id.firstAccumulated);
            mFirstAccumulate.setText(HHMMSSFormater.print(accumulate));

            // Store the accumulate in case there is no other entry/exit
            eva.setDailyAccumulate(day,
                    DailyAccumulateFormatter.print(accumulate));
        }

        // Second Entry
        String secondEntry = eva.getSecondEntry(day);
        if (!firstEntry.isEmpty() && !firstExit.isEmpty()
                && !secondEntry.isEmpty()) {
            mDisplay.addView(mInflater.inflate(R.layout.second_entry, mDisplay,
                    false));
            mSecondEntry = (TextView) mDisplay.findViewById(R.id.secondEntry);
            mSecondEntry.setText(secondEntry);
        }

        // Second Exit
        String secondExit = eva.getSecondExit(day);
        if (!firstEntry.isEmpty() && !firstExit.isEmpty()
                && !secondEntry.isEmpty() && !secondExit.isEmpty()) {
            mDisplay.addView(mInflater.inflate(R.layout.second_exit, mDisplay,
                    false));
            mSecondExit = (TextView) mDisplay.findViewById(R.id.secondExit);
            mSecondExit.setText(secondExit);

            // Compute the second accumulated
            Period secondAccumulate = eva.computePartialAcumulate(secondEntry,
                    secondExit);

            accumulate = accumulate.plusHours(secondAccumulate.getHours())
                    .plusMinutes(secondAccumulate.getMinutes()).toPeriod();

            mSecondAccumulate = (TextView) mDisplay
                    .findViewById(R.id.secondAccumulate);

            mSecondAccumulate.setText(HHMMSSFormater.print(accumulate));

            // Replace the store daily accumulate as we have a second entry/exit
            eva.setDailyAccumulate(day,
                    DailyAccumulateFormatter.print(accumulate));
        }

        // Insert the add
        adView.loadAd(new AdRequest());
    }
}