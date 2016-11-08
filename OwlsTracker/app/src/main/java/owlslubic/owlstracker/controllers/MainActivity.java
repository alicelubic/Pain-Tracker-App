package owlslubic.owlstracker.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.models.DBHelper;
import owlslubic.owlstracker.models.RatingScale;
import owlslubic.owlstracker.models.Remedy;
import owlslubic.owlstracker.models.SpecialtyTreatment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //temp stuff
        Button insertData = (Button) findViewById(R.id.button_insert_data);
        Button queryDB = (Button) findViewById(R.id.button_query_db);
        Button dumpData = (Button) findViewById(R.id.button_dump_data);
        final TextView summary = (TextView) findViewById(R.id.textview_main);
        summary.setMovementMethod(new ScrollingMovementMethod());

        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add stuff to the database
                createExampleData();
                Toast.makeText(MainActivity.this, "data is being added...", Toast.LENGTH_SHORT).show();
            }
        });
        dumpData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper.getInstance(MainActivity.this).dumpAllTables();
                Toast.makeText(MainActivity.this, "dumping data...", Toast.LENGTH_SHORT).show();
            }
        });
        queryDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //query the db
                String results = DBHelper.getInstance(MainActivity.this).getAllByDate(getCurrentDate());
                //set results to textview
                summary.setText(results);
            }
        });


    }


    private void createExampleData() {

        SpecialtyTreatment rfD = new SpecialtyTreatment(
                "Radiofrequency Denervation", null, getCurrentDate(), 6);
        RatingScale pain = new RatingScale(
                "Pain Scale", null, getCurrentDate(), 9.0);//9.0 will correspond with a pain face
        Remedy pot = new Remedy("Herbal", null, getCurrentDate(), true, 5);
        //then add to database
        DBHelper helper = DBHelper.getInstance(MainActivity.this);
        helper.writeToDatabase(pot, null, null);
        helper.writeToDatabase(null, pain, null);
        helper.writeToDatabase(null, null, rfD);
    }

    private String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d yyyy");
        Log.i(TAG, "getDate: " + df.format(Calendar.getInstance().getTime()));
//        String date = "\'" + df.format(Calendar.getInstance().getTime()) + "\'";
        return df.format(Calendar.getInstance().getTime());
    }


}
