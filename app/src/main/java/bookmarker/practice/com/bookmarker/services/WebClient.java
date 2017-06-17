package bookmarker.practice.com.bookmarker.services;

import java.util.List;

import bookmarker.practice.com.bookmarker.domain.Bookmark;

public interface WebClient {

    CancellableCallback listBookmarks(final ResponseCallback<List<Bookmark>> responseCallback);

    CancellableCallback storeUrl(String url, final ResponseCallback<Bookmark> responseCallback);

}
