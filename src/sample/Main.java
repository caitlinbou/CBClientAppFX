package sample;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.Objects;
/**
 * This is the entry point to the CBClientAppFX application. It gives terminal feedback when the application is starting, loads the initial Login.fxml view,
 * opens and closes the database connection with the help of the JDBC helper file, and provides terminal feedback when the application is stopped.
 */
public class Main extends Application {

    @Override
    public void init() {
        System.out.println("Starting up");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/Login.fxml")));
        primaryStage.setTitle("Appointment Manager XYZ Company");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.out.println("Stopping");
    }
    public static void main(String[] args) {

        JDBC.openConnection();
        launch(args);
        //code goes HERE
        JDBC.closeConnection();
    }
}
