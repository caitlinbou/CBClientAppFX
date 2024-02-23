package Controller;

import DAO.*;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The EditCustomersController provides access to the EditCustomers.fxml for presentation, and handles page actions that may occur.
 */
public class EditCustomersController {
    Stage stage;
    Parent scene;
        @FXML
        private TextField editAddress;

        @FXML
        private ComboBox<Countries> editCountry;

        @FXML
        private TextField editCustId;

        @FXML
        private TextField editCustName;

        @FXML
        private ComboBox<Division> editDiv;

        @FXML
        private TextField editPhone;

        @FXML
        private TextField editPostal;
        ObservableList<Division> divisionList = DBDivisions.getAllDivisions();
    /**
     * The handleCountrySelected function filters the Div combo-box based on the country selected in the country combo-box. It uses the filter lambda available to the ObservableList object
     * to create a list of available divisions based on the country selected. **This is one of the two required usages of a lambda function in this project**.
     * @param event response to an action event (combo-box selection)
     */
    @FXML
        void handleCountrySelected(ActionEvent event) {
        Countries selectedCountry = editCountry.getValue();
        ObservableList<Division> filteredDivisions = divisionList.stream()
                .filter(Division -> Objects.equals(Division.getCountryId(), selectedCountry.getId()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        editDiv.setItems(filteredDivisions);
        editDiv.getSelectionModel().clearSelection();
        editDiv.setValue(null);
    }

    /**
     * The handleCancel function exits the editCustomers view without action and reloads the ViewCustomers.fxml.
     * @param event response to an action event (button click)
     */
        @FXML
        void handleCancel(ActionEvent event) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/ViewCustomers.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(scene));
            stage.show();
        }

    Customer thisCust;
    int custId, divId;
    String custName, custAddress, custPostal, custPhone;

    /**
     * The sendCustomer function sets the values that will load on the EditCustomer page and is used by the ViewCustomersController.handleCustUpdate()
     * function.
     * @param customer takes in a customer Object
     */
    public void sendCustomer(Customer customer) {
        thisCust = customer;
        ObservableList<Division> selectedDivision = DBDivisions.getDivisionByDivId(thisCust.getDivId());
        ObservableList<Division> divList = DBDivisions.getAllDivisions();
        ObservableList<Countries> selectedCountry = DBCountries.getCountrybyId(thisCust.getCustCountryID());
        ObservableList<Countries> countryList = DBCountries.getAllCountries();
        editCustId.setText(String.valueOf(thisCust.getCustId()));
        editCustName.setText(thisCust.getCustName());
        editAddress.setText(thisCust.getCustAddress());
        editPostal.setText(thisCust.getCustPostal());
        editDiv.setItems(divList);
        editDiv.setValue(selectedDivision.get(0));
        editCountry.setItems(countryList);
        editCountry.setValue(selectedCountry.get(0));
        editPhone.setText(thisCust.getCustPhone());

    }

    /**
     * the handleSubmit function gets the data entered into EditCustomer.fxml fields and updates the customer table in the database with the changes.
     * It then loads the ViewCustomers.fxml view.
     * @param event response to an action event (button click)
     * @throws IOException handles error related to input/output exception
     * @throws SQLException handles error related to SQLException
     */
        @FXML
        void handleSubmit(ActionEvent event) throws IOException, SQLException {
            custId = Integer.parseInt(editCustId.getText());
            custName = editCustName.getText();
            custAddress = editAddress.getText();
            custPostal = editPostal.getText();
            custPhone = editPhone.getText();
            Division custDiv =  editDiv.getValue();
            if (custName.isEmpty() || custAddress.isEmpty() || custPostal.isEmpty() || custPhone.isEmpty() || editDiv.getSelectionModel().isEmpty() || editCountry.getSelectionModel().isEmpty()) {
                Alert alert;
                alert = new Alert(Alert.AlertType.WARNING, "All inputs are required, please update missing fields");
                alert.showAndWait();
            } else {
                divId = custDiv.getDivId();
                DBCustomers.update(custId, custName, custAddress, custPostal, custPhone, divId);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ViewCustomers.fxml")));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }


