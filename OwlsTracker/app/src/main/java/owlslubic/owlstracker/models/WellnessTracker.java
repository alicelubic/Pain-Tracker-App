package owlslubic.owlstracker.models;

/**
 * Created by owlslubic on 11/7/16.
 */

public class WellnessTracker {
    private String name, notes;

    public WellnessTracker(String name, String notes) {
        this.name = name;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
