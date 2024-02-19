package Model;

/**
 * This describes the "Contact" class with getters and setters for all class variables.
 */
public class Contact {
    private int contactId;
    private String contactName;
    private String email;


    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return this.contactName;
    }

    /**
     * This allows public access of the "Contact" class to create an instance of a Contact Object.
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }


}
