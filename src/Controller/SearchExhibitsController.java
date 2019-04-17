package Controller;

import Model.DBconnector;
import Model.ExhibitModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SearchExhibitsController {
    @FXML private TextField nameField;
    @FXML private ChoiceBox<String> waterFeatureChoice;
    @FXML private Spinner sizeMinSpinner;
    @FXML private Spinner sizeMaxSpinner;
    @FXML private Spinner numAnimalMinSpinner;
    @FXML private Spinner numAnimalMaxSpinner;
    @FXML private TableView<ExhibitModel> table;
    private ObservableList<ExhibitModel> data;
    DBconnector DB;

    @FXML public void initialize(){
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(125);
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ExhibitModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ExhibitModel, String> param) {
                return param.getValue().nameProperty();
            }
        });

        TableColumn sizeColumn = new TableColumn("Size");
        sizeColumn.setMinWidth(125);
        sizeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ExhibitModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ExhibitModel, String> param) {
                return param.getValue().sizeProperty();
            }
        });

        TableColumn waterFeatureColumn = new TableColumn("Water Feature");
        waterFeatureColumn.setMinWidth(125);
        waterFeatureColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ExhibitModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ExhibitModel, String> param) {
                return param.getValue().waterFeatureProperty();
            }
        });

        TableColumn numAnimalsColumn = new TableColumn("Number of Animals");
        numAnimalsColumn.setMinWidth(145);
        numAnimalsColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ExhibitModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ExhibitModel, String> param) {
                return param.getValue().numAnimalsProperty();
            }
        });

        table.getColumns().addAll(nameColumn, sizeColumn, waterFeatureColumn, numAnimalsColumn);

        Statement myStmt = null;
        ResultSet myRs = null;
        DB = new DBconnector();
        data = FXCollections.observableArrayList();
        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("select name, Size, Water_feature from Exhibit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while(myRs.next()){
                //Iterate Row
                String wf = "No";
                if (myRs.getString(3).equals("1")){
                    wf = "Yes";
                }
                Statement numAnimalStmt = null;
                ResultSet numAnimalRs = null;
                try {
                    numAnimalStmt = DB.getCon().createStatement();
                    numAnimalRs
                            = numAnimalStmt.executeQuery("select count(*) as num from Has where Exhibit_name = '"+myRs.getString(1)+"'");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    while(numAnimalRs.next()){
                        Integer numAnimal = numAnimalRs.getInt("num");
                        data.add(new ExhibitModel(myRs.getString(1), myRs.getString(2), wf, numAnimal.toString()));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //populate table view
        table.setItems(data);


        //set mouse listener for table
        table.setRowFactory( tv -> {
            TableRow<ExhibitModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                        //go to exhibit detail page
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/exhibit_detail.fxml"));
                            Parent animalDetailParent = loader.load();
                            ExhibitDetailController controller = loader.<ExhibitDetailController>getController();

                            //pass the exhibit to the controller
                            controller.setExhibit(row.getItem());
                            Scene exhibitDetailScene = new Scene(animalDetailParent);
                            Stage exhibitDetailStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            exhibitDetailStage.hide();
                            exhibitDetailStage.setScene(exhibitDetailScene);
                            exhibitDetailStage.setTitle("Exhibit Detail");
                            exhibitDetailStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            });
            return row ;
        });

        sizeMinSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                System.out.println("New value: "+newValue));
        sizeMaxSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                System.out.println("New value: "+newValue));
        numAnimalMinSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                System.out.println("New value: "+newValue));
        numAnimalMaxSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                System.out.println("New value: "+newValue));




    }
    @FXML protected void handleSearchButtonAction(ActionEvent event) {
        data = FXCollections.observableArrayList();

        int sizeMin = (int) sizeMinSpinner.getValue();
        int sizeMax = (int) sizeMaxSpinner.getValue();
        int numAnimalMax = (int) numAnimalMaxSpinner.getValue();
        int numAnimalMin = (int) numAnimalMinSpinner.getValue();
        String name = nameField.getText();

        List<String> querySet= new ArrayList<>();
        if (name.length()>0){
            querySet.add("name = '"+name+"'");
        }
        querySet.add("Size > '"+sizeMin+"' and Size < '" + sizeMax + "'");
        if (waterFeatureChoice.getValue() != null){
            if(waterFeatureChoice.getValue().equals("Yes")){
                querySet.add("Water_feature = '1'");
            }else{
                querySet.add("Water_feature = '0'");
            }
        }
        String finalQuery = "select name, Size, Water_feature from Exhibit";
        if (querySet.size()>0){
            finalQuery = finalQuery+" where ";
        }
        String condition = "";
        for (String query: querySet){
            if(condition.equals("")){
                condition = condition+query;
            }else{
                condition = condition + " and " + query;
            }
        }
        finalQuery = finalQuery+ condition;

        Statement myStmt = null;
        ResultSet myRs = null;
        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery(finalQuery);
            System.out.println(finalQuery+" has been performed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //populate table view

        try {
            while(myRs.next()){
                //Iterate Row
                String wf = "No";
                if (myRs.getString(3).equals("1")){
                    wf = "Yes";
                }
                Statement numAnimalStmt = null;
                ResultSet numAnimalRs = null;
                try {
                    numAnimalStmt = DB.getCon().createStatement();
                    numAnimalRs
                            = numAnimalStmt.executeQuery("select count(*) as num from Has where Exhibit_name = '"+myRs.getString(1)+"'");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    while(numAnimalRs.next()){
                        Integer numAnimal = numAnimalRs.getInt("num");
                        System.out.println("num min = "+numAnimalMin+" num max = "+numAnimalMax+" num Animal: "+numAnimal);
                        if ((numAnimalMin) <= numAnimal && (numAnimal <= numAnimalMax)){
                            data.add(new ExhibitModel(myRs.getString(1), myRs.getString(2), wf, numAnimal.toString()));
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //FINALLY ADDED TO TableView
        table.setItems(data);


    }
    @FXML protected void handleHomeButtonAction(ActionEvent event) {
        // move onto visitor page
        DB.closeConnection();
        try {
            Parent visitorPageParent = FXMLLoader.load(getClass().getResource("../View/visitorPage.fxml"));
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

