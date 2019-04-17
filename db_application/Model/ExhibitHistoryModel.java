package Model;

import javafx.beans.property.SimpleStringProperty;

public class ExhibitHistoryModel {
    private final SimpleStringProperty name;
    private final SimpleStringProperty date;
    private final SimpleStringProperty num_visits;

    public ExhibitHistoryModel(String name, String date, String num_visits){
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
        this.num_visits = new SimpleStringProperty(num_visits);
    }

    public SimpleStringProperty nameProperty(){
        return name;
    }
    public SimpleStringProperty dateProperty(){
        return date;
    }
    public SimpleStringProperty visitsProperty(){
        return num_visits;
    }

    public String getString(){
        return(name +" "+date+" "+" "+num_visits);
    }
}

