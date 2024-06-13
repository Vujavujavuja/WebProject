package touristapp.services;

import touristapp.util.DatabaseUtil;
import touristapp.enteties.Article;
import touristapp.enteties.User;
import touristapp.enteties.Destination;
import touristapp.enteties.enums.UserType;
import touristapp.util.LocalDateTimeDeserializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleService {

    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles ORDER BY time DESC";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getTimestamp("time").toLocalDateTime(),
                        rs.getInt("views"),
                        rs.getInt("author_id"),
                        rs.getInt("destination_id"),
                        rs.getInt("activity_id")
                );

                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return articles;
    }

    public void addArticle(Article article) {
        String query = "INSERT INTO articles (title, text, time, views, author_id, destination_id, activity_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, article.getTitle());
            stmt.setString(2, article.getText());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(article.getTime()));
            stmt.setInt(4, article.getViews());
            stmt.setInt(5, article.getAuthorId());
            stmt.setInt(6, article.getDestinationId());
            stmt.setInt(7, article.getActivityId());
            System.out.println(stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Article getArticle(int id) {
        String query = "SELECT * FROM articles WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Article(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("text"),
                            rs.getTimestamp("time").toLocalDateTime(),
                            rs.getInt("views"),
                            rs.getInt("author_id"),
                            rs.getInt("destination_id"),
                            rs.getInt("activity_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteArticle(int id) {
        String query = "DELETE FROM articles WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateArticle(int id, Article article) {
        String query = "UPDATE articles SET title = ?, text = ?, time = ?, views = ? WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, article.getTitle());
            stmt.setString(2, article.getText());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(article.getTime()));
            stmt.setInt(4, article.getViews());
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean existsInTable(String tableName, int id) {
        String query = "SELECT 1 FROM " + tableName + " WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Article> getPlaginizedArticles(int pageNumber, int pageSize) {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles ORDER BY time DESC LIMIT ? OFFSET ?";
        int offset = (pageNumber - 1) * pageSize;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Article article = new Article(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("text"),
                            rs.getTimestamp("time").toLocalDateTime(),
                            rs.getInt("views"),
                            rs.getInt("author_id"),
                            rs.getInt("destination_id"),
                            rs.getInt("activity_id")
                    );
                    articles.add(article);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public int getTotalArticles() {
        String countQuery = "SELECT COUNT(*) AS total FROM articles";
        int totalArticles = 0;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(countQuery);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                totalArticles = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalArticles;
    }

    public List<Article> getMostViewedArticles() {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles ORDER BY views DESC LIMIT 10";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getTimestamp("time").toLocalDateTime(),
                        rs.getInt("views"),
                        rs.getInt("author_id"),
                        rs.getInt("destination_id"),
                        rs.getInt("activity_id")
                );

                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return articles;
    }

    public void addArticleView(int id) {
        String query = "UPDATE articles SET views = views + 1 WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Article> getArticleDestination(int id) {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles WHERE destination_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Article article = new Article(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("text"),
                            rs.getTimestamp("time").toLocalDateTime(),
                            rs.getInt("views"),
                            rs.getInt("author_id"),
                            rs.getInt("destination_id"),
                            rs.getInt("activity_id")
                    );
                    articles.add(article);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public List<Article> getArticleActivity(int id) {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles WHERE activity_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Article article = new Article(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("text"),
                            rs.getTimestamp("time").toLocalDateTime(),
                            rs.getInt("views"),
                            rs.getInt("author_id"),
                            rs.getInt("destination_id"),
                            rs.getInt("activity_id")
                    );
                    articles.add(article);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public int getActivityId(int articleId) {
        String query = "SELECT activity_id FROM articles WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, articleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("activity_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
