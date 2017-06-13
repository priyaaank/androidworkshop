package bookmarker.practice.com.bookmarker.views.upload;

import android.support.annotation.StringRes;

public interface UploadView {

    void endFlow();

    void showErrorMessage(@StringRes  int message);

    void showSuccessMessage(@StringRes  int message);
}
