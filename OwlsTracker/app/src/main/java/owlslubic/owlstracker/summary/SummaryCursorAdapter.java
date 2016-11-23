package owlslubic.owlstracker.summary;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.main.DBHelper;

/**
 * Created by owlslubic on 11/8/16.
 */

class SummaryCursorAdapter extends CursorAdapter {
    private static final String TAG = "CursorAdapter";

    SummaryCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_NAME));
        String type = cursor.getString(cursor.getColumnIndex(DBHelper.COL_MED_OR_ACT));
        double rating = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COL_RATING));
        //if the rating is 0, use the other
        int qtyOrDegree = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_QTY_OR_DEGREE));
        String text = "";
        if (type.equals(DBHelper.MED) || (!type.equals(DBHelper.ACTIVITY))) {
            text = name + ": ";
            if (rating < 1) {
                text += String.valueOf(qtyOrDegree);
            } else if (qtyOrDegree < 1) {
                text += String.valueOf(rating);
            }
        } else if (type.equals(DBHelper.ACTIVITY)) {
            //put it in past tense
            if (name.charAt(name.length() - 1) == 'e') {
                name += "d";
            } else {
                name += "ed";
            }

            text = name + " ";

            if (qtyOrDegree == 1) {
                text+= "a bit";
            } else if (qtyOrDegree == 2) {
                text+= "a lot";
            } else if (qtyOrDegree == 3) {
                text+= "tons";
            }
        }

        textView.setText(text);
        Log.d(TAG, "cursor adapter binding text: "+text);

        //TODO callback here to tell the fragment which text is being set... or soemthing
        //because we need to determine that in order to set the data to the lil detail fraggie


    }
}
