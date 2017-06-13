package bookmarker.practice.com.bookmarker.views.upload;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import bookmarker.practice.com.bookmarker.services.ManagedUrlService;

public class UploadActivity extends Activity implements UploadView {

    private UploadPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.presenter = new UploadPresenter(this, ManagedUrlService.getInstance());
        this.presenter.onCreate();
        this.presenter.onIntentReceived(getIntent());
    }

    @Override
    public void endFlow() {
        this.finish();
    }

    @Override
    public void showErrorMessage(int message) {
        Toast.makeText(getApplicationContext(), getString(message), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessMessage(int message) {
        Toast.makeText(getApplicationContext(), getString(message), Toast.LENGTH_LONG).show();
    }
}
