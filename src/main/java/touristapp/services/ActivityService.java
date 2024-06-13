package touristapp.services;

import touristapp.util.DatabaseUtil;
import touristapp.enteties.Activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityService {

    public List<Activity> getAllActivities() {
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT * FROM activities";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Activity activity = new Activity(
                        rs.getInt("id"),
                        Arrays.asList(rs.getString("keywords").split(","))
                );
                activities.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activities;
    }

    public void addActivity(Activity activity) {
        String query = "INSERT INTO activities (keywords) VALUES (?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, activity.getKeywordsAsString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // get the id of the newly added activity
        activity.setId(getActivityByKeywords(activity.getKeywordsAsString()).getId());
        System.out.println("ID===" + activity.getId());
    }

    public Activity getActivityByKeywords(String keywords) {
        Activity activity = null;
        String query = "SELECT * FROM activities WHERE keywords = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, keywords);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                activity = new Activity(
                        rs.getInt("id"),
                        Arrays.asList(rs.getString("keywords").split(","))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activity;
    }

    public Activity getActivityById(int id) {
        Activity activity = null;
        String query = "SELECT * FROM activities WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                activity = new Activity(
                        rs.getInt("id"),
                        Arrays.asList(rs.getString("keywords").split(","))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activity;
    }

}
