package owlslubic.owlstracker.main.asyncTasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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

public class LoadRemediesTask extends AsyncTask<Void,Integer,Void> {
    private static final String TAG = "LoadRemediesTask";
    private Context mContext;
    private ProgressBar mProgress;
    private View mView;

    public LoadRemediesTask(Context context, ProgressBar progress, View view) {
        mContext = context;
        mProgress = progress;
        mView = view;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "doInBackground called");
        //db ref
        SQLiteDatabase db = DBHelper.getInstance(mContext).getWritableDatabase();
        //create the objects
        //this may change when, in the future, the user can add their own also...
        ArrayList<Remedy> list = new ArrayList<>();
        list.add(new Remedy(mContext.getString(R.string.advil), null, MainActivity.getTheDate(), false, 0, R.drawable.advil, MED));
        list.add(new Remedy(mContext.getString(R.string.ativan), null, MainActivity.getTheDate(), false, 0, R.drawable.ativan, MED));
        list.add(new Remedy(mContext.getString(R.string.herbal_supp), null, MainActivity.getTheDate(), false, 0, R.drawable.herb, MED));
        list.add(new Remedy(mContext.getString(R.string.turmeric), null, MainActivity.getTheDate(), false, 0, R.drawable.turmeric, MED));
        list.add(new Remedy(mContext.getString(R.string.remedy_walk), null, MainActivity.getTheDate(), false, 0, R.drawable.walk, ACTIVITY));
        list.add(new Remedy(mContext.getString(R.string.remedy_ice), null, MainActivity.getTheDate(), false, 0, R.drawable.ice, ACTIVITY));
        list.add(new Remedy(mContext.getString(R.string.remedy_stretch), null, MainActivity.getTheDate(), false, 0, R.drawable.stretch, ACTIVITY));
        Log.d(TAG, "doInBackground: list size is "+list.size());
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
            Log.d(TAG, "doInBackground: inserting... "+rem.getName());
        }

        db.close();
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(mProgress!=null){
            mProgress.setVisibility(View.VISIBLE);
            mProgress.setMax(100);
            for (int i = 0; i < values.length-1; i++) {
                mProgress.setProgress(i);
            }

        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(mProgress!=null){
            mProgress.setVisibility(View.INVISIBLE);
        }
        if (mView != null) {
            Snackbar snack = Snackbar.make(mView, "remedies loaded!", Snackbar.LENGTH_SHORT);
            snack.show();
        }
    }
}
