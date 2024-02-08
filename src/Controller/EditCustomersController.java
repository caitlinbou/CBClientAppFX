package Controller;

import DAO.DBContacts;
import DAO.DBCountries;
import DAO.DBDivisions;
import DAO.DBUsers;
import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

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

        @FXML
        void countryCombo(ActionEvent event) {

        }

        @FXML
        void divisionCombo(ActionEvent event) {

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
    String custName, custAddress, custPostal, country, custPhone;

    public void sendCustomer(Customer customer) throws IOException, SQLException {
        thisCust = customer;
        ObservableList<Division> selectedDivision = DBDivisions.getDivisionByDivId(thisCust.getDivId());
        ObservableList<Division> divList = DBDivisions.getAllDivisions();
        ObservableList<Countries> selectedCountry = DBCountries.getCountrybyDiv(thisCust.getDivId());
        ObservableList<Countries> countryList = DBCountries.getAllCountries();

        editCustId.setText(String.valueOf(thisCust.getCustId()));
        editCustName.setText(thisCust.getCustName());
        editAddress.setText(thisCust.getCustAddress());
        editPostal.setText(thisCust.getCustPostal());
        editDiv.setItems(divList);
        editDiv.setValue(selectedDivision.get(0));
        editCountry.setItems(countryList);
        //editCountry.setValue(selectedCountry.get(0));
        editPhone.setText(thisCust.getCustPhone());

    }

        @FXML
        void handleSubmit(ActionEvent event) throws IOException {
//TODO: Figure out how to get the edited information and pass to
            custId = Integer.parseInt(editCustId.getText());
          //  divId = editDiv.getSelectionModel();
            custName = editCustName.getText();
            custAddress = editAddress.getText();
            custPostal = editPostal.getText();
            custPhone = editPhone.getText();

            // TODO: load the information into the Customer DB.

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ViewCustomers.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }


