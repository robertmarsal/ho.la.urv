package eu.robertboloc.holaurv;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;

import eu.robertboloc.holaurv.lib.Day;
import eu.robertboloc.holaurv.lib.Evalos;
import eu.robertboloc.holaurv.lib.TypefaceSpan;

public class ReportActivity extends SherlockFragmentActivity {

    TextView mReportTableLegend;

    TextView mReportTableMondayTheoretical;
    TextView mReportTableMondayReal;
    TextView mReportTableMondayBalance;

    TextView mReportTableTuesdayTheoretical;
    TextView mReportTableTuesdayReal;
    TextView mReportTableTuesdayBalance;

    TextView mReportTableWednesdayTheoretical;
    TextView mReportTableWednesdayReal;
    TextView mReportTableWednesdayBalance;

    TextView mReportTableThursdayTheoretical;
    TextView mReportTableThursdayReal;
    TextView mReportTableThursdayBalance;

    TextView mReportTableFridayTheoretical;
    TextView mReportTableFridayReal;
    TextView mReportTableFridayBalance;

    TextView mReportTableSaturdayTheoretical;
    TextView mReportTableSaturdayReal;
    TextView mReportTableSaturdayBalance;

    TextView mReportTableSundayTheoretical;
    TextView mReportTableSundayReal;
    TextView mReportTableSundayBalance;

    ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Set custom font and colors to the Action Bar
        SpannableString s = new SpannableString(
                getText(R.string.menu_item_report));
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        s.setSpan(new TypefaceSpan(this, "Exo-ExtraBold"), 0, s.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        actionBar.setTitle(s);

        // Obtain the views
        mReportTableLegend = (TextView) findViewById(R.id.reportTableLegend);

        mReportTableMondayTheoretical = (TextView) findViewById(R.id.reportTableMondayTheoretical);
        mReportTableMondayReal = (TextView) findViewById(R.id.reportTableMondayReal);
        mReportTableMondayBalance = (TextView) findViewById(R.id.reportTableMondayBalance);

        mReportTableTuesdayTheoretical = (TextView) findViewById(R.id.reportTableTuesdayTheoretical);
        mReportTableTuesdayReal = (TextView) findViewById(R.id.reportTableTuesdayReal);
        mReportTableTuesdayBalance = (TextView) findViewById(R.id.reportTableTuesdayBalance);

        mReportTableWednesdayTheoretical = (TextView) findViewById(R.id.reportTableWednesdayTheoretical);
        mReportTableWednesdayReal = (TextView) findViewById(R.id.reportTableWednesdayReal);
        mReportTableWednesdayBalance = (TextView) findViewById(R.id.reportTableWednesdayBalance);

        mReportTableThursdayTheoretical = (TextView) findViewById(R.id.reportTableThursdayTheoretical);
        mReportTableThursdayReal = (TextView) findViewById(R.id.reportTableThursdayReal);
        mReportTableThursdayBalance = (TextView) findViewById(R.id.reportTableThursdayBalance);

        mReportTableFridayTheoretical = (TextView) findViewById(R.id.reportTableFridayTheoretical);
        mReportTableFridayReal = (TextView) findViewById(R.id.reportTableFridayReal);
        mReportTableFridayBalance = (TextView) findViewById(R.id.reportTableFridayBalance);

        mReportTableSaturdayTheoretical = (TextView) findViewById(R.id.reportTableSaturdayTheoretical);
        mReportTableSaturdayReal = (TextView) findViewById(R.id.reportTableSaturdayReal);
        mReportTableSaturdayBalance = (TextView) findViewById(R.id.reportTableSaturdayBalance);

        mReportTableSundayTheoretical = (TextView) findViewById(R.id.reportTableSundayTheoretical);
        mReportTableSundayReal = (TextView) findViewById(R.id.reportTableSundayReal);
        mReportTableSundayBalance = (TextView) findViewById(R.id.reportTableSundayBalance);

        // Set the report legend
        // If today is SUNDAY then offset all by 7 as Evalos changes the week on
        // sundays
        DateTime now = DateTime.now();
        DateTime intervalStart = (Day.today() == Day.SUNDAY) ? now.plusDays(1)
                : now.minusDays(Day.today());
        DateTime intervalEnd = intervalStart.plusDays(6);

        DateTimeFormatter reportIntervalDisplayer = new DateTimeFormatterBuilder()
                .appendDayOfMonth(2).appendLiteral("/").appendMonthOfYear(2)
                .appendLiteral("/").appendYear(4, 4).toFormatter();

        mReportTableLegend.setText(reportIntervalDisplayer.print(intervalStart)
                + " - " + reportIntervalDisplayer.print(intervalEnd));

        // Obtain the application state
        HoLaURV appState = ((HoLaURV) getApplicationContext());
        Evalos eva = appState.getEva();

        // In case the app was killed by the OS
        if (!(eva instanceof Evalos)) {
            Intent intent = new Intent(this, LoginActivity_.class);
            startActivity(intent);
            this.finish();
        } else {
            refresh(eva);
        }
    }

