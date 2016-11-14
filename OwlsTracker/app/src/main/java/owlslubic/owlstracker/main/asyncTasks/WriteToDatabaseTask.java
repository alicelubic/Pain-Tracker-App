package owlslubic.owlstracker.main.asyncTasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.main.DBHelper;
import owlslubic.owlstracker.models.RatingScale;
import owlslubic.owlstracker.models.Remedy;
import owlslubic.owlstracker.models.WellnessTracker;
import static owlslubic.owlstracker.main.DBHelper.*;



/**
 * Created by owlslubic on 11/14/16.
 */

public class WriteToDatabaseTask extends AsyncTask<WellnessTracker, Void, Void> {

    private static final String TAG = "WriteDBTask";
    private Context mContext;
    private View mView;

    public WriteToDatabaseTask(Context context, View view) {
        mContext = context;
        mView = view;
    }

    @Override
    protected Void doInBackground(WellnessTracker... params) {
        Remedy rem = null;
        RatingScale rat = null;
        if (params[0].getClass().equals(Remedy.class)) {
            rem = (Remedy) params[0];
        } else if (params[0].getClass().equals(RatingScale.class)) {
            rat = (RatingScale) params[0];
        } else {
            return null;
        }
        String date;
        String name;
        String notes;
        int qtyOrDegree;
        double rating;
        String type;
        if ((rat == null) && (rem != null)) {// && (rem.wasUsedToday())) {
            name = rem.getName();
            notes = rem.getNotes();
            date = rem.getDate();
            qtyOrDegree = rem.getQtyOrDegree();
            rating = 0;
            type = rem.getMedOrActivity();

        } else if ((rem == null) && (rat != null)) {
            name = rat.getName();
            notes = rat.getNotes();
            date = rat.getDate();
            qtyOrDegree = 0;
            rating = rat.getRating();
            type = null;
        } else {
            Log.d(TAG, "writeToDatabase: no valid object type");
            //saying return because if it's not at least one of those, then it's not gonna work
            return null;
        }

        SQLiteDatabase db = DBHelper.getInstance(mContext).getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(COL_NAME, name);
        vals.put(COL_NOTES, notes);
        vals.put(COL_DATE, date);
        vals.put(COL_QTY_OR_DEGREE, qtyOrDegree);
        vals.put(COL_RATING, rating);
        vals.put(COL_MED_OR_ACT, type);
        db.insertWithOnConflict(DBHelper.USER_TABLE, null, vals, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mView != null) {
            Snackbar snack = Snackbar.make(mView, "Saved!", Snackbar.LENGTH_SHORT);
            snack.show();
        }
    }

}
