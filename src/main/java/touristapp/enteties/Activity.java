package touristapp.enteties;

import java.util.Arrays;
import java.util.List;

public class Activity {
    private int id;
    private List<String> keywords;

    public Activity(int id, List<String> keywords) {
        this.id = id;
        this.keywords = keywords;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void addKeyword(String keyword) {
        keywords.add(keyword);
    }

    public void removeKeyword(String keyword) {
        keywords.remove(keyword);
    }

    public void clearKeywords() {
        keywords.clear();
    }

    public String getKeywordsAsString() {
        return String.join(",", keywords);
    }

    public void setKeywordsFromString(String keywordsStr) {
        this.keywords = Arrays.asList(keywordsStr.split(","));
    }
}
