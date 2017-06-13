package bookmarker.practice.com.bookmarker.domain;

public class Bookmark {

    private String url;
    private String name;

    public Bookmark(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getIdentifier() {
        return name.substring(0, 1).toUpperCase();
    }

}
