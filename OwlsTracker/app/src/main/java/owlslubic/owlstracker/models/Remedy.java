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

    /*for activities rather than meds, there are 3 degrees:
    * 1 = a little
    * 2 = some
    * 3 = a lot
    * */
    private boolean usedToday;
    private int qtyOrDegree;
    private int imageId;
    private String medOrActivity;

    public Remedy(String name, String notes, String date, boolean usedToday, int qtyOrDegree, int imageId, String medOrActivity) {
        super(name, notes, date);
        this.usedToday = usedToday;
        this.qtyOrDegree = qtyOrDegree;
        this.imageId = imageId;
        this.medOrActivity = medOrActivity;
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

    public String getMedOrActivity() {
        return medOrActivity;
    }

    public void setMedOrActivity(String medOrActivity) {
        this.medOrActivity = medOrActivity;
    }
}
