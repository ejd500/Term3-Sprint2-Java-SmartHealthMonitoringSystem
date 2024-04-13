
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// import org.mindrot.jbcrypt.BCrypt;

public class HealthDataDao {
  
  public static boolean createHealthData(HealthData healthData){ 
    // insert health data into database 
    boolean bool = false;

    // Prepare the SQL query
    String query = "INSERT INTO health_data (user_id, weight, height, steps, heart_rate, date) VALUES (?, ?, ?, ?, ?, ?)";

    // Database logic to insert data using PREPARED Statement
    try {
        Connection con = DatabaseConnection.getCon();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, healthData.getUserId());
        statement.setDouble(2, healthData.getWeight());
        statement.setDouble(3, healthData.getHeight());
        statement.setInt(4, healthData.getSteps());
        statement.setInt(5, healthData.getHeartRate());
        statement.setString(6, healthData.getDate());
        int updatedRows = statement.executeUpdate();
        if (updatedRows != 0) {
            System.out.println();
            System.out.println("Congratulations! Your health data has been successfully added to the database!");
            System.out.println();
            bool = true;
        } 
        return bool;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println();
        System.out.println("Sorry, your health data could not be added to the database. Try again.");
        System.out.println();
        return bool;
    }
  
  };
  
  public static List<HealthData> getHealthDataByUserId(int userId){ 
    // /* get health data by user id from database */ 
    List<HealthData> healthData = new ArrayList<>();
    String query = "SELECT weight, height, steps, heart_rate, date FROM health_data WHERE user_id = ?";
    
    try (Connection con = DatabaseConnection.getCon();
        PreparedStatement statement = con.prepareStatement(query)) {
        
        statement.setInt(1, userId);
        try (ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                double weight = rs.getDouble("weight");
                double height = rs.getDouble("height");
                int steps = rs.getInt("steps");
                int heartRate = rs.getInt("heart_rate");
                String date = rs.getString("date");
                HealthData hd = new HealthData(userId, weight, height, steps, heartRate, date);
                healthData.add(hd);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return healthData;

  
  }


  public static boolean updateHealthData(int healthDataId, HealthData healthData) { 
    /* update health data in the database */ 
    boolean bool = false;
    // Prepare the SQL query
    String query = "UPDATE health_data " +
            "SET weight = ?, height = ?, steps = ?, heart_rate = ?, date = ? " +
            "WHERE health_data_id = ?";
    // Database logic to get update health data Using Prepared Statement
    try {
        Connection con = DatabaseConnection.getCon();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setDouble(1, healthData.getWeight());
        statement.setDouble(2, healthData.getHeight());
        statement.setInt(3, healthData.getSteps());
        statement.setInt(4, healthData.getHeartRate());
        statement.setString(5, healthData.getDate());
        statement.setInt(6, healthDataId);
        int updatedRows = statement.executeUpdate();
        if (updatedRows != 0) {
            bool = true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return bool;
  }
  

  public static boolean deleteHealthData(int healthDataId) { 
    /* delete health data from the database */ 
    boolean bool = false;
    // Prepare the SQL query
    String query = "DELETE FROM health_data WHERE health_data_id = ?";

    // Database logic to delete health data
    try {
        Connection con = DatabaseConnection.getCon();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, healthDataId);
        statement.executeUpdate();
        bool = true;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return bool;
  
  }
  
  
  public static int getHealthDataId(int userId, double weight, double height, int steps, int heartRate, String date){
    // String query // SQL Statement
    String query = "SELECT health_data_id FROM health_data WHERE user_id = ? and weight = ? and height = ? and steps = ? and heart_rate = ? and date = ?";
    // Implement logic to retrieve id using the Bcrypt
    try {
        Connection con = DatabaseConnection.getCon();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setDouble(2, weight);
        statement.setDouble(3, height);
        statement.setInt(4, steps);
        statement.setInt(5, heartRate);
        statement.setString(6, date);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            int healthDataId = rs.getInt("health_data_id");
            return healthDataId;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
  }
}
