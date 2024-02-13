package helper;

import Model.LoginAttempt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logins {

    public static void writeLoginAttempt(LoginAttempt loginAttempt) {
        String fileName = "login_activity.txt";
        String content = loginAttempt.getTimeStamp().toString() + " LoginSuccessful?: " + loginAttempt.isOutcome() + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);
            System.out.println("Content has been written to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}