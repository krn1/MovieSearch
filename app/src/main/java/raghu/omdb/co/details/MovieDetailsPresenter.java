package raghu.omdb.co.details;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import raghu.omdb.co.dagger.PerActivity;
import raghu.omdb.co.repository.model.MovieDetail;
import raghu.omdb.co.repository.network.RestApi;

@PerActivity
class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private MovieDetailsContract.View view;
    private CompositeDisposable disposable;
    private RestApi apiService;
    private String omdbApiKey;
    private String imdbID;

    @Inject
    MovieDetailsPresenter(MovieDetailsContract.View view,
                          RestApi apiService,
                          String omdbApiKey,
                          CompositeDisposable disposable,
                          StringBuffer imdbID) {
        this.view = view;
        this.apiService = apiService;
        this.omdbApiKey = omdbApiKey;
        this.disposable = disposable;

        this.imdbID = imdbID.toString();
    }

    @Override
    public void start() {
        getMovieInfo();
    }

    @Override
    public void stop() {
        disposable.clear();
    }

    private void getMovieInfo() {
        view.showSpinner();
        disposable.add(apiService.searchByOMDbId(omdbApiKey, imdbID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<MovieDetail>() {
                    @Override
                    public void onNext(MovieDetail movieDetail) {
                        view.hideSpinner();
                        view.showMovieInfo(movieDetail);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        view.hideSpinner();
                        view.showError(""); // default error msg
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }
}
