package owlslubic.owlstracker.controllers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import owlslubic.owlstracker.fragments.RatingsFragment;
import owlslubic.owlstracker.fragments.RemediesFragment;
import owlslubic.owlstracker.fragments.SummaryFragment;

/**
 * Created by owlslubic on 11/7/16.
 */

public class SomePagerAdapter extends FragmentPagerAdapter{
    public SomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new RatingsFragment();
            case 1:
                return new RemediesFragment();
            case 2:
                return new RemediesFragment();
            case 3:
                return new SummaryFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
