package raghu.omdb.co.details;

import raghu.omdb.co.repository.model.MovieDetail;

interface MovieDetailsContract {
    interface View {

        void showSpinner();

        void hideSpinner();

        void showError(String msg);

        void showMovieInfo(MovieDetail movieInfo);
    }

    interface Presenter {

        void start();

        void stop();

    }
}
