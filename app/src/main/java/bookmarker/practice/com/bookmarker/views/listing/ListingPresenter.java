package bookmarker.practice.com.bookmarker.views.listing;

import java.util.ArrayList;
import java.util.List;

import bookmarker.practice.com.bookmarker.R;
import bookmarker.practice.com.bookmarker.domain.Bookmark;
import bookmarker.practice.com.bookmarker.services.CancellableCallback;
import bookmarker.practice.com.bookmarker.services.ManagedUrlService;
import bookmarker.practice.com.bookmarker.services.ResponseCallback;
import bookmarker.practice.com.bookmarker.services.model.BookmarkRecord;

public class ListingPresenter {

    private ListingView listingView;
    private ManagedUrlService managedUrlService;
    private ResponseCallback<List<Bookmark>> bookmarkListingCallback;
    private List<Bookmark> bookmarks;
    private CancellableCallback<List<BookmarkRecord>> listCancellableCallback;


    ListingPresenter(ListingView listingView, ManagedUrlService managedUrlService) {
        this.listingView = listingView;
        this.managedUrlService = managedUrlService;
        this.bookmarks = new ArrayList<>();
        initializeCallback();
    }

    void onActivityCreate() {
        this.listingView.toggleEmptyViewVisibility(Boolean.TRUE);
        this.listingView.showLoadingView();
        this.listingView.setupAdapter();
        loadBookmarks();
    }

    void onStop() {
        if (listCancellableCallback != null) {
            this.listCancellableCallback.cancel();
            this.listCancellableCallback = null;
        }
    }

    Bookmark getBookmarkAtPos(int index) {
        return bookmarks.get(index);
    }

    long getBookmarkIdForPos(int index) {
        return index;
    }

    int getBookmarksCount() {
        return this.bookmarks == null ? 0 : this.bookmarks.size();
    }

    private void loadBookmarks() {
        listCancellableCallback = managedUrlService.listBookmarks(this.bookmarkListingCallback);
    }

    private void initializeCallback() {
        this.bookmarkListingCallback = new ResponseCallback<List<Bookmark>>() {
            @Override
            public void success(List<Bookmark> bookmarks) {
                showLoadedBookmarks(bookmarks);
            }

            @Override
            public void failure(Throwable error) {
                handleBookmarkLoadFailure();
            }
        };
    }

    private void handleBookmarkLoadFailure() {
        this.listCancellableCallback = null;
        this.showErrorMessage();
        this.listingView.toggleEmptyViewVisibility(Boolean.TRUE);
        this.listingView.toggleListViewVisibility(Boolean.FALSE);
        this.listingView.hideLoadingView();
    }

    private void showLoadedBookmarks(List<Bookmark> bookmarks) {
        this.listCancellableCallback = null;
        this.bookmarks = bookmarks;
        this.listingView.refreshView();
        this.listingView.toggleEmptyViewVisibility(Boolean.FALSE);
        this.listingView.toggleListViewVisibility(Boolean.TRUE);
        this.listingView.hideLoadingView();
    }

    private void showErrorMessage() {
        this.listingView.showErrorMessage(R.string.listing_fetch_error_message);
        this.listingView.hideLoadingView();
    }

}
