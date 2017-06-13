package bookmarker.practice.com.bookmarker.views.listing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import bookmarker.practice.com.bookmarker.R;
import bookmarker.practice.com.bookmarker.domain.Bookmark;

class BookmarksAdapter extends BaseAdapter {

    private final Context context;
    private ListingPresenter presenter;

    BookmarksAdapter(Context context, ListingPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return this.presenter.getBookmarksCount();
    }

    @Override
    public Bookmark getItem(int index) {
        return this.presenter.getBookmarkAtPos(index);
    }

    @Override
    public long getItemId(int index) {
        return this.presenter.getBookmarkIdForPos(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bookmark_item_row, parent, false);
        }
        Bookmark currentBookmark = getItem(position);
        TextView mugshotTextView = convertView.findViewById(R.id.mugshot);
        TextView titleTextView = convertView.findViewById(R.id.title);
        TextView linkTextView = convertView.findViewById(R.id.link);
        mugshotTextView.setText(currentBookmark.getIdentifier());
        titleTextView.setText(currentBookmark.getName());
        linkTextView.setText(currentBookmark.getUrl());
        return convertView;
    }

}
