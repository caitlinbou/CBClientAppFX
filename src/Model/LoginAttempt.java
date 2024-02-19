package Model;

import java.time.LocalDateTime;
/**
 * This describes the "LoginAttempt" class with getters and setters for all class variables.
 */
public class LoginAttempt{

    private boolean outcome;

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    private LocalDateTime timeStamp;
    private int userId;

    public boolean isOutcome() {
        return outcome;
    }

    public void setOutcome(boolean outcome) {
        this.outcome = outcome;
    }
    /**
     * This allows public access of the "LoginAttempt" class to create an instance of a LoginAttempt Object.
     */
    public LoginAttempt(boolean outcome, LocalDateTime timeStamp) {
        this.outcome = outcome;
        this.timeStamp = timeStamp;
    }

}
