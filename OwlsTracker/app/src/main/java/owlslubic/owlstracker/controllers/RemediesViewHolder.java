package owlslubic.owlstracker.controllers;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.List;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.models.Remedy;

/**
 * Created by owlslubic on 11/7/16.
 */

public class RemediesViewHolder extends RecyclerView.ViewHolder {
    private CardView mCard;
    private TextView mTitle, mLow, mMed, mHi;
    private LinearLayout mDegreeLayout, mQuantityLayout;
    private ImageView mImage;
    private ImageButton mDecrease,mIncrease;
    private EditText mQuantity;

    public RemediesViewHolder(View itemView) {
        super(itemView);
        mCard = (CardView) itemView.findViewById(R.id.cardview_remedies);
        mTitle = (TextView) itemView.findViewById(R.id.textview_remedies_card);
        mImage = (ImageView) itemView.findViewById(R.id.imageview_remedies_card);
        mDegreeLayout = (LinearLayout) itemView.findViewById(R.id.linearlayout_remedies_card_degrees);
        mLow = (TextView) itemView.findViewById(R.id.textview_degree_low);
        mMed = (TextView) itemView.findViewById(R.id.textview_degree_med);
        mHi = (TextView) itemView.findViewById(R.id.textview_degree_hi);
        mQuantityLayout = (LinearLayout) itemView.findViewById(R.id.linearlayout_remedies_card_quantity);
        mDecrease = (ImageButton) itemView.findViewById(R.id.imagebutton_qty_decrease);
        mIncrease = (ImageButton) itemView.findViewById(R.id.imagebutton_qty_increase);
        mQuantity = (EditText) itemView.findViewById(R.id.edittext_remedies_card_quantity);


    }

    class RemediesRecyclerAdapter extends RecyclerView.Adapter<RemediesViewHolder> implements View.OnClickListener {
        private List<Remedy> mRemedies;
        private Context mContext;

        public RemediesRecyclerAdapter(Context context, List<Remedy> remedies) {
            mRemedies = remedies;
            mContext = context;
        }

        @Override
        public RemediesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RemediesViewHolder(LayoutInflater.from
                    (parent.getContext()).inflate(R.layout.card_remedies, parent));
        }

        @Override
        public void onBindViewHolder(RemediesViewHolder holder, int position) {
            mCard.setOnClickListener(this);
            mImage.setOnClickListener(this);
            mLow.setOnClickListener(this);
            mMed.setOnClickListener(this);
            mHi.setOnClickListener(this);

        }

        @Override
        public int getItemCount() {
            return mRemedies.size();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cardview_remedies:
                    expandCard();
                    break;
                case R.id.imageview_remedies_card:
                    expandCard();
                    break;
                case R.id.textview_degree_low:
                    //remedy.setQtyOrDegree(1);
                    break;
                case R.id.textview_degree_med:
                    //remedy.setQtyOrDegree(2);
                    break;
                case R.id.textview_degree_hi:
                    //remedy.setQtyOrDegree(3);
                    break;
                case R.id.imagebutton_qty_decrease:
                    //int qty = remedy.getQtyOrDegree;
                    //remedy.setQtyOrDegree(qty-1);
                    break;
                case R.id.imagebutton_qty_increase:
                    //int qty = remedy.getQtyOrDegree;
                    //remedy.setQtyOrDegree(qty+1);
                    break;
                case R.id.edittext_remedies_card_quantity:
                    //don't know if this needs an onclick listener
                    //but whatever it does, it should getText() and set it to the object's qty attr
                    break;


            }
        }
        public boolean isMed(Context context, int position){
            String name = mRemedies.get(position).getName();
            if((name.equals(context.getString(R.string.remedy_walk)))||(
                    name.equals(context.getString(R.string.remedy_ice))||(
                            name.equals(context.getString(R.string.remedy_stretch))))){
                return false;
            }else{
                return true;
            }

        }

        public void expandCard(){
            if(isMed()){
                //it is a med, it should:
                if(mQuantityLayout.getVisibility()==View.VISIBLE){
                    mQuantityLayout.setVisibility(View.INVISIBLE);
                }else{
                    mQuantityLayout.setVisibility(View.VISIBLE);
                }
            }else{
                //but if it is an activity, it should:
                if(mDegreeLayout.getVisibility()==View.VISIBLE){
                    mDegreeLayout.setVisibility(View.INVISIBLE);
                }else{
                    mDegreeLayout.setVisibility(View.VISIBLE);
                }
            }
        }
    }


}
