package bookmarker.practice.com.bookmarker.services.model;

import bookmarker.practice.com.bookmarker.domain.Bookmark;

public class BookmarkRecord {

    private String url;
    private String name;

    public BookmarkRecord(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public Bookmark asBookmark() {
        return new Bookmark(url, name);
    }
}
