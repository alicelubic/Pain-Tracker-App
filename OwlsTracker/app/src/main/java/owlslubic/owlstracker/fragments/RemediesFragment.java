package owlslubic.owlstracker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.controllers.MainActivity;
import owlslubic.owlstracker.controllers.RemediesRecyclerAdapter;
import owlslubic.owlstracker.controllers.RemediesViewHolder;
import owlslubic.owlstracker.models.RatingScale;
import owlslubic.owlstracker.models.Remedy;
import owlslubic.owlstracker.models.SpecialtyTreatment;

/**
 * Created by owlslubic on 11/7/16.
 */

public class RemediesFragment extends Fragment {
    private RecyclerView mRecyclerView;

    public static RemediesFragment newInstance() {
        Bundle args = new Bundle();
        //put stuff in args here
        //so when i call this, if i need any info to be stashed in case
        //android recreates this fragment, i can do so aqui

        RemediesFragment fragment = new RemediesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_remedies,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_remedies_frag);
//        LinearLayout layout = (LinearLayout) view.findViewById(R.id.linearlayout_frag_remedies);
//        layout.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get the list of objects
        //pass to and set the adapter
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        RemediesRecyclerAdapter adapter = new RemediesRecyclerAdapter(getContext(),generateRemediesList());
        mRecyclerView.setAdapter(adapter);



    }

    public List<Remedy> generateRemediesList(){
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
