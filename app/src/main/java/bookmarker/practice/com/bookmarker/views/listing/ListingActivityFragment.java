package bookmarker.practice.com.bookmarker.views.listing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import bookmarker.practice.com.bookmarker.R;
import bookmarker.practice.com.bookmarker.services.RetrofitWebClient;
import bookmarker.practice.com.bookmarker.views.progress.ProgressDialogFragment;

public class ListingActivityFragment extends ListFragment implements ListingView {

    public static final String PROGRESS_FRAGMENT_TAG = "progress";
    private ListingPresenter presenter;
    private View view;

    public ListingActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.presenter = new ListingPresenter(this, RetrofitWebClient.getInstance());
        view = inflater.inflate(R.layout.fragment_listing, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.presenter.onActivityCreate();
    }

    @Override
    public void setupAdapter() {
        setListAdapter(new BookmarksAdapter(getActivity().getApplicationContext(), presenter));
    }

    @Override
    public void showErrorMessage(int message) {
        Toast.makeText(getContext(), getString(message), Toast.LENGTH_LONG).show();
    }

    @Override
    public void refreshView() {
        ((BookmarksAdapter) this.getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void showLoadingView() {
        ProgressDialogFragment.newInstance().show(getFragmentManager().beginTransaction(), PROGRESS_FRAGMENT_TAG);
    }

    @Override
    public void hideLoadingView() {
        ProgressDialogFragment progressDialog = (ProgressDialogFragment) getFragmentManager().findFragmentByTag(PROGRESS_FRAGMENT_TAG);
        progressDialog.dismiss();
    }

    @Override
    public void toggleListViewVisibility(Boolean visible) {
        getListView().setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void toggleEmptyViewVisibility(Boolean visible) {
        this.view.findViewById(R.id.included_empty_view).setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.presenter.onStop();
    }
}
