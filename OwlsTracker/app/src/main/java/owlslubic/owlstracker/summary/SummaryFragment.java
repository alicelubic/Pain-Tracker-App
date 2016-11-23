package owlslubic.owlstracker.summary;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.main.MainActivity;
import owlslubic.owlstracker.main.DBHelper;
import owlslubic.owlstracker.models.WellnessTracker;

/**
 * Created by owlslubic on 11/7/16.
 */

public class SummaryFragment extends Fragment {
    private static final String TAG = "SummaryFragment";
    private ListView mListView;
    private FrameLayout mContainer;
    private SwipeRefreshLayout mRefresh;
    private DBHelper mHelper;
    private SummaryCursorAdapter mAdapter;
    private TextView mNoData;


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
        View view = inflater.inflate(R.layout.frag_summary, container, false);
        mListView = (ListView) view.findViewById(R.id.listview_frag_summary);
        mContainer = (FrameLayout) view.findViewById(R.id.frame_detailfrag_container);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_summary);
        mHelper = DBHelper.getInstance(getContext());
        mAdapter = new SummaryCursorAdapter(getContext(),
                mHelper.getAllByDate(MainActivity.getTheDate()),
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mNoData = (TextView) view.findViewById(R.id.textview_no_summary_available);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ((MainActivity)getActivity()).getSupportActionBar().setTitle("today's summary");


        checkCursorCount(mAdapter.getCursor());
        mListView.setAdapter(mAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //launch a frag that shows the rest of the info!
//                launchEditFrag(getContext(), parent, position);

            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "long click!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //swipe to refresh list results
        setRefresher();

    }

    //preserve data for configuration change
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    public void setRefresher() {
        mRefresh.setColorSchemeColors(getContext().getResources().getColor(R.color.light_turquoise));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh.setRefreshing(false);
                //query the db again, swap in the cursor with updated data
                Cursor newCursor = mHelper.getAllByDate(MainActivity.getTheDate());
                Log.d(TAG, "onRefresh: new cursor count is: " + newCursor.getCount());

                checkCursorCount(newCursor);
                mAdapter.swapCursor(newCursor);

            }
        });

    }

    private void checkCursorCount(Cursor cursor) {
        if (cursor.getCount() == 0) {
            //if there's no data, display a "no summary available" message
            mNoData.setVisibility(View.VISIBLE);
            mNoData.setText(R.string.no_summary);
        } else {
            mNoData.setVisibility(View.GONE);
        }

    }

    public void launchEditFrag(Context context, AdapterView<?> parent, int position) {
        WellnessTracker currentItem = (WellnessTracker) parent.getAdapter().getItem(position);
        //get the info, set it as arguments when you're launchin le detailfrag
        Toast.makeText(context, "edit frag!", Toast.LENGTH_SHORT).show();
    }
}
