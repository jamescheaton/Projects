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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SearchShowController {
    @FXML private TextField nameField;
    @FXML private ChoiceBox<String> exhibitChoice;
    @FXML private DatePicker datepicker;
    @FXML private TableView<ShowModel> table;
    @FXML private ObservableList<ShowModel> data;
    @FXML private ChoiceBox<String> sortChoice;
    @FXML private Label message;
    @FXML TableColumn nameColumn;
    @FXML TableColumn exhibitColumn;
    @FXML TableColumn dateColumn;
    DBconnector DB;
    String name;
    String date;
    String exhibit;
    String order;

    @FXML public void initialize(){
        Statement myStmt = null;
        ResultSet myRs = null;

        DB = new DBconnector();
        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("select * from Located_At");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShowModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShowModel, String> param) {
                return param.getValue().nameProperty();
            }
        });
        exhibitColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShowModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShowModel, String> param) {
                return param.getValue().exhibitProperty();
            }
        });


        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShowModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShowModel, String> param) {
                return param.getValue().dateFeatureProperty();
            }
        });
    }

    @FXML protected void handleSearchButtonAction(ActionEvent event){
        data = FXCollections.observableArrayList();
        Statement myStmt = null;
        ResultSet myRs = null;
        try {
            myStmt = DB.getCon().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(sortChoice.getValue()==null || sortChoice.getValue().equals("")){
            if(datepicker.getValue() == null){
                if(exhibitChoice.getValue()==null|| exhibitChoice.getValue().equals("")){
                    if(nameField.getLength()==0){
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At");
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    } else{
                        name = nameField.getText().trim();
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where show_name = '" +name + "'" );
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    exhibit = exhibitChoice.getValue().toString().trim();
                    if(nameField.getLength()==0){
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where Exhibit_name = '" + exhibit + "'");
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    } else{
                        name = nameField.getText().trim();
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where Exhibit_name = '" + exhibit + "' and show_name = '"+ name + "'");
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                }
            }else{
                date = datepicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                date = date+"%";
                System.out.print(date);
                if(exhibitChoice.getValue()==null || exhibitChoice.getValue().equals("")){
                    if(nameField.getLength()==0){
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where Datetime like '" + date+"'");
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    } else{
                        name = nameField.getText().trim();
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where show_name = '" +name + "' and Datetime like '" + date+"'");
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    exhibit = exhibitChoice.getValue().toString().trim();
                    if(nameField.getLength()==0){
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where Exhibit_name = '" + exhibit + "' and Datetime like '" + date+"'");
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    } else{
                        name = nameField.getText().trim();
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where Exhibit_name = '" + exhibit + "' and show_name = '"+ name + "' and Datetime like like '" + date+"'");
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }else{
            order = sortChoice.getValue().toString().trim();
            if(datepicker.getValue() == null){
                if(exhibitChoice.getValue()==null|| exhibitChoice.getValue().equals("")){
                    if(nameField.getLength()==0){
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At order by show_name "+order + ", Datetime " + order +", Exhibit_name " + order);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    } else{
                        name = nameField.getText().trim();
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where show_name = '" +name + "' order by show_name "+order + ", Datetime " + order +", Exhibit_name " + order );
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    exhibit = exhibitChoice.getValue().toString().trim();
                    if(nameField.getLength()==0){
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where Exhibit_name = '" + exhibit + "'order by show_name "+order + ", Datetime " + order +", Exhibit_name " + order);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    } else{
                        name = nameField.getText().trim();
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where Exhibit_name = '" + exhibit + "' and show_name = '"+ name + "'order by show_name "+order + ", Datetime " + order +", Exhibit_name " + order);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                }
            }else{
                date = datepicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                date = date+"%";
                System.out.print(date);
                if(exhibitChoice.getValue()==null || exhibitChoice.getValue().equals("")){
                    if(nameField.getLength()==0){
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where Datetime like '" + date+"'order by show_name "+order + ", Datetime " + order +", Exhibit_name " + order);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    } else{
                        name = nameField.getText().trim();
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where show_name = '" +name + "' and Datetime like '" + date+"'order by show_name "+order + ", Datetime " + order +", Exhibit_name " + order);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    exhibit = exhibitChoice.getValue().toString().trim();
                    if(nameField.getLength()==0){
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where Exhibit_name = '" + exhibit + "' and Datetime like '" + date+"'order by show_name "+order + ", Datetime " + order +", Exhibit_name " + order);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    } else{
                        name = nameField.getText().trim();
                        try{
                            myRs = myStmt.executeQuery("select * from Located_At where Exhibit_name = '" + exhibit + "' and show_name = '"+ name + "' and Datetime like like '" + date+"'order by show_name "+order + ", Datetime " + order +", Exhibit_name " + order);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

        try{
            while(myRs.next()){
                //System.out.println(myRs.getString(1));
                data.add(new ShowModel(myRs.getString(1),myRs.getString(3),myRs.getString(2)));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        table.setItems(data);
        //DB.closeConnection();
        //Stage curStage = (Stage)nameField.getScene().getWindow();
        table.setRowFactory(tv -> {
            TableRow<ShowModel> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())){
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("exhibit_detail.fxml"));
                        Parent exhibitDetailParent = loader.load();
                        ExhibitDetailController exhibitDetailController = loader.<ExhibitDetailController>getController();

                        exhibitDetailController.setExhibit(row.getItem().exhibitProperty().getValue());
                        Scene exhibitDetailScene = new Scene(exhibitDetailParent);
                        Stage exhibitDetailStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        exhibitDetailStage.hide();
                        exhibitDetailStage.setScene(exhibitDetailScene);
                        exhibitDetailStage.setTitle("Exhibit Detail");
                        exhibitDetailStage.show();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });


    }

    @FXML public void handleLogButtonAction(ActionEvent event){
        Statement myStmt = null;
        ResultSet myRs = null;
        int success = 0;
        String username = UserInfo.username;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        ShowModel showModel = table.getSelectionModel().getSelectedItem();
        //System.out.println(showModel.nameProperty().get());
        name = showModel.nameProperty().get();
        exhibit = showModel.exhibitProperty().get();
        date = showModel.dateFeatureProperty().get();
        System.out.println(dtf.format(now));
        System.out.println(date.compareTo(dtf.format(now)));
        System.out.println(date);
        try {
            myStmt = DB.getCon().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(date.compareTo(dtf.format(now))<0){
            message.setText("please choose a date ahead");
        } else {
            try{
                success = myStmt.executeUpdate("insert into Show_Visits (username, name, datetime) values('"+username+"','"+name+"','"+date+"')");
                success = myStmt.executeUpdate("insert into Exhibit_Visits (username, Name, Datetime) values('"+username+"','"+exhibit+"','"+date+"')");
                message.setText("");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        System.out.println(username);
        System.out.println(name);


        System.out.println(success);


    }

    @FXML public void handleHomeButtonAction(ActionEvent actionEvent) {

        DB.closeConnection();
        try {
            Parent visitorPageParent = FXMLLoader.load(getClass().getResource("../View/visitorPage.fxml"));
            Scene visitorPageScene = new Scene(visitorPageParent);
            Stage visitorPageStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            visitorPageStage.hide();
            visitorPageStage.setScene(visitorPageScene);
            visitorPageStage.setTitle("Visitor Page");
            visitorPageStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
