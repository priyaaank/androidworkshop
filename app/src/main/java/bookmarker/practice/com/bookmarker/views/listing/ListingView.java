package bookmarker.practice.com.bookmarker.views.listing;

import android.support.annotation.StringRes;

public interface ListingView {

    void setupAdapter();

    void showLoadingView();

    void showErrorMessage(@StringRes int message);

    void refreshView();

    void hideLoadingView();

    void toggleListViewVisibility(Boolean visible);

    void toggleEmptyViewVisibility(Boolean visible);
}
