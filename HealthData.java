
public class HealthData {
    private int healthDataId;
    private int userId;
    private double weight;
    private double height;
    private int steps;
    private int heartRate;
    private String date;

    // Constructor, getters, and setters

    public HealthData(int userId, double weight, double height, int steps, int heartRate, String date){
        this.healthDataId = HealthDataDao.getHealthDataId(userId, weight, height, steps, heartRate, date);
        this.userId = userId;
        this.weight = weight;
        this.height = height;
        this.steps = steps;
        this.heartRate = heartRate;
        this.date = date;
    }

    public int getHealthDataId() {
        return healthDataId;
    }

    public void setHealthDataId(int healthDataId) {
        this.healthDataId = healthDataId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Health Data ID: " + healthDataId);
        sb.append(" - Weight: " + weight);
        sb.append(" - Height: " + height);
        sb.append(" - Steps: " + steps);
        sb.append(" - Heart Rate: " + heartRate);
        sb.append(" - Date: " + date);
        String result = sb.toString();
        return result;
    }

}
