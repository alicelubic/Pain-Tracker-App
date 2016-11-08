package owlslubic.owlstracker.models;

/**
 * Created by owlslubic on 11/7/16.
 */

public class RatingScale extends WellnessTracker {
    /*
    * this class will cover:
    * pain
    * sleep
    * food
    * stress
    * overall
    * */

    private double rating;

    public RatingScale(String name, String notes, String date, double rating) {
        super(name, notes, date);
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
