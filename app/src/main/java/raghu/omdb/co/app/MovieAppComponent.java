package raghu.omdb.co.app;

import javax.inject.Singleton;

import dagger.Component;
import raghu.omdb.co.repository.network.NetworkModule;
import raghu.omdb.co.repository.network.RestApi;

@Singleton
@Component(modules = {MovieAppModule.class, NetworkModule.class})
public interface MovieAppComponent {

    void inject(MovieApp application);

    MovieApp application();

    RestApi restApi();
}
