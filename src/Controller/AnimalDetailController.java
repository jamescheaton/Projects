package Controller;

import Model.AnimalModel;
import Model.DBconnector;
import Model.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AnimalDetailController {
    @FXML private Text nameTarget;
    @FXML private Text speciesTarget;
    @FXML private Text ageTarget;
    @FXML private Text exhibitTarget;
    @FXML private Text typeTarget;
    AnimalModel animal;
    DBconnector DB;

    @FXML void initialize(){

    }
    @FXML protected void handleHomeButtonAction(ActionEvent event) {
        //return to home(visitor functionality page)
        try {
            if (UserInfo.role == UserInfo.userType.ADMIN){
                //Go To Admin Page

                try {
                    Parent AdminPageParent = FXMLLoader.load(getClass().getResource("../View/adminPage.fxml"));
                    Scene AdminPageScene = new Scene(AdminPageParent);
                    Stage AdminPageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    AdminPageStage.hide();
                    AdminPageStage.setScene(AdminPageScene);
                    AdminPageStage.setTitle("Admin Page");
                    AdminPageStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Parent visitorPageParent = FXMLLoader.load(getClass().getResource("../View/visitorPage.fxml"));
                Scene visitorPageScene = new Scene(visitorPageParent);
                Stage visitorPageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                visitorPageStage.hide();
                visitorPageStage.setScene(visitorPageScene);
                visitorPageStage.setTitle("Visitor Page");
                visitorPageStage.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setAnimal(AnimalModel animal){
        this.animal = animal;
        nameTarget.setText(animal.nameProperty().getValue());
        speciesTarget.setText(animal.speciesProperty().getValue());
        ageTarget.setText(animal.ageProperty().getValue());
        exhibitTarget.setText(animal.exhibitProperty().getValue());
        typeTarget.setText(animal.typeProperty().getValue());

    }

    public void handleDeleteAnimalButtonAction(ActionEvent event) {
        String name = animal.nameProperty().getValue();
        String species = animal.speciesProperty().getValue();
        DB = new DBconnector();
        Statement myStmt;
        ResultSet myRS;

        try {
            myStmt=DB.getCon().createStatement();
            myStmt.execute("DELETE FROM Animal WHERE name = '" + name + "' AND Species = '" + species + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DB.closeConnection();
    }
}
