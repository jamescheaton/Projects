package Controller;

import Model.DBconnector;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddAnimalController {
    @FXML private TextField nameField;
    @FXML private ChoiceBox<String> ExhibitchoiceBox;
    @FXML private ChoiceBox<String> TypeChoiceBox;
    @FXML private TextField exhibitField;
    @FXML private TextField SpeciesField;
    @FXML private Spinner<Integer> ageSpinner;
    private ObservableList<ObservableList> data;
    DBconnector DB;


    @FXML public void initialize() {
        ExhibitchoiceBox.getItems().addAll("Birds", "Jungle", "Mountainous", "Pacific", "Sahara");
        TypeChoiceBox.getItems().addAll("Mammal", "Bird", "Amphibian", "Reptile", "Fish", "Invertebrate");
    }

    @FXML protected void handleAddAnimalButtonAction(ActionEvent event) {
        String name = nameField.getText();
        String exhibit = ExhibitchoiceBox.getValue();
        String type = TypeChoiceBox.getValue();
        String species = SpeciesField.getText();
        int age = ageSpinner.getValue();

        if (name.length()==0||exhibit == null||type == null|| species.length()==0||age ==0){
            System.out.println("Details are missing");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Details are missing");
            alert.show();
        }else{
            Statement myStmt = null;
            DB = new DBconnector();
            // check if there is any key constraints.
            try {
                myStmt = DB.getCon().createStatement();
                ResultSet myRs = myStmt.executeQuery("select * from Animal where name = '" + name + "' and Species = '"+species+"'");
                if (myRs.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "This animal already exists.");
                    alert.show();
                    return;
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                String queryAddingToAnimal = "INSERT INTO Animal (name, Species, Age, Type)";
                String animalValue = "VALUES ('"+name+"', '"+species+"', '"+age+"', '"+type+"')";
                String queryAddingToHas = "INSERT INTO Has (animal_name, Species, Exhibit_name)";
                String hasValue = "VALUES ('"+name+"', '"+species+"', '"+exhibit+"')";
                System.out.println(animalValue+" has been ready for Has");

                myStmt = DB.getCon().createStatement();
                System.out.println(myStmt.execute(queryAddingToAnimal+" "+animalValue));
                System.out.println(animalValue+" has been inserted to Animal");

                System.out.println(myStmt.execute(queryAddingToHas+" "+hasValue));
                System.out.println(animalValue+" has been inserted to Has");

            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.closeConnection();
        }


    }

    @FXML protected void handleHomeButtonAction(ActionEvent event) {
        //return to home(admin functionality page)
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
    }
}