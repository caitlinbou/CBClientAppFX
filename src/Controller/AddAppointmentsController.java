package Controller;

import DAO.DBAppointments;
import DAO.DBContacts;
import DAO.DBCustomers;
import DAO.DBUsers;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddAppointmentsController implements Initializable{
    Stage stage;
    Parent scene;

    @FXML
    private TextField addDescription;

    @FXML
    private ComboBox<Contact> addContact;

    @FXML
    private ComboBox<Customer> addCustId;

    @FXML
    private DatePicker addEndDate;

    @FXML
    private TextField addEndTime;


    @FXML
    private TextField addLocation;

    @FXML
    private DatePicker addStartDate;

    @FXML
    private TextField addStartTime;

    @FXML
    private TextField addTitle;

    @FXML
    private TextField addType;

    @FXML
    private ComboBox<User> addUserId;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        ObservableList<Customer> customerList = DBCustomers.getAllCustomers();
        addCustId.setItems(customerList);
        addCustId.setVisibleRowCount(5);
        addCustId.setPromptText("Select Customer Name");
        ObservableList<User> userList = DBUsers.getAllUsers();
        addUserId.setItems(userList);
        addUserId.setPromptText("Select Related User");
        ObservableList<Contact> contactList = DBContacts.getAllContacts();
        addContact.setItems(contactList);
        addContact.setPromptText("Select Contact Name");
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

    private boolean overlap(LocalDateTime startDateTime){
        Alert alert;
        ObservableList<Appointment> allAppointmentList = DBAppointments.getAllAppointments();
        ObservableList<Appointment> concurrent = FXCollections.observableArrayList();

        for (Appointment A : allAppointmentList) {
            LocalDateTime apptDateTime = A.getStart();
            int timeCompare = apptDateTime.compareTo(startDateTime);
            if (timeCompare == 0) {
                alert = new Alert(Alert.AlertType.WARNING, "There is already an Appointment scheduled during that time slot");
                alert.showAndWait();
                concurrent.add(A);
                break;
            }
        }
        return !concurrent.isEmpty();
    }

    @FXML
    void handleSubmit(ActionEvent event) {
        try {
            String title = addTitle.getText();
            String type = addType.getText();
            String location = addLocation.getText();
            Contact apptContact = addContact.getValue();
            String startTimeText = addStartTime.getText();
            String startDateText = addStartDate.getValue().toString();
            LocalDate startDate = LocalDate.parse(startDateText);
            LocalTime startTime = LocalTime.parse(startTimeText);
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            Timestamp start = Timestamp.valueOf(startDateTime);
            String endTimeText = addEndTime.getText();
            String endDateText = addEndDate.getValue().toString();
            LocalDate endDate = LocalDate.parse(endDateText);
            LocalTime endTime = LocalTime.parse(endTimeText);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
            Timestamp end = Timestamp.valueOf(endDateTime);
            int contactId = apptContact.getContactId();
            String description = addDescription.getText();
            Customer apptCust = addCustId.getValue();
            int custId = apptCust.getCustId();
            User apptUser = addUserId.getValue();
            int userId = apptUser.getId();
            //convert start to LocalDateTime to EST
            //TODO NEED HELP WITH CONVERSION TO PST
            ZonedDateTime apptStartEST = ZonedDateTime.of(startDateTime, ZoneId.of("America/New_York"));
            System.out.println("Appointment Start Time in EST: " + apptStartEST);
            LocalTime businessStart = LocalTime.of(8,0);
            LocalTime businessEnd = LocalTime.of(22,0);
            LocalTime apptStartLocalTime = apptStartEST.toLocalTime();
            System.out.println("Appointment Start Local Time in EST: " + apptStartLocalTime);
            DayOfWeek apptDay = apptStartEST.getDayOfWeek();
            Alert alert;
            if(apptDay == DayOfWeek.SATURDAY || apptDay ==DayOfWeek.SUNDAY) {
                alert = new Alert(Alert.AlertType.WARNING, "Please select M-F");
                alert.showAndWait();
            }else if (apptStartLocalTime.isAfter(businessStart) && (apptStartLocalTime.isBefore(businessEnd))) {
                int timeCheck = start.compareTo(end);
                if (timeCheck < 0 && !overlap(startDateTime)) {
                    DBAppointments.insert(title, description, location, type, start, end, custId, userId, contactId);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ViewAppointments.fxml")));
                    stage.setScene(new Scene(scene));
                    stage.show();
                } else if (timeCheck >= 0){
                    alert = new Alert(Alert.AlertType.WARNING, "Please make sure the End Date/Time is after the Start Date/Time");
                    alert.showAndWait();
                }
            } else {alert = new Alert(Alert.AlertType.WARNING, "Please select a time within business hours");
                alert.showAndWait();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
