package touristapp.enteties;

public class Destination {
    private int id;
    private String name;
    private String description;
    private String country;
    private int rating;

    public Destination(int id, String name, String description, String country, int rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.country = country;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
