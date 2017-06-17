package bookmarker.practice.com.bookmarker.services;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import bookmarker.practice.com.bookmarker.domain.Bookmark;
import bookmarker.practice.com.bookmarker.services.model.BookmarkRecord;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitWebClient implements WebClient {

    private RetrofitWebRepo bookmarkRepo;
    private static RetrofitWebClient instance;

    private RetrofitWebClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder().client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:4567/").build();
        this.bookmarkRepo = retrofit.create(RetrofitWebRepo.class);
    }

    public static RetrofitWebClient getInstance() {
        if (instance == null) {
            instance = new RetrofitWebClient();
        }
        return instance;
    }

    public CancellableCallback listBookmarks(final ResponseCallback<List<Bookmark>> responseCallback) {
        Call<List<BookmarkRecord>> listCall = this.bookmarkRepo.listBookmarks();
        listCall.enqueue(new Callback<List<BookmarkRecord>>() {
            @Override
            public void onResponse(@NonNull Call<List<BookmarkRecord>> call, @NonNull Response<List<BookmarkRecord>> response) {
                List<Bookmark> bookmarks = new ArrayList<>();
                for (BookmarkRecord record : response.body()) {
                    bookmarks.add(record.asBookmark());
                }
                responseCallback.success(bookmarks);
            }

            @Override
            public void onFailure(@NonNull Call<List<BookmarkRecord>> call, @NonNull Throwable t) {
                responseCallback.failure(t);
            }
        });
        return new CancellableRetrofitCallback(listCall);
    }

    public CancellableCallback storeUrl(String url, final ResponseCallback<Bookmark> responseCallback) {
        Call<BookmarkRecord> uploadFileCall = this.bookmarkRepo.uploadFile(new BookmarkRecord(url));
        uploadFileCall.enqueue(new Callback<BookmarkRecord>() {
            @Override
            public void onResponse(@NonNull Call<BookmarkRecord> call, @NonNull Response<BookmarkRecord> response) {
                Bookmark bookmark = (response.body() != null) ? response.body().asBookmark() : null;
                responseCallback.success(bookmark);
            }

            @Override
            public void onFailure(@NonNull Call<BookmarkRecord> call, @NonNull Throwable t) {
                responseCallback.failure(t);
            }
        });
        return new CancellableRetrofitCallback(uploadFileCall);
    }

}
