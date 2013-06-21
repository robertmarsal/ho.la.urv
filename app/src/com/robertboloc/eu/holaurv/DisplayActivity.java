package com.robertboloc.eu.holaurv;

import org.joda.time.DateTime;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class DisplayActivity extends FragmentActivity {

    ViewPager mViewPager;
    DayCollectionPagerAdapter mDayCollectionPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        DateTime date = org.joda.time.DateTime.now();

        mDayCollectionPagerAdapter = new DayCollectionPagerAdapter(
                getSupportFragmentManager(), this);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDayCollectionPagerAdapter);
        mViewPager.setCurrentItem(date.getDayOfWeek());
    }
}
