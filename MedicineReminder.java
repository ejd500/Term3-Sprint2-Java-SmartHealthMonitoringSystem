

public class MedicineReminder {
    private int medicineReminderId;
    private int userId;
    private String medicineName;
    private String dosage;
    private String schedule;
    private String startDate;
    private String endDate;

    // Constructor, getters, and setters
    public MedicineReminder(int userId, String medicineName, String dosage, String schedule, String startDate, String endDate){
        this.medicineReminderId = MedicineReminderManager.getMedicineReminderId(userId, medicineName, dosage, schedule, startDate, endDate);
        this.userId = userId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.schedule = schedule;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getMedicineReminderId() {
        return medicineReminderId;
    }
    public void setMedicineReminderId(int medicineReminderId) {
        this.medicineReminderId = medicineReminderId;
    }

    public int getUserId(){
        return userId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }

    public String getMedicineName(){
        return medicineName;
    }
    public void setMedicineName(String medicineName){
        this.medicineName = medicineName;
    }

    public String getDosage(){
        return dosage;
    }
    public void setDosage(String dosage){
        this.dosage = dosage;
    }

    public String getSchedule(){
        return schedule;
    }
    public void setSchedule(String schedule){
        this.schedule = schedule;
    }

    public String getStartDate(){
        return startDate;
    }
    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public String getEndDate(){
        return endDate;
    }
    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Medicine Reminder ID: " + medicineReminderId);
        sb.append(" - User ID: " + userId);
        sb.append(" - Medicine Name: " + medicineName);
        sb.append(" - Dosage: " + dosage);
        sb.append(" - Schedule: " + schedule);
        sb.append(" - Start Date: " + startDate);
        sb.append(" - End Date: " + endDate);
        String result = sb.toString();
        return result;
    }
}
