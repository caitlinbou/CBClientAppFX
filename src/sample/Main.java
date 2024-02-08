package sample;

import DAO.DBAppointments;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void init() {
        System.out.println("Starting up");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        primaryStage.setTitle("Appointment Manager XYZ Company");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.out.println("Stopping");
    }
    public static void main(String[] args) throws SQLException {

        JDBC.openConnection();

      /*  Timestamp startTime = Timestamp.valueOf("2020-05-28 12:00:00");
        Timestamp endTime = Timestamp.valueOf("2020-05-28 13:00:00");
        int rowsAffected = AppointmentsQuery.update(1,"cool", "yes", "planning", "type", startTime, endTime,1, 1, 1);
        if(rowsAffected>0) System.out.println("Update Successful");
        else System.out.println("No Update");

        int rowsAffected = AppointmentsQuery.delete(1);
        if(rowsAffected>0) System.out.println("Update Successful");
        else System.out.println("No Update");
        */
        DBAppointments.select(1);
        launch(args);
        //code goes HERE
        JDBC.closeConnection();
    }

}
