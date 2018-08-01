package raghu.omdb.co;

import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import raghu.omdb.co.dagger.PerActivity;
import raghu.omdb.co.repository.model.MovieInfo;
import raghu.omdb.co.repository.model.MovieResults;
import raghu.omdb.co.repository.network.RestApi;
import timber.log.Timber;

@PerActivity
class MainActivityPresenter implements MainActivityContract.Presenter {

    @VisibleForTesting
    protected int PAGE_NUM = 1;
    private MainActivityContract.View view;
    private CompositeDisposable disposable;
    private RestApi apiService;
    private String omdbApiKey;

    @VisibleForTesting
    ArrayList<MovieInfo> movieList;

    @Inject
    MainActivityPresenter(MainActivityContract.View view,
                          RestApi apiService,
                          String omdbApiKey,
                          CompositeDisposable disposable) {
        this.view = view;
        this.apiService = apiService;
        this.omdbApiKey = omdbApiKey;
        this.disposable = disposable;

        this.movieList = new ArrayList<>();
    }

    @Override
    public void start() {
        PAGE_NUM = 1;
    }

    @Override
    public void stop() {
        disposable.clear();
    }

    @Override
    public void loadMovieListsByPage(String movieName) {
        view.showSpinner();
        disposable.add(apiService.searchByTitle(omdbApiKey,
                movieName,
                PAGE_NUM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<MovieResults>() {
                    @Override
                    public void onNext(MovieResults movieResults) {
                        view.hideSpinner();

                        if (movieResults.getError() != null || movieResults.getResults().isEmpty()) {
                            view.showError("Some search words dont yeild right results.Please modify your input!");
                        } else {
                            movieList = (ArrayList<MovieInfo>) movieResults.getResults();
                            updateSearchResult();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Timber.e("Wtf " + throwable.getMessage());
                        view.hideSpinner();
                        view.showError(""); // default error msg
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    private void updateSearchResult() {
        PAGE_NUM++;
        view.showMovieList(movieList);
    }
}
