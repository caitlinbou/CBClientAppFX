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
    public static void displayApptAlert() {
        Alert alert;
        ObservableList<Appointment> allAppointmentList = DBAppointments.getAllAppointments();
        ObservableList<Appointment> futureAppt = FXCollections.observableArrayList();
        ObservableList<Appointment> upcomingAppt = FXCollections.observableArrayList();
        String showApptData = "";
        for (Appointment A : allAppointmentList) {
            LocalDateTime apptDateTime = A.getStart();
            LocalDate apptDate = apptDateTime.toLocalDate();
            LocalDateTime nowPlus15 = LocalDateTime.of(LocalDate.now(), LocalTime.now().plusMinutes(15));
            LocalDate dateNow = nowPlus15.toLocalDate();
            int dateCompare = apptDate.compareTo(dateNow);
            System.out.println(nowPlus15);
            int timeCompare = apptDateTime.compareTo(nowPlus15);
            if (timeCompare > 0) {
                futureAppt.add(A);
            } else if (dateCompare == 0){
                upcomingAppt.add(A);
                System.out.println("THIS IS UPCOMINGAPPT" + upcomingAppt);
                showApptData = (showApptData + "Appt ID " + A.getApptId() + " Date and Time " + A.getStart() + "\n");
                System.out.println("THIS IS STRING" + showApptData);
            }
        }
        if (upcomingAppt.isEmpty()) {
            alert = new Alert(Alert.AlertType.INFORMATION, "Sign on Successful! There are no upcoming appointments");
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION, "Sign on Successful! The following Appointments are within 15 minutes: " + showApptData);
        }
        alert.showAndWait();
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
