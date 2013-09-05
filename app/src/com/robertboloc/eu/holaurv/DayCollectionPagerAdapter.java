package com.robertboloc.eu.holaurv;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

public class DayCollectionPagerAdapter extends FragmentStatePagerAdapter {

    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;

    public final SparseArray<String> daysOfWeek = new SparseArray<String>();

    public DayCollectionPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);

        // Get the translations of the days of the week
        daysOfWeek.put(MONDAY, ctx.getString(R.string.monday));
        daysOfWeek.put(TUESDAY, ctx.getString(R.string.tuesday));
        daysOfWeek.put(WEDNESDAY, ctx.getString(R.string.wednesday));
        daysOfWeek.put(THURSDAY, ctx.getString(R.string.thursday));
        daysOfWeek.put(FRIDAY, ctx.getString(R.string.friday));
        daysOfWeek.put(SATURDAY, ctx.getString(R.string.saturday));
        daysOfWeek.put(SUNDAY, ctx.getString(R.string.sunday));
    }

    @Override
    public int getCount() {
        return 7; // There are 7 days in a week (for now at least...)
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new DayObjectFragment();
        Bundle args = new Bundle();
        args.putInt(DayObjectFragment.ARG_OBJECT, i);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return daysOfWeek.get(position + 1);
    }
}