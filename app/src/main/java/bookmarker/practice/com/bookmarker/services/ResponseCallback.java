package bookmarker.practice.com.bookmarker.services;

public interface ResponseCallback<T> {

    void success(T response);

    void failure(Throwable error);

}
