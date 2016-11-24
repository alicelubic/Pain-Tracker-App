package owlslubic.owlstracker.remedies;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.main.MainActivity;
import owlslubic.owlstracker.main.DBHelper;
import owlslubic.owlstracker.main.asyncTasks.WriteToDatabaseTask;
import owlslubic.owlstracker.models.Remedy;
import owlslubic.owlstracker.models.RemedyListSingleton;
import owlslubic.owlstracker.models.SaveRemediesEvent;

import static owlslubic.owlstracker.main.DBHelper.ACTIVITY;
import static owlslubic.owlstracker.main.DBHelper.COL_DATE;
import static owlslubic.owlstracker.main.DBHelper.COL_IMAGE_ID;
import static owlslubic.owlstracker.main.DBHelper.COL_MED_OR_ACT;
import static owlslubic.owlstracker.main.DBHelper.COL_NAME;
import static owlslubic.owlstracker.main.DBHelper.COL_NOTES;
import static owlslubic.owlstracker.main.DBHelper.MED;
import static owlslubic.owlstracker.main.DBHelper.REMEDY_OPTIONS_TABLE;

/**
 * Created by owlslubic on 11/7/16.
 */

public class RemediesFragment extends Fragment {
    private static final String TAG = "RemediesFrag";
    private RecyclerView mRecyclerView;
    private ArrayList<Remedy> mRemedies;
    private RemediesRecyclerAdapter mAdapter;
    private FrameLayout mLayout;

    public static RemediesFragment newInstance() {
        Bundle args = new Bundle();
        //put stuff in args here
        //so when i call this, if i need any info to be stashed in case
        //android recreates this fragment, i can do so aqui

        RemediesFragment fragment = new RemediesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity.getBusInstance().register(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_remedies, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_remedies_frag);
        mLayout = (FrameLayout) view.findViewById(R.id.framelayout_frag_remedies);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get the list of objects//TODO decide how you want to be getting this list. DB? Singleton? pick!
        mRemedies = RemedyListSingleton.getInstance(getContext()).getFreshRemediesList();

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        //pass list to and set the adapter
        mAdapter = new RemediesRecyclerAdapter(
                getContext(), mRemedies);
        mRecyclerView.setAdapter(mAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));

        } else {
            mLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
//        .setAlpha((float) 0.5);

    }


    /**
     * when toolbar's done button is clicked, event is fired from main activity
     * i realize that the event in this method must match the event that is sent out by the bus
     * and by passing that event, i could manipulate it
     * but in making this a method, am i supposed to call it somewhere?
     */
    @Subscribe
    public void writeRemediesToDB(SaveRemediesEvent event) {
        //write them to the database
        for (Remedy rem : mRemedies) {
            if (rem.wasUsedToday()) {
                Log.d(TAG, "writeRemediesToDB: remWasUsedToday: " + rem.getName());
                new WriteToDatabaseTask(getContext(), getView()).execute(rem);
            }
        }

        /** so if i call notify even though i didnt actually change the data,
        * it'll call OnBindViewHolder and thus set my qty to  0 again it seems
        * because i'm not changing the data, i just need it to rebind and reset the UI accordingly*/
        mAdapter.notifyDataSetChanged();

    }


    //preserve data for configuration change
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }


}
