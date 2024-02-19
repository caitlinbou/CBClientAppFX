package DAO;

import Model.Contact;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * This allows for two queries to the database using the JDBC helper function. getAllContacts() gets all contacts from the database.
 * getContactByID allows for retrieval of a specific contact based on contact ID. The Contact class is used to create Contact objects
 * to store this data in the application and to present it in the user interface. There is no need for an update, delete, or insert
 * statement in the case of the contacts DB, as those functions are administratively restricted according to the requirements.
 */
public class DBContacts {

    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * from contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact C = new Contact(contactId, contactName,email);
                contactList.add(C);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } return contactList;
    }

    public static ObservableList<Contact> getContactById(int cId) {
        ObservableList<Contact> selectedContact = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, cId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact c = new Contact(contactId, name, email);
                selectedContact.add(c);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return selectedContact;
    }
}
