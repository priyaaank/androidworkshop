package bookmarker.practice.com.bookmarker.views.upload;

import android.content.Intent;

import bookmarker.practice.com.bookmarker.R;
import bookmarker.practice.com.bookmarker.domain.Bookmark;
import bookmarker.practice.com.bookmarker.services.CancellableCallback;
import bookmarker.practice.com.bookmarker.services.ResponseCallback;
import bookmarker.practice.com.bookmarker.services.WebClient;

public class UploadPresenter {

    private UploadView view;
    private WebClient webClient;
    private ResponseCallback<Bookmark> bookmarkCallback;
    private CancellableCallback bookmarkRecordCancellableCallback;

    UploadPresenter(UploadView view, WebClient webClient) {
        this.view = view;
        this.webClient = webClient;
    }

    void onCreate() {
        setupListeners();
    }

    private void setupListeners() {
        this.bookmarkCallback = new ResponseCallback<Bookmark>() {
            @Override
            public void success(Bookmark response) {
                bookmarkRecordCancellableCallback = null;
                view.showSuccessMessage(R.string.bookmark_create_success_msg);
                view.endFlow();
            }

            @Override
            public void failure(Throwable error) {
                bookmarkRecordCancellableCallback = null;
                view.showErrorMessage(R.string.bookmark_create_failure_msg);
                view.endFlow();
            }
        };
    }

    void onIntentReceived(Intent intent) {
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null && type.startsWith("text/")) {
            storeLink(intent.getStringExtra(Intent.EXTRA_TEXT));
        }
    }

    private void storeLink(String link) {
        bookmarkRecordCancellableCallback = this.webClient.storeUrl(link, this.bookmarkCallback);
    }
}