    private void refresh(Evalos eva) {
        PeriodFormatter HHMMFormater = new PeriodFormatterBuilder()
                .printZeroAlways().minimumPrintedDigits(2).appendHours()
                .appendSeparator(":").appendMinutes().toFormatter();

        long accumulatedBalance = 0;

        // Fill Monday
        mReportTableMondayTheoretical.setText(eva.getDay(Day.MONDAY)
                .getShiftDisplay());
        if (Day.today() >= Day.MONDAY) {
            Period mondayReal = eva.getDay(Day.MONDAY).getAccumulate();
            if (mondayReal != null) {
                mReportTableMondayReal.setText(HHMMFormater.print(mondayReal));

                accumulatedBalance += eva.computeBalance(eva.getDay(Day.MONDAY)
                        .getShiftDisplay(), HHMMFormater.print(mondayReal));
                mReportTableMondayBalance
                        .setText(milisToDisplayTime(accumulatedBalance));
            }
        }

        // Fill Tuesday
        mReportTableTuesdayTheoretical.setText(eva.getDay(Day.TUESDAY)
                .getShiftDisplay());
        if (Day.today() >= Day.TUESDAY) {
            Period tuesdayReal = eva.getDay(Day.TUESDAY).getAccumulate();
            if (tuesdayReal != null) {
                mReportTableTuesdayReal
                        .setText(HHMMFormater.print(tuesdayReal));

                accumulatedBalance += eva.computeBalance(eva
                        .getDay(Day.TUESDAY).getShiftDisplay(), HHMMFormater
                        .print(tuesdayReal));
                mReportTableTuesdayBalance
                        .setText(milisToDisplayTime(accumulatedBalance));
            }
        }

        // Fill Wednesday
        mReportTableWednesdayTheoretical.setText(eva.getDay(Day.WEDNESDAY)
                .getShiftDisplay());
        if (Day.today() >= Day.WEDNESDAY) {
            Period wednesdayReal = eva.getDay(Day.WEDNESDAY).getAccumulate();
            if (wednesdayReal != null) {
                mReportTableWednesdayReal.setText(HHMMFormater
                        .print(wednesdayReal));

                accumulatedBalance += eva.computeBalance(
                        eva.getDay(Day.WEDNESDAY).getShiftDisplay(),
                        HHMMFormater.print(wednesdayReal));
                mReportTableWednesdayBalance
                        .setText(milisToDisplayTime(accumulatedBalance));
            }

        }

        // Fill Thursday
        mReportTableThursdayTheoretical.setText(eva.getDay(Day.THURSDAY)
                .getShiftDisplay());
        if (Day.today() >= Day.THURSDAY) {
            Period thursdayReal = eva.getDay(Day.THURSDAY).getAccumulate();
            if (thursdayReal != null) {
                mReportTableThursdayReal.setText(HHMMFormater
                        .print(thursdayReal));

                accumulatedBalance += eva.computeBalance(
                        eva.getDay(Day.THURSDAY).getShiftDisplay(),
                        HHMMFormater.print(thursdayReal));
                mReportTableThursdayBalance
                        .setText(milisToDisplayTime(accumulatedBalance));
            }
        }

        // Fill Friday
        mReportTableFridayTheoretical.setText(eva.getDay(Day.FRIDAY)
                .getShiftDisplay());
        if (Day.today() >= Day.FRIDAY) {
            Period fridayReal = eva.getDay(Day.FRIDAY).getAccumulate();
            if (fridayReal != null) {
                mReportTableFridayReal.setText(HHMMFormater.print(fridayReal));

                accumulatedBalance += eva.computeBalance(eva.getDay(Day.FRIDAY)
                        .getShiftDisplay(), HHMMFormater.print(fridayReal));
                mReportTableFridayBalance
                        .setText(milisToDisplayTime(accumulatedBalance));
            }
        }

        // Fill Saturday
        mReportTableSaturdayTheoretical.setText(eva.getDay(Day.SATURDAY)
                .getShiftDisplay());
        if (Day.today() >= Day.SATURDAY) {
            Period saturdayReal = eva.getDay(Day.SATURDAY).getAccumulate();
            if (saturdayReal != null) {
                mReportTableSaturdayReal.setText(HHMMFormater
                        .print(saturdayReal));

                accumulatedBalance += eva.computeBalance(
                        eva.getDay(Day.SATURDAY).getShiftDisplay(),
                        HHMMFormater.print(saturdayReal));
                mReportTableSaturdayBalance
                        .setText(milisToDisplayTime(accumulatedBalance));
            }
        }

        // Fill Sunday
        mReportTableSundayTheoretical.setText(eva.getDay(Day.SUNDAY)
                .getShiftDisplay());
        if (Day.today() >= Day.SUNDAY) {
            Period sundayReal = eva.getDay(Day.SUNDAY).getAccumulate();
            if (sundayReal != null) {
                mReportTableSundayReal.setText(HHMMFormater.print(sundayReal));

                accumulatedBalance += eva.computeBalance(eva.getDay(Day.SUNDAY)
                        .getShiftDisplay(), HHMMFormater.print(sundayReal));
                mReportTableSundayBalance
                        .setText(milisToDisplayTime(accumulatedBalance));
            }
        }
    }

    public String milisToDisplayTime(long milis) {
        Duration duration = new Duration(milis);
        String hours = String.valueOf(Math.abs(duration.getStandardHours()));

        // Now remove the value of the hours from the duration
        duration = duration.minus(duration.getStandardHours() * 3600000);

        String minutes = String
                .valueOf(Math.abs(duration.getStandardMinutes()));

        if (hours.length() == 1) {
            hours = "0" + hours;
        }

        if (minutes.length() == 1) {
            minutes = "0" + minutes;
        }

        return milis < 0 ? "-" + hours + ":" + minutes : hours + ":" + minutes;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
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
