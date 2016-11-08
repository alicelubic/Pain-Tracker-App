package owlslubic.owlstracker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.controllers.MainActivity;
import owlslubic.owlstracker.models.RatingScale;
import owlslubic.owlstracker.models.Remedy;
import owlslubic.owlstracker.models.SpecialtyTreatment;

/**
 * Created by owlslubic on 11/7/16.
 */

public class RemediesFragment extends Fragment {
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_remedies,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_remedies_frag);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get the list of objects
        //pass to and set the adapter



    }

    public List<Remedy> getRemediesList(){
        ArrayList<Remedy> list = new ArrayList<>();
        list.add(new Remedy("Advil", null, MainActivity.getTheDate(), false, 0, R.drawable.advil));
        list.add(new Remedy("Alieve", null, MainActivity.getTheDate(), false, 0, R.drawable.advil));
        list.add(new Remedy("Ativan", null, MainActivity.getTheDate(), false, 0, R.drawable.advil));
        list.add(new Remedy("Herbal", null, MainActivity.getTheDate(), false, 0, R.drawable.advil));
        list.add(new Remedy("Turmeric", null, MainActivity.getTheDate(), false, 0, R.drawable.advil));
        list.add(new Remedy(getString(R.string.remedy_walk), null, MainActivity.getTheDate(), false, 0, R.drawable.advil));
        list.add(new Remedy(getString(R.string.remedy_ice), null, MainActivity.getTheDate(), false, 0, R.drawable.advil));
        list.add(new Remedy(getString(R.string.remedy_stretch), null, MainActivity.getTheDate(), false, 0, R.drawable.advil));



        return list;
    }
}
