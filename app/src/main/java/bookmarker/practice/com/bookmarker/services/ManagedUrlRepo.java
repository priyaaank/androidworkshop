package bookmarker.practice.com.bookmarker.services;

import java.util.List;

import bookmarker.practice.com.bookmarker.services.model.BookmarkRecord;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ManagedUrlRepo {

    @GET("/bookmarks")
    Call<List<BookmarkRecord>> listBookmarks();

    @POST("/bookmark")
    Call<BookmarkRecord> uploadFile(@Body BookmarkRecord createRequest);

}
