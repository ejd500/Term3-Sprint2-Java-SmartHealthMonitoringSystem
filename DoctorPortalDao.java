import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorPortalDao {
    // private UserDao userDao;
    // private HealthDataDao healthDataDao;

   // Complete all these methods and add more as needed

    // public DoctorPortalDao() {
    //     userDao = new UserDao();
    //     healthDataDao = new HealthDataDao();
    // }

    public static boolean createDoctor(Doctor doctor) {
        
        boolean bool = false;

        // insert user into database 
        String hashedPassword = BCrypt.hashpw(doctor.getPassword(), BCrypt.gensalt());

        // Prepare the SQL query
        String query = "INSERT INTO doctors (first_name, last_name, email, password, is_doctor, medical_licence_number, specialization) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Database logic to insert data using PREPARED Statement
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, doctor.getFirstName());
            statement.setString(2, doctor.getLastName());
            statement.setString(3, doctor.getEmail());
            statement.setString(4, hashedPassword);
            statement.setBoolean(5, doctor.isDoctor());
            statement.setString(6, doctor.getMedicalLicenceNumber());
            statement.setString(7, doctor.getSpecialization());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                System.out.println();
                System.out.println("Congratulations! " + doctor.getFirstName() + " " + doctor.getLastName() + " has been successfully registered as a doctor!");
                bool = true;
            }
            return bool;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println();
            System.out.println("Sorry, " + doctor.getFirstName() + " " + doctor.getLastName() + " could not be registered as a doctor. Try again.");
            return bool;
        }
    }

    public static Doctor getDoctorById(int doctorId) {
        // Prepare the SQL query
        String query = "SELECT * FROM doctors WHERE doctor_id = ?";

        // Database logic to get data by ID Using Prepared Statement
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, doctorId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                // int user_id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                boolean is_doctor = rs.getBoolean("is_doctor");
                String medicalLicenceNumber = rs.getString("medical_licence_number");
                String specialization = rs.getString("specialization");
                return new Doctor(firstName, lastName, email, password, is_doctor, medicalLicenceNumber, specialization);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Doctor getDoctorByEmail(String email) { // get doctor by email from database 
        // Prepare the SQL query
        String query = "SELECT * FROM doctors WHERE email = ?";
        
        // Database logic to get data by email Using Prepared Statement
        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // int id = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String user_email = rs.getString("email");
                    String password = rs.getString("password");
                    boolean is_doctor = rs.getBoolean("is_doctor");
                    String medicalLicenceNumber = rs.getString("medical_licence_number");
                    String specialization = rs.getString("specialization");
                    return new Doctor(firstName, lastName, user_email, password, is_doctor, medicalLicenceNumber, specialization);
                } else {
                    return null; // User with the provided email not found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean verifyPassword(String email, String password){
        boolean bool = false;
        // String query // SQL Statement
        String query = "SELECT password FROM doctors WHERE email = ?";
        // Implement logic to retrieve password using the Bcrypt
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            String hashedPassword = null;
            while (rs.next()) {
                hashedPassword = rs.getString("password");
            }
            if (BCrypt.checkpw(password, hashedPassword)) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static boolean updateDoctor(int doctorId, Doctor doctor) {
        boolean bool = false;
        // Prepare the SQL query
        String query = "UPDATE doctors " +
                "SET first_name = ?, last_name = ?, email = ?, password = ?, is_doctor = ?, medical_licence_number = ?, specialization = ? " +
                "WHERE doctor_id = ?";
        String hashedPassword = BCrypt.hashpw(doctor.getPassword(), BCrypt.gensalt());

        // Database logic to get update user Using Prepared Statement
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, doctor.getFirstName());
            statement.setString(2, doctor.getLastName());
            statement.setString(3, doctor.getEmail());
            statement.setString(4, hashedPassword);
            statement.setBoolean(5, doctor.isDoctor());
            statement.setString(6, doctor.getMedicalLicenceNumber());
            statement.setString(7, doctor.getSpecialization());
            statement.setInt(8, doctorId);
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }


    public static List<User> getPatientsByDoctorId(int doctorId) {
        List<User> users = new ArrayList<>();
        String query = "SELECT patient_id FROM doctor_patient WHERE doctor_id = ?";
        
        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            
            statement.setInt(1, doctorId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int patientId = rs.getInt("patient_id");
                    users.add(UserDao.getUserById(patientId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return users;
    }

    public static List<HealthData> getHealthDataByPatientId(int patientId) {
        List<HealthData> healthData = new ArrayList<>();
        String query = "SELECT weight, height, steps, heart_rate, date FROM health_data WHERE user_id = ?";
        
        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            
            statement.setInt(1, patientId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    double weight = rs.getDouble("weight");
                    double height = rs.getDouble("height");
                    int steps = rs.getInt("steps");
                    int heartRate = rs.getInt("heart_rate");
                    String date = rs.getString("date");
                    HealthData hd = new HealthData(patientId, weight, height, steps, heartRate, date);
                    healthData.add(hd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return healthData;
    }

    // Add more methods for other doctor-specific tasks

    public static int getDoctorIdByEmail(String email){
        // String query // SQL Statement
        String query = "SELECT doctor_id FROM doctors WHERE email = ?";
        // Implement logic to retrieve id using the Bcrypt
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatemen2t(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int doctorId = rs.getInt("doctor_id");
                return doctorId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
}

