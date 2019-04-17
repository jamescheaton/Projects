package Model;

import javafx.beans.property.SimpleStringProperty;

public class NoteModel {
    private final SimpleStringProperty staffName;
    private final SimpleStringProperty note;
    private final SimpleStringProperty date;

    public NoteModel(String staffName, String note, String date){
        this.staffName =new SimpleStringProperty(staffName);
        this.note = new SimpleStringProperty(note);
        this.date = new SimpleStringProperty(date);
    }

    public SimpleStringProperty nameProperty(){ return staffName; }
    public SimpleStringProperty noteProperty(){ return note; }
    public SimpleStringProperty dateProperty(){ return date; }
}
