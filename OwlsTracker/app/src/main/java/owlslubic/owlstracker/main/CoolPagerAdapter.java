package owlslubic.owlstracker.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.ratings.RatingsFragment;
import owlslubic.owlstracker.remedies.RemediesFragment;
import owlslubic.owlstracker.summary.SummaryFragment;

/**
 * Created by owlslubic on 11/7/16.
 */

public class CoolPagerAdapter extends FragmentPagerAdapter{
    private Context mContext;
    public CoolPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return RatingsFragment.newInstance();
            case 1:
                return RemediesFragment.newInstance();
            case 2:
                return SummaryFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return mContext.getString(R.string.tab_scales);
            case 1:
                return mContext.getString(R.string.tab_remedies);
            case 2:
                return mContext.getString(R.string.tab_summary);
            default:
                return null;
        }
    }
}
