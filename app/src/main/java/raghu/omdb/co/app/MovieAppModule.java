package raghu.omdb.co.app;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieAppModule {
    private final MovieApp application;

    MovieAppModule(MovieApp application) {
        this.application = application;
    }

    @Provides
    public MovieApp provideApplication() {
        return application;
    }

}
