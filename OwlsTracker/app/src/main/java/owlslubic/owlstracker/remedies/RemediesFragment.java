package owlslubic.owlstracker.remedies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.main.MainActivity;
import owlslubic.owlstracker.models.DBHelper;
import owlslubic.owlstracker.models.Remedy;
import owlslubic.owlstracker.models.SaveRemediesEvent;

/**
 * Created by owlslubic on 11/7/16.
 */

public class RemediesFragment extends Fragment {
    private static final String TAG = "RemediesFrag";
    private RecyclerView mRecyclerView;
    private List<Remedy> mRemedies;
    private RemediesRecyclerAdapter mAdapter;

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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get the list of objects
        //pass to and set the adapter
        mRemedies = generateRemediesList();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new RemediesRecyclerAdapter(
                getContext(), mRemedies);
        mRecyclerView.setAdapter(mAdapter);


    }

    //when done button is clicked, event is fired from main activity
    @Subscribe
    public void writeRemediesToDB(SaveRemediesEvent event) {
        //write them to the database
        for (Remedy rem : mRemedies) {
            if (rem.wasUsedToday()) {
                DBHelper.getInstance(getContext()).writeToDatabase(rem, null);
            }
        }
        //then reset the cards by passing a fresh list to the adapter
        mRemedies = generateRemediesList();
        mAdapter.notifyDataSetChanged();
        //TODO add a snackbar to let the user know the deed is done

    }


    public List<Remedy> generateRemediesList() {
        ArrayList<Remedy> list = new ArrayList<>();
        list.add(new Remedy(getString(R.string.advil), null, MainActivity.getTheDate(), false, 0, R.drawable.advil));
        list.add(new Remedy(getString(R.string.aleve), null, MainActivity.getTheDate(), false, 0, R.drawable.aleve));
        list.add(new Remedy(getString(R.string.ativan), null, MainActivity.getTheDate(), false, 0, R.drawable.ativan));
        list.add(new Remedy(getString(R.string.herbal_supp), null, MainActivity.getTheDate(), false, 0, R.drawable.herb));
        list.add(new Remedy(getString(R.string.turmeric), null, MainActivity.getTheDate(), false, 0, R.drawable.turmeric));
        list.add(new Remedy(getString(R.string.remedy_walk), null, MainActivity.getTheDate(), false, 0, R.drawable.walk));
        list.add(new Remedy(getString(R.string.remedy_ice), null, MainActivity.getTheDate(), false, 0, R.drawable.ice));
        list.add(new Remedy(getString(R.string.remedy_stretch), null, MainActivity.getTheDate(), false, 0, R.drawable.stretch));
        return list;
    }
}
