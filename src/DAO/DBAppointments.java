package DAO;

import Model.Count;
import helper.JDBC;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * This allows for one query to the database using the JDBC helper function, and 3 other database actions. This follows the CRUD standard for database access, allowing
 * for the application to create, read, update, and destroy rows from the appointment database. getAllAppointments() gets all appointments from the database and uses the Appointment
 * class to create Appointment Objects for use in the application. Insert allows for the application to Add appointments to the db, update allows for appointment information to be updated
 * in the db, and delete allows for appointments to be deleted from the db.
 */
public abstract class DBAppointments {
    /**
     * @return apptLIst
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return apptList;
    }

    public static ObservableList<Count> apptReport() {
        ObservableList<Count> reportList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Type, monthname(start) AS month, Count(*) as \n" +
                    "Count FROM client_schedule.appointments GROUP BY Type, monthname(start);";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Type");
                String month = rs.getString("month");
                int count = rs.getInt("count");
                Count C = new Count(type, month, count);
                reportList.add(C);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reportList;
    }

    public static int insert(String title, String description, String location, String type, Timestamp start, Timestamp end, int custId, int userId, int contactId) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, custId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int apptId, String title, String description, String location, String type, Timestamp start, Timestamp end, int custId, int userId, int contactId) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID =?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, custId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
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
}
