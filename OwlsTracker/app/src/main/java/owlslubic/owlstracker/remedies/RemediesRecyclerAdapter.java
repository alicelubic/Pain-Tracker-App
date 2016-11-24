package owlslubic.owlstracker.remedies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.List;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.main.MainActivity;
import owlslubic.owlstracker.main.DBHelper;
import owlslubic.owlstracker.models.Remedy;
import owlslubic.owlstracker.models.SaveRemediesEvent;
import owlslubic.owlstracker.models.UpdateRemediesEvent;

/**
 * Created by owlslubic on 11/7/16.
 */

class RemediesRecyclerAdapter extends RecyclerView.Adapter<RemediesViewHolder> {
    private static final String TAG = "RemediesRecyclerAdapter";
    private List<Remedy> mRemedies;
    private Context mContext;


    RemediesRecyclerAdapter(Context context, List<Remedy> remedies) {
        mRemedies = remedies;
        mContext = context;
        MainActivity.getBusInstance().register(this);
    }



    @Override
    public RemediesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RemediesViewHolder(LayoutInflater.from
                (parent.getContext()).inflate(R.layout.card_remedies, parent, false));
    }

    @Override
    public void onBindViewHolder(final RemediesViewHolder holder, final int position) {
        final Remedy currentItem = mRemedies.get(position);

        holder.mTitle.setText(currentItem.getName());
        holder.mImage.setImageDrawable(mContext.getDrawable(currentItem.getImageId()));
        if(currentItem.getQtyOrDegree()==0){
            holder.mCard.setBackgroundColor(mContext.getResources().getColor(R.color.purple));
        }
        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCounter(holder, currentItem);
            }
        });
        holder.mCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                resetCounter(holder, currentItem);
                return true;
            }
        });

        //resetting the data on bind because that's how i will refresh it after writing to db
        currentItem.setUsedToday(false);
        //reset card color
        holder.mCard.setBackgroundColor(mContext.getResources().getColor(R.color.purple));
        //hide quantity textview
        holder.mQuantity.setVisibility(View.GONE);
        //reset item quantity
        currentItem.setQtyOrDegree(0);


    }



    private void resetCounter(RemediesViewHolder holder, Remedy currentItem) {
        currentItem.setUsedToday(false);

        //reset card color
        holder.mCard.setBackgroundColor(mContext.getResources().getColor(R.color.purple));
        //hide quantity textview
        holder.mQuantity.setVisibility(View.GONE);
        //reset item quantity
        currentItem.setQtyOrDegree(0);
    }

    private void updateCounter(RemediesViewHolder holder, Remedy currentItem) {
        //if it's clicked at all, that means it's been used today, so:
        currentItem.setUsedToday(true);
        int preClickQuantity = currentItem.getQtyOrDegree();

        if (preClickQuantity == 0) {
            //reset color card
            holder.mCard.setBackgroundColor(mContext.getResources().getColor(R.color.purple));
            //hide quantity textview in case we don't need it yet...
            holder.mQuantity.setVisibility(View.GONE);
        }
        //increment item's quantity once per click
        currentItem.setQtyOrDegree(preClickQuantity + 1);
        int currentQuantity = currentItem.getQtyOrDegree();
        if (currentQuantity == 1) {
            //change color to indicate change
            holder.mCard.setBackgroundColor(mContext.getResources().getColor(R.color.light_turquoise));
        } else if (currentQuantity > 1) {
            //set textview to show quantity
            holder.mQuantity.setVisibility(View.VISIBLE);
            //if it's a med, show the qty, if it's an activity, show a verbal degree indicator
            if (currentItem.getMedOrActivity().equals(DBHelper.MED)) {
                holder.mQuantity.setText(String.valueOf(currentQuantity));
            } else {
                String degree = null;
                if (currentQuantity == 2) {
                    degree = mContext.getString(R.string.degree_2);
                } else if (currentQuantity == 3) {
                    degree = mContext.getString(R.string.degree_3);
                } else if (currentQuantity > 3) {
                    //only 3 degrees, we dont wanna go crazy here
                    currentItem.setQtyOrDegree(0);
                    holder.mCard.setBackgroundColor(mContext.getResources().getColor(R.color.purple));
                }
//                holder.mQuantity.setTextSize(mContext.getResources().getDimension(R.dimen.big_text));
                holder.mQuantity.setText(degree);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mRemedies.size();
    }



}
