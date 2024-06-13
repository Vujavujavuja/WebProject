package touristapp.services;

import org.mindrot.jbcrypt.BCrypt;
import touristapp.util.DatabaseUtil;
import touristapp.enteties.User;
import touristapp.enteties.enums.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        UserType.valueOf(rs.getString("user_type")),
                        rs.getBoolean("status")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void addUser(User user) {
        System.out.println(user.getUserType());
        String query = "INSERT INTO users (email, password, first_name, last_name, user_type, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, user.getEmail());
            stmt.setString(2, hashPassword(user.getPassword()));
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, (user.getUserType()).toString());
            stmt.setBoolean(6, user.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //password management
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verify(String email, String password) {
        String query = "SELECT password FROM users WHERE email = ?";
        String storedHash = null;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    storedHash = rs.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return storedHash != null && BCrypt.checkpw(password, storedHash);
    }

    public boolean active(String email) {
        String query = "SELECT status FROM users WHERE email = ?";
        boolean status = false;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getBoolean("status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            UserType.valueOf(rs.getString("user_type")),
                            rs.getBoolean("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return user;
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        User user = null;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            UserType.valueOf(rs.getString("user_type")),
                            rs.getBoolean("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return user;
    }

    public boolean isAdmin(String email) {
        String query = "SELECT user_type FROM users WHERE email = ?";
        String userType = null;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userType = rs.getString("user_type");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(userType);
        return userType != null && userType.equals("ADMIN");
    }

    public boolean isEditor(String email) {
        String query = "SELECT user_type FROM users WHERE email = ?";
        String userType = null;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userType = rs.getString("user_type");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(userType);
        return userType != null && userType.equals("EDITOR");
    }

    public void updateUser(int id, User user) {
        String query = "UPDATE users SET email = ?, password = ?, first_name = ?, last_name = ?, user_type = ?, status = ? WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, user.getEmail());
            stmt.setString(2, hashPassword(user.getPassword()));
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, (user.getUserType()).toString());
            stmt.setBoolean(6, user.getStatus());
            stmt.setInt(7, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}