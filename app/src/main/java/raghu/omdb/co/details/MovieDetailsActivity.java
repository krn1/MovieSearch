package raghu.omdb.co.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import raghu.omdb.co.R;
import raghu.omdb.co.app.MovieApp;
import raghu.omdb.co.repository.model.MovieInfo;
import raghu.omdb.co.utils.AlertUtils;
import timber.log.Timber;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View {

    private static String IMDB_ID = "imdbId";

    @BindView(R.id.ll_movie_container)
    protected View movieContainerView;

    @BindView(R.id.progress_bar_container)
    FrameLayout progressBarContainer;

    @BindView(R.id.iv_poster)
    ImageView posterImageView;

    @BindView(R.id.iv_thumbnail)
    ImageView thumbnailImageView;

    @BindView(R.id.tv_title)
    TextView titleTextView;

    @BindView(R.id.tv_year_runtime)
    TextView yearRuntimeTextView;

    @BindView(R.id.tv_rating)
    TextView ratingTextView;

    @BindView(R.id.tv_plot)
    TextView plotTextView;

    @BindView(R.id.tv_actors)
    TextView actorsTextView;

    @BindView(R.id.tv_director)
    TextView directorTextView;

    @BindView(R.id.tv_production)
    TextView productionTextView;

    @BindView(R.id.tv_writers)
    TextView writersTextView;

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

        Timber.e("here ?");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getComponent(bundle.getString(IMDB_ID)).inject(this);
        } else {
            finish();
        }
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

    // region private

    private MovieDetailsComponent getComponent(String imdbId) {
        return DaggerMovieDetailsComponent.builder()
                .movieAppComponent(((MovieApp) getApplication()).getComponent())
                .movieDetailsModule(new MovieDetailsModule(this,imdbId))
                .build();
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
    public void showMovieInfo(MovieInfo movieInfo) {

    }

    //endregion
    //    @Override
//    public void setPresenter(DetailsContract.Presenter presenter) {
//        this.presenter = presenter;
//    }
//
//    @Override
//    public void setPoster(String poster) {
//        Picasso.with(this).load(poster).fit().centerCrop().into(posterImageView);
//    }
//
//    @Override
//    public void setTitle(String title) {
//        titleTextView.setText(title);
//    }
//
//    @Override
//    public void setThumbnail(String poster) {
//        Picasso.with(this).load(poster).fit().into(thumbnailImageView);
//    }
//
//    @Override
//    public void setPlot(String plot) {
//        plotTextView.setText(plot);
//    }
//
//    @Override
//    public void setYearAndRuntime(String year, String runtime) {
//        yearRuntimeTextView.setText(getString(R.string.year_and_runtime, year, runtime));
//    }
//
//    @Override
//    public void setActors(String actors) {
//        actorsTextView.setText(actors);
//    }
//
//    @Override
//    public void setDirector(String director) {
//        directorTextView.setText(director);
//    }
//
//    @Override
//    public void setProduction(String production) {
//        productionTextView.setText(production);
//    }
//
//    @Override
//    public void setWriters(String writers) {
//        writersTextView.setText(writers);
//    }
//
//    @Override
//    public void setIMDbRating(String imdbRating) {
//        ratingTextView.setText(getString(R.string.imdb_rating, imdbRating));
//    }
//
//    @Override
//    public void showError() {
//        Toast.makeText(this, getText(R.string.error_state), Toast.LENGTH_SHORT).show();
//        finish();
//    }
//
//    @Override
//    public void showLoading() {
//        loadingView.setVisibility(View.VISIBLE);
//        movieContainerView.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void dismissLoading() {
//        loadingView.setVisibility(View.GONE);
//        movieContainerView.setVisibility(View.VISIBLE);
//    }
}