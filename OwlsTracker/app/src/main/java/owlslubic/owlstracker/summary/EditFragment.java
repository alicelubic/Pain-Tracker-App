package owlslubic.owlstracker.summary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import owlslubic.owlstracker.R;

/**
 * Created by owlslubic on 11/10/16.
 */

public class EditFragment extends Fragment implements View.OnClickListener{
    private EditText mEditName;
    private TextView mEditDate, mLow, mMed, mHi;
    private NumberPicker mNumPicker;
    private LinearLayout mDegreesLayout;
    private String mName, mDate;
    private int  mQtyOrDegs;
    private static final String NAME = "name";
    private static final String DATE = "date";
    private static final String QTY = "quantityOrDegrees";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_edit,container,false);
        defineViews(view);
        //getArguments
        mName = getArguments().getString(NAME);
        mDate = getArguments().getString(DATE);
        mQtyOrDegs = getArguments().getInt(QTY);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditName.setText(mName);
        mNumPicker.setValue(mQtyOrDegs);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textview_degree_low:
                //set the obj qty to 1
                //somehow show that it is selected
                break;
            case R.id.textview_degree_med:
                //qty = 2
                break;
            case R.id.textview_degree_hi:
                //qty = 3
                break;
        }

    }

    public void defineViews(View view){
        mEditName = (EditText) view.findViewById(R.id.edittext_edit_name);
        mEditDate = (TextView) view.findViewById(R.id.textview_edit_date);
        mNumPicker = (NumberPicker) view.findViewById(R.id.np_edit);
        mDegreesLayout = (LinearLayout) view.findViewById(R.id.linearlayout_edit_degrees);
        mLow = (TextView) view.findViewById(R.id.textview_degree_low);
        mMed = (TextView) view.findViewById(R.id.textview_degree_med);
        mHi = (TextView) view.findViewById(R.id.textview_degree_hi);
    }

    //preserve data for configuration change
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }
}
