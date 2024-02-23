package Controller;

import DAO.DBAppointments;
import DAO.DBContacts;
import DAO.DBCustomers;
import DAO.DBUsers;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
/**
 * The EditAppointmentsController provides access to the EditAppointments.fxml for presentation, and handles page actions that may occur.
 */
public class EditAppointmentsController {

    Stage stage;
    Parent scene;
    @FXML
    private TextField editApptId;

    @FXML
    private ComboBox<Contact> editContact;

    @FXML
    private ComboBox<Customer> editCustId;
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

    /**
     * The handleCancel function exits the editAppointments view without action and reloads the ViewAppointments.fxml.
     *
     * @param event response to an action event (button click)
     */
    @FXML
    void handleCancel(ActionEvent event) {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
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

    /**
     * The sendAppointments function sets the values that will load on the EditAppointments page and is used by the ViewAppointmentsController.handleUpdateBtn()
     * function.
     *
     * @param appointment takes in an appointment object
     */
    public void sendAppointment(Appointment appointment) {
        thisAppt = appointment;
        ObservableList<Contact> selectedContact = DBContacts.getContactById(thisAppt.getContactId());
        ObservableList<Contact> contactList = DBContacts.getAllContacts();
        ObservableList<User> selectedUser = DBUsers.getUserById(thisAppt.getUserId());
        ObservableList<User> userList = DBUsers.getAllUsers();
        ObservableList<Customer> selectedCustomer = DBCustomers.getCustomerById(thisAppt.getCustId());
        ObservableList<Customer> customerList = DBCustomers.getAllCustomers();

        editApptId.setText(String.valueOf(thisAppt.getApptId()));
        editCustId.setValue(selectedCustomer.get(0));
        editCustId.setItems(customerList);
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

    /**
     * The overlap function checks the time of all appointments belonging to the selected customer against the time input on the EditAppointments view. If the
     * customer already has an appointment at that time, an alert is given.
     *
     * @param startDateTime takes in LocalDateTime of the user input
     * @param custID        takes in custId of the customer related to the appointment being edited
     * @param apptId        takes in the apptId of the appointment being edited
     * @return This returns a boolean response that can be used in further checks, and is used by the handleSubmit function.
     */
    private boolean overlap(LocalDateTime startDateTime, int custID, int apptId) {
        Alert alert;
        ObservableList<Appointment> allAppointmentList = DBAppointments.getAllAppointments();
        ObservableList<Appointment> concurrent = FXCollections.observableArrayList();

        for (Appointment A : allAppointmentList) {
            LocalDateTime apptDateTime = A.getStart();
            int apptCustID = A.getCustId();
            int timeCompare = apptDateTime.compareTo(startDateTime);
            if (timeCompare == 0 && apptCustID == custID && A.getApptId() != apptId) {
                alert = new Alert(Alert.AlertType.WARNING, "This customer already has an Appointment scheduled for that time");
                alert.showAndWait();
                concurrent.add(A);
                break;
            }
        }
        return !concurrent.isEmpty();
    }

    /**
     * The handleSubmit function gets all input values from the EditCustomer form. It checks the input time against the business hours (in EST).
     * If it is within EST business hours, and does not overlap with any other appointments for the customer, the appointment details are sent
     * to the database with the DBAppointments.update function. If the end time is not after the start time, it sends an alert, and if it falls outside of business
     * hours it sends a different alert.
     *
     * @param event response to an action event (button click)
     * @throws IOException  handles error related to input/output exception
     * @throws SQLException handles error related to SQLException
     */
    @FXML
    void handleSubmit(ActionEvent event) throws IOException, SQLException {
        apptId = Integer.parseInt(editApptId.getText());
        title = editTitle.getText();
        description = editDescription.getText();
        type = editType.getText();
        location = editLocation.getText();
        contactId = editContact.getValue().getContactId();
        userId = editUserId.getValue().getId();
        custId = editCustId.getValue().getCustId();
        String startTimeText = editStartTime.getText();
        String endTimeText = editEndTime.getText();
        Alert alert;
        if (startTimeText.isEmpty() || endTimeText.isEmpty() || editCustId.getValue() == null || editStartDate.getValue() == null || editEndDate.getValue() == null || title.isEmpty() || type.isEmpty() || location.isEmpty() || description.isEmpty() || editUserId.getValue() == null || editContact.getValue() == null) {
            alert = new Alert(Alert.AlertType.WARNING, "All inputs are required, please update missing fields");
            alert.showAndWait();
        } else {
            String startDateText = editStartDate.getValue().toString();
            LocalDate startDate = LocalDate.parse(startDateText);
            LocalTime startTime = LocalTime.parse(startTimeText);
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            String endDateText = editEndDate.getValue().toString();
            LocalDate endDate = LocalDate.parse(endDateText);
            LocalTime endTime = LocalTime.parse(endTimeText);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
            start = Timestamp.valueOf(startDateTime);
            end = Timestamp.valueOf(endDateTime);
            DateTimeFormatter dtF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            ZonedDateTime startTimeEST = ZonedDateTime.parse(LocalDate.now() + " 08:00", dtF.withZone(ZoneId.of("America/New_York")));
            ZonedDateTime endTimeEST = ZonedDateTime.parse(LocalDate.now() + " 22:00", dtF.withZone(ZoneId.of("America/New_York")));
            ZonedDateTime startTimeLocal = startTimeEST.withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime endTimeLocal = endTimeEST.withZoneSameInstant(ZoneId.systemDefault());
            System.out.println("Appointment Start Time in EST: " + startTimeEST);
            LocalTime startTimeLocalTime = startTimeLocal.toLocalTime();
            LocalTime endTimeLocalTime = endTimeLocal.toLocalTime();
            if (startTime.isAfter(startTimeLocalTime) && (startTime.isBefore(endTimeLocalTime))) {
                int timeCheck = start.compareTo(end);
                if (timeCheck < 0 && !overlap(startDateTime, custId, apptId)) {
                    DBAppointments.update(apptId, title, description, location, type, start, end, custId, userId, contactId);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ViewAppointments.fxml")));
                    stage.setScene(new Scene(scene));
                    stage.show();
                } else if (timeCheck >= 0) {
                    alert = new Alert(Alert.AlertType.WARNING, "Please make sure the End Date/Time is after the Start Date/Time");
                    alert.showAndWait();
                }
            } else {
                alert = new Alert(Alert.AlertType.WARNING, "Please select a time within business hours");
                alert.showAndWait();
            }
        }
    }
}



