package owlslubic.owlstracker.main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import owlslubic.owlstracker.models.Remedy;

/**
 * Created by owlslubic on 11/7/16.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "dbhelper";
    private static final int DATABASE_VERSION = 10;
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
    public void dumpTableData(Context context) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + USER_TABLE);
        Toast.makeText(context, "user_table contents deleted", Toast.LENGTH_SHORT).show();
        db.execSQL("DELETE FROM "+ REMEDY_OPTIONS_TABLE);
        Toast.makeText(context, "remedies_table contents deleted", Toast.LENGTH_SHORT).show();
        db.close();
    }



    /** moved this to an asynctask, but without the med/activity separation
     * because i dont remember why  i set this up that way in the first place...
     * */
  /*  public ArrayList<Remedy> getRemedyOptionsList(boolean isMed, boolean isActivity) {
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
*/

    //TODO make this not on the UI thread, but not necessaraily in asynctask?
    public Cursor getAllByDate(String date) {
        SQLiteDatabase db = getReadableDatabase();
        String queryRemedies = "SELECT * FROM " + USER_TABLE + " WHERE " + COL_DATE + " =?";
        //note to self: earlier sql thought that the date was my column name, and it is because the
        //date needs to be surrounded by single quotes to be included in the query string
        return db.rawQuery(queryRemedies, new String[]{date});
    }


}


