package Controller;

import DAO.DBAppointments;
import DAO.DBCustomers;
import Model.Appointment;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewCustomersController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> custAddress;

    @FXML
    private TableColumn<Customer, String> custCountry;

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
    }

    @FXML
    void handleCustAddBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddCustomers.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void handleCustDeleteBtn(ActionEvent event) {
  //TODO: Select item to delete and remove it from the customer DB.
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
    void handleCustUpdateBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/EditCustomers.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
