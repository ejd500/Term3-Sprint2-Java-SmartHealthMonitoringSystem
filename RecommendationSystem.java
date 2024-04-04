

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * In this basic version of the
 * RecommendationSystem class, complete the generateRecommendations to take a
 * HealthData object as input and generates recommendations based on the user's heart rate and step count.
 * You can also expand this class to include more health data analysis and generate more specific
 * recommendations based on the user's unique health profile
 * NOTE:
 * To integrate this class into your application, you'll need to pass the HealthData object to the generateRecommendations method
 * and store the generated recommendations in the recommendations table in the database.
 */

public class RecommendationSystem {
    private static final int MIN_HEART_RATE = 60;
    private static final int MAX_HEART_RATE = 100;
    private static final int MIN_STEPS = 10000;

    public static List<String> generateRecommendations(HealthData healthData) {
        List<String> recommendations = new ArrayList<>();

        // Analyze heart rate
        int heartRate = healthData.getHeartRate();

        if (heartRate < MIN_HEART_RATE) {
                String recommendation_text = "Your heart rate is lower than the recommended range. Consider increasing your physical activity to improve your cardiovascular health.";
                recommendations.add(recommendation_text);
        }
        if (heartRate > MAX_HEART_RATE){
                String recommendation_text2 = "Your heart rate is higher than the recommended range. Consider decreasing " + 
                "your physical activity to stay within the recommended range.";
                recommendations.add(recommendation_text2);
        }

        // Analyze steps
        int steps = healthData.getSteps();
        if (steps < MIN_STEPS) {
                String recommendation_text3 = "You're not reaching the recommended daily step count. " +
                "Try to incorporate more walking or other physical activities into your daily routine.";
                recommendations.add(recommendation_text3);
        }
        
        return recommendations;
        // Add more health data analysis and recommendations as needed
    }
}
