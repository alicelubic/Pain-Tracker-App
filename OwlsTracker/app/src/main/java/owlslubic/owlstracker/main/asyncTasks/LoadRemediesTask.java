package owlslubic.owlstracker.main.asyncTasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.main.DBHelper;
import owlslubic.owlstracker.main.MainActivity;
import owlslubic.owlstracker.models.Remedy;

import static owlslubic.owlstracker.main.DBHelper.ACTIVITY;
import static owlslubic.owlstracker.main.DBHelper.COL_DATE;
import static owlslubic.owlstracker.main.DBHelper.COL_IMAGE_ID;
import static owlslubic.owlstracker.main.DBHelper.COL_MED_OR_ACT;
import static owlslubic.owlstracker.main.DBHelper.COL_NAME;
import static owlslubic.owlstracker.main.DBHelper.COL_NOTES;
import static owlslubic.owlstracker.main.DBHelper.COL_QTY_OR_DEGREE;
import static owlslubic.owlstracker.main.DBHelper.MED;
import static owlslubic.owlstracker.main.DBHelper.REMEDY_OPTIONS_TABLE;

public class LoadRemediesTask extends AsyncTask<Void, Integer, Void> {
    private static final String TAG = "LoadRemediesTask";
    private Context mContext;

    public LoadRemediesTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "doInBackground called");
        //db ref
        SQLiteDatabase db = DBHelper.getInstance(mContext).getWritableDatabase();
        //create the objects
        //this may change when, in the future, the user can add their own also...
        ArrayList<Remedy> list = new ArrayList<>();
        list.add(new Remedy(mContext.getString(R.string.advil), MainActivity.getTheDate(), false, 0, R.drawable.advil, MED));
        list.add(new Remedy(mContext.getString(R.string.ativan), MainActivity.getTheDate(), false, 0, R.drawable.ativan, MED));
        list.add(new Remedy(mContext.getString(R.string.herbal_supp), MainActivity.getTheDate(), false, 0, R.drawable.herb, MED));
        list.add(new Remedy(mContext.getString(R.string.turmeric), MainActivity.getTheDate(), false, 0, R.drawable.turmeric, MED));
        list.add(new Remedy(mContext.getString(R.string.remedy_walk), MainActivity.getTheDate(), false, 0, R.drawable.walk, ACTIVITY));
        list.add(new Remedy(mContext.getString(R.string.remedy_ice), MainActivity.getTheDate(), false, 0, R.drawable.ice, ACTIVITY));
        list.add(new Remedy(mContext.getString(R.string.remedy_stretch), MainActivity.getTheDate(), false, 0, R.drawable.stretch, ACTIVITY));
        Log.d(TAG, "doInBackground: list size is " + list.size());
        //then write them to the database
        ContentValues vals = new ContentValues();
        for (Remedy rem : list) {
            vals.put(COL_NAME, rem.getName());
            vals.put(COL_NOTES, rem.getNotes());
            vals.put(COL_DATE, rem.getDate());
            vals.put(COL_QTY_OR_DEGREE, rem.getQtyOrDegree());
            vals.put(COL_MED_OR_ACT, rem.getMedOrActivity());
            vals.put(COL_IMAGE_ID, rem.getImageId());
            db.insertWithOnConflict(REMEDY_OPTIONS_TABLE, null, vals, SQLiteDatabase.CONFLICT_REPLACE);
            Log.d(TAG, "doInBackground: inserting... " + rem.getName());
        }

        db.close();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d(TAG, "onPostExecute: REMEDIES HAVE BEEN LOADED TO DB");
    }
}
