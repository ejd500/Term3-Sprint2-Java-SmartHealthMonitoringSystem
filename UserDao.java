import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDao {
   
    public static boolean createUser(User user) {
        
        boolean bool = false;

        // insert user into database 
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        // Prepare the SQL query
        String query = "INSERT INTO users (first_name, last_name, email, password, is_doctor) " +
                "VALUES (?, ?, ?, ?, ?)";

        // Database logic to insert data using PREPARED Statement
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, hashedPassword);
            statement.setBoolean(5, user.isDoctor());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                System.out.println();
                System.out.println("Congratulations! " + user.getFirstName() + " " + user.getLastName() + " has been successfully registered as a user!");
                bool = true;
            } 
            return bool;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println();
            System.out.println("Sorry, " + user.getFirstName() + " " + user.getLastName() + " could not be registered as a user. Try again.");
            return bool;
        }
        
    }


    public static User getUserById(int id) { //get user by id from database 
        // Prepare the SQL query
        String query = "SELECT * FROM users WHERE id = ?";

        // Database logic to get data by ID Using Prepared Statement
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                // int user_id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                boolean is_doctor = rs.getBoolean("is_doctor");
                return new User(firstName, lastName, email, password, is_doctor);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User getUserByEmail(String email) { // get user by email from database 
        // Prepare the SQL query
        String query = "SELECT * FROM users WHERE email = ?";
        
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
                    return new User(firstName, lastName, user_email, password, is_doctor);
                } else {
                    return null; // User with the provided email not found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean updateUser(int id, User user) {
        boolean bool = false;
        // Prepare the SQL query
        String query = "UPDATE users " +
                "SET first_name = ?, last_name = ?, email = ?, password = ?, is_doctor = ? " +
                "WHERE id = ?";
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        // Database logic to get update user Using Prepared Statement
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, hashedPassword);
            statement.setBoolean(5, user.isDoctor());
            statement.setInt(6, id);
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static boolean deleteUser(int id) { // delete user from the database 
        boolean bool = false;
        // Prepare the SQL query
        String query = "DELETE FROM users WHERE id = ?";

        // Database logic to delete user
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated != 0){
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static boolean verifyPassword(String email, String password){
        boolean bool = false;
        // String query // SQL Statement
        String query = "SELECT password FROM users WHERE email = ?";
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

    public static int getIdByEmail(String email){
        // String query // SQL Statement
        String query = "SELECT id FROM users WHERE email = ?";
        // Implement logic to retrieve id using the Bcrypt
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
        
    }
 
}
