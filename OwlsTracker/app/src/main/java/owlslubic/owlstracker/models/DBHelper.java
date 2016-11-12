package owlslubic.owlstracker.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.widget.Toast;

/**
 * Created by owlslubic on 11/7/16.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "dbhelper";
    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "owls_tracker.db";
    private static final String REMEDIES_TABLE = "remedies_table";
    private static final String COL_ID = "_id";
    public static final String COL_DATE = "date";
    public static final String COL_NAME = "name";
    public static final String COL_NOTES = "notes";
    public static final String COL_QTY_OR_DEGREE = "quantity_or_degree";
    public static final String COL_RATING = "rating";
    private static DBHelper sInstance;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context);
        }
        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_REMEDIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_REMEDIES_TABLE);
    }

    //define remedies, ratings and treatments table
    private static final String SQL_CREATE_REMEDIES_TABLE = "CREATE TABLE " + REMEDIES_TABLE + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_DATE + " TEXT, " +
            COL_NAME + " TEXT, " +
            COL_NOTES + " TEXT, " +
            COL_QTY_OR_DEGREE + " INT, " +
            COL_RATING + " DOUBLE)";

    //define delete tables strings
    private static final String SQL_DELETE_REMEDIES_TABLE = "DROP TABLE IF EXISTS " + REMEDIES_TABLE;


            /*   helper methods!   */

    //empty table contents
    public void dumpTableData() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + REMEDIES_TABLE);

        db.close();
    }

    //write to db, //TODO replace if exists
    public void writeToDatabase(@Nullable Remedy rem, @Nullable RatingScale rat) {
        String date;
        String name;
        String notes;
        int qtyOrDegree;
        double rating;

        if (rat == null && rem != null) {
            name = rem.getName();
            notes = rem.getNotes();
            date = rem.getDate();
            qtyOrDegree = rem.getQtyOrDegree();
            rating = 0;

        } else if (rem == null && rat != null) {
            name = rat.getName();
            notes = rat.getNotes();
            date = rat.getDate();
            qtyOrDegree = 0;
            rating = rat.getRating();
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
        db.insertWithOnConflict(REMEDIES_TABLE, null, vals, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
        //TODO should have some sort of completion listener
        // to make sure it actually added before Toasting that the data was added
    }


    public Cursor getAllByDate(String date) {
        SQLiteDatabase db = getReadableDatabase();
        String queryRemedies = "SELECT * FROM " + REMEDIES_TABLE + " WHERE " + COL_DATE + " =?";
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
        String update = "UPDATE " + REMEDIES_TABLE +
                " SET " + COL_QTY_OR_DEGREE + " = " + amt +
                " WHERE " + COL_NAME + " = \'" + data.getName() +
                "\' AND " + COL_DATE + " = \'" + data.getDate() + "\'";
        db.execSQL(update);
        db.close();
    }


}


