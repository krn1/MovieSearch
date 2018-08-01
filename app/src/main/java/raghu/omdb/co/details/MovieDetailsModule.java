package raghu.omdb.co.details;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import raghu.omdb.co.R;
import raghu.omdb.co.dagger.PerActivity;


@Module
class MovieDetailsModule {
    private MovieDetailsContract.View view;
    private String imdbId = null;

    MovieDetailsModule(MovieDetailsContract.View view, String imdbId) {
        this.view = view;
        this.imdbId = imdbId;
    }

    @PerActivity
    @Provides
    MovieDetailsContract.View providesView() {
        return view;
    }

    @PerActivity
    @Provides
    CompositeDisposable providesDisposable() {
        return new CompositeDisposable();
    }

    @PerActivity
    @Provides
    String provideOMDBKey() {
        return ((MovieDetailsActivity) view).getResources().getString(R.string.api_key);
    }

    @PerActivity
    @Provides
    StringBuffer provideImdbID() {
        return new StringBuffer(imdbId);
    }

}
