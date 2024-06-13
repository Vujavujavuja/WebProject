package touristapp.services;

import touristapp.util.DatabaseUtil;
import touristapp.enteties.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentService {

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM comments";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Comment comment = new Comment(
                        rs.getInt("id"),
                        rs.getString("author"),
                        rs.getString("text"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getInt("article_id")
                );
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    public void addComment(Comment comment) {
        String query = "INSERT INTO comments (article_id, author, text, date) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, comment.getArticleId());
            stmt.setString(2, comment.getAuthor());
            stmt.setString(3, comment.getText());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(LocalDateTime.now())); // Set current date and time
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Comment> getCommentsByArticleId(int articleId) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM comments WHERE article_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, articleId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment(
                        rs.getInt("id"),
                        rs.getString("author"),
                        rs.getString("text"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getInt("article_id")
                );
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }
}
