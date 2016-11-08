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


public class RemediesViewHolder extends RecyclerView.ViewHolder {
    public CardView mCard;
    public TextView mTitle, mLow, mMed, mHi;
    public LinearLayout mDegreeLayout, mQuantityLayout;
    public ImageView mImage;
    public ImageButton mDecrease, mIncrease;
    public EditText mQuantity;

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
}
