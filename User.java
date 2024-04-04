// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;

// import org.mindrot.jbcrypt.BCrypt;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    public String email;
    private String password;
    private boolean isDoctor;

    public User(String firstName, String lastName, String email, String password, boolean isDoctor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isDoctor = isDoctor;
        this.id = UserDao.getIdByEmail(email);
    }

    public int getId() {
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDoctor() {
        return isDoctor;
    }

    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID: " + id);
        sb.append("\nName: " + firstName + " " + lastName);
        sb.append("\nEmail: " + email);
        String result = sb.toString();
        return result;
    }
   
}
