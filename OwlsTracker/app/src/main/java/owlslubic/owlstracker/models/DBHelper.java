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


/**
 * >> tryna use the same column names in two tables,
 * make sure it doesnt cause a prob before moving on <<
 **/

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "dbhelper";
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "owls_tracker.db";
    private static final String REMEDIES_TABLE = "remedies_table";
    private static final String RATINGS_TABLE = "ratings_table";
    private static final String SPECIALTY_TABLE = "specialty_table";
    private static final String COL_ID = "_id";
    private static final String COL_DATE = "date";
    private static final String COL_NAME = "name";
    private static final String COL_NOTES = "notes";
    private static final String COL_QTY_OR_DEGREE = "quantity_or_degree";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_RATING = "rating";
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
        db.execSQL(SQL_CREATE_RATINGS_TABLE);
        db.execSQL(SQL_CREATE_SPECIALTY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_REMEDIES_TABLE);
        db.execSQL(SQL_DELETE_RATINGS_TABLE);
        db.execSQL(SQL_DELETE_SPECIALTY_TABLE);
    }

    //define remedies table
    private static final String SQL_CREATE_REMEDIES_TABLE = "CREATE TABLE " + REMEDIES_TABLE + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_DATE + " TEXT, " +
            COL_NAME + " TEXT, " +
            COL_NOTES + " TEXT, " +
            COL_QTY_OR_DEGREE + " INT)";

    //define ratings table
    private static final String SQL_CREATE_RATINGS_TABLE = "CREATE TABLE " + RATINGS_TABLE + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_DATE + " TEXT, " +
            COL_NAME + " TEXT, " +
            COL_NOTES + " TEXT, " +
            COL_RATING + " DOUBLE)";

    //define specialty treatment table
    private static final String SQL_CREATE_SPECIALTY_TABLE = "CREATE TABLE " + SPECIALTY_TABLE + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_DATE + " TEXT, " +
            COL_NAME + " TEXT, " +
            COL_NOTES + " TEXT, " +
            COL_QUANTITY + " INT)";

    //define delete tables strings
    private static final String SQL_DELETE_REMEDIES_TABLE = "DROP TABLE IF EXISTS " + REMEDIES_TABLE;
    private static final String SQL_DELETE_RATINGS_TABLE = "DROP TABLE IF EXISTS " + RATINGS_TABLE;
    private static final String SQL_DELETE_SPECIALTY_TABLE = "DROP TABLE IF EXISTS " + SPECIALTY_TABLE;


            /*   helper methods!   */

    //empty table contents
    public void dumpAllTables() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + REMEDIES_TABLE);
        db.execSQL("DELETE FROM " + RATINGS_TABLE);
        db.execSQL("DELETE FROM " + SPECIALTY_TABLE);

        db.close();
    }

    //write to db
    public void writeToDatabase(@Nullable Remedy rem, @Nullable RatingScale rat, @Nullable SpecialtyTreatment spec) {
        String tableName;
        String date;
        String name;
        String notes;
        String colQtyOrRating;
        int qtyOrRating;

        if ((rat == null && spec == null) && rem != null) {
            tableName = REMEDIES_TABLE;
            name = rem.getName();
            notes = rem.getNotes();
            date = rem.getDate();
            qtyOrRating = rem.getQtyOrDegree();
            colQtyOrRating = COL_QTY_OR_DEGREE;

        } else if ((rem == null && rat == null) && spec != null) {
            tableName = SPECIALTY_TABLE;
            name = spec.getName();
            notes = spec.getNotes();
            date = spec.getDate();
            qtyOrRating = spec.getQuantity();
            colQtyOrRating = COL_QUANTITY;

        } else if ((rem == null && spec == null) && rat != null) {
            tableName = RATINGS_TABLE;
            name = rat.getName();
            notes = rat.getNotes();
            date = rat.getDate();
            qtyOrRating = (int) rat.getRating();
            colQtyOrRating = COL_RATING;
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
        vals.put(colQtyOrRating, qtyOrRating);
        db.insertOrThrow(tableName, null, vals);
        db.close();
        //TODO should have some sort of completion listener
        // to make sure it actually added before Toasting that the data was added
    }


    /* this method is returning a string becuase i dont know yet how i'll really want to manipulate the data
    in which case i'll return a cursor */
    public String getAllByDate(String date) {
        StringBuilder resultsBuilder = new StringBuilder(100);
        SQLiteDatabase db = getReadableDatabase();

        String queryRemedies = "SELECT * FROM " + REMEDIES_TABLE + " WHERE " + COL_DATE + " =?";// + date;
        //note to self: earlier sql thought that the date was my column name, and it is because the
        //date needs to be surrounded by single quotes to be included in the query string
        String queryRatings = "SELECT * FROM " + RATINGS_TABLE + " WHERE " + COL_DATE + " =?";// + date;
        String querySpecialty = "SELECT * FROM " + SPECIALTY_TABLE + " WHERE " + COL_DATE + " =?";// + date;

//        Cursor cursor = db.rawQuery(queryRemedies, null);
        Cursor cursor = db.rawQuery(queryRemedies,new String[]{date});
        Log.d(TAG, "getAllByDate: cursor count = "+cursor.getCount());
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                resultsBuilder.append("remedy: ").append(name).append("\n");
                Log.i(TAG, "getAllByDate: remedy name: " + name);
                cursor.moveToNext();
            }
        }
        cursor.close();


        Cursor cursor1 = db.rawQuery(queryRatings, new String[]{date});
        Log.d(TAG, "getAllByDate: cursor1 count = " + cursor1.getCount());
        if (cursor1.moveToFirst()) {
            while (!cursor1.isAfterLast()) {
                String name = cursor1.getString(cursor1.getColumnIndex(COL_NAME));
                resultsBuilder.append("rating: ").append(name).append("\n");
                Log.i(TAG, "getAllByDate: rating name: " + name);
                cursor1.moveToNext();
            }
        }
        cursor1.close();

        Cursor cursor2 = db.rawQuery(querySpecialty, new String[]{date});
        if (cursor2.moveToFirst()) {
            while (!cursor2.isAfterLast()) {
                String name = cursor2.getString(cursor2.getColumnIndex(COL_NAME));
                resultsBuilder.append("specialty: ").append(name).append("\n");
                Log.i(TAG, "getAllByDate: specialty name: " + name);
                cursor2.moveToNext();
            }
        }
        cursor2.close();



        return resultsBuilder.toString();
    }

    public void editQuantity(Remedy data, int amt, boolean isSpecialtyTreatment) {
        //change it on the screen

        //set it to the data object
        data.setQtyOrDegree(amt);

        //update the column in the db
        SQLiteDatabase db = getWritableDatabase();
        String table;
        String qtyCol;
        if (isSpecialtyTreatment) {
            table = SPECIALTY_TABLE;
            qtyCol = COL_QUANTITY;
        } else {
            table = REMEDIES_TABLE;
            qtyCol = COL_QTY_OR_DEGREE;
        }
        String update = "UPDATE " + table +
                " SET " + qtyCol + " = " + amt +
                " WHERE " + COL_NAME + " = " + data.getName() +
                " AND " + COL_DATE + " = " + data.getDate();

        db.execSQL(update);
        db.close();
    }


}


