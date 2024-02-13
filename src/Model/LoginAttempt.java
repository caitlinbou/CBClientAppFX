package Model;

import java.time.LocalDateTime;

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

    public LoginAttempt(boolean outcome, LocalDateTime timeStamp) {
        this.outcome = outcome;
        this.timeStamp = timeStamp;
    }

}
