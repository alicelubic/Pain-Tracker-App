package owlslubic.owlstracker.controllers;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    public void initViews() {
        //setting up pager
        mPager = (ViewPager) findViewById(R.id.main_viewpager);
        SomePagerAdapter adapter = new SomePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);
        //setting up tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Scales"));
        tabLayout.addTab(tabLayout.newTab().setText("Meds"));
        tabLayout.addTab(tabLayout.newTab().setText("Activites"));
        tabLayout.addTab(tabLayout.newTab().setText("Summary"));
        //set tab bar gravity
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //change page when tab selected
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void createExampleData() {

        SpecialtyTreatment rfD = new SpecialtyTreatment(
                "Radiofrequency Denervation", null, getTheDate(), 6);
        RatingScale pain = new RatingScale(
                "Pain Scale", null, getTheDate(), 9.0);//9.0 will correspond with a pain face
        Remedy pot = new Remedy("Herbal", null, getTheDate(), true, 5,0);
        //then add to database
        DBHelper helper = DBHelper.getInstance(MainActivity.this);
        helper.writeToDatabase(pot, null, null);
        helper.writeToDatabase(null, pain, null);
        helper.writeToDatabase(null, null, rfD);
    }

    public static String getTheDate() {
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d yyyy");
        Log.i(TAG, "getDate: " + df.format(Calendar.getInstance().getTime()));
        return df.format(Calendar.getInstance().getTime());
    }


}
