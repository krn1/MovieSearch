package raghu.omdb.co;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyRecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import raghu.omdb.co.app.MovieApp;
import raghu.omdb.co.epoxy.MovieGalleryController;
import raghu.omdb.co.repository.model.MovieInfo;
import raghu.omdb.co.utils.AlertUtils;
import raghu.omdb.co.utils.KeyboardUtils;
import raghu.omdb.co.utils.PaginationUtils;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    @Inject
    MainActivityPresenter presenter;

    @BindView(R.id.movie_search)
    AppCompatAutoCompleteTextView searchView;

    @BindView(R.id.progress_bar_container)
    FrameLayout progressBarContainer;

    @BindView(R.id.list_header)
    TextView searchHeader;

    @BindView(R.id.list)
    EpoxyRecyclerView list;

    private MovieGalleryController movieGalleryController;
    private String currentSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        getComponent().inject(this);

        searchView.setOnClickListener(listener -> onSearchClicked());
        presenter.start();
        setUpEpoxy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stop();
    }

    @Override
    public void showSpinner() {
        progressBarContainer.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void hideSpinner() {
        progressBarContainer.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void showError(String msg) {
        AlertUtils.displayError(this, msg);
    }

    @Override
    public void showMovieList(ArrayList<MovieInfo> movieList) {
        searchHeader.setVisibility(View.VISIBLE);
        movieGalleryController.setContents(movieList);
        KeyboardUtils.hideKeyboard(this);
    }

    // region private

    private MainActivityComponent getComponent() {
        return DaggerMainActivityComponent.builder()
                .movieAppComponent(((MovieApp) getApplication()).getComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build();
    }

    private void setUpEpoxy() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        movieGalleryController = new MovieGalleryController();
        list.setLayoutManager(layoutManager);
        list.setController(movieGalleryController);

        list.addOnScrollListener(new PaginationUtils(layoutManager, null) {
            @Override
            public void loadNextPage() {
                if (!isLoading()) {
                    presenter.loadMovieListsByPage(currentSearch);
                }
            }
        });
    }

    private void onSearchClicked() {
        String movieName = String.valueOf(searchView.getText());
        if (movieName != null && !TextUtils.isEmpty(movieName) && !movieName.equalsIgnoreCase(currentSearch)) {
            currentSearch = movieName;
            presenter.start();
            presenter.loadMovieListsByPage(movieName);
        }
    }

    private boolean isLoading() {
        return progressBarContainer.getVisibility() == View.VISIBLE;
    }
    // end region
}
