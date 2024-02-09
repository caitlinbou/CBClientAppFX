package Controller;

import DAO.DBAppointments;
import DAO.DBCountries;
import DAO.DBCustomers;
import DAO.DBDivisions;
import Model.Appointment;
import Model.Countries;
import Model.Customer;
import Model.Division;
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
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

    @FXML
    private Label customMessage;



    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        //TODO: Bring in division and country (DROP DOWN LIST FOR THE ADD/Modify)
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

    @FXML
    void handleCustAddBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddCustomers.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void handleCustDeleteBtn(ActionEvent event) throws SQLException {
  //TODO: Select item to delete and remove it from the customer DB.
        Alert alert;
        if ((customerTableView.getSelectionModel().getSelectedItem()) == null) {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Select a Customer");
            alert.showAndWait();
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Appointment> apptList = DBAppointments.getAllAppointments();
                Customer customer = customerTableView.getSelectionModel().getSelectedItem();
                ObservableList<Appointment> filteredAppointments = apptList.stream()
                        .filter(Appointment -> Objects.equals(Appointment.getCustId(), customer.getCustId()))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
                System.out.println(filteredAppointments);
                if (!filteredAppointments.isEmpty()) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION, "You must delete all appointments associated with this customer first.");
                    alert.showAndWait();
                } else {
                    DBCustomers.delete(customer.getCustId());
                    ObservableList<Customer> customerList = DBCustomers.getAllCustomers();
                    customerTableView.setItems(customerList);
                }
            }
        }
    }

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

    @FXML
    void handleCustUpdateBtn(ActionEvent event) throws IOException, SQLException {
        //TODO: properly select item and load it INTO the edit panel. Reference "MainForm" and "ModifyPart" controllers from SW1
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
