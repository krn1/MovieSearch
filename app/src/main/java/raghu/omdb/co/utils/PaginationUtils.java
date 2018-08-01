package raghu.omdb.co.utils;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Adds listener to display the next set of pages on scrolling. This abstract class let the derived
 * class to define the action to call behavior with loadNextPage() method
 */
public abstract class PaginationUtils extends RecyclerView.OnScrollListener {

    private static final int VISIBLE_THRESHOLD_DEFAULT = 3;

    // The minimum amount of items to have below your current scroll position before loading more.
    private int visibleThreshold = VISIBLE_THRESHOLD_DEFAULT;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout pullToRefresh;

    protected PaginationUtils(LinearLayoutManager layoutManager,
                              SwipeRefreshLayout pullToRefresh) {
        this(layoutManager, pullToRefresh, VISIBLE_THRESHOLD_DEFAULT);
    }

    protected PaginationUtils(LinearLayoutManager linearLayoutManager,
                              SwipeRefreshLayout pullToRefresh,
                              int visibleThreshold) {
        this.layoutManager = linearLayoutManager;
        this.pullToRefresh = pullToRefresh;
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        // if pull to refresh exist enable it
        if (pullToRefresh != null) {
            pullToRefresh.setEnabled(layoutManager.findFirstCompletelyVisibleItemPosition() == 0);
        }

        // End has been reached to take action
        if ((totalItemCount - visibleItemCount)
                <= (firstVisibleItemPosition + visibleThreshold)) {
            loadNextPage();
        }
    }

    // caller specific behavior left for derived class implementation
    public abstract void loadNextPage();
}