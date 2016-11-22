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
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.main.MainActivity;
import owlslubic.owlstracker.main.DBHelper;
import owlslubic.owlstracker.models.Rating;
import owlslubic.owlstracker.models.Remedy;
import owlslubic.owlstracker.models.SaveRatingsEvent;

import static owlslubic.owlstracker.main.DBHelper.ACTIVITY;
import static owlslubic.owlstracker.main.DBHelper.MED;

/**
 * Created by owlslubic on 11/7/16.
 */

public class RatingsFragment extends Fragment {
    private static final String TAG = "RatingsFrag";
    private RecyclerView mRecyclerView;

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

    //proving concept for how to save from each separate page in the pager
    @Subscribe
    public void writeToDB(SaveRatingsEvent event) {
        Toast.makeText(getContext(), "ratings!", Toast.LENGTH_SHORT).show();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ratings, container, false);
        FrameLayout layout = (FrameLayout) view.findViewById(R.id.framelayout_frag_ratings);
        layout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_ratings_frag);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        PainRatingsAdapter adapter = new PainRatingsAdapter(getPainScaleList(),getContext());
        mRecyclerView.setAdapter(adapter);

    }

    //preserve data for configuration change
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    public ArrayList<Rating> getPainScaleList() {
        ArrayList<Rating> ratings = new ArrayList<>();

        ratings.add(new Rating("fsp0", null, null, 0.0, R.drawable.fsp0, false));
        ratings.add(new Rating("fsp2", null, null, 2.0, R.drawable.fsp2, false));
        ratings.add(new Rating("fsp4", null, null, 4.0, R.drawable.fsp4, false));
        ratings.add(new Rating("fsp6", null, null, 6.0, R.drawable.fsp6, false));
        ratings.add(new Rating("fsp8", null, null, 8.0, R.drawable.fsp8, false));
        ratings.add(new Rating("fsp10", null, null, 10.0, R.drawable.fsp10, false));


        return ratings;
    }

}
