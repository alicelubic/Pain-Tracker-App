package owlslubic.owlstracker.controllers;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private ActionMenuView mMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    public void initViews() {
        //setting up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
//        mMenuView = (ActionMenuView) toolbar.findViewById(R.id.actionmenuitemview_main);
//        mMenuView.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return onOptionsItemSelected(item);
//            }
//        });
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

    }

    //for the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_add_specialty) {
            //launch a dialog box about it
            Toast.makeText(this, "make a dialog to do this!", Toast.LENGTH_SHORT).show();
            return true;
        } else if(item.getItemId()==R.id.item_done){
            //determine which page is showing
            //gather the data from that page
            //save it to database
            //let user know it has been done
            //set the page back to normal, i.e. un-expand the cards
            Toast.makeText(this, "done!", Toast.LENGTH_SHORT).show();


        }
        return super.onOptionsItemSelected(item);
    }


    // A method to find height of the status bar in order to adjust padding
    // so toolbar does not go under status bar
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
}
