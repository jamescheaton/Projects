package Model;

import javafx.beans.property.SimpleStringProperty;

public class UserModel {
    private final SimpleStringProperty username;
    private final SimpleStringProperty email;

    public UserModel(String username, String email){
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
    }

    public SimpleStringProperty usernameProperty(){
        return username;
    }
    public SimpleStringProperty emailProperty(){
        return email;
    }
    public String getString(){
        return(username+" "+" "+email);
    }
}
