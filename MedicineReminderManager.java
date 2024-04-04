

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The MedicineReminderManager class should have methods to add reminders, get reminders
 *  1. for a specific user, and
 *  2. get reminders that are DUE for a specific user.
 *
 *  You'll need to integrate this class with your application and database logic to
 *  1. store,
 *  2. update, and
 *  3. delete reminders as needed.
 */

public class MedicineReminderManager {
    private static List<MedicineReminder> reminders = new ArrayList<>();

    public static boolean addReminder(MedicineReminder reminder) {
        reminders.add(reminder);
        // insert reminder data into database 
        boolean bool = false;

        // Prepare the SQL query
        String query = "INSERT INTO medicine_reminders (user_id, medicine_name, dosage, schedule, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";

        // Database logic to insert data using PREPARED Statement
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, reminder.getUserId());
            statement.setString(2, reminder.getMedicineName());
            statement.setString(3, reminder.getDosage());
            statement.setString(4, reminder.getSchedule());
            statement.setString(5, reminder.getStartDate());
            statement.setString(6, reminder.getEndDate());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                System.out.println();
                System.out.println("Congratulations! Your medicine reminder has been successfully added to the database!");
                System.out.println();
                bool = true;
            } 
            return bool;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println();
            System.out.println("Sorry, your medicine reminder could not be added to the database. Try again.");
            System.out.println();
            return bool;
        }
    }

    public static List<MedicineReminder> getRemindersForUser(int userId) {
        List<MedicineReminder> userReminders = new ArrayList<>();
        // Write your logic here
        String query = "SELECT medicine_reminder_id, user_id, medicine_name, dosage, schedule, start_date, end_date FROM medicine_reminders WHERE user_id = ?";
    
        try (Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query)) {
            
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String medicineName = rs.getString("medicine_name");
                    String dosage = rs.getString("dosage");
                    String schedule = rs.getString("schedule");
                    String startDate = rs.getString("start_date");
                    String endDate = rs.getString("end_date");
                    MedicineReminder mr = new MedicineReminder(userId, medicineName, dosage, schedule, startDate, endDate);
                    userReminders.add(mr);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userReminders;

  
    }

    public static List<MedicineReminder> getOverdueReminders(int userId) {
        List<MedicineReminder> overdueReminders = new ArrayList<>();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Write your logic here
        List<MedicineReminder> userReminders = getRemindersForUser(userId);
        for (MedicineReminder medicineReminder : userReminders) {
            LocalDate endDate = LocalDate.parse(medicineReminder.getEndDate(), formatter);
            if (endDate.isBefore(now)){
                overdueReminders.add(medicineReminder);
            }
        }
        return overdueReminders;
    }

    public static int getMedicineReminderId(int userId, String medicineName, String dosage, String schedule, String startDate, String endDate){
        // String query // SQL Statement
        String query = "SELECT medicine_reminder_id FROM medicine_reminders WHERE user_id = ? and medicine_name = ? and dosage = ? and schedule = ? and start_date = ? and end_date = ?";
        // Implement logic to retrieve id
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setString(2, medicineName);
            statement.setString(3, dosage);
            statement.setString(4, schedule);
            statement.setString(5, startDate);
            statement.setString(6, endDate);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int medicineReminderId = rs.getInt("medicine_reminder_id");
                return medicineReminderId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean updateMedicineReminder(int medicineReminderId, MedicineReminder medicineReminder) { 
        /* update health data in the database */ 
        boolean bool = false;
        // Prepare the SQL query
        String query = "UPDATE medicine_reminders " +
                "SET user_id = ?, medicine_name = ?, dosage = ?, schedule = ?, start_date = ?, end_date = ? " +
                "WHERE medicine_reminder_id = ?";
        // Database logic to get update health data Using Prepared Statement
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, medicineReminder.getUserId());
            statement.setString(2, medicineReminder.getMedicineName());
            statement.setString(3, medicineReminder.getDosage());
            statement.setString(4, medicineReminder.getSchedule());
            statement.setString(5, medicineReminder.getStartDate());
            statement.setString(6, medicineReminder.getEndDate());
            statement.setInt(7, medicineReminderId);
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static boolean deleteMedicineReminder(int medicineReminderId) { 
        /* delete medicine reminder from the database */ 
        boolean bool = false;
        // Prepare the SQL query
        String query = "DELETE FROM medicine_reminders WHERE medicine_reminder_id = ?";
    
        // Database logic to delete medicine reminder
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, medicineReminderId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated != 0){
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
      
      }
    


}