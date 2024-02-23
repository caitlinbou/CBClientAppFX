package Controller;

import DAO.DBAppointments;
import DAO.DBCustomers;
import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
/**
 * The ViewCustomersController provides access to the ViewCustomers.fxml for presentation, and handles page actions that may occur.
 */
public class ViewCustomersController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> custAddress;

    public TableColumn<Customer, String> custCountry;

    @FXML
    private TableColumn<Customer, String> custFirstLevel;

    @FXML
    private TableColumn<Customer, Integer> custId;

    @FXML
    private TableColumn<Customer, String> custName;

    @FXML
    private TableColumn<Customer, String> custPhone;

    @FXML
    private TableColumn<Customer, String> custPostal;

    /**
     * The initialize function loads the customer table with all customers.
     * @param url takes in url
     * @param resourceBundle takes in resource bundle
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        ObservableList<Customer> customerList = DBCustomers.getAllCustomers();
        customerTableView.setItems(customerList);
        custAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        custId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        custName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        custPhone.setCellValueFactory(new PropertyValueFactory<>("custPhone"));
        custPostal.setCellValueFactory(new PropertyValueFactory<>("custPostal"));
        custFirstLevel.setCellValueFactory(new PropertyValueFactory<>("custDivision"));
        custCountry.setCellValueFactory(new PropertyValueFactory<>("custCountry"));
    }

    /**
     * The handleCustAddBtn loads the AddCustomers.fxml page when the add button is clicked.
     * @param event response to an action event (button click)
     * @throws IOException handles error related to input/output exception
     */
    @FXML
    void handleCustAddBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddCustomers.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * The handleCustDeleteBtn function gets the customer ID of the selected row, if a selection is made. It checks to see if that customer has any appointments
     * scheduled, notifies that deleting the customer will also delete the associated appointments, and upon confirmation it calls the delete function from
     * DBAppointments and from DBCustomers to delete the associated appointments and then the customer from the database.
     * It gives feedback by way of alerts to confirm. It reloads the remaining customers from the database.
     */
    @FXML
    void handleCustDeleteBtn(ActionEvent event) throws SQLException {
        Alert alert;
        if ((customerTableView.getSelectionModel().getSelectedItem()) == null) {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Select a Customer");
            alert.showAndWait();
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete this customer record. All appointments related to this customer will also be deleted.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Appointment> apptList = DBAppointments.getAllAppointments();
                Customer customer = customerTableView.getSelectionModel().getSelectedItem();
                ObservableList<Appointment> filteredAppointments = apptList.stream()
                        .filter(Appointment -> Objects.equals(Appointment.getCustId(), customer.getCustId()))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
                DBAppointments.delete(customer.getCustId());
                DBCustomers.delete(customer.getCustId());
                ObservableList<Customer> customerList = DBCustomers.getAllCustomers();
                customerTableView.setItems(customerList);
            }
        }
    }

    /**
     * The handleCustExitBtn function exits the customer view and reloads the ViewAppointments.fxml view.
     * @param event response to an action event (button click)
     */
    @FXML
    void handleCustExitBtn(ActionEvent event) {
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
     * The handleCustUpdateBtn function gets the selected row and passes the information to the
     * EditCustomersController using the EditCustomersController.sendCustomer() function. It then loads the EditCustomers.fxml
     * view with the selected information so that it may be edited.
     * @param event response to an action event (button click)
     * @throws IOException handles error related to input/output exception
     */
    @FXML
    void handleCustUpdateBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("/view/EditCustomers.fxml")));
        loader.load();
        if (customerTableView.getSelectionModel().getSelectedItem() != null) {
            EditCustomersController ModifyController = loader.getController();
            ModifyController.sendCustomer(customerTableView.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
}
