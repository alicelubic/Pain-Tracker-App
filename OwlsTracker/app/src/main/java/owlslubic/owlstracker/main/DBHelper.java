package owlslubic.owlstracker.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.models.RatingScale;
import owlslubic.owlstracker.models.Remedy;
import owlslubic.owlstracker.models.WellnessTracker;

/**
 * Created by owlslubic on 11/7/16.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "dbhelper";
    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "owls_tracker.db";
    public static final String USER_TABLE = "user_table";
    public static final String REMEDY_OPTIONS_TABLE = "remedies_table";
    private static final String COL_ID = "_id";
    public static final String COL_DATE = "date";
    public static final String COL_NAME = "name";
    public static final String COL_NOTES = "notes";
    public static final String COL_QTY_OR_DEGREE = "quantity_or_degree";
    public static final String COL_RATING = "rating";
    public static final String COL_MED_OR_ACT = "type";
    public static final String COL_IMAGE_ID = "imageId";
    public static final String MED = "med";
    public static final String ACTIVITY = "activity";
    private static  DBHelper sInstance;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_REMEDY_OPTIONS_TABLE);

        //insert remedy options
//        insertRemedyOptions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USER_TABLE);
        db.execSQL(SQL_DELETE_REMEDY_OPTIONS_TABLE);
    }

    //define table that will hold user's data
    private static final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_DATE + " TEXT, " +
            COL_NAME + " TEXT, " +
            COL_NOTES + " TEXT, " +
            COL_QTY_OR_DEGREE + " INT, " +
            COL_RATING + " DOUBLE, " +
            COL_MED_OR_ACT + " TEXT)";

    //define table for all available remedy, rating and treatment options
    private static final String SQL_CREATE_REMEDY_OPTIONS_TABLE = "CREATE TABLE " + REMEDY_OPTIONS_TABLE + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_DATE + " TEXT, " +
            COL_NAME + " TEXT, " +
            COL_NOTES + " TEXT, " +
            COL_QTY_OR_DEGREE + " INT, " +
            COL_MED_OR_ACT + " TEXT, " +
            COL_IMAGE_ID + " INT)";

    //define delete tables strings
    private static final String SQL_DELETE_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE;
    private static final String SQL_DELETE_REMEDY_OPTIONS_TABLE = "DROP TABLE IF EXISTS " + REMEDY_OPTIONS_TABLE;


            /*   helper methods!   */

    //empty table contents
    public void dumpTableData() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + USER_TABLE);

        db.close();
    }

    //write to db, //TODO replace if exists, put this in an async task
    public void writeToDatabase(@Nullable Remedy rem, @Nullable RatingScale rat) {
        String date;
        String name;
        String notes;
        int qtyOrDegree;
        double rating;
        String type;

        if (rat == null && rem != null) {
            name = rem.getName();
            notes = rem.getNotes();
            date = rem.getDate();
            qtyOrDegree = rem.getQtyOrDegree();
            rating = 0;
            type = rem.getMedOrActivity();

        } else if (rem == null && rat != null) {
            name = rat.getName();
            notes = rat.getNotes();
            date = rat.getDate();
            qtyOrDegree = 0;
            rating = rat.getRating();
            type = null;
        } else {
            Log.d(TAG, "writeToDatabase: no valid object type");
            //saying return because if it's not at least one of those, then it's not gonna work
            return;
        }

        SQLiteDatabase db = getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(COL_NAME, name);
        vals.put(COL_NOTES, notes);
        vals.put(COL_DATE, date);
        vals.put(COL_QTY_OR_DEGREE, qtyOrDegree);
        vals.put(COL_RATING, rating);
        vals.put(COL_MED_OR_ACT, type);
        db.insertWithOnConflict(USER_TABLE, null, vals, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        //TODO should have some sort of completion listener
        // to make sure it actually added before Toasting that the data was added
    }


    private void insertRemedyOptions(SQLiteDatabase db) {
        //create the objects
        //this may change when, in the future, the user can add their own also...
        ArrayList<Remedy> list = new ArrayList<>();

       /* list.add(new Remedy(mContext.getString(R.string.advil), null, MainActivity.getTheDate(), false, 0, R.drawable.advil, MED));
        list.add(new Remedy(mContext.getString(R.string.ativan), null, MainActivity.getTheDate(), false, 0, R.drawable.ativan, MED));
        list.add(new Remedy(mContext.getString(R.string.herbal_supp), null, MainActivity.getTheDate(), false, 0, R.drawable.herb, MED));
        list.add(new Remedy(mContext.getString(R.string.turmeric), null, MainActivity.getTheDate(), false, 0, R.drawable.turmeric, MED));
        list.add(new Remedy(mContext.getString(R.string.remedy_walk), null, MainActivity.getTheDate(), false, 0, R.drawable.walk, ACTIVITY));
        list.add(new Remedy(mContext.getString(R.string.remedy_ice), null, MainActivity.getTheDate(), false, 0, R.drawable.ice, ACTIVITY));
        list.add(new Remedy(mContext.getString(R.string.remedy_stretch), null, MainActivity.getTheDate(), false, 0, R.drawable.stretch, ACTIVITY));
*/
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
        }
        //not closing db like normal since im putting this in oncreate...?


    }

    public ArrayList<Remedy> getRemedyOptionsList(boolean isMed, boolean isActivity) {
        SQLiteDatabase db = getReadableDatabase();
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

                if (isMed && type.equals(MED)) {
                    medList.add(new Remedy(name, notes, date, false, 0, imageId, type));
                } else if (isActivity && type.equals(ACTIVITY)) {
                    activityList.add(new Remedy(name, notes, date, false, 0, imageId, type));
                } else {
                    allList.add(new Remedy(name, notes, date, false, 0, imageId, type));
                }

            }
        }
        cursor.close();

        if (isMed) {
            return medList;
        } else if (isActivity) {
            return activityList;
        } else {
            Log.d(TAG, "getRemedyOptionsList: list size before return is: " + allList.size());

            return allList;
        }
    }


    public Cursor getAllByDate(String date) {
        SQLiteDatabase db = getReadableDatabase();
        String queryRemedies = "SELECT * FROM " + USER_TABLE + " WHERE " + COL_DATE + " =?";
        //note to self: earlier sql thought that the date was my column name, and it is because the
        //date needs to be surrounded by single quotes to be included in the query string
        return db.rawQuery(queryRemedies, new String[]{date});
    }

    public void editQuantity(Remedy data, int amt) {
        //change it on the screen

        //set it to the data object
        data.setQtyOrDegree(amt);

        //update the column in the db
        SQLiteDatabase db = getWritableDatabase();
        String update = "UPDATE " + USER_TABLE +
                " SET " + COL_QTY_OR_DEGREE + " = " + amt +
                " WHERE " + COL_NAME + " = \'" + data.getName() +
                "\' AND " + COL_DATE + " = \'" + data.getDate() + "\'";
        db.execSQL(update);
        db.close();
    }



}


