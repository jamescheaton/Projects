package Model;

import javafx.beans.property.SimpleStringProperty;

public class ShowHistoryModel {
    private final SimpleStringProperty name;
    private final SimpleStringProperty time;
    private final SimpleStringProperty exhibit;

    public ShowHistoryModel(String name, String date, String exhibit){
        this.name = new SimpleStringProperty(name);
        this.time = new SimpleStringProperty(date);
        this.exhibit = new SimpleStringProperty(exhibit);
    }

    public SimpleStringProperty nameProperty(){
        return name;
    }
    public SimpleStringProperty timeProperty(){
        return time;
    }
    public SimpleStringProperty exhibitProperty(){
        return exhibit;
    }

    public String getString(){
        return(name +" "+time+" "+" "+exhibit);
    }
}