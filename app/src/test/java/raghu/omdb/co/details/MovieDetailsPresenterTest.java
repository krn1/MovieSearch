package raghu.omdb.co.details;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import raghu.omdb.co.moviesearch.utils.TrampolineSchedulerUtils;
import raghu.omdb.co.repository.model.MovieDetail;
import raghu.omdb.co.repository.network.RestApi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class MovieDetailsPresenterTest {
    @Mock
    private MovieDetailsContract.View view;
    @Mock
    private RestApi apiService;

    private CompositeDisposable disposable;

    private MovieDetailsPresenter presenter;
    StringBuffer imdbId = new StringBuffer("imdbId");
    String omdbKey = "omdbKey";

    @BeforeClass
    public static void setUpClass() {
        TrampolineSchedulerUtils.convertSchedulersToTrampoline();
    }

    @Before
    public void setUp() throws Exception {
        disposable = spy(new CompositeDisposable());
        presenter = new MovieDetailsPresenter(view, apiService, omdbKey, disposable, imdbId);
    }

    @Test
    public void getMovieInfo() throws Exception {
        // Given
        MovieDetail movieDetail = createMovie(23);
        when(apiService.searchByOMDbId( omdbKey,imdbId.toString())).thenReturn(Flowable.just(movieDetail));

        // When
        presenter.start();

        // Then
        verify(disposable, times(1)).add(any(Disposable.class));
        verify(view, times(1)).hideSpinner();
        verify(view, times(1)).showMovieInfo(movieDetail);
        verify(view, times(0)).showError("");

    }

    @Test
    public void errorMovieInfo() throws Exception {
        // Given
        when(apiService.searchByOMDbId( omdbKey,imdbId.toString())).thenReturn(Flowable.error(new Throwable()));

        // When
        presenter.start();

        // Then
        verify(disposable, times(1)).add(any(Disposable.class));
        verify(view, times(1)).hideSpinner();
        verify(view, times(0)).showMovieInfo(null);
        verify(view, times(1)).showError("");

    }

    // region private

    private MovieDetail createMovie(int i) {
        MovieDetail movieInfo = new MovieDetail();
        movieInfo.setImdbId(String.valueOf(i));
        movieInfo.setPoster("www.image.com" + i);
        movieInfo.setTitle("title" + i);
        movieInfo.setYear("201" + i);
        movieInfo.setActors("actors"+i);
        movieInfo.setDirector("dir"+i);
        return movieInfo;
    }

    private MovieDetail generateErrorList() {
        MovieDetail emptyList = new MovieDetail();
        return emptyList;
    }

    //endregion
}
