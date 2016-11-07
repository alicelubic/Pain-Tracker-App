package owlslubic.owlstracker.models;

/**
 * Created by owlslubic on 11/7/16.
 */

public class RatingScale extends WellnessTracker {

    private double rating;

    public RatingScale(String name, String notes, double rating) {
        super(name, notes);
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
