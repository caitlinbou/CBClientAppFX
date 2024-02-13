package Controller;

import DAO.DBUsers;
import Model.LoginAttempt;
import helper.Logins;
import javafx.fxml.FXML;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController<userList> implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private Label errorFeedback;

    @FXML
    private Label timeZoneLbl;

    @FXML
    private Label language;

    @FXML
    private TextField password;

    @FXML
    private TextField userName;

    @FXML
    private Label nameLabel;

    @FXML
    private Button btnLabel;

    @FXML
    private Label passLabel;

    public String errorMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Get system time Zone
        String tz = TimeZone.getDefault().getID();
        //Get the time zone of the default time zone
        TimeZone timeZone = TimeZone.getTimeZone(tz);
        timeZoneLbl.setText(timeZone.getDisplayName());

        try {
            ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
                language.setText(rb.getString("hello"));
                errorMessage = rb.getString("error");
                nameLabel.setText(rb.getString("username"));
                passLabel.setText(rb.getString("password"));
                btnLabel.setText(rb.getString("submit"));

        }catch (Exception e){

            System.out.println("Error:" + e.getMessage());
        };
    }


    @FXML
    private void handleButtonAction (ActionEvent event){
        boolean outcome = false;
        LocalDateTime timeStamp;
        ObservableList<User> userList = DBUsers.getAllUsers();
        String nameInput = userName.getText();
        String passInput = password.getText();
      try{  for (User U : userList) {
            if (Objects.equals(U.getName(), nameInput) && Objects.equals(U.getPassword(), passInput)) {
                outcome = true;
                break;
            }else outcome = false;
        }
        timeStamp = LocalDateTime.now();
        LoginAttempt attempt;
        if (outcome) {
            attempt = new LoginAttempt(true, timeStamp);
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/ViewAppointments.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }else{
            attempt = new LoginAttempt(false, timeStamp);
            errorFeedback.setText(errorMessage);
        }
        Logins.writeLoginAttempt(attempt);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }

    }

}
