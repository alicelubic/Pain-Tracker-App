package owlslubic.owlstracker.models;

import android.content.Context;

import java.util.List;

import owlslubic.owlstracker.main.DBHelper;

/**
 * Created by owlslubic on 11/14/16.
 */

public class RemedyListSingleton {
    public List<Remedy> mRemedies;
    private static RemedyListSingleton sInstance;

    public RemedyListSingleton() {

    }

    public static synchronized RemedyListSingleton getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RemedyListSingleton();
        }
        return sInstance;
    }

    public List<Remedy> getFreshRemediesList(){

return mRemedies;

    }

}
