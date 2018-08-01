package raghu.omdb.co;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import raghu.omdb.co.moviesearch.utils.TrampolineSchedulerUtils;
import raghu.omdb.co.repository.model.MovieInfo;
import raghu.omdb.co.repository.model.MovieResults;
import raghu.omdb.co.repository.network.RestApi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {
    @Mock
    private MainActivityContract.View view;
    @Mock
    private RestApi apiService;

    private CompositeDisposable disposable;

    private MainActivityPresenter presenter;

    @BeforeClass
    public static void setUpClass() {
        TrampolineSchedulerUtils.convertSchedulersToTrampoline();
    }

    @Before
    public void setUp() throws Exception {
        disposable = spy(new CompositeDisposable());
        presenter = new MainActivityPresenter(view, apiService, "omdbKey", disposable);
    }

    @Test
    public void emptyList() throws Exception {
        // Given
        MovieResults emptyList = new MovieResults();
        emptyList.setResults(Collections.emptyList());
        when(apiService.searchByTitle("omdbKey", "moviename", presenter.PAGE_NUM)).thenReturn(Flowable.just(emptyList));

        // When
        presenter.start();
        presenter.loadMovieListsByPage("moviename");

        // Then
        verify(disposable, times(1)).add(any(Disposable.class));
        verify(view, times(1)).hideSpinner();
        verify(view, times(0)).showMovieList(presenter.movieList);
        verify(view, times(1)).showError("Some search words dont yeild right results.Please modify your input!");

    }

    @Test
    public void emptyListWithError() throws Exception {
        // Given
        when(apiService.searchByTitle("omdbKey", "moviename", presenter.PAGE_NUM)).thenReturn(Flowable.just(generateErrorList()));

        // When
        presenter.start();
        presenter.loadMovieListsByPage("moviename");

        // Then
        verify(disposable, times(1)).add(any(Disposable.class));
        verify(view, times(1)).hideSpinner();
        verify(view, times(0)).showMovieList(presenter.movieList);
        verify(view, times(1)).showError("Some search words dont yeild right results.Please modify your input!");

    }

    @Test
    public void fetchMovieList() throws Exception {
        // Given
        MovieResults movieResults = new MovieResults();
        movieResults.setResults(createMovieList(6));

        when(apiService.searchByTitle("omdbKey", "moviename", presenter.PAGE_NUM)).thenReturn(Flowable.just(movieResults));

        // When
        presenter.start();
        presenter.loadMovieListsByPage("moviename");

        // Then
        verify(view, times(1)).showSpinner();
        verify(view, times(1)).hideSpinner();
        verify(disposable, times(1)).add(any(Disposable.class));
        verify(view, times(1)).showMovieList(presenter.movieList);
        assert (presenter.PAGE_NUM == 2);
    }

    @Test
    public void fetchMovieListOnScrolling() throws Exception {
        // Given
        MovieResults movieResults = new MovieResults();
        movieResults.setResults(createMovieList(6));
        presenter.PAGE_NUM = 5;

        when(apiService.searchByTitle("omdbKey", "moviename", presenter.PAGE_NUM)).thenReturn(Flowable.just(movieResults));

        // When
        presenter.loadMovieListsByPage("moviename");

        // Then
        verify(view, times(1)).showSpinner();
        verify(view, times(1)).hideSpinner();
        verify(disposable, times(1)).add(any(Disposable.class));
        verify(view, times(1)).showMovieList(presenter.movieList);
        assert (presenter.PAGE_NUM == 6);
    }

    // region private
    private List<MovieInfo> createMovieList(int size) {
        List<MovieInfo> movieInfos = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            movieInfos.add(createMovie(i));
        }
        return movieInfos;
    }

    private MovieInfo createMovie(int i) {
        MovieInfo movieInfo = new MovieInfo();
        movieInfo.setImdbId(String.valueOf(i));
        movieInfo.setPoster("www.image.com" + i);
        movieInfo.setTitle("title" + i);
        movieInfo.setYear("201" + i);
        return movieInfo;
    }

    private MovieResults generateErrorList() {
        MovieResults emptyList = new MovieResults();
        emptyList.setResults(Collections.emptyList());
        emptyList.setError("Some thing wrong");
        return emptyList;
    }

    //endregion
}
