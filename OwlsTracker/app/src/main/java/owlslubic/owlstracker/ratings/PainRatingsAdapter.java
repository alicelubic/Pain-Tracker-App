package owlslubic.owlstracker.ratings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.models.Rating;

/**
 * Created by owlslubic on 11/16/16.
 */

class PainRatingsAdapter extends RecyclerView.Adapter<PainRatingsViewHolder> {
    private ArrayList<Rating> mRatings;
    private Context mContext;

    PainRatingsAdapter(ArrayList<Rating> mRatings, Context mContext) {
        this.mRatings = mRatings;
        this.mContext = mContext;
    }

    @Override
    public PainRatingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PainRatingsViewHolder(LayoutInflater.from
                (parent.getContext()).inflate(R.layout.card_painscale, parent, false));
    }

    @Override
    public void onBindViewHolder(final PainRatingsViewHolder holder, int position) {
        final Rating currentItem = mRatings.get(position);

        holder.mImage.setImageDrawable(mContext.getDrawable(currentItem.getImageId()));
        holder.mNum.setText(String.valueOf((int) currentItem.getRating()));
        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change color to mark it as selected
                holder.mCard.setBackgroundColor(mContext.getResources().getColor(R.color.light_turquoise));
//                holder.mNum.setTextSize(holder.mNum.getTextSize() + 1);
                //TODO devise a way to select this so it can be saved alongside the others like remedies
                //only one can be selected
                //currentItem.setSelected();

            }
        });
        holder.mCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.mCard.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//                holder.mNum.setTextSize(holder.mNum.getTextSize() - 1);

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRatings.size();
    }
}
