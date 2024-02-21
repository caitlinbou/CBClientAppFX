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
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
/**
 * The AddAppointmentsController provides access to the AddAppointments.fxml for presentation, and handles page actions that may occur.
 */
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
    /**
     * The initialize function sets the initial comboBox information for the AddAppointment.fxml view.
     * @param url Gets the URL
     * @param resourceBundle Gets the resource bundle
     */
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

    /**
     * The handleCancel function loads the ViewAppointments.fxml view.
     * @param event response to an action event (button click)
     */
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

    /**
     * he overlap function checks the time of all appointments belonging to the selected customer against the time input on the AddAppointments view. If the
     * customer already has an appointment at that time, an alert is given.
     * **This function contains one of the two lambda functions required to be used in the application**.
     * It uses the lambda expression to allow for instructions on what to do once client acknowledges the warning with the "OK" button. In this case
     * the start and end time text fields are cleared, requiring new inputs. The return value of this function may be used for additional error checks, as in the
     * case of the function call in the handleSubmit function below.
     * @param startDateTime takes in a Start LocalDateTime
     * @param custID Takes in an int customer ID
     * @return True/False
     */
    private boolean overlap(LocalDateTime startDateTime, int custID){
        Alert alert;
        ObservableList<Appointment> allAppointmentList = DBAppointments.getAllAppointments();
        ObservableList<Appointment> concurrent = FXCollections.observableArrayList();

        for (Appointment A : allAppointmentList) {
            LocalDateTime apptDateTime = A.getStart();
            int apptCustID = A.getCustId();
            int timeCompare = apptDateTime.compareTo(startDateTime);
            if (timeCompare == 0 && apptCustID == custID) {
                alert = new Alert(Alert.AlertType.WARNING, "This customer already has an Appointment scheduled for that time.");
                alert.showAndWait().ifPresent((response ->{
                    if (response == ButtonType.OK) {
                        addStartTime.clear();
                        addEndTime.clear();
                    }
                }));
                concurrent.add(A);
                break;
            }
        }
        return !concurrent.isEmpty();
    }

    /**
     * The handleSubmit function gets all input values from the AddAppointment form. It checks the input time against the business hours (in EST).
     * If it is within EST business hours, and does not overlap with any other appointments for the customer, the appointment details are sent
     * to the database with the DBAppointments.insert function. If the end time is not after the start time, it sends an alert, and if it falls outside of business
     * hours it sends a different alert.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void handleSubmit(ActionEvent event) throws SQLException, IOException {
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
            DateTimeFormatter dtF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            ZonedDateTime startTimeEST = ZonedDateTime.parse(LocalDate.now() + " 08:00", dtF.withZone(ZoneId.of("America/New_York")));
            ZonedDateTime endTimeEST = ZonedDateTime.parse(LocalDate.now() + " 22:00", dtF.withZone(ZoneId.of("America/New_York")));
            ZonedDateTime startTimeLocal = startTimeEST.withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime endTimeLocal = endTimeEST.withZoneSameInstant(ZoneId.systemDefault());
            System.out.println("Appointment Start Time in EST: " + startTimeEST);
            LocalTime startTimeLocalTime = startTimeLocal.toLocalTime();
            LocalTime endTimeLocalTime = endTimeLocal.toLocalTime();
            Alert alert;
            if (startTime.isAfter(startTimeLocalTime) && (startTime.isBefore(endTimeLocalTime))) {
                int timeCheck = start.compareTo(end);
                if (timeCheck < 0 && !overlap(startDateTime, custId)) {
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
        }
}
