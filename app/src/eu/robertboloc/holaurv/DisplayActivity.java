package eu.robertboloc.holaurv;

import org.joda.time.DateTime;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import eu.robertboloc.holaurv.R;
import eu.robertboloc.holaurv.lib.TypefaceSpan;

public class DisplayActivity extends SherlockFragmentActivity implements
        com.actionbarsherlock.app.ActionBar.TabListener {

    ViewPager mViewPager;
    DayCollectionPagerAdapter mDayCollectionPagerAdapter;
    ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        DateTime date = org.joda.time.DateTime.now();

        mDayCollectionPagerAdapter = new DayCollectionPagerAdapter(
                getSupportFragmentManager(), this);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDayCollectionPagerAdapter);

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set custom font and colors to the Action Bar
        SpannableString s = new SpannableString(getText(R.string.brand));
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        s.setSpan(new ForegroundColorSpan(Color.BLACK), 3, 6,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        s.setSpan(new TypefaceSpan(this, "Exo-ExtraBold"), 0, s.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        actionBar.setTitle(s);

        mViewPager
                .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        for (int i = 0; i < mDayCollectionPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(mDayCollectionPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }

        // Swipe to today
        mViewPager.setCurrentItem(date.getDayOfWeek() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Add the info option
        menu.add(0, 0, 0, getText(R.string.menu_item_about))
                .setIcon(R.drawable.ic_action_about)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
        case 0:
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

}
