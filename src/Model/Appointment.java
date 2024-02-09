package Model;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Appointment {
    private int apptId;
    private String title;
    private String description;
    private String location;
    private int contactId;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int custId;
    private int userId;


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setApptId( int apptId) {this.apptId = apptId;}

    public Appointment(int apptId, String title, String description, String location, int contactId, String type, LocalDateTime start, LocalDateTime end, int custId, int userId) {
        this.apptId = apptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.custId = custId;
        this.userId = userId;
    }

    public int getApptId() {return apptId;}

    public String getTitle() {return title;}

    public String getDescription() {return description;}

    public String getLocation() {return location;}

    public int getContactId() {return contactId;}

    public String getType() {return type;}

    public LocalDateTime getStart() {return start;}

    public LocalDateTime getEnd() {return end;}

    public int getCustId() {return custId;}

    public int getUserId() {return userId;}
}
