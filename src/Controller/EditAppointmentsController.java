package Controller;

import DAO.DBAppointments;
import DAO.DBContacts;
import DAO.DBCustomers;
import DAO.DBUsers;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class EditAppointmentsController {

    Stage stage;
    Parent scene;
    @FXML
    private TextField editApptId;

    @FXML
    private ComboBox<Contact> editContact;

    @FXML
    private TextField editCustId;

    @FXML
    private TextField editDescription;

    @FXML
    private DatePicker editStartDate;

    @FXML
    private TextField editStartTime;

    @FXML
    private TextField editLocation;

    @FXML
    private DatePicker editEndDate;

    @FXML
    private TextField editEndTime;

    @FXML
    private TextField editTitle;

    @FXML
    private TextField editType;

    @FXML
    private ComboBox<User> editUserId;

    @FXML
    void handleEndDate(ActionEvent event) {

    }

    @FXML
    void handleStartDate(ActionEvent event) {

    }

    @FXML
    void handleCancel(ActionEvent event) {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/ViewAppointments.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }


    Appointment thisAppt;
    int apptId, contactId, custId, userId;
    String title, description, location, type;
    Timestamp start;
    Timestamp end;

    public void sendAppointment(Appointment appointment) throws IOException, SQLException {
        thisAppt = appointment;
        ObservableList<Contact> selectedContact = DBContacts.getContactById(thisAppt.getContactId());
        ObservableList<Contact> contactList = DBContacts.getAllContacts();
        ObservableList<User> selectedUser = DBUsers.getUserById(thisAppt.getUserId());
        ObservableList<User> userList = DBUsers.getAllUsers();

        editApptId.setText(String.valueOf(thisAppt.getApptId()));
        editCustId.setText(String.valueOf(thisAppt.getCustId()));
        editContact.setValue(selectedContact.get(0));
        editContact.setItems(contactList);
        editUserId.setValue(selectedUser.get(0));
        editUserId.setItems(userList);
        editTitle.setText(thisAppt.getTitle());
        editLocation.setText(thisAppt.getLocation());
        editType.setText(thisAppt.getType());
        editDescription.setText(thisAppt.getDescription());
        LocalDateTime start = thisAppt.getStart();
        LocalTime startTime = start.toLocalTime();
        LocalDate startDate = start.toLocalDate();
        editStartTime.setText(String.valueOf(startTime));
        editStartDate.setValue(startDate);
        LocalDateTime end = thisAppt.getEnd();
        LocalTime endTime = end.toLocalTime();
        LocalDate endDate = end.toLocalDate();
        editEndTime.setText(String.valueOf(endTime));
        editEndDate.setValue(endDate);

    }
    @FXML
    void handleSubmit(ActionEvent event) throws IOException, SQLException {
        apptId = Integer.parseInt(editApptId.getText());
        title = editTitle.getText();
        description = editDescription.getText();
        type = editType.getText();
        location = editLocation.getText();
        contactId = editContact.getValue().getContactId();
        userId = editUserId.getValue().getId();
        custId = Integer.parseInt(editCustId.getText());
        String startTimeText = editStartTime.getText();
        String startDateText = editStartDate.getValue().toString();
        LocalDate startDate = LocalDate.parse(startDateText);
        LocalTime startTime = LocalTime.parse(startTimeText);
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        String endTimeText = editEndTime.getText();
        String endDateText = editEndDate.getValue().toString();
        LocalDate endDate = LocalDate.parse(endDateText);
        LocalTime endTime = LocalTime.parse(endTimeText);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        start = Timestamp.valueOf(startDateTime);
        end = Timestamp.valueOf(endDateTime);
        DBAppointments.update(apptId, title, description, location, type, start, end, custId, userId, contactId);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ViewAppointments.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

