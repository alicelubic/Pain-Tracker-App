package owlslubic.owlstracker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import owlslubic.owlstracker.R;

/**
 * Created by owlslubic on 11/7/16.
 */

public class RatingsFragment extends Fragment {

    public static RatingsFragment newInstance() {

        Bundle args = new Bundle();
        //put stuff in args here
        //so when i call this, if i need any info to be stashed in case
        //android recreates this fragment, i can do so aqui

        RatingsFragment fragment = new RatingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ratings,container,false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.linearlayout_frag_ratings);
        layout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}
