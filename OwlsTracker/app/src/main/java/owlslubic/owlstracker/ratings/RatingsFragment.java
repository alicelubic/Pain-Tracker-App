package owlslubic.owlstracker.ratings;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.lang.reflect.Array;
import java.util.ArrayList;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.main.MainActivity;
import owlslubic.owlstracker.main.DBHelper;
import owlslubic.owlstracker.main.asyncTasks.WriteToDatabaseTask;
import owlslubic.owlstracker.models.Rating;
import owlslubic.owlstracker.models.RatingListSingleton;
import owlslubic.owlstracker.models.Remedy;
import owlslubic.owlstracker.models.SaveRatingsEvent;

import static owlslubic.owlstracker.main.DBHelper.ACTIVITY;
import static owlslubic.owlstracker.main.DBHelper.MED;

/**
 * Created by owlslubic on 11/7/16.
 */

public class RatingsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "RatingsFrag";
    private RecyclerView mRecyclerView;
    private TextView mPainTitle, mStressTitle, mStressQty, mSleepTitle, mSleepQty, mFoodTitle, mFoodQty;
    private SeekBar mStressBar, mSleepBar, mFoodBar;
    private ArrayList<Rating> mTodaysRatings;
    private ArrayList<Rating> mPainRatings;
    private String mDate;
    private PainRatingsAdapter mAdapter;

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

    @Subscribe
    public void writeToDB(SaveRatingsEvent event) {
        for (Rating rat : mPainRatings) {
            if (rat.isSelected()) {
                mTodaysRatings.add(rat);
            }
        }
        mTodaysRatings.add(new Rating("Stress",
                mDate, Integer.valueOf((String) mStressQty.getText()), 0, true));
        mTodaysRatings.add(new Rating("Sleep",
                mDate, Integer.valueOf((String) mSleepQty.getText()), 0, true));
        mTodaysRatings.add(new Rating("Food",
                mDate, Integer.valueOf((String) mFoodQty.getText()), 0, true));

        for (Rating rat : mTodaysRatings) {
            new WriteToDatabaseTask(getContext(), getView());
            rat.setSelected(false);

        }
        resetRatings();

    }

    public void resetRatings(){
        mStressBar.setProgress(0);
        mSleepBar.setProgress(0);
        mFoodBar.setProgress(0);
        mStressQty.setText("");
        mSleepQty.setText("");
        mFoodQty.setText("");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ratings, container, false);
        FrameLayout layout = (FrameLayout) view.findViewById(R.id.framelayout_frag_ratings);
        layout.setBackgroundColor(getResources().getColor(R.color.light_turquoise));
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_ratings_frag);
        mPainTitle = (TextView) view.findViewById(R.id.tv_pain_title);
        mStressTitle = (TextView) view.findViewById(R.id.tv_stress_title);
        mStressQty = (TextView) view.findViewById(R.id.tv_stress_qty);
        mSleepTitle = (TextView) view.findViewById(R.id.tv_sleep_title);
        mSleepQty = (TextView) view.findViewById(R.id.tv_sleep_qty);
        mFoodTitle = (TextView) view.findViewById(R.id.tv_food_title);
        mFoodQty = (TextView) view.findViewById(R.id.tv_food_qty);
        mStressBar = (SeekBar) view.findViewById(R.id.seekbar_stress);
        mSleepBar = (SeekBar) view.findViewById(R.id.seekbar_sleep);
        mFoodBar = (SeekBar) view.findViewById(R.id.seekbar_food);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDate = MainActivity.getTheDate();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mPainRatings = RatingListSingleton.getInstance(getContext()).getPainScaleList();
        mAdapter = new PainRatingsAdapter(mPainRatings, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mStressBar.setOnSeekBarChangeListener(this);
        mSleepBar.setOnSeekBarChangeListener(this);
        mFoodBar.setOnSeekBarChangeListener(this);

        mTodaysRatings = new ArrayList<>();
        /** the idea is that once each rating is selected, they will be added as new objects to this list
         * and theyll all be written to db COOL */
    }

    //preserve data for configuration change
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekbar_stress:
                mStressQty.setText(String.valueOf(progress));
                break;
            case R.id.seekbar_sleep:
                mSleepQty.setText(String.valueOf(progress));
                break;
            case R.id.seekbar_food:
                mFoodQty.setText(String.valueOf(progress));
                break;

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
