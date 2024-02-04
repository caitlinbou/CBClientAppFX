package sample;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void init() {
        System.out.println("Starting up");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        primaryStage.setTitle("DB Connection");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.out.println("Stopping");
    }
    public static void main(String[] args) {

        try {
        ResourceBundle rb = ResourceBundle.getBundle("Utilities/Nat", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")){
            System.out.println(rb.getString("hello" + " " + "userName"));
        }
        else {System.out.println("ENGLISH");
        }
        } catch (Exception e){
            System.out.println("Error:" + e.getMessage());
        };

        JDBC.openConnection();
        launch(args);
        //code goes HERE
        JDBC.closeConnection();
    }

}
