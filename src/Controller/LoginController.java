package Controller;

import DAO.DBAppointments;
import DAO.DBUsers;
import Model.Appointment;
import Model.LoginAttempt;
import helper.Logins;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * The LoginController provides access to the Login.fxml for presentation, and handles page actions that may occur. The Initialize function accesses the user's
 * computer timezone and language settings to display the relevant timezone on the login page upon loading, as well as the correct language based on computer settings.
 * (Limited to French and English based on the requirements provided).
 */
public class LoginController implements Initializable {
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
        //Get default time zone
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
        }
    }

    /**
     * The displayApptAlert function gets a list of all appointments from the database, and loops over the list to separate them into two new lists
     * based on whether they are to occur within 15 minutes or in greater than 15 minutes. It then provides successful sign on feedback, as well as a
     * notification as to whether there are appointments within 15 minutes or not. If there are, it provides appointment details.
     */
    public static void displayApptAlert() {
        Alert alert;
        ObservableList<Appointment> allAppointmentList = DBAppointments.getAllAppointments();
        ObservableList<Appointment> upcomingAppt = FXCollections.observableArrayList();
        String showApptData = "";
        System.out.println("Date/Time NOW:" + LocalDateTime.now());
        LocalDateTime nowPlus15 = LocalDateTime.of(LocalDate.now(), LocalTime.now().plusMinutes(15));
        for (Appointment A : allAppointmentList) {
            LocalDateTime apptDateTime = A.getStart();
            if (apptDateTime.isAfter(LocalDateTime.of(LocalDate.now(), LocalTime.now())) && apptDateTime.isBefore(nowPlus15)) {
                upcomingAppt.add(A);
                showApptData = (showApptData + "Appt ID: " + A.getApptId() + " Date and Time: " + A.getStart() + "\n");
            }
        }
        if (upcomingAppt.isEmpty()) {
            alert = new Alert(Alert.AlertType.INFORMATION, "Sign on Successful! There are no upcoming appointments");
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION, "Sign on Successful! The following Appointments are within 15 minutes: " + showApptData);
        }
        alert.showAndWait();
    }
    /**
     * The handleButtonAction function checks user input username and password against the database and either allows access to the application, or
     * notifies the user to re-enter their username and password. It calls the Logins.WriteLoginAttempt to record the attempt and whether it was
     * successful or not. If it is successful, it calls the above displayApptAlert, and then loads the ViewAppointments.fxml.
     */
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
            displayApptAlert();
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
