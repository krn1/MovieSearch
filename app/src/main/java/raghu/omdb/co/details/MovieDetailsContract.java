package raghu.omdb.co.details;

import raghu.omdb.co.repository.model.MovieInfo;

interface MovieDetailsContract {
    interface View {

        void showSpinner();

        void hideSpinner();

        void showError(String msg);

        void showMovieInfo(MovieInfo movieInfo);
    }

    interface Presenter {

        void start();

        void stop();

    }
}
