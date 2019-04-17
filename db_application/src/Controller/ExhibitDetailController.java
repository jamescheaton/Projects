package Controller;

import Model.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ExhibitDetailController {
    @FXML Text nameTarget;
    @FXML Text sizeTarget;
    @FXML Text wfTarget;
    @FXML Text numAnimalsTarget;
    @FXML TableView table;
    ExhibitModel exhibit;
    ExhibitHistoryModel exhibitHistory;
    DBconnector DB;
    ObservableList<AnimalModel> data;

    @FXML void initialize(){
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(264);
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AnimalModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AnimalModel, String> param) {
                return param.getValue().nameProperty();
            }
        });

        TableColumn speciesColumn = new TableColumn("Species");
        speciesColumn.setMinWidth(264);
        speciesColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AnimalModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AnimalModel, String> param) {
                return param.getValue().speciesProperty();
            }
        });
        table.getColumns().addAll(nameColumn, speciesColumn);


        //set mouse listener
        table.setRowFactory( tv -> {
            TableRow<AnimalModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    //change to animal detail activity
                    if(UserInfo.role == UserInfo.userType.VISITOR) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/AnimalDetail.fxml"));
                            Parent animalDetailParent = loader.load();
                            AnimalDetailController controller = loader.<AnimalDetailController>getController();
                            //pass animal to the controller
                            controller.setAnimal(row.getItem());
                            Scene animalDetailScene = new Scene(animalDetailParent);
                            Stage animalDetailStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            animalDetailStage.hide();
                            animalDetailStage.setScene(animalDetailScene);
                            animalDetailStage.setTitle("Animal Detail");
                            animalDetailStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (UserInfo.role == UserInfo.userType.STAFF){
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("AnimalCare.fxml"));
                            Parent animalCareParent = loader.load();
                            AnimalCareController controller = loader.<AnimalCareController>getController();
                            controller.setAnimal(row.getItem());
                            Scene animalCareScene = new Scene(animalCareParent);
                            Stage animalCareStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            animalCareStage.hide();
                            animalCareStage.setScene(animalCareScene);
                            animalCareStage.setTitle("Animal Detail");
                            animalCareStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            return row ;
        });
    }

    public void handleLogVisitButtonAction(ActionEvent event) {
        Statement myStmt = null;
        ResultSet myRs = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        DB = new DBconnector();

        try {
            myStmt = DB.getCon().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        try{
            myStmt.execute("insert into Exhibit_Visits (username, Name, Datetime) values('"+UserInfo.username+"','"+exhibit.nameProperty().getValue()+"','"+ now +"')");
            }catch (SQLException e){
            e.printStackTrace();
        }
        DB.closeConnection();
    }





    public void setExhibit(ExhibitModel exhibit){
        this.exhibit = exhibit;

        nameTarget.setText(exhibit.nameProperty().getValue());
        sizeTarget.setText(exhibit.sizeProperty().getValue());
        wfTarget.setText(exhibit.waterFeatureProperty().getValue());
        numAnimalsTarget.setText(exhibit.numAnimalsProperty().getValue());
        data = FXCollections.observableArrayList();
        DB = new DBconnector();
        Statement myStmt;
        ResultSet myRs;
        //combine strings and execute query
        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("select * from AnimalSearchView where Exhibit_Name = '" + exhibit.nameProperty().getValue() + "'");

            while (myRs.next()) {
                //Iterate Row
                AnimalModel animal = new AnimalModel(myRs.getString(1), myRs.getString(2), myRs.getString(4), myRs.getString(3), myRs.getString(5));
                data.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //FINALLY ADDED TO TableView
        table.setItems(data);
        DB.closeConnection();


    }

    void setExhibit(String exhibitName){
        DB = new DBconnector();
        nameTarget.setText(exhibitName);
        ResultSet myRs;
        Statement myStmt;
        String wf = "No";
        String size = "0";
        data = FXCollections.observableArrayList();
        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("SELECT * FROM Exhibit WHERE NAME = '" + exhibitName +"'");
            if (myRs.next()){
                sizeTarget.setText(myRs.getString(3));
                size = myRs.getString(3);
                String temp = "No";
                if (myRs.getInt(2) == 1){
                    temp="Yes";
                    wf = "Yes";
                }
                wfTarget.setText(temp);
            }
            Statement numAnimalStmt = null;
            ResultSet numAnimalRs = null;
            try {
                numAnimalStmt = DB.getCon().createStatement();
                numAnimalRs
                        = numAnimalStmt.executeQuery("select count(*) as num from Has where Exhibit_name = '"+exhibitName+"'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(numAnimalRs.next()){
                    Integer numAnimal = numAnimalRs.getInt("num");
                    numAnimalsTarget.setText(numAnimal.toString());
                    System.out.println(numAnimal.toString());
                    this.exhibit = new ExhibitModel(exhibitName, size, wf, numAnimal.toString());

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }




            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("select * from AnimalSearchView where Exhibit_Name = '" + exhibitName + "'");

            while (myRs.next()) {
                //Iterate Row
                AnimalModel animal = new AnimalModel(myRs.getString(1), myRs.getString(2), myRs.getString(4), myRs.getString(3), myRs.getString(5));
                data.add(animal);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        table.setItems(data);
        DB.closeConnection();
    }

    @FXML protected void handleHomeButtonAction(ActionEvent event) {
        // move onto visitor page
        DB.closeConnection();
        try {
            Parent visitorPageParent = FXMLLoader.load(getClass().getResource("visitorPage.fxml"));
            Scene visitorPageScene = new Scene(visitorPageParent);
            Stage visitorPageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            visitorPageStage.hide();
            visitorPageStage.setScene(visitorPageScene);
            visitorPageStage.setTitle("Visitor Page");
            visitorPageStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

