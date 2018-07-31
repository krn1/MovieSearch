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

        setUpEpoxy();
        searchView.setOnClickListener(listener -> onSearchClicked());
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void showSpinner() {
        progressBarContainer.setVisibility(View.VISIBLE);
        searchHeader.setVisibility(View.GONE);
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
    }

    private void onSearchClicked() {
        String movieName = String.valueOf(searchView.getText());
        if (movieName != null && !TextUtils.isEmpty(movieName) && !movieName.equalsIgnoreCase(currentSearch)) {
            currentSearch = movieName;
            presenter.getMovieList(movieName);
        }
    }

    // end region
}
