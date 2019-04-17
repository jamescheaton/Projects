package Controller;

import Model.DBconnector;
import Model.UserModel;
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

public class ViewStaffController {
    @FXML private TableView<UserModel> table;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    private ObservableList<UserModel> data;
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
        //add columns to table
        Statement myStmt = null;
        ResultSet myRs = null;
        DB = new DBconnector();
        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("select * from User");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TableColumn nameColumn = new TableColumn("Username");
        nameColumn.setMinWidth(125);
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<UserModel, String> param) {
                return param.getValue().usernameProperty();
            }
        });

        TableColumn emailColumn = new TableColumn("Email");
        emailColumn.setMinWidth(125);
        emailColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<UserModel, String> param) {
                return param.getValue().emailProperty();
            }
        });
        table.getColumns().addAll(nameColumn, emailColumn);

        // gather the data
        data = FXCollections.observableArrayList();
        Set<String> staffSet = new HashSet();
        Iterator itr = staffSet.iterator();
        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("select * from Staff");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (myRs.next()){
                staffSet.add(myRs.getString(1));
                System.out.println(myRs.getString(1) + "is added to the staffSet");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try{
            for (String staff : staffSet){
                try {
                    myStmt = DB.getCon().createStatement();
                    myRs = myStmt.executeQuery("select Username, Email from User where Username = '"+staff+"'");
                    try {
                        while (myRs.next()){
                            System.out.println(myRs.getString(1));
                            System.out.println(myRs.getString(2));
                            data.add(new UserModel(myRs.getString(1), myRs.getString(2)));
                            System.out.println("Succesfully added to table");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //FINALLY ADDED TO TableView
            table.setItems(data);
            DB.closeConnection();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // make each row clickable
        String usernameToDelete = new String();
        table.setRowFactory(tv -> {
            TableRow<UserModel> row = new TableRow<>();
            row.setOnMouseClicked(tableClickEvent -> {
                if (! row.isEmpty()) {
                    UserModel rowData = row.getItem();
                    // usernameToDelete =(rowData.usernameProperty().toString());
                    System.out.println("Click on: "+rowData.getString());
                }
            });
            return row;
        });

    }

    // Do deletion with selected row.
    @FXML protected void handleDeleteButtonAction(ActionEvent event) {
        UserModel selectedItem = table.getSelectionModel().getSelectedItem();

        // remove from the view
        table.getItems().remove(selectedItem);

        //remove from the actual database.
        String username = selectedItem.usernameProperty().getValue();

        Statement myStmt = null;
        ResultSet myRs = null;
        DB = new DBconnector();
        try {
            myStmt = DB.getCon().createStatement();
            myStmt.execute("delete from User where Username = '"+username+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(username+" has been removed");

    }

    // Search portion
    @FXML protected void handleSearchButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String email = emailField.getText();
        List<String> querySet= new ArrayList<>();
        if (username.length()>0){
            querySet.add("Username = '"+username+"'");
        }
        if (email.length()>0){
            querySet.add("Email = '"+email+"'");
        }
        String finalQuery = "select Username, Email from User natural join Staff";
        if (querySet.size()>0){
            finalQuery = finalQuery+" where ";
        }
        String queryPart = "";
        for (String query: querySet){
            if (queryPart.equals("")){
                queryPart = queryPart + query;
            } else {
                queryPart = queryPart +" and "+query;
            }
        }
        finalQuery = finalQuery+queryPart;

        Statement myStmt = null;
        ResultSet myRs = null;
        DB = new DBconnector();
        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery(finalQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        data.clear();
        try {
            while (myRs.next()){
                data.add(new UserModel(myRs.getString(1), myRs.getString(2)));
                System.out.println(myRs.getString(1) + " is added to the visitorSet");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setItems(data);
        DB.closeConnection();

    }
}
