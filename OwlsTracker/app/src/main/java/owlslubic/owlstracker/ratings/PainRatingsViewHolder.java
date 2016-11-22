package owlslubic.owlstracker.ratings;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import owlslubic.owlstracker.R;

/**
 * Created by owlslubic on 11/16/16.
 */

public class PainRatingsViewHolder extends RecyclerView.ViewHolder {
    CardView mCard;
    ImageView mImage;
    TextView mNum;
    public PainRatingsViewHolder(View itemView) {
        super(itemView);
        mCard = (CardView) itemView.findViewById(R.id.cardview_ratings);
        mImage = (ImageView) itemView.findViewById(R.id.imageview_ratings_card);
        mNum = (TextView) itemView.findViewById(R.id.textview_pain_rating);
    }
}
