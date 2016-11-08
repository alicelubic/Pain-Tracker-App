package owlslubic.owlstracker.controllers;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.models.Remedy;

/**
 * Created by owlslubic on 11/7/16.
 */

public class RemediesRecyclerAdapter extends RecyclerView.Adapter<RemediesViewHolder> implements View.OnClickListener {
    private List<Remedy> mRemedies;
    private Context mContext;

    public RemediesRecyclerAdapter(Context context, List<Remedy> remedies) {
        mRemedies = remedies;
        mContext = context;
    }

    @Override
    public RemediesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RemediesViewHolder(LayoutInflater.from
                (parent.getContext()).inflate(R.layout.card_remedies, parent, false));
    }

    @Override
    public void onBindViewHolder(final RemediesViewHolder holder, final int position) {
        setListeners(holder);
        Remedy currentItem = mRemedies.get(position);
        holder.mTitle.setText(currentItem.getName());
        holder.mImage.setImageDrawable(mContext.getDrawable(currentItem.getImageId()));
        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                expandCard(holder,position);
                holder.mCard.setBackgroundColor(mContext.getColor(R.color.light_turquoise));
            }
        });
        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                expandCard(holder,position);
                holder.mCard.setBackgroundColor(mContext.getColor(R.color.light_turquoise));

            }
        });
//        holder.mQuantity.setText(currentItem.getQtyOrDegree());



    }

    @Override
    public int getItemCount() {
        return mRemedies.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.cardview_remedies:
//                    expandCard();
//                Toast.makeText(mContext, "hey~~", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.imageview_remedies_card:
//                    expandCard();
//                break;
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

    public boolean isMed(Context context, int position) {
        String name = mRemedies.get(position).getName();
        if ((name.equals(context.getString(R.string.remedy_walk))) || (
                name.equals(context.getString(R.string.remedy_ice)) || (
                        name.equals(context.getString(R.string.remedy_stretch))))) {
            return false;
        } else {
            return true;
        }

    }

        public void expandCard(RemediesViewHolder holder, int position){
            if(isMed(mContext,position)){
                //it is a med, it should display the qty edittext and buttons

//                holder.mCard.setLayoutTransition();

                //make the layout visible
               /* holder.mQuantityLayout.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams quantParams = holder.mQuantityLayout.getLayoutParams();
                holder.mQuantityLayout.setLayoutParams(new RecyclerView.LayoutParams(quantParams.width, ViewGroup.LayoutParams.WRAP_CONTENT));
                //adjust the cardview to wrap content around new layout
                ViewGroup.LayoutParams cardParams = holder.mCard.getLayoutParams();
                holder.mCard.setLayoutParams(new RecyclerView.LayoutParams(cardParams.width, ViewGroup.LayoutParams.WRAP_CONTENT));

*/
               /* if(holder.mQuantityLayout.getVisibility()==View.VISIBLE){
                    holder.mQuantityLayout.setVisibility(View.INVISIBLE);
                }else{
                    holder.mQuantityLayout.setVisibility(View.VISIBLE);
                }*/
            }else{
                //but if it is an activity, it should display the color degree squares

                Toast.makeText(mContext, "deal with this in a moment!", Toast.LENGTH_SHORT).show();

                /*if(holder.mDegreeLayout.getVisibility()==View.VISIBLE){
                    holder.mDegreeLayout.setVisibility(View.INVISIBLE);
                }else{
                    holder.mDegreeLayout.setVisibility(View.VISIBLE);
                }*/
            }
        }

    public void setListeners(RemediesViewHolder holder){
//        holder.mCard.setOnClickListener(this);
        holder.mImage.setOnClickListener(this);
        holder.mLow.setOnClickListener(this);
        holder.mMed.setOnClickListener(this);
        holder.mHi.setOnClickListener(this);
        holder.mDecrease.setOnClickListener(this);
        holder.mIncrease.setOnClickListener(this);
    }
/*
    public int getQuantityFromEditText(RemediesViewHolder holder, Remedy rem){
        return Integer.valueOf(holder.mQuantity.getText().toString().trim());
    }*/


}
