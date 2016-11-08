package owlslubic.owlstracker.models;

/**
 * Created by owlslubic on 11/7/16.
 */

public class SpecialtyTreatment extends WellnessTracker {
    /*
   * this class will cover:
   * RfD
   * cortisone injections
   * */
    private int quantity;

    public SpecialtyTreatment(String name, String notes, String date, int quantity) {
        super(name, notes, date);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
