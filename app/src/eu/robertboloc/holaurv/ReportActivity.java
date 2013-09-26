package eu.robertboloc.holaurv;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

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

import eu.robertboloc.holaurv.lib.Evalos;
import eu.robertboloc.holaurv.lib.TypefaceSpan;

public class ReportActivity extends SherlockFragmentActivity {

    private TextView mReportTableLegend;

    private TextView mReportTableMondayTheoretical;
    private TextView mReportTableMondayReal;
    private TextView mReportTableMondayBalance;

    private TextView mReportTableTuesdayTheoretical;
    private TextView mReportTableTuesdayReal;
    private TextView mReportTableTuesdayBalance;

    private TextView mReportTableWednesdayTheoretical;
    private TextView mReportTableWednesdayReal;
    private TextView mReportTableWednesdayBalance;

    private TextView mReportTableThursdayTheoretical;
    private TextView mReportTableThursdayReal;
    private TextView mReportTableThursdayBalance;

    private TextView mReportTableFridayTheoretical;
    private TextView mReportTableFridayReal;
    private TextView mReportTableFridayBalance;

    private TextView mReportTableSaturdayTheoretical;
    private TextView mReportTableSaturdayReal;
    private TextView mReportTableSaturdayBalance;

    private TextView mReportTableSundayTheoretical;
    private TextView mReportTableSundayReal;
    private TextView mReportTableSundayBalance;

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
        DateTime now = org.joda.time.DateTime.now();
        DateTime intervalStart = now.minusDays(now.getDayOfWeek() - 1);
        DateTime intervalEnd = intervalStart.plusDays(7);

        DateTimeFormatter reportIntervalDisplayer = new DateTimeFormatterBuilder()
                .appendDayOfMonth(2).appendLiteral("/").appendMonthOfYear(2)
                .appendLiteral("/").appendYear(4, 4).toFormatter();

        mReportTableLegend.setText(reportIntervalDisplayer.print(intervalStart)
                + " - " + reportIntervalDisplayer.print(intervalEnd));

        // Current day of the week
        int currentDayOfTheWeek = (now.getDayOfWeek() - 1);

        // Obtain the application state
        HoLaURV appState = ((HoLaURV) getApplicationContext());
        Evalos eva = appState.getEva();

        String reportContent[] = new String[7];
        long accumulatedBalance = 0;

        // Fill Monday
        reportContent[0] = eva.getShift(0);
        mReportTableMondayTheoretical.setText(reportContent[0]);
        if (currentDayOfTheWeek >= 0 && !reportContent[0].isEmpty()) {
            mReportTableMondayReal.setText(eva.getDailyAccumulate(0));
            accumulatedBalance += eva.computeBalance(reportContent[0],
                    eva.getDailyAccumulate(0));
            mReportTableMondayBalance.setText(this
                    .milisToDisplayTime(accumulatedBalance));
        }

        // Fill Tuesday
        reportContent[1] = eva.getShift(1);
        mReportTableTuesdayTheoretical.setText(reportContent[1]);
        if (currentDayOfTheWeek >= 1 && !reportContent[1].isEmpty()) {
            mReportTableTuesdayReal.setText(eva.getDailyAccumulate(1));
            accumulatedBalance += eva.computeBalance(reportContent[1],
                    eva.getDailyAccumulate(1));
            mReportTableTuesdayBalance.setText(this
                    .milisToDisplayTime(accumulatedBalance));
        }

        // Fill Wednesday
        reportContent[2] = eva.getShift(2);
        mReportTableWednesdayTheoretical.setText(reportContent[2]);
        if (currentDayOfTheWeek >= 2 && !reportContent[2].isEmpty()) {
            mReportTableWednesdayReal.setText(eva.getDailyAccumulate(2));
            accumulatedBalance += eva.computeBalance(reportContent[2],
                    eva.getDailyAccumulate(2));
            mReportTableWednesdayBalance.setText(this
                    .milisToDisplayTime(accumulatedBalance));
        }

        // Fill Thursday
        reportContent[3] = eva.getShift(3);
        mReportTableThursdayTheoretical.setText(reportContent[3]);
        if (currentDayOfTheWeek >= 3 && !reportContent[3].isEmpty()) {
            mReportTableThursdayReal.setText(eva.getDailyAccumulate(3));
            accumulatedBalance += eva.computeBalance(reportContent[3],
                    eva.getDailyAccumulate(3));
            mReportTableThursdayBalance.setText(this
                    .milisToDisplayTime(accumulatedBalance));
        }

        // Fill Friday
        reportContent[4] = eva.getShift(4);
        mReportTableFridayTheoretical.setText(reportContent[4]);
        if (currentDayOfTheWeek >= 4 && !reportContent[4].isEmpty()) {
            mReportTableFridayReal.setText(eva.getDailyAccumulate(4));
            accumulatedBalance += eva.computeBalance(reportContent[4],
                    eva.getDailyAccumulate(4));
            mReportTableFridayBalance.setText(this
                    .milisToDisplayTime(accumulatedBalance));
        }

        // Fill Saturday
        reportContent[5] = eva.getShift(5);
        mReportTableSaturdayTheoretical.setText(reportContent[5]);
        if (currentDayOfTheWeek >= 5 && !reportContent[5].isEmpty()) {
            mReportTableSaturdayReal.setText(eva.getDailyAccumulate(5));
            accumulatedBalance += eva.computeBalance(reportContent[5],
                    eva.getDailyAccumulate(5));
            mReportTableSaturdayBalance.setText(this
                    .milisToDisplayTime(accumulatedBalance));
        }

        // Fill Sunday
        reportContent[6] = eva.getShift(6);
        mReportTableSundayTheoretical.setText(reportContent[6]);
        if (currentDayOfTheWeek == 6 && !reportContent[6].isEmpty()) {
            mReportTableSundayReal.setText(eva.getDailyAccumulate(6));
            accumulatedBalance += eva.computeBalance(reportContent[6],
                    eva.getDailyAccumulate(6));
            mReportTableSundayBalance.setText(this
                    .milisToDisplayTime(accumulatedBalance));
        }
    }

    public String milisToDisplayTime(long milis) {

        Duration duration = new Duration(milis);
        String hours = String.valueOf(Math.abs(duration.getStandardHours()));
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
}
