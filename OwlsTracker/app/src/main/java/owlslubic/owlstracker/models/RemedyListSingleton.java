package owlslubic.owlstracker.models;

import android.content.Context;

import java.util.ArrayList;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.main.MainActivity;

import static owlslubic.owlstracker.main.DBHelper.*;


/**
 * Created by owlslubic on 11/14/16.
 */

public class RemedyListSingleton {
    public ArrayList<Remedy> mRemedies;
    private static RemedyListSingleton sInstance;
    private Context mContext;

    public RemedyListSingleton(Context context) {
        mContext = context;
    }

    public static synchronized RemedyListSingleton getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RemedyListSingleton(context.getApplicationContext());
        }
        return sInstance;
    }

    public ArrayList<Remedy> getFreshRemediesList() {
        /** this list should be grabbed from the db.........?
         * because when a user adds their own shit, it should be to db, no?*/
        mRemedies = new ArrayList<>();

        mRemedies.add(new Remedy(mContext.getString(R.string.advil), MainActivity.getTheDate(), false, 0, R.drawable.advil, MED));
        mRemedies.add(new Remedy(mContext.getString(R.string.aleve), MainActivity.getTheDate(), false, 0, R.drawable.aleve, MED));
        mRemedies.add(new Remedy(mContext.getString(R.string.ativan), MainActivity.getTheDate(), false, 0, R.drawable.ativan, MED));
        mRemedies.add(new Remedy(mContext.getString(R.string.herbal_supp), MainActivity.getTheDate(), false, 0, R.drawable.herb, MED));
        mRemedies.add(new Remedy(mContext.getString(R.string.turmeric), MainActivity.getTheDate(), false, 0, R.drawable.turmeric, MED));
        mRemedies.add(new Remedy(mContext.getString(R.string.remedy_walk), MainActivity.getTheDate(), false, 0, R.drawable.walk, ACTIVITY));
        mRemedies.add(new Remedy(mContext.getString(R.string.remedy_ice), MainActivity.getTheDate(), false, 0, R.drawable.ice, ACTIVITY));
        mRemedies.add(new Remedy(mContext.getString(R.string.remedy_stretch), MainActivity.getTheDate(), false, 0, R.drawable.stretch, ACTIVITY));
        return mRemedies;

    }



}

