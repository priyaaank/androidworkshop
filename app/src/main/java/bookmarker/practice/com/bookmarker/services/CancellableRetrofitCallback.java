package bookmarker.practice.com.bookmarker.services;

import retrofit2.Call;

public class CancellableRetrofitCallback implements CancellableCallback {

    private Call wrappedCallback;

    public CancellableRetrofitCallback(Call wrappedCallback) {
        this.wrappedCallback = wrappedCallback;
    }

    public void cancel() {
        this.wrappedCallback.cancel();
    }

}
