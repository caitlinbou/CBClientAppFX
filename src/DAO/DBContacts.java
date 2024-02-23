package DAO;

import Model.Contact;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
/**
 * Defines class DBContacts allowing for database access to the contacts table.
 */
public class DBContacts {
    /**
     * This selects all Contacts from the contacts table in the database and returns an ObservableList of contacts for access in
     * the application.
     * @return ObservableList of all contacts from the db
     */
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

    /**
     * This takes in a Contact ID and queries for the specified contact in the SQL database.
     * @param cId takes in cId of a contact object
     * @return It returns the specified contact.
     */
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
