package Controller;

import Model.DBconnector;
import Model.ShowModel;
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
import java.util.*;


public class ViewShowsController {
    @FXML private TextField nameField;
    @FXML private ChoiceBox<String> exhibitChoiceBox;
    @FXML private DatePicker showDatePicker;
    @FXML private TableView<ShowModel> table;
    private ObservableList<ShowModel> data;
    DBconnector DB;

    @FXML protected void handleHomeButtonAction(ActionEvent event) {
        // move onto Admin Page
        try {
            Parent adminPageParent = FXMLLoader.load(getClass().getResource("../View/adminPage.fxml"));
            Scene adminPageScene = new Scene(adminPageParent);
            Stage adminPageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            adminPageStage.hide();
            adminPageStage.setScene(adminPageScene);
            adminPageStage.setTitle("Admin Page");
            adminPageStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void initialize(){
        exhibitChoiceBox.getItems().addAll("Birds", "Jungle", "Mountainous", "Pacific", "Sahara");

        //add columns to table
        Statement myStmt = null;
        ResultSet myRs = null;
        DB = new DBconnector();
        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("select show_name, Exhibit_name, Datetime from Located_At");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(125);
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShowModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShowModel, String> param) {
                return param.getValue().nameProperty();
            }
        });

        TableColumn exhibitColumn = new TableColumn("Exhibits");
        exhibitColumn.setMinWidth(125);
        exhibitColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShowModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShowModel, String> param) {
                return param.getValue().exhibitProperty();
            }
        });

        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setMinWidth(125);
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShowModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShowModel, String> param) {
                return param.getValue().dateFeatureProperty();
            }
        });
        table.getColumns().addAll(nameColumn, exhibitColumn, dateColumn);

        // gather the data
        data = FXCollections.observableArrayList();
        try {
            while (myRs.next()){
                System.out.println(myRs.getString(1));
                System.out.println(myRs.getString(2));
                System.out.println(myRs.getString(3));
                data.add(new ShowModel(myRs.getString(1), myRs.getString(2), myRs.getString(3)));
                System.out.println("Succesfully added to table");
            }

            //FINALLY ADDED TO TableView
            table.setItems(data);
            DB.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // make each row clickable
        String usernameToDelete = new String();
        table.setRowFactory(tv -> {
            TableRow<ShowModel> row = new TableRow<>();
            row.setOnMouseClicked(tableClickEvent -> {
                if (! row.isEmpty()) {
                    ShowModel rowData = row.getItem();
                    // usernameToDelete =(rowData.usernameProperty().toString());
                    System.out.println("Click on: "+rowData.getString());
                }
            });
            return row;
        });
    }

    // Do deletion with selected row.
    @FXML protected void handleRemoveButtonAction(ActionEvent event) {
        ShowModel selectedItem = table.getSelectionModel().getSelectedItem();

        // remove from the view
        table.getItems().remove(selectedItem);

        //remove from the actual database.
        String showname = selectedItem.nameProperty().getValue();

        Statement myStmt = null;
        ResultSet myRs = null;
        DB = new DBconnector();
        try {
            myStmt = DB.getCon().createStatement();
            System.out.println(myStmt.execute("DELETE FROM Staff_Show WHERE name = '"+showname+"'"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(showname+" has been removed");
        DB.closeConnection();
    }

    @FXML protected void handleSearchButtonAction(ActionEvent event) {
        String showname = nameField.getText();
        String exhibitChoice = exhibitChoiceBox.getValue();
        System.out.println("Search clicked with "+showname+", "+exhibitChoice+", "+showDatePicker.isPressed());

        // Parts of Query that might be needed
        List<String> query_parts= new ArrayList<>();
        if(showname.length()>0){
            query_parts.add("show_name = '" + showname + "'");
        }
        if(exhibitChoice != null){
            query_parts.add("Exhibit_name = '" + exhibitChoice + "'");
        }
        if(showDatePicker.getValue().toString()!=null){
            String date = showDatePicker.getValue().toString();
            query_parts.add("DATE(Datetime) = '" + date + "'");
        }
        String searchQuery = "";
        for (String part: query_parts){
            if(searchQuery.length()==0){
                searchQuery = searchQuery+part;
            } else {
                searchQuery = searchQuery+" and "+part;
            }
        }
        System.out.println("Query condition: "+searchQuery);

        // Actual search on database
        DB = new DBconnector();
        data = FXCollections.observableArrayList();
        Statement myStmt = null;
        ResultSet myRs = null;
        try {
            myStmt = DB.getCon().createStatement();
            if(searchQuery.length()==0){
                myRs = myStmt.executeQuery("select show_name, Exhibit_name, Datetime from Located_At");
            }else {
                myRs = myStmt.executeQuery("select show_name, Exhibit_name, Datetime from Located_At where " + searchQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (myRs.next()){
                System.out.println(myRs.getString(1));
                System.out.println(myRs.getString(2));
                System.out.println(myRs.getString(3));
                data.add(new ShowModel(myRs.getString(1), myRs.getString(2), myRs.getString(3)));
                System.out.println("Succesfully added to table");
            }

            //FINALLY ADDED TO TableView
            table.setItems(data);
            DB.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}