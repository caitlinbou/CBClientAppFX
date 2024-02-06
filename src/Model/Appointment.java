package Model;

import java.sql.Time;
import java.time.LocalDateTime;

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

    public void setApptId( int apptId) {this.apptId = apptId;}

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
