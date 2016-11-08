package owlslubic.owlstracker.models;

/**
 * Created by owlslubic on 11/7/16.
 */

public class Remedy extends WellnessTracker {
    /*
    * this class will cover:
    * advil
    * alieve
    * ativan
    * turmeric
    * herbal
    * walk
    * stretch
    * ice
    * */

    private boolean usedToday;
    private int qtyOrDegree;

    public Remedy(String name, String notes, String date, boolean usedToday, int qtyOrDegree) {
        super(name, notes, date);
        this.usedToday = usedToday;
        this.qtyOrDegree = qtyOrDegree;
    }

    public boolean wasUsedToday() {
        return usedToday;
    }

    public void setUsedToday(boolean usedToday) {
        this.usedToday = usedToday;
    }

    public int getQtyOrDegree() {
        return qtyOrDegree;
    }

    public void setQtyOrDegree(int qtyOrDegree) {
        this.qtyOrDegree = qtyOrDegree;
    }
}
