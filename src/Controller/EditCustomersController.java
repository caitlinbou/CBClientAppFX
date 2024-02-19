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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * This function filters the Div combo-box based on the country selected in the country combo-box. It uses the filter lambda available to the ObservableList object
     * to create a list of available divisions based on the country selected. This is one of the two required usages of a lambda function.
     *
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

        @FXML
        void handleSubmit(ActionEvent event) throws IOException, SQLException {
            custId = Integer.parseInt(editCustId.getText());
            custName = editCustName.getText();
            custAddress = editAddress.getText();
            custPostal = editPostal.getText();
            custPhone = editPhone.getText();
            Division custDiv =  editDiv.getValue();
            divId = custDiv.getDivId();
            DBCustomers.update(custId, custName, custAddress, custPostal, custPhone, divId);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ViewCustomers.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }


