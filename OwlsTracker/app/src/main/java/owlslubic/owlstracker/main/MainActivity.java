package owlslubic.owlstracker.main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.otto.Bus;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.main.asyncTasks.LoadRemediesTask;
import owlslubic.owlstracker.models.SaveRatingsEvent;
import owlslubic.owlstracker.models.SaveRemediesEvent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String PREFS = "prefs";
    private ViewPager mPager;
    public ProgressBar mProgressBar;
    public static Bus mBus = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        new LoadRemediesTask(this, mProgressBar, getCurrentFocus()).execute();
        Log.d(TAG+" LoadRem", "onCreate: called LoadRemediesTask");

    }


    public void initViews() {
        //setting up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_done, null));

        //setting up pager
        mPager = (ViewPager) findViewById(R.id.main_viewpager);
        SomePagerAdapter adapter = new SomePagerAdapter(getSupportFragmentManager(), this);
        mPager.setAdapter(adapter);
        //setting up tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(mPager);
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

        //for loading db
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_main);
    }

    //for the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void submitData() {

        /**i realize that OTTO or any event bus is not intended for this,
         * but i wanted to get a sense of how it works in a simple way*/


        Bus bus = getBusInstance();

        switch (mPager.getCurrentItem()) {
            case 0://ratings
                bus.post(new SaveRatingsEvent());
                break;
            case 1://remedies
                bus.post(new SaveRemediesEvent());
                break;
            case 2://summary
                //do nothing
                Toast.makeText(this, "shouldn't even be here today", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_add_specialty) {
            //launch a dialog box about it
            Toast.makeText(this, "make a dialog to do this!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            submitData();
        } else if (item.getItemId() == R.id.dump_db) {
            DBHelper.getInstance(this).dumpTableData();
            Toast.makeText(this, "dumping...", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == R.id.edit_db) {
            Toast.makeText(this, "edit!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    // A method to find height of the status bar in order to adjust padding
    // so toolbar does not go under our translucent status bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static String getTheDate() {
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d yyyy");
        Log.i(TAG, "getDate: " + df.format(Calendar.getInstance().getTime()));
        return df.format(Calendar.getInstance().getTime());
    }

    public static Bus getBusInstance() {
        if (mBus == null) {
            mBus = new Bus();
        }
        return mBus;
    }

    /*
    public void loadDataOnFirstRun(){

        //currently having this async task makes it crash
        //TODO come back to this

        boolean b;
        SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        b = prefs.getBoolean("FIRST_RUN", false);
        //if this is the first run, load
        if (!b) {
            new LoadDatabaseTask().execute();
            Log.d(TAG, "loadDataOnFirstRun: task was called");
        }
        SharedPreferences.Editor editor = prefs.edit();
        //then edit prefs to show that the first run has already gone
        editor.putBoolean("FIRST_RUN", true);
        editor.apply();
    }

*/

}
