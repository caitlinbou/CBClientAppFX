package helper;

import Model.LoginAttempt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This is called by the LoginController at the time of each login attempt to record each loginAttempt in the file "login_activity.txt".
 */
public class Logins {
    /**
     * This takes in an instance of a loginAttempt and appends the time and success of the attempt into the login_activity.txt file.
     * @param loginAttempt
     */
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