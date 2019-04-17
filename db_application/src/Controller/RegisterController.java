package Controller;

import Model.DBconnector;
import Model.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {
    @FXML private Text actiontarget;
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordConfirmField;
    @FXML private PasswordField passwordField;


    DBconnector DB;
    String email;
    String password;
    String username;

    @FXML protected void handleRegisterVisitorButtonAction(ActionEvent event) {
        email = emailField.getText();
        Integer hashedPassword = (Integer) passwordField.getText().hashCode();
        password = passwordField.getText();
        username = usernameField.getText();
        if (password.length()<8){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Password is too short");
            alert.show();
            return;
        }
        if (!(password.equals(passwordConfirmField.getText()))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Passwords do not match");
            alert.show();
            return;
        }

        if(email.length()==0||password.length()==0||username.length()==0){
            System.out.println("Details are missing");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please fill up all the information");
            alert.show();
        } else {
            //all details are filled out. Time to query
            //check email format and hash password.

            password = hashedPassword.toString();
            Pattern pattern = Pattern.compile("[A-Za-z0-9]+@+[A-Za-z0-9]+\\.+[A-Za-z0-9]+$");
            Matcher m = pattern.matcher(email);
            if (! m.matches()) {
                System.out.println(email);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Email is invalid");
                alert.show();
                return;
            }

            DB = new DBconnector();

            Statement myStmt = null;
            ResultSet myRs = null;

            try {
                myStmt = DB.getCon().createStatement();
                myRs = myStmt.executeQuery("select * from User where Email = " + "'" + email + "'");
                if (myRs.next()) {
                    actiontarget.setText("This email is already registered");
                    myRs.beforeFirst();
                    return;
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            UserInfo.email = email;
            UserInfo.role = UserInfo.userType.VISITOR;
            UserInfo.username = username;
            String query = " insert into User (Email, Password, Username)"
                    + " values (?, ?, ?)";
            try {
                PreparedStatement statement = DB.getCon().prepareStatement(query);
                statement.setString(1, email);
                statement.setString(2, password);
                statement.setString(3, username);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                myStmt = DB.getCon().createStatement();
                myStmt.execute("insert into Visitor(USERNAME) VALUES ('" + username + "')");
            } catch (SQLException e) {
                e.printStackTrace();
            }

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

            DB.closeConnection();
        }

    }

    @FXML protected void handleRegisterStaffButtonAction(ActionEvent event) {
        email = emailField.getText();
        Integer hashedPassword = (Integer) passwordField.getText().hashCode();
        password = passwordField.getText();
        username = usernameField.getText();
        if (password.length()<8){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Password is too short");
            alert.show();
            return;
        }
        if (!(password.equals(passwordConfirmField.getText()))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Passwords do not match");
            alert.show();
            return;
        }
        if(email.length()==0||password.length()==0||username.length()==0){
            System.out.println("Details are missing");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please fill up all the information");
            alert.show();
        }else {
            //all details are filled out. Time to query
            //check email format and hash password.

            password = hashedPassword.toString();
            Pattern pattern = Pattern.compile("[A-Za-z0-9]+@+[A-Za-z0-9]+\\.+[A-Za-z0-9]+$");
            Matcher m = pattern.matcher(email);
            if (! m.matches()) {
                System.out.println(email);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Email is invalid");
                alert.show();
                return;
            }

            DB = new DBconnector();

            Statement myStmt = null;
            ResultSet myRs = null;

            try {
                myStmt = DB.getCon().createStatement();
                myRs = myStmt.executeQuery("select * from User where Email = " + "'" + email + "'");
                if (myRs.next()) {
                    actiontarget.setText("This email is already registered");
                    myRs.beforeFirst();
                    return;
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            String query = " insert into User (Email, Password, Username)"
                    + " values (?, ?, ?)";
            UserInfo.email = email;
            UserInfo.role = UserInfo.userType.STAFF;
            UserInfo.username = username;
            try {
                PreparedStatement statement = DB.getCon().prepareStatement(query);
                statement.setString(1, email);
                statement.setString(2, password);
                statement.setString(3, username);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                myStmt = DB.getCon().createStatement();
                myStmt.execute("insert into Staff(USERNAME) VALUES ('" + username + "')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

            DB.closeConnection();
        }

    }
}
