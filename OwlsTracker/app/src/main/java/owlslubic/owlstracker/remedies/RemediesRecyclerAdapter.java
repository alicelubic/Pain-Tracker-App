package owlslubic.owlstracker.remedies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import owlslubic.owlstracker.R;
import owlslubic.owlstracker.main.MainActivity;
import owlslubic.owlstracker.models.Remedy;

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
        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: quantity is: " + currentItem.getQtyOrDegree());
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
        currentItem.setUsedToday(true);
        int preClickQuantity = currentItem.getQtyOrDegree();
        //increment item's quantity once per click
        currentItem.setQtyOrDegree(preClickQuantity + 1);
        int currentQuantity = currentItem.getQtyOrDegree();
        if (currentQuantity == 1) {
            //change color to indicate change
            holder.mCard.setBackgroundColor(mContext.getResources().getColor(R.color.light_turquoise));
        } else if (currentQuantity > 1) {
            //set textview to show quantity
            holder.mQuantity.setVisibility(View.VISIBLE);
            holder.mQuantity.setText(String.valueOf(currentQuantity));
        } else {

        }

    }

    @Override
    public int getItemCount() {
        return mRemedies.size();
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

}
