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
    private int imageId;

    public Remedy(String name, String notes, String date, boolean usedToday, int qtyOrDegree, int imageId) {
        super(name, notes, date);
        this.usedToday = usedToday;
        this.qtyOrDegree = qtyOrDegree;
        this.imageId = imageId;
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


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
