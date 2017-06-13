package bookmarker.practice.com.bookmarker.services;

import retrofit2.Call;

public class CancellableCallback<T> {

    private Call<T> wrappedCallback;

    public CancellableCallback(Call<T> wrappedCallback) {
        this.wrappedCallback = wrappedCallback;
    }

    public void cancel() {
        this.wrappedCallback.cancel();
    }

}
