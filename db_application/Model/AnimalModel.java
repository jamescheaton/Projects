package Model;

import javafx.beans.property.SimpleStringProperty;

public class AnimalModel {
    private final SimpleStringProperty name;
    private final SimpleStringProperty species;
    private final SimpleStringProperty type;
    private final SimpleStringProperty age;
    private final SimpleStringProperty exhibit;

    public AnimalModel(String name, String species, String type, String age, String exhibit){
        this.name = new SimpleStringProperty(name);
        this.species = new SimpleStringProperty(species);
        this.type = new SimpleStringProperty(type);
        this.age = new SimpleStringProperty(age);
        this.exhibit = new SimpleStringProperty(exhibit);
    }

    public SimpleStringProperty nameProperty(){
        return name;
    }
    public SimpleStringProperty speciesProperty(){
        return species;
    }
    public SimpleStringProperty typeProperty(){
        return type;
    }
    public SimpleStringProperty ageProperty(){
        return age;
    }
    public SimpleStringProperty exhibitProperty(){
        return exhibit;
    }

    String getString(){
        return(name +" "+type+" "+" "+species+" "+age + " " + exhibit);
    }
}
