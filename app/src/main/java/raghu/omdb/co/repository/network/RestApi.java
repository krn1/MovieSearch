package raghu.omdb.co.repository.network;

import android.graphics.Movie;

import io.reactivex.Flowable;
import raghu.omdb.co.repository.model.MovieResults;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET("/")
    Flowable<MovieResults> searchByTitle( @Query("apikey") String apiKey,@Query("s") String title,@Query("page")int page);

    @GET("/")
    Flowable<Movie> searchByOMDbId(@Query("i") String omdbId);
}
