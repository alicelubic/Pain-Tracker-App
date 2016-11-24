package owlslubic.owlstracker.models;

import android.content.Context;

import java.util.ArrayList;

import owlslubic.owlstracker.R;

/**
 * Created by owlslubic on 11/23/16.
 */

public class RatingListSingleton {

    private ArrayList<Rating> mRatings;
    private static RatingListSingleton sInstance;
    private Context mContext;

    public RatingListSingleton(Context context) {
        mContext = context;
    }

    public static RatingListSingleton getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RatingListSingleton(context.getApplicationContext());
        }
        return sInstance;
    }

    public ArrayList<Rating> getPainScaleList() {
        ArrayList<Rating> ratings = new ArrayList<>();

        ratings.add(new Rating("Pain", null, 0, R.drawable.fsp0, false));
        ratings.add(new Rating("Pain", null, 2, R.drawable.fsp2, false));
        ratings.add(new Rating("Pain", null, 4, R.drawable.fsp4, false));
        ratings.add(new Rating("Pain", null, 6, R.drawable.fsp6, false));
        ratings.add(new Rating("Pain", null, 8, R.drawable.fsp8, false));
        ratings.add(new Rating("Pain", null, 10, R.drawable.fsp10, false));


        return ratings;
    }

    public ArrayList<Rating> getFreshRatingList() {
        mRatings = new ArrayList<>();
        mRatings.add(new Rating("Pain", null, 0, 0, false));


        return mRatings;
    }


}
