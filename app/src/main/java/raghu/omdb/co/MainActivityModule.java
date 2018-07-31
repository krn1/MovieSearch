package raghu.omdb.co;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import raghu.omdb.co.app.MovieAppComponent;
import raghu.omdb.co.dagger.PerActivity;


@Module
class MainActivityModule {
    private MainActivityContract.View view;
    MainActivityModule(MainActivityContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    MainActivityContract.View providesView() {
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
        return ((MainActivity) view).getResources().getString(R.string.api_key);
    }

}
