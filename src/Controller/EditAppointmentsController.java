package Controller;

import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;

public class EditAppointmentsController {
    @FXML
    private int contactID;

    @FXML
    private TextField contactIDtxt;

    @FXML
    private int customerID;

    @FXML
    private TextField customerIDtxt;

    @FXML
    private LocalDateTime end;

    @FXML
    private TextField endTxt;

    @FXML
    private LocalDateTime start;

    @FXML
    private TextField startTxt;

    @FXML
    private String title;

    @FXML
    private TextField titleTxt;

    @FXML
    private String type;

    @FXML
    private TextField typeTxt;

    public void sendAppointment(Appointment appointment) throws IOException {
        customerID = appointment.getCustId();
        contactID = appointment.getContactId();
        title = appointment.getTitle();
        type = appointment.getType();
        start = appointment.getStart();
        end = appointment.getEnd();

        customerIDtxt.setText(String.valueOf(customerID));
        contactIDtxt.setText(String.valueOf(contactID));
        titleTxt.setText(title);
        typeTxt.setText(type);

    }

    public void onClickSaveUpdate(ActionEvent actionEvent) {
    }

    public void onClickCancelUpdate(ActionEvent actionEvent) {
    }
}

