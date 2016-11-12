package owlslubic.owlstracker.remedies;

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


class RemediesViewHolder extends RecyclerView.ViewHolder {
    CardView mCard;
    TextView mTitle, mQuantity;
    ImageView mImage;

    RemediesViewHolder(View itemView) {
        super(itemView);
        mCard = (CardView) itemView.findViewById(R.id.cardview_remedies);
        mTitle = (TextView) itemView.findViewById(R.id.textview_remedies_card);
        mImage = (ImageView) itemView.findViewById(R.id.imageview_remedies_card);
        mQuantity = (TextView) itemView.findViewById(R.id.textview_rem_card_quantity);

    }
}
