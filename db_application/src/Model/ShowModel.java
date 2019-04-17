package Model;

import javafx.beans.property.SimpleStringProperty;

public class ShowModel {
    private final SimpleStringProperty name;
    private final SimpleStringProperty exhibit;
    private final SimpleStringProperty date;

    public ShowModel(String name, String exhibit, String date){
        this.name =new SimpleStringProperty(name);
        this.exhibit = new SimpleStringProperty(exhibit);
        this.date = new SimpleStringProperty(date);
    }

    public SimpleStringProperty nameProperty(){ return name; }
    public SimpleStringProperty exhibitProperty(){ return exhibit; }
    public SimpleStringProperty dateFeatureProperty(){ return date; }
    public String getString(){
        return(name+" "+" "+exhibit+" "+date);
    }
}
