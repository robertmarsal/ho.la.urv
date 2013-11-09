package eu.robertboloc.holaurv.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import eu.robertboloc.holaurv.R;
import eu.robertboloc.holaurv.fragments.DayObjectFragment;
import eu.robertboloc.holaurv.models.Day;

public class DayCollectionPagerAdapter extends FragmentStatePagerAdapter {

    public final SparseArray<String> daysOfWeek = new SparseArray<String>();

    public DayCollectionPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);

        // Get the translations of the days of the week
        daysOfWeek.put(Day.MONDAY, ctx.getString(R.string.monday));
        daysOfWeek.put(Day.TUESDAY, ctx.getString(R.string.tuesday));
        daysOfWeek.put(Day.WEDNESDAY, ctx.getString(R.string.wednesday));
        daysOfWeek.put(Day.THURSDAY, ctx.getString(R.string.thursday));
        daysOfWeek.put(Day.FRIDAY, ctx.getString(R.string.friday));
        daysOfWeek.put(Day.SATURDAY, ctx.getString(R.string.saturday));
        daysOfWeek.put(Day.SUNDAY, ctx.getString(R.string.sunday));
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
        return daysOfWeek.get(position);
    }
}