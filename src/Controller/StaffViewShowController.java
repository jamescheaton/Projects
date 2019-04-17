package Controller;

import Model.DBconnector;
import Model.ShowModel;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StaffViewShowController {

    @FXML TableView table;
    DBconnector DB;
    ObservableList<ShowModel> data;

    @FXML void initialize(){
        Statement myStmt = null;
        ResultSet myRs = null;
        data = FXCollections.observableArrayList();
        DB = new DBconnector();
        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("select Located_At.show_name, Located_At.Datetime, Located_At.Exhibit_Name from Located_At JOIN Hosts ON" +
                    " Located_At.show_name = Hosts.show_name AND Located_At.Datetime = Hosts.Datetime WHERE username = '" + UserInfo.username + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TableColumn nameColumn = new TableColumn("Show Name");
        nameColumn.setMinWidth(170);
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShowModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShowModel, String> param) {
                return param.getValue().nameProperty();
            }
        });
        TableColumn exhibitColumn = new TableColumn("Exhibit Name");
        exhibitColumn.setMinWidth(170);
        exhibitColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShowModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShowModel, String> param) {
                return param.getValue().exhibitProperty();
            }
        });
        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setMinWidth(170);
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShowModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShowModel, String> param) {
                return param.getValue().dateFeatureProperty();
            }
        });

        table.getColumns().setAll(nameColumn, exhibitColumn, dateColumn);

        try{
            while(myRs.next()){
                //System.out.println(myRs.getString(1));
                data.add(new ShowModel(myRs.getString(1),myRs.getString(3),myRs.getString(2)));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        table.setItems(data);
        DB.closeConnection();

    }
    public void handleHomeButtonAction(ActionEvent event) {
        try {
            Parent StaffPageParent = FXMLLoader.load(getClass().getResource("../View/staffPage.fxml"));
            Scene StaffPageScene = new Scene(StaffPageParent);
            Stage StaffPageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            StaffPageStage.hide();
            StaffPageStage.setScene(StaffPageScene);
            StaffPageStage.setTitle("Staff Page");
            StaffPageStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
