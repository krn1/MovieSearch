package raghu.omdb.co;

import dagger.Component;
import raghu.omdb.co.app.MovieAppComponent;
import raghu.omdb.co.dagger.PerActivity;

@PerActivity
@Component(dependencies = MovieAppComponent.class, modules = MainActivityModule.class)
interface MainActivityComponent {

    void inject(MainActivity mainActivity);
}
