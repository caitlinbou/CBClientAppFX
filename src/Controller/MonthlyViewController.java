package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;
import java.util.Objects;

//TODO: establish monthly view and load here
public class MonthlyViewController {
    Stage stage;
    Parent scene;

    public void handleExitBtn(ActionEvent event) throws IOException{

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void toggleSwitch(ActionEvent actionEvent) {

    }
}
