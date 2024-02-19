package helper;

import Model.Appointment;
import javafx.collections.ObservableList;
import java.time.Month;
import java.util.Map;
import java.util.TreeMap;

/**
 * This creates two different Maps to meet part of the report requirements specified in section 3f. The Maps allow for a count of the specified data from the Appointments List.
 * "apptsByMonth" maps the number of appointments that belong to each month. The "apptsByType" maps the number of appointments that belong to each appointment type. The other reporting requirements
 * are handled differently, and included in the ViewAppointmentsController.
 */
public class Reports {

    // Show total number of appts by Month (to display in the viewAppointment Text field labeled "Count of Appointments by Month"
    public static Map<Month, Integer> apptsByMonth(ObservableList<Appointment> appointments) {
        Map<Month, Integer> apptsByMonth = new TreeMap<>();

        for (Appointment appointment : appointments) {
            Month month = appointment.getStart().getMonth();
            apptsByMonth.put(month, apptsByMonth.getOrDefault(month, 0) +1);
        }
        System.out.println("apptsByMonth: " + apptsByMonth);
        return apptsByMonth;
    }
    //Show total number of appts by type (to display in the ViewAppointment Text field labeled "Count of Appointments by Type"
    public static Map<String, Integer> apptsByType(ObservableList<Appointment> appointments) {
        Map<String, Integer> apptsByType = new TreeMap<>();

        for (Appointment appointment : appointments) {
            String type = appointment.getType();
            apptsByType.put(type, apptsByType.getOrDefault(type, 0) + 1);
        }
        System.out.println("apptsByType: " + apptsByType);
        return apptsByType;
    }
}