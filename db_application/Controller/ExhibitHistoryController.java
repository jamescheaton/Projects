package Controller;

import Model.DBconnector;
import Model.ExhibitHistoryModel;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;
import java.util.Date;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExhibitHistoryController {
    @FXML
    private TextField exhibitName;
    @FXML
    private Spinner visitsMinSpinner;
    @FXML
    private Spinner visitsMaxSpinner;
    @FXML
    private TableView<ExhibitHistoryModel> table;
    @FXML private DatePicker showDatePicker;
    private ObservableList<ExhibitHistoryModel> data;
    DBconnector DB;

    @FXML
    public void initialize() {
        data = FXCollections.observableArrayList();
        Statement myStmt = null;
        ResultSet myRs = null;
        DB = new DBconnector();
        String current_username = UserInfo.username;
        System.out.println(current_username);

        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("SELECT Name, Datetime AS Time, COUNT(Datetime) AS Number_of_Visits"
                    + " FROM Exhibit_Visits WHERE username = " + "'"+current_username+"'" + " GROUP BY Name");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(125);
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ExhibitHistoryModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ExhibitHistoryModel, String> param) {
                return param.getValue().nameProperty();
            }
        });

        TableColumn dateColumn = new TableColumn("Time");
        dateColumn.setMinWidth(100);
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ExhibitHistoryModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ExhibitHistoryModel, String> param) {
                return param.getValue().dateProperty();
            }
        });

        TableColumn visitsColumn = new TableColumn("Number of Visits");
        visitsColumn.setMinWidth(100);
        visitsColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ExhibitHistoryModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ExhibitHistoryModel, String> param) {
                return param.getValue().visitsProperty();
            }
        });
        table.getColumns().addAll(nameColumn, dateColumn, visitsColumn);


        try{
            while(myRs.next()) {
                ExhibitHistoryModel visit = new ExhibitHistoryModel(myRs.getString(1),myRs.getString(2),myRs.getString(3));
                data.add(visit);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        table.setItems(data);
        //set mouse listener for table
        table.setRowFactory( tv -> {
            TableRow<ExhibitHistoryModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    //go to exhibit detail page
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/exhibit_detail.fxml"));
                        Parent animalDetailParent = loader.load();
                        ExhibitDetailController controller = loader.<ExhibitDetailController>getController();

                        //pass the exhibit to the controller
                        controller.setExhibit(row.getItem().nameProperty().get());
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
        DB.closeConnection();

    }

    @FXML protected void handleSearchButtonAction(ActionEvent event) {
        DB = new DBconnector();
        data = FXCollections.observableArrayList();

        Statement myStmt = null;
        ResultSet myRs = null;
        String exhibit;

        try{
            exhibit = exhibitName.getText();
        } catch (Exception e){
            exhibit = "";
            e.printStackTrace();
        }

        String current_username = UserInfo.username;
        int minVisits = (int) visitsMinSpinner.getValue();
        int maxVisits = (int) visitsMaxSpinner.getValue();

        // Can compare timestamp to string, so put selected date into format
        String dateChoice = showDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dateChoice = "'"+dateChoice+" 00:00'";

        String searchQuery;

        // can search by either exhibit name, date of visit, or both so need separate queries for 3 conditions
        // if nothing selected, just show all exhibit visits falling within default min and max (let = 0 & 10 for simplicity)

        if(exhibit.isEmpty()) {
            searchQuery = "SELECT Name, Time, Number_Of_Visits FROM"
                    + " (SELECT Name, Datetime AS Time, COUNT(Datetime) AS Number_Of_Visits FROM"
                    + " Exhibit_Visits WHERE username = " + "'"+current_username+"'"
                    + " AND Datetime > " + dateChoice + " GROUP BY NAME) AS T"
                    + " WHERE Number_Of_Visits >= "+minVisits+ " AND Number_Of_Visits <= "+maxVisits;
        }else {
            searchQuery = "SELECT Name, Time, Number_Of_Visits FROM"
                    + " (SELECT Name, Datetime AS Time, COUNT(Datetime) AS Number_Of_Visits FROM"
                    + " Exhibit_Visits WHERE username = " + "'" + current_username + "'"
                    + " AND Name = " + "'" + exhibit + "'"
                    + " AND Datetime > " + dateChoice  + " GROUP BY NAME) AS T"
                    + " WHERE Number_Of_Visits >= "+minVisits+ " AND Number_Of_Visits <= "+maxVisits;
        }

        try {
            myStmt = DB.getCon().createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try {
            myRs = myStmt.executeQuery(searchQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try{
            while(myRs.next()) {
                ExhibitHistoryModel visit = new ExhibitHistoryModel(myRs.getString(1),myRs.getString(2),myRs.getString(3));
                data.add(visit);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        table.setItems(data);
        DB.closeConnection();




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
