package owlslubic.owlstracker.main.asyncTasks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import owlslubic.owlstracker.main.DBHelper;
import owlslubic.owlstracker.models.Remedy;

import static owlslubic.owlstracker.main.DBHelper.*;

/**
 * Created by owlslubic on 11/14/16.
 */
class RemedyListSingleton {
    public List<Remedy> mRemedies;
    private static owlslubic.owlstracker.models.RemedyListSingleton sInstance;

    public RemedyListSingleton(Context context) {
        //by running this in here, when a new isntance is called, it doesnt run this anew
        new GetRemediesListTask(context).execute("i want full list");
    }

    public static synchronized owlslubic.owlstracker.models.RemedyListSingleton getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new owlslubic.owlstracker.models.RemedyListSingleton();
        }
        return sInstance;
    }

    public List<Remedy> getAllRemediesList() {
        if (mRemedies != null) {
            return mRemedies;
        } else {
            return null;
        }

    }


    public class GetRemediesListTask extends AsyncTask<String, Void, List<Remedy>> {
        private static final String TAG = "GetRemediesListTask";
        private Context mContext;

        public GetRemediesListTask(Context context) {
            mContext = context;
        }

        @Override
        protected List<Remedy> doInBackground(String... params) {
            //String passed in should be MED, ACTIVITY, or nothing
            boolean isMedList = false;
            boolean isActivityList = false;
            if (params[0].equals(MED)) {
                isMedList = true;
            } else if (params[0].equals(ACTIVITY)) {
                isActivityList = true;
            }

            SQLiteDatabase db = DBHelper.getInstance(mContext).getReadableDatabase();
            ArrayList<Remedy> allList = new ArrayList<>();
            ArrayList<Remedy> medList = new ArrayList<>();
            ArrayList<Remedy> activityList = new ArrayList<>();
            String query = "SELECT * FROM " + REMEDY_OPTIONS_TABLE;
            String name;
            String notes;
            String date;
            int imageId;
            String type;

            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                    notes = cursor.getString(cursor.getColumnIndex(COL_NOTES));
                    date = cursor.getString(cursor.getColumnIndex(COL_DATE));
                    imageId = cursor.getInt(cursor.getColumnIndex(COL_IMAGE_ID));
                    type = cursor.getString(cursor.getColumnIndex(COL_MED_OR_ACT));
                    Remedy newRem = new Remedy(name, notes, date, false, 0, imageId, type);

                    if ((isMedList) && (type.equals(MED))) {
                        medList.add(newRem);
                        Log.d(TAG, "doInBackground: adding " + name + " to medList");
                    } else if ((isActivityList) && (type.equals(ACTIVITY))) {
                        activityList.add(newRem);
                        Log.d(TAG, "doInBackground: adding " + name + " to activityList");
                    } else {
                        allList.add(newRem);
                        Log.d(TAG, "doInBackground: adding " + name + " to allList");
                    }

                    cursor.moveToNext();
                }
            }
            cursor.close();

            if (isMedList) {
                return medList;
            } else if (isActivityList) {
                return activityList;
            } else {
                Log.d(TAG, "getRemedyOptionsList: list size before return is: " + allList.size());

                return allList;
            }
        }

        @Override
        protected void onPostExecute(List<Remedy> remedies) {
            super.onPostExecute(remedies);
            Log.d(TAG, "onPostExecute: remedies list size: " + remedies.size());

            mRemedies = remedies;
        }


    }
}
