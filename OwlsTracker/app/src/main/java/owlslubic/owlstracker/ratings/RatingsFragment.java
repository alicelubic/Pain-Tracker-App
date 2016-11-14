package owlslubic.owlstracker.ratings;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.main.MainActivity;
import owlslubic.owlstracker.main.DBHelper;
import owlslubic.owlstracker.models.Remedy;
import owlslubic.owlstracker.models.SaveRatingsEvent;

import static owlslubic.owlstracker.main.DBHelper.ACTIVITY;
import static owlslubic.owlstracker.main.DBHelper.MED;

/**
 * Created by owlslubic on 11/7/16.
 */

public class RatingsFragment extends Fragment {
    private static final String TAG = "RatingsFrag";
    Button mTempButton;

    public static RatingsFragment newInstance() {

        Bundle args = new Bundle();
        //put stuff in args here
        //so when i call this, if i need any info to be stashed in case
        //android recreates this fragment, i can do so aqui

        RatingsFragment fragment = new RatingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        MainActivity.getBusInstance().register(this);
    }

    //proving concept for how to save from each separate page
    @Subscribe
    public void writeToDB(SaveRatingsEvent event) {
        Toast.makeText(getContext(), "ratings!", Toast.LENGTH_SHORT).show();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ratings, container, false);
        FrameLayout layout = (FrameLayout) view.findViewById(R.id.framelayout_frag_ratings);
        layout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
//        mTempButton = (Button) view.findViewById(R.id.temp_button);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*

        mTempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                Cursor c = DBHelper.getInstance(getContext()).getAllByDate(MainActivity.getTheDate());
                Toast.makeText(getContext(), "count is " + c.getCount(), Toast.LENGTH_SHORT).show();
            }

        });
*/

    }
    //preserve data for configuration change
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    public void loadData() {


        SQLiteDatabase db = DBHelper.getInstance(getContext()).getReadableDatabase();
        //create the objects
        //this may change when, in the future, the user can add their own also...
        ArrayList<Remedy> list = new ArrayList<>();

        list.add(new Remedy(getString(R.string.advil), null, MainActivity.getTheDate(), false, 0, R.drawable.advil, MED));
        list.add(new Remedy(getString(R.string.aleve), null, MainActivity.getTheDate(), false, 0, R.drawable.aleve, MED));
        list.add(new Remedy(getString(R.string.ativan), null, MainActivity.getTheDate(), false, 0, R.drawable.ativan, MED));
        list.add(new Remedy(getString(R.string.herbal_supp), null, MainActivity.getTheDate(), false, 0, R.drawable.herb, MED));
        list.add(new Remedy(getString(R.string.turmeric), null, MainActivity.getTheDate(), false, 0, R.drawable.turmeric, MED));
        list.add(new Remedy(getString(R.string.remedy_walk), null, MainActivity.getTheDate(), false, 0, R.drawable.walk, ACTIVITY));
        list.add(new Remedy(getString(R.string.remedy_ice), null, MainActivity.getTheDate(), false, 0, R.drawable.ice, ACTIVITY));
        list.add(new Remedy(getString(R.string.remedy_stretch), null, MainActivity.getTheDate(), false, 0, R.drawable.stretch, ACTIVITY));

        //then write them to the database
        ContentValues vals = new ContentValues();
        for (Remedy rem : list) {
            vals.put(DBHelper.COL_NAME, rem.getName());
            vals.put(DBHelper.COL_NOTES, rem.getNotes());
            vals.put(DBHelper.COL_DATE, rem.getDate());
            vals.put(DBHelper.COL_QTY_OR_DEGREE, rem.getQtyOrDegree());
            vals.put(DBHelper.COL_MED_OR_ACT, rem.getMedOrActivity());
            vals.put(DBHelper.COL_IMAGE_ID, rem.getImageId());
            db.insertWithOnConflict(DBHelper.REMEDY_OPTIONS_TABLE, null, vals, SQLiteDatabase.CONFLICT_REPLACE);
        }
        Log.d(TAG, "loadDataOnFirstRun: without async");
    }
}
