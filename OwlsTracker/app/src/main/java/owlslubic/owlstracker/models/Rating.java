package owlslubic.owlstracker.models;

import android.support.annotation.Nullable;

/**
 * Created by owlslubic on 11/7/16.
 */

public class Rating extends WellnessTracker {
    /*
    * this class will cover:
    * pain
    * sleep
    * food
    * stress
    * overall
    * */

    private int rating;
    private int imageId;
    private boolean isSelected;

    public Rating(String name, String date, int rating, @Nullable int imageId, boolean isSelected) {
        super(name, date);
        this.rating = rating;
        this.imageId = imageId;
        this.isSelected = isSelected;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
