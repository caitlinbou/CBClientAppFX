package Model;

import java.sql.Time;

public class Appointment {
    private int customerID;
    private int contactID;
    private String title;
    private String type;
    private Time start;
    private Time end;

    public Appointment(int customerID, int contactID, String title, String type, Time start, Time end){
        this.customerID = customerID;
        this.contactID = contactID;
        this.title = title;
        this.type = type;
        this.start = start;
        this.end = end;
    }

    public int getCustomerID() {return customerID;}

    public int getContactID() {return contactID;}

    public String getTitle() {return title;}

    public String getType() {return type;}

    public Time getStart() {return start;}

    public Time getEnd() {return end;}
}
