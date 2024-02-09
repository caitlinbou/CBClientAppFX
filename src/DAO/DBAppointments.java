package DAO;

import helper.JDBC;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class DBAppointments {
    public static ObservableList<Appointment> getAllAppointments(){

        ObservableList<Appointment> apptList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int apptId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                java.sql.Timestamp startTimestamp = rs.getTimestamp("start");
                LocalDateTime start = startTimestamp.toLocalDateTime();
                java.sql.Timestamp endTimestamp = rs.getTimestamp("end");
                LocalDateTime end = endTimestamp.toLocalDateTime();
                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Appointment A = new Appointment(apptId, title, description, location, contactId, type, start, end, custId, userId);
                apptList.add(A);
                //System.out.println(apptList.get(0).getApptId());
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } return apptList;
    }
    public static ObservableList<Appointment> getApptsByUser(int id){
        ObservableList<Appointment> apptUserList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from appointments WHERE User_ID = ' " + id + "'";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int apptId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                java.sql.Timestamp startTimestamp = rs.getTimestamp("start");
                LocalDateTime start = startTimestamp.toLocalDateTime();
                java.sql.Timestamp endTimestamp = rs.getTimestamp("end");
                LocalDateTime end = endTimestamp.toLocalDateTime();
                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Appointment A = new Appointment(apptId, title, description, location, contactId, type, start, end, custId, userId);
                apptUserList.add(A);
                //System.out.println(apptList.get(0).getApptId());
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } return apptUserList;
    }
    public static int insert(String title, String description, String location, String type, Timestamp start, Timestamp end, int custId, int userId, int contactId) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5,start);
        ps.setTimestamp(6,end);
        ps.setInt(7,custId);
        ps.setInt(8,userId);
        ps.setInt(9,contactId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int apptId,String title, String description, String location, String type, Timestamp start, Timestamp end, int custId, int userId, int contactId ) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID =?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5,start);
        ps.setTimestamp(6,end);
        ps.setInt(7,custId);
        ps.setInt(8,userId);
        ps.setInt(9,contactId);
        ps.setInt(10, apptId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int apptId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, apptId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static void select(int custId) throws SQLException {
        String sql = "Select * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, custId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int apptId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int custIdFK = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            System.out.println(apptId + " | " + title);
            System.out.println(custId + "\n");

        }
    }
}
