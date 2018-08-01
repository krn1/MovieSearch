package raghu.omdb.co.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import raghu.omdb.co.R;
import raghu.omdb.co.app.MovieApp;
import raghu.omdb.co.repository.model.MovieDetail;
import raghu.omdb.co.utils.AlertUtils;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View {

    private static String IMDB_ID = "imdbId";

    @BindView(R.id.root_viewr)
    protected View movieContainerView;

    @BindView(R.id.progress_bar_container)
    FrameLayout progressBarContainer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.poster)
    SimpleDraweeView posterImageView;

    @BindView(R.id.thumbnail)
    SimpleDraweeView thumbnailImageView;

    @BindView(R.id.title)
    TextView titleView;

    @BindView(R.id.year)
    TextView yearView;

    @BindView(R.id.rating)
    TextView ratingView;

    @BindView(R.id.plot)
    TextView plotView;

    @BindView(R.id.actors)
    TextView actorsView;

    @BindView(R.id.director)
    TextView directorView;

    @BindView(R.id.production)
    TextView productionView;

    @BindView(R.id.writers)
    TextView writersView;

    @Inject
    MovieDetailsPresenter presenter;

    public static void start(Context context, String imdbId) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(IMDB_ID, imdbId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getComponent(bundle.getString(IMDB_ID)).inject(this);
        } else {
            finish();
        }

        setupToolbar();
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
    public void showMovieInfo(MovieDetail movieInfo) {

        posterImageView.setImageURI(movieInfo.getPoster());
        thumbnailImageView.setImageURI(movieInfo.getPoster());
        titleView.setText(movieInfo.getTitle());
        toolbar.setTitle(movieInfo.getTitle());
        yearView.setText(getString(R.string.year_and_runtime, movieInfo.getYear(), movieInfo.getRuntime()));
        ratingView.setText(getString(R.string.imdb_rating, movieInfo.getImdbRating()));
        plotView.setText(movieInfo.getPlot());
        actorsView.setText(movieInfo.getActors());
        directorView.setText(movieInfo.getDirector());
        productionView.setText(movieInfo.getProduction());
        writersView.setText(movieInfo.getWriter());
    }

    // region private

    private MovieDetailsComponent getComponent(String imdbId) {
        return DaggerMovieDetailsComponent.builder()
                .movieAppComponent(((MovieApp) getApplication()).getComponent())
                .movieDetailsModule(new MovieDetailsModule(this, imdbId))
                .build();
    }
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationIcon(getDrawable(R.drawable.abc_ic_ab_back_material));
        toolbar.setNavigationOnClickListener(toolbar -> onBackPressed());
        toolbar.setTitle("Movie name");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    //endregion
}