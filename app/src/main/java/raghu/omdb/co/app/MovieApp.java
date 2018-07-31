package raghu.omdb.co.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import timber.log.Timber;

public class MovieApp extends Application {

    private MovieAppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        // dependency injection graph
        component = DaggerMovieAppComponent.builder().movieAppModule(new MovieAppModule(this)).build();
        component.inject(this);

        Timber.plant(new Timber.DebugTree());
        initializeFresco();
    }

    public MovieAppComponent getComponent() {
        return component;
    }

    // region private
    private void initializeFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);
    }
    //endregion
}
