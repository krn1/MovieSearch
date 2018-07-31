package raghu.omdb.co;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import raghu.omdb.co.app.MovieApp;
import raghu.omdb.co.app.MovieAppComponent;
import raghu.omdb.co.repository.model.MovieInfo;
import raghu.omdb.co.repository.model.MovieResults;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity  implements MainActivityContract.View{

    @Inject
    MainActivityPresenter presenter;

    @BindView(R.id.movie_search)
    AppCompatEditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        getComponent().inject(this);

        searchView.setOnClickListener(listener -> {
            String movieName = String.valueOf(searchView.getText());
            if (isValidText(movieName)) {
                presenter.getMovieList(movieName);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void showSpinner() {

    }

    @Override
    public void hideSpinner() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showCarsList(ArrayList<MovieInfo> movieList) {

    }

    // region private

    private MainActivityComponent getComponent() {
        return DaggerMainActivityComponent.builder()
                .movieAppComponent(((MovieApp) getApplication()).getComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build();
    }

    private boolean isValidText(String movieName) {
        return (movieName!=null && !TextUtils.isEmpty(movieName));
    }
    // end region
}
