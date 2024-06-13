package touristapp.enteties;

import java.time.LocalDateTime;

public class Comment {
    private int id;
    private String author;
    private String text;
    private LocalDateTime date;
    private int articleId;

    public Comment(int id, String author, String text, LocalDateTime date, int articleId) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.date = date;
        this.articleId = articleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
