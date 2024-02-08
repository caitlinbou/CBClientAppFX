package DAO;

import Model.Contact;
import Model.Customer;
import helper.JDBC;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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

    public static ObservableList<Contact> getContactById(int cId) throws SQLException {
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
