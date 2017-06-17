package bookmarker.practice.com.bookmarker.services;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import bookmarker.practice.com.bookmarker.domain.Bookmark;
import bookmarker.practice.com.bookmarker.services.model.BookmarkRecord;

public class AsyncWebClient implements WebClient {

    public CancellableCallback listBookmarks(final ResponseCallback<List<Bookmark>> responseCallback) {
        final GetBookmarks getBookmarksCall = new GetBookmarks(responseCallback);
        getBookmarksCall.execute();
        return new CancellableCallback() {
            @Override
            public void cancel() {
                getBookmarksCall.cancel(Boolean.TRUE);
            }
        };
    }

    @Override
    public CancellableCallback storeUrl(String url, ResponseCallback<Bookmark> responseCallback) {
        return null;
    }

    private class GetBookmarks extends AsyncTask<Void, Void, List<BookmarkRecord>> {

        private ResponseCallback<List<Bookmark>> responseCallback;

        GetBookmarks(ResponseCallback<List<Bookmark>> responseCallback) {
            this.responseCallback = responseCallback;
        }

        @Override
        protected List<BookmarkRecord> doInBackground(Void... args) {
            try {
                String recordListAsString = ClientBuilder.newClient().target("http://10.0.2.2:4567/bookmarks").request().accept(MediaType.APPLICATION_JSON).get(String.class);
                Gson gson = new Gson();
                Type type = new TypeToken<List<BookmarkRecord>>() {
                }.getType();
                List<BookmarkRecord> list = gson.fromJson(recordListAsString, type);
                return list;
            } catch (Exception ce) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<BookmarkRecord> bookmarkRecords) {
            super.onPostExecute(bookmarkRecords);
            if (bookmarkRecords == null && !isCancelled()) {
                this.responseCallback.failure(new Exception("Could not fetch bookmarks"));
            } else {
                List<Bookmark> bookmarks = new ArrayList<>();
                for (BookmarkRecord record : bookmarkRecords) {
                    bookmarks.add(record.asBookmark());
                }
                if (!isCancelled()) {
                    this.responseCallback.success(bookmarks);
                }
            }
        }
    }

}
