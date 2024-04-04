

// import com.DataBaseConnection;
// import java.sql.Date;
// import java.sql.SQLException;
// import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class HealthMonitoringApp {

    // private static final String Quit = null;
    // private static UserDaoExample userDao = new UserDaoExample();

    public static void main(String[] args) {
        // // Testing getUserById:
        // User user = UserDao.getUserById(1);
        // System.out.println(user.getFirstName());

        // // Testing updateUser:
        // User user2 = new User("George", "Kennedy", "george@email.com", "george", false);
    
        // UserDao.updateUser(16, user2);

        // UserDao.deleteUser(16);
        
        mainMenu();

    }

    public static void mainMenu(){
        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.println("Please choose from the following options:");
        System.out.println("1. Add a New User");
        System.out.println("2. Login to the System");
        System.out.println("3. Quit the System");

        System.out.print("Enter Choice: ");
        int choice = input.nextInt();
        input.nextLine();

        if (choice == 1) {
            addNewUser(input); // Call the method to add a new user
            mainMenu();
        } else if (choice == 2) {
            loginUser(input);
        } else if (choice == 3){
            System.out.println("Thank you for using the Health Monitoring App! Goodbye!");
            System.exit(0);
        } else {
            System.out.println();
            System.out.println("Invalid choice. Try again.");
            mainMenu();
        }
    }

    public static void addNewUser(Scanner input){
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();
        
        System.out.print("Enter email: ");
        String email = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Are you a doctor (true or false)? ");
        boolean isDoctor = input.nextBoolean();
        input.nextLine();

        if(isDoctor){
            System.out.println("What is your medical licence number? ");
            String medicalLicenseNumber = input.nextLine();
            System.out.println("What is your specialization? ");
            String specialization = input.nextLine();
            Doctor doctorEntry = new Doctor(firstName, lastName, email, password, isDoctor, medicalLicenseNumber, specialization);
            DoctorPortalDao.createDoctor(doctorEntry);
        } else {
            User userEntry = new User(firstName, lastName, email, password, isDoctor);
            UserDao.createUser(userEntry);
        }
    
    }

    public static boolean loginUser(Scanner input) {

        System.out.print("Enter email: ");
        String email = input.nextLine();
        User user = UserDao.getUserByEmail(email);
        Doctor doctor = DoctorPortalDao.getDoctorByEmail(email);
        
        if(user == null && doctor == null){
            System.out.println();
            System.out.println("Sorry, you entered an invalid email. Try again.");
            mainMenu();
            return false;
        } else {
            if (user != null ) {
                // Compare the stored hashed password with the given password and return result
                System.out.print("Enter password: ");
                String password = input.nextLine();
                if (UserDao.verifyPassword(email, password) == false){
                    System.out.println();
                    System.out.println("Sorry, you entered the incorrect password, try again.");
                    System.out.println();
                    mainMenu();
                    return false;
                } else {
                    System.out.println();
                    System.out.println("Thank you for logging in, user!");
                    System.out.println();
                    testPatientPortal(input, user);
                    return true;
                }
            } 
            if (doctor != null){
                // Compare the stored hashed password with the given password and return result
                System.out.print("Enter password: ");
                String password = input.nextLine();
                if (DoctorPortalDao.verifyPassword(email, password) == false){
                    System.out.println();
                    System.out.println("Sorry, you entered the incorrect password, try again.");
                    System.out.println();
                    mainMenu();
                    return false;
                } else {
                    System.out.println();
                    System.out.println("Thank you for logging in, doctor!");
                    System.out.println();
                    testDoctorPortal(input, doctor);
                    return true;
                }
            }
        }
        return false; // Return false if neither user nor doctor is found (this should not happen)
    }


    /**
     * To test the Doctor Portal in your Health Monitoring System, provide a simple test code method that you can add
     * to your main application class.
     * In this method, we'll test the following functionalities:
     * 1. Fetching a doctor by ID
     * 2. Fetching patients associated with a doctor
     * 3. Fetching health data for a specific patient
      */
    public static void testDoctorPortal(Scanner input, Doctor doctor) {
        System.out.println("Choose from the following options: ");
        System.out.println("1. View and Update Your Information");
        System.out.println("2. Look-Up Doctor by Doctor ID");
        System.out.println("3. Look-up Patients by Doctor ID");
        System.out.println("4. List health data for a particular patient");
        System.out.println("5. Log Out");
        
        System.out.print("Enter choice: ");
        int choice = input.nextInt();

        if(choice == 1){
            viewAndUpdateDoctorInfo(input, doctor);
        } else if (choice == 2){
            lookUpDoctorByDoctorId(input);
            testDoctorPortal(input, doctor);
        } else if (choice == 3){
            lookUpPatientsByDoctorId(input);
            testDoctorPortal(input, doctor);
        } else if (choice == 4){
            listHealthDataForAPatient(input);
            testDoctorPortal(input, doctor);
        } else if (choice == 5){
            System.out.println("Thank you for using the doctor portal.");
            mainMenu();
        }
    };

    public static void viewAndUpdateDoctorInfo(Scanner input, Doctor doctor){
        int doctorId = doctor.getDoctorId();
        System.out.println();
        System.out.println("Your doctor ID is: " + doctorId);
        System.out.println("Your full name is: " + doctor.getFirstName() + " " + doctor.getLastName());
        System.out.println("Your email is: " + doctor.getEmail());
        System.out.println("Your medical licence number is: " + doctor.getMedicalLicenceNumber());
        System.out.println("Your specialization is: " + doctor.getSpecialization());

        System.out.println();
        System.out.print("Do you want to update your information? ");
        System.out.print("Enter 1 for Yes or 2 for No: ");
        int choice2 = input.nextInt();
        input.nextLine();

        if (choice2 == 1){
            System.out.print("Enter your new first name: ");
            String newFirstName = input.nextLine();
            System.out.print("Enter your new last name: ");
            String newLastName = input.nextLine();
            System.out.print("Enter your new email: ");
            String newEmail = input.nextLine();
            System.out.print("Enter your new password: ");
            String newPassword = input.nextLine();
            System.out.print("Enter your new medical licence number: ");
            String newMedicalLicenceNumber = input.nextLine();
            System.out.print("Enter your new Specialization: ");
            String newSpecialization = input.nextLine();

            Doctor newDoctor = new Doctor(newFirstName, newLastName, newEmail, newPassword, true, newMedicalLicenceNumber, newSpecialization);

            DoctorPortalDao.updateDoctor(doctorId, newDoctor);
            if (!DoctorPortalDao.updateDoctor(doctorId, newDoctor)){
                System.out.println();
                System.out.println("Sorry, your information could not be updated in the database. Please try again.");
                System.out.println();
                testDoctorPortal(input, doctor);
            } else {
                System.out.println();
                doctor.setFirstName(newFirstName);
                doctor.setLastName(newLastName);
                doctor.setEmail(newEmail);
                doctor.setPassword(newPassword);
                doctor.setMedicalLicenceNumber(newMedicalLicenceNumber);
                doctor.setSpecialization(newSpecialization);
                System.out.println("Thank you. Your information has been updated in the database.");
                System.out.println();
                testDoctorPortal(input, doctor);
            }
        } else if (choice2 == 2){
            System.out.println();
            System.out.println("Thanks for viewing your information.");
            System.out.println();
            testDoctorPortal(input, doctor);
        } else {
            System.out.println("Sorry, that was an invalid response.");
            testDoctorPortal(input, doctor);
        }
    };

    public static void lookUpDoctorByDoctorId(Scanner input){
        System.out.print("Enter the Doctor ID you are searching for: ");
        int doctorId = input.nextInt();

        Doctor d1 = DoctorPortalDao.getDoctorById(doctorId);
    
        System.out.println();
        System.out.println("Result for Doctor #" + doctorId + ":");
        System.out.println();
        System.out.println(d1);
        System.out.println();
    };

    public static void lookUpPatientsByDoctorId(Scanner input){
        System.out.print("To get a list of patients, enter the Doctor's ID: ");
        int doctorId = input.nextInt();
        List<User> users = DoctorPortalDao.getPatientsByDoctorId(doctorId);
        System.out.println();
        System.out.println("Patient Results for Doctor #" + doctorId + ": ");
        for (User user : users) {
            System.out.println();
            System.out.println(user);
        }
        System.out.println();
    };

    public static void listHealthDataForAPatient(Scanner input){
        System.out.print("Enter the Patient's ID: ");
        int patientId = input.nextInt();
        List<HealthData> healthData = DoctorPortalDao.getHealthDataByPatientId(patientId);
        System.out.println();
        System.out.println("Health Data Results for Patient #" + patientId + ": ");
        for (HealthData data : healthData) {
            System.out.println();
            System.out.println(data);
        }
        System.out.println();
    
    };



    public static void testPatientPortal(Scanner input, User user){
        System.out.println("Please choose one of the following options: ");
        System.out.println("1. View and Update Your Information");
        System.out.println("2. Input Health Data");
        System.out.println("3. View and Update Health Data");
        System.out.println("4. Delete Health Data");
        System.out.println("5. View Personalized Health Recommendations");
        System.out.println("6. Set a Medicine Reminder");
        System.out.println("7. View and Update Medicine Reminders");
        System.out.println("8. View OVERDUE Medicine Reminders");
        System.out.println("9. Delete a Medicine Reminder");
        System.out.println("10. Quit");

        System.out.print("Enter Choice: ");
        int choice = input.nextInt();
        
        if(choice == 1){
            viewAndUpdateUserInfo(input, user);
        } else if (choice == 2){
            inputHealthData(input, user);
            testPatientPortal(input, user);
        } else if (choice == 3){
            viewAndUpdateHealthData(input, user);
            testPatientPortal(input, user);
        } else if (choice == 4){
            deleteHealthData(input);
            testPatientPortal(input, user);
        } else if (choice == 5){
            viewHealthRecommendations(user);
            testPatientPortal(input, user);
        } else if (choice == 6){
            setMedicineReminder(input, user);
            testPatientPortal(input, user);
        } else if(choice == 7){
            viewAndUpdateMedicineReminders(input, user);
            testPatientPortal(input, user);
        } else if (choice == 8){
            getOverdueMedicineReminders(user);
            testPatientPortal(input, user);
        } else if (choice == 9){
            deleteMedicineReminder(input);
            testPatientPortal(input, user);
        } else if (choice == 10){
            mainMenu();
        } else {
            System.out.println();
            System.out.println("Invalid choice. Try again.");
            System.out.println();
            testPatientPortal(input, user);
        }
    }

    public static void viewAndUpdateUserInfo(Scanner input, User user){
        int userId = user.getId();
        System.out.println();
        System.out.println("Your user ID is: " + userId);
        System.out.println("Your full name is: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("Your email is: " + user.getEmail());

        System.out.println();
        System.out.print("Do you want to update your information? ");
        System.out.print("Enter 1 for Yes or 2 for No: ");
        int choice2 = input.nextInt();
        input.nextLine();

        if (choice2 == 1){
            System.out.print("Enter your new first name: ");
            String newFirstName = input.nextLine();
            System.out.print("Enter your new last name: ");
            String newLastName = input.nextLine();
            System.out.print("Enter your new email: ");
            String newEmail = input.nextLine();
            System.out.print("Enter your new password: ");
            String newPassword = input.nextLine();

            User newUser = new User(newFirstName, newLastName, newEmail, newPassword, false);

            UserDao.updateUser(userId, newUser);
            if (!UserDao.updateUser(userId, newUser)){
                System.out.println();
                System.out.println("Sorry, your information could not be updated in the database. Please try again.");
                System.out.println();
                testPatientPortal(input, user);
            } else {
                System.out.println();
                user.setFirstName(newFirstName);
                user.setLastName(newLastName);
                user.setEmail(newEmail);
                user.setPassword(newPassword);
                System.out.println("Thank you. Your information has been updated in the database.");
                System.out.println();
                testPatientPortal(input, user);
            }
        } else if (choice2 == 2){
            System.out.println();
            System.out.println("Thanks for viewing your information.");
            System.out.println();
            testPatientPortal(input, user);
        } else {
            System.out.println("Sorry, that was an invalid response.");
            testPatientPortal(input, user);
        }
    };


    public static HealthData inputHealthData(Scanner input, User user){
        int userId = user.getId();

        System.out.print("Enter your weight: ");
        double weight = input.nextDouble();

        System.out.print("Enter your height: ");
        double height = input.nextDouble();

        System.out.print("Enter the number of steps you took: ");
        int steps = input.nextInt();

        System.out.print("Enter your heart rate: ");
        int heartRate = input.nextInt();
        input.nextLine();

        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = input.nextLine();

        HealthData healthDataEntry = new HealthData(userId, weight, height, steps, heartRate, date);

        HealthDataDao.createHealthData(healthDataEntry);

        return healthDataEntry;
    }

    public static void viewAndUpdateHealthData(Scanner input, User user){
        List<HealthData> healthData = HealthDataDao.getHealthDataByUserId(user.getId());
        System.out.println();
        System.out.println("Here is your health data: ");
        for (HealthData hData : healthData) {
            System.out.println();
            System.out.println(hData);
        }
        System.out.println();
        System.out.print("Do you want to update your health data? ");
        System.out.print("Enter 1 for Yes or 2 for No: ");
        int choice2 = input.nextInt();
        input.nextLine();

        if (choice2 == 1){
            System.out.print("What health data ID do you want to update? ");
            int healthDataId = input.nextInt();
            System.out.print("Enter your new weight: ");
            double newWeight = input.nextDouble();
            System.out.print("Enter your new height: ");
            double newHeight = input.nextDouble();
            System.out.print("Enter your new steps: ");
            int newSteps = input.nextInt();
            System.out.print("Enter your new heart rate: ");
            int newHeartRate = input.nextInt();
            input.nextLine();
            System.out.print("Enter your new date (YYYY-MM-DD): ");
            String newDate = input.nextLine();

            HealthData newHealthData = new HealthData(user.getId(), newWeight, newHeight, newSteps, newHeartRate, newDate);
            
            HealthDataDao.updateHealthData(healthDataId, newHealthData);

            if (!HealthDataDao.updateHealthData(healthDataId, newHealthData)){
                System.out.println();
                System.out.println("Sorry, your information could not be updated in the database. Please try again.");
                System.out.println();
                testPatientPortal(input, user);
            } else {
                System.out.println();
                for (HealthData hData : healthData) {
                    if (hData.getHealthDataId() == healthDataId) {
                        hData.setWeight(newWeight);
                        hData.setHeight(newHeight);
                        hData.setSteps(newSteps);
                        hData.setHeartRate(newHeartRate);
                        hData.setDate(newDate);
                        break; // Exit the loop since we found the desired HealthData object
                    }
                }
                System.out.println("Thank you. Your health data with an ID of " + healthDataId + " has been updated in the database.");
                System.out.println();
                testPatientPortal(input, user);
            }
        } else if (choice2 == 2){
            System.out.println();
            System.out.println("Thanks for viewing your health data.");
            System.out.println();
            testPatientPortal(input, user);
        } else {
            System.out.println("Sorry, that was an invalid response.");
            testPatientPortal(input, user);
        }
    }

    public static void deleteHealthData(Scanner input){
        System.out.print("What health data ID # do you want to delete? ");
        int healthDataId = input.nextInt();

        HealthDataDao.deleteHealthData(healthDataId);
        if (!HealthDataDao.deleteHealthData(healthDataId)){
            System.out.println("Sorry, your health data could not be deleted.");
        } else {
            System.out.println("Your health data with an ID of " + healthDataId + " was successfully deleted from the database!");
        }
    }

    public static void viewHealthRecommendations(User user){

        List<HealthData> healthDataList = HealthDataDao.getHealthDataByUserId(user.getId());

        for (HealthData healthData : healthDataList) {
            List<String> recommendations = RecommendationSystem.generateRecommendations(healthData);
            System.out.println();
            System.out.println("---- For health data ID " + healthData.getHealthDataId() + " the recommendation is as follows: ---- ");
            for (String recommendation : recommendations) {
                System.out.println();
                System.out.println(recommendation);
                
                // Check if the recommendation already exists in the database
                if (!recommendationExists(user.getId(), recommendation, healthData.getDate())) {
                    // Prepare the SQL query
                    String query = "INSERT INTO recommendations (user_id, recommendation_text, date) VALUES (?, ?, ?)";
                    // Database logic to insert data using PREPARED Statement
                    try {
                        Connection con = DatabaseConnection.getCon();
                        PreparedStatement statement = con.prepareStatement(query);
                        statement.setInt(1, healthData.getUserId());
                        statement.setString(2, recommendation);
                        statement.setString(3, healthData.getDate());
                        int updatedRows = statement.executeUpdate();
                        if (updatedRows != 0) {
                            System.out.println();
                            System.out.println("Congratulations! Your recommendation has been successfully added to the database!");
                            System.out.println();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println();
                        System.out.println("Sorry, your recommendation could not be added to the database. Try again.");
                        System.out.println();
                            
                    }
                } else {
                    System.out.println();
                    System.out.println("This recommendation already exists in the database.");
                }
            }
        }

        System.out.println();
        System.out.println("---- Thank you for viewing your health recommendations ----");
        System.out.println();
        
    }

    // Method to check if a recommendation already exists in the database
    private static boolean recommendationExists(int userId, String recommendation, String date) {
        // Prepare the SQL query
        String query = "SELECT COUNT(*) FROM recommendations WHERE user_id = ? AND recommendation_text = ? AND date = ?";
        try (Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setString(2, recommendation);
            statement.setString(3, date);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Return true if recommendation exists, false otherwise
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Default to false in case of exceptions or empty result set
    }

    public static void setMedicineReminder(Scanner input, User user){
        System.out.println();
        System.err.println("To set a medicine reminder: ");
        input.nextLine();
        System.out.print("Enter the medicine name: ");
        String medicineName = input.nextLine();
        System.out.print("Enter the dosage: ");
        String dosage = input.nextLine();
        System.out.print("Enter the schedule: ");
        String schedule = input.nextLine();
        System.out.print("Enter the start date (YYYY-MM-DD): ");
        String startDate = input.nextLine();
        System.out.print("Enter the end date (YYYY-MM-DD): ");
        String endDate = input.nextLine();

        MedicineReminder reminder = new MedicineReminder(user.getId(), medicineName, dosage, schedule, startDate, endDate);
        MedicineReminderManager.addReminder(reminder);
    };

    public static void viewAndUpdateMedicineReminders(Scanner input, User user){
        List<MedicineReminder> userReminders = MedicineReminderManager.getRemindersForUser(user.getId());
        System.out.println();
        System.out.println("Here are your medicine reminders: ");
        for (MedicineReminder medicineReminder : userReminders) {
            System.out.println();
            System.out.println(medicineReminder);
        }
        System.out.println();
        System.out.print("Do you want to update a reminder? Enter 1 for yes or 2 for no: ");
        int choice = input.nextInt();
        
        if(choice == 1){
            System.out.print("What medicine reminder ID do you want to update? ");
            int medicineReminderId = input.nextInt();
            input.nextLine();
            System.out.print("Enter the new medicine name: ");
            String newMedicineName = input.nextLine();
            System.out.print("Enter the new dosage: ");
            String newDosage = input.nextLine();
            System.out.print("Enter the new schedule: ");
            String newSchedule = input.nextLine();
            System.out.print("Enter the new start date (YYYY-MM-DD): ");
            String newStartDate = input.nextLine();
            System.out.print("Enter the new end date (YYYY-MM-DD): ");
            String newEndDate = input.nextLine();

            MedicineReminder newMedicineReminder = new MedicineReminder(user.getId(), newMedicineName, newDosage, newSchedule, newStartDate, newEndDate);
            
            MedicineReminderManager.updateMedicineReminder(medicineReminderId, newMedicineReminder);

            if (!MedicineReminderManager.updateMedicineReminder(medicineReminderId, newMedicineReminder)){
                System.out.println();
                System.out.println("Sorry, your medicine reminder could not be updated in the database. Please try again.");
                System.out.println();
                testPatientPortal(input, user);
            } else {
                System.out.println();
                for (MedicineReminder mReminder : userReminders) {
                    if (mReminder.getMedicineReminderId() == medicineReminderId) {
                        mReminder.setMedicineName(newMedicineName);
                        mReminder.setDosage(newDosage);
                        mReminder.setSchedule(newSchedule);
                        mReminder.setStartDate(newStartDate);
                        mReminder.setEndDate(newEndDate);
                        break; // Exit the loop since we found the desired Medicine Reminder object
                    }
                }
                System.out.println("Thank you. Your medicine reminder with an ID of " + medicineReminderId + " has been updated in the database.");
                System.out.println();
                testPatientPortal(input, user);
            }
        } else if (choice == 2){
            System.out.println();
            System.out.println("Thanks for viewing your medicine reminders.");
            System.out.println();
            testPatientPortal(input, user);
        } else {
            System.out.println("Sorry, that's an invalid entry. Try again.");
            viewAndUpdateMedicineReminders(input, user);
        }

    };

    public static void getOverdueMedicineReminders(User user){
        List<MedicineReminder> overdueReminders = MedicineReminderManager.getOverdueReminders(user.getId());
        if (overdueReminders.isEmpty()) {
            System.out.println();
            System.out.println("You have no overdue medicine reminders.");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("Here is a list of your overdue medicine reminders: ");
            for (MedicineReminder medicineReminder : overdueReminders) {
                System.out.println();
                System.out.println(medicineReminder);
            }
            System.out.println();
        }
        
    };

    public static void deleteMedicineReminder(Scanner input){
        System.out.print("What medicine reminder ID do you want to delete? ");
        int medicineReminderId = input.nextInt();

        boolean bool = MedicineReminderManager.deleteMedicineReminder(medicineReminderId);
        if(bool == false){
            System.out.println();
            System.out.println("Sorry, the medicine reminder could not be deleted from the database. ");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("You have successfully deleted the medicine reminder with an ID of " + medicineReminderId + " from the database.");
            System.out.println();
        }
    }

}
