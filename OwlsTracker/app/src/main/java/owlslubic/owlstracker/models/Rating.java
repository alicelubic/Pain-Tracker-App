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

    private double rating;
    private int imageId;
    private boolean isSelected;

    public Rating(String name, String notes, String date, double rating, @Nullable int imageId, boolean isSelected) {
        super(name, notes, date);
        this.rating = rating;
        this.imageId = imageId;
        this.isSelected = isSelected;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
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
