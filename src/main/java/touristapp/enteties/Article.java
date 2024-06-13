package touristapp.enteties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Article {
    private int id;
    private String title;
    private String text;
    private LocalDateTime time;
    private int views;
    private int authorId;
    private int destinationId;
    private List<Comment> comments;
    private int activityId;

    public Article(int id, String title, String text, LocalDateTime time, int views, int authorId, int destinationId, int activityId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.time = time;
        this.views = views;
        this.authorId = authorId;
        this.destinationId = destinationId;
        this.comments = new ArrayList<>();
        this.activityId = activityId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void incrementViews() {
        this.views++;
    }

    public int getActivityId() {
        return activityId;
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }

    public void setComment(int index, Comment comment) {
        this.comments.set(index, comment);
    }



    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", time=" + time +
                ", views=" + views +
                ", authorId=" + authorId +
                ", destinationId=" + destinationId +
                ", comments=" + comments +
                ", activityId=" + activityId +
                '}';
    }
}
