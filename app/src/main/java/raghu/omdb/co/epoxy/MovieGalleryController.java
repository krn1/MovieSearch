package raghu.omdb.co.epoxy;

import com.airbnb.epoxy.EpoxyController;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import raghu.omdb.co.repository.model.MovieInfo;

public class MovieGalleryController extends EpoxyController {

    private Set<MovieInfo> movies;

    public MovieGalleryController() {
        movies = new LinkedHashSet<>();
    }

    @Override
    protected void buildModels() {
        for (MovieInfo movieInfo : movies) {
            new ItemViewModel_()
                    .id(movieInfo.getImdbId())
                    .content(movieInfo)
                    .addTo(this);
        }
    }


    public void setContents(List<MovieInfo> movieInfoList) {
        if (!movies.isEmpty()) {
            movies.clear();
        }
        movies.addAll(movieInfoList);
        requestModelBuild();
    }
}
