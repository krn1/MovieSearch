package raghu.omdb.co;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import javax.inject.Inject;

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

    private CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        getComponent().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getMovieList("you");
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposable.clear();
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
    // end region
}
