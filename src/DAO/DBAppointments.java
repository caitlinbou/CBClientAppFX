package DAO;

import helper.JDBC;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DBAppointments {
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
}
