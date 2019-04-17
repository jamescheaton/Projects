package Model;

import javafx.beans.property.SimpleStringProperty;

public class ExhibitModel {
    private final SimpleStringProperty name;
    private final SimpleStringProperty size;
    private final SimpleStringProperty waterFeature;
    private final SimpleStringProperty numAnimals;

    public ExhibitModel(String name, String size, String waterFeature, String numAnimals){
        this.name =new SimpleStringProperty(name);
        this.numAnimals = new SimpleStringProperty(numAnimals);
        this.size = new SimpleStringProperty(size);
        this.waterFeature = new SimpleStringProperty(waterFeature);
    }

    public SimpleStringProperty nameProperty(){
        return name;
    }
    public SimpleStringProperty sizeProperty(){
        return size;
    }
    public SimpleStringProperty waterFeatureProperty(){
        return waterFeature;
    }
    public SimpleStringProperty numAnimalsProperty(){
        return numAnimals;
    }
}