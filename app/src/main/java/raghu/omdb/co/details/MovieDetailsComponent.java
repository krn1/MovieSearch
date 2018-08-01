package raghu.omdb.co.details;

import dagger.Component;
import raghu.omdb.co.app.MovieAppComponent;
import raghu.omdb.co.dagger.PerActivity;

@PerActivity
@Component(dependencies = MovieAppComponent.class, modules = MovieDetailsModule.class)
interface MovieDetailsComponent {
    void inject(MovieDetailsActivity mainActivity);
}
