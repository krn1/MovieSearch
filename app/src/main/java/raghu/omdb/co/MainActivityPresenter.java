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
    public void stop() {
        disposable.clear();
    }

    @Override
    public void getMovieList(String movieName) {
        Timber.e("get the movie list ");
        view.showSpinner();
        disposable.add(apiService.searchByTitle(omdbApiKey,
                movieName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<MovieResults>() {
                    @Override
                    public void onNext(MovieResults movieResults) {
                        Timber.e("We got size %d\n ", movieResults.getResults().size());
                        view.hideSpinner();
                        Timber.e(movieResults.toString());

                        if (movieResults.getResults().isEmpty()) {
                            view.showError("Some thing is wrong with the backend");
                        } else {
                            //updateSearchResult(movieResults.getResults());
                            movieList = (ArrayList<MovieInfo>) movieResults.getResults();
                            view.showCarsList(movieList);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Timber.e("Wtf " + throwable.getMessage());
                        view.hideSpinner();
                        view.showError("");
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    //    private void updateSearchResult(List<Result> rentalCars) {
    //        ArrayList<Car> cars = new ArrayList<>();
    //
    //        int id = 1;
    //        for (Result result : rentalCars) {
    //            for (Car car : result.getCars()) {
    //                car.setCompanyName(result.getProvider().getCompanyName());
    //                car.setCarLocation(result.getLocation());
    //                car.setUserLocation(userLocation);
    //                car.setAddress(result.getAddress());
    //                car.setAirport(result.getAirport());
    //                car.setId(id);
    //                cars.add(car);
    //                id++;
    //            }
    //        }
    //        if (!carList.isEmpty()) {
    //            carList.clear();
    //        }
    //        carList.addAll(cars);
    //        view.showCarsList(carList);
    //
    //    }
}
