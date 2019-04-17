package Controller;

import Model.AnimalModel;
import Model.DBconnector;
import Model.NoteModel;
import Model.UserInfo;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AnimalCareController {
    @FXML private Text nameTarget;
    @FXML private Text speciesTarget;
    @FXML private Text ageTarget;
    @FXML private Text exhibitTarget;
    @FXML private Text typeTarget;
    @FXML private TableView<NoteModel> table;
    @FXML private ObservableList<NoteModel> data;
    @FXML TableColumn staffColumn;
    @FXML TableColumn noteColumn;
    @FXML TableColumn dateColumn;
    @FXML TextField textField;
    AnimalModel animal;
    DBconnector DB;

    @FXML protected void initialize(){

    }
    @FXML protected void handleHomeButtonAction(ActionEvent event) {
        //return to home(visitor functionality page)
        try {
            Parent staffPageParent = FXMLLoader.load(getClass().getResource("../View/staffPage.fxml"));
            Scene staffPageScene = new Scene(staffPageParent);
            Stage staffPageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            staffPageStage.hide();
            staffPageStage.setScene(staffPageScene);
            staffPageStage.setTitle("Staff Page");
            staffPageStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void logNotes(){
        //format (username, Animal_name, Species, Time, Text)
        data = FXCollections.observableArrayList();
        String username = UserInfo.username;
        String animalName = animal.nameProperty().getValue().toString().trim();
        String species = animal.speciesProperty().getValue().toString().trim();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        String message = textField.getText().trim();
        Statement myStmt = null;
        ResultSet myRs = null;
        int success = 0;

        DB = new DBconnector();
        try {
            myStmt = DB.getCon().createStatement();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            success = myStmt.executeUpdate("insert into Note (username, Animal_name, Species, Time, Text) values ('"+ username+"','"+animalName+"','"+species+"','"+time +"','"+message+"')");
            myRs = myStmt.executeQuery("Select * from Note where Animal_name = '"+ animal.nameProperty().getValue() +"'");
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            while(myRs.next()){
                data.add(new NoteModel(myRs.getString(1),myRs.getString(5), myRs.getString(4)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        table.setItems(data);

    }

    void setAnimal(AnimalModel animal){
        this.animal = animal;
        nameTarget.setText(animal.nameProperty().getValue());
        speciesTarget.setText(animal.speciesProperty().getValue());
        ageTarget.setText(animal.ageProperty().getValue());
        exhibitTarget.setText(animal.exhibitProperty().getValue());
        typeTarget.setText(animal.typeProperty().getValue());
        Statement myStmt = null;
        ResultSet myRs = null;

        DB = new DBconnector();
        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("select * from Note");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        staffColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NoteModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NoteModel, String> param) {
                return param.getValue().nameProperty();
            }
        });
        noteColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NoteModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NoteModel, String> param) {
                return param.getValue().noteProperty();
            }
        });
        dateColumn.setCellValueFactory(new Callback<javafx.scene.control.TableColumn.CellDataFeatures<NoteModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(javafx.scene.control.TableColumn.CellDataFeatures<NoteModel, String> param) {
                return param.getValue().dateProperty();
            }
        });
        data = FXCollections.observableArrayList();
        System.out.println(animal);
        try{
            myRs = myStmt.executeQuery("Select * from Note where Animal_name = '"+ animal.nameProperty().getValue() +"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            while(myRs.next()){
                data.add(new NoteModel(myRs.getString(1),myRs.getString(5), myRs.getString(4)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        table.setItems(data);

    }
}
