package Controller;

import Model.Appointment;
import Model.Customer;
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
import java.time.LocalDateTime;
import java.util.Objects;

public class EditCustomersController {
    Stage stage;
    Parent scene;
        @FXML
        private TextField editAddress;

        @FXML
        private ComboBox<?> editCountry;

        @FXML
        private TextField editCustId;

        @FXML
        private TextField editCustName;

        @FXML
        private ComboBox<?> editDiv;

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
    int custId;
    //TODO: The carrot
    SingleSelectionModel<?> divId;
    String custName, custAddress, custPostal, custPhone;


    public void sendAppointment(Customer customer) throws IOException {
        thisCust = customer;
        editCustId.setText(String.valueOf(thisCust.getCustId()));
        //editDiv.setSelectionModel(String.valueOf(thisCust.getDivId()));
        editCustName.setText(thisCust.getCustName());
        editAddress.setText(thisCust.getCustAddress());
        editPostal.setText(thisCust.getCustPostal());
    }
        @FXML
        void handleSubmit(ActionEvent event) throws IOException {
//TODO: Figure out how to get the edited information and pass to
            custId = Integer.parseInt(editCustId.getText());
            divId = editDiv.getSelectionModel();
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


