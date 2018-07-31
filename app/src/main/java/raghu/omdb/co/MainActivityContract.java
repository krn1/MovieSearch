package raghu.omdb.co;

import android.location.Location;

import java.util.ArrayList;

import raghu.omdb.co.repository.model.MovieInfo;
import raghu.omdb.co.repository.model.MovieResults;

interface MainActivityContract {
    interface View {

        void showSpinner();

        void hideSpinner();

        void showError(String msg);

        void showCarsList(ArrayList<MovieInfo> movieList);
    }

    interface Presenter {

        void stop();

        void getMovieList(String movieName);
    }
}
