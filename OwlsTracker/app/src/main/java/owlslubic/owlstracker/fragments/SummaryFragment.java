package owlslubic.owlstracker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.controllers.SummaryCursorAdapter;

/**
 * Created by owlslubic on 11/7/16.
 */

public class SummaryFragment extends Fragment {
    private ListView mListView;

    public static SummaryFragment newInstance() {
        Bundle args = new Bundle();
        //put stuff in args here
        //so when i call this, if i need any info to be stashed in case
        //android recreates this fragment, i can do so aqui

        SummaryFragment fragment = new SummaryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_summary,container,false);
//        LinearLayout layout = (LinearLayout) view.findViewById(R.id.linearlayout_frag_summary);
//        layout.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        mListView = (ListView) view.findViewById(R.id.listview_frag_summary);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //create and set adapter
        //pass it a list of all database info
//        SummaryCursorAdapter adapter = new SummaryCursorAdapter(getContext(),)


    }
}
