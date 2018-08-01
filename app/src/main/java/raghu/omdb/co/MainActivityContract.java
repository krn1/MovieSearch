package raghu.omdb.co;

import java.util.ArrayList;

import raghu.omdb.co.repository.model.MovieInfo;

interface MainActivityContract {
    interface View {

        void showSpinner();

        void hideSpinner();

        void showError(String msg);

        void showMovieList(ArrayList<MovieInfo> movieList);
    }

    interface Presenter {

        void start();

        void stop();

        void loadMovieListsByPage(String movieName);
    }
}
