package com.diogorborges.themoviedbsample.presentation.main;

import android.annotation.SuppressLint;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.diogorborges.themoviedbsample.data.model.Configuration;
import com.diogorborges.themoviedbsample.data.model.Genre;
import com.diogorborges.themoviedbsample.data.model.Genres;
import com.diogorborges.themoviedbsample.data.model.Movies;
import com.diogorborges.themoviedbsample.data.usecase.GetConfiguration;
import com.diogorborges.themoviedbsample.data.usecase.GetGenres;
import com.diogorborges.themoviedbsample.data.usecase.GetMovies;
import com.diogorborges.themoviedbsample.data.usecase.GetSearchResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

public class MainPresenter implements MainContract.Presenter {

    private static final String TAG = "MainPresenter";

    private MainContract.View view;
    private int page = 1;

    private GetConfiguration getConfiguration;
    private GetMovies getMovies;
    private GetGenres getGenres;
    private GetSearchResult getSearchResult;

    @Inject
    public MainPresenter(GetConfiguration getConfiguration, GetMovies getMovies, GetGenres getGenres,
                         GetSearchResult getSearchResult) {
        this.getConfiguration = getConfiguration;
        this.getMovies = getMovies;
        this.getGenres = getGenres;
        this.getSearchResult = getSearchResult;
    }

    @Override
    public void onViewStarted(MainContract.View view) {
        attachedView(view);

        view.showLoading(false);
        loadMovies(true);

        loadConfigurations();
    }


    @Override
    public void onViewResumed(MainContract.View view) {
        attachedView(view);
    }

    @Override
    public void onSearchQueried(String query) {
        getSearchResult.execute(query).subscribe(new Subscriber<Movies>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: onSearchQueried");
            }

            @Override
            public void onError(Throwable e) {
                defaultErrorHandling(e);
            }

            @Override
            public void onNext(Movies movies) {
                if (!movies.movies.isEmpty()) {
                    loadGenres(movies, true);
                } else {
                    view.showNotFoundError();
                }
            }
        });
    }

    private void attachedView(MainContract.View view) {
        this.view = view;
    }

    private void loadConfigurations() {
        getConfiguration.execute().subscribe(new Subscriber<Configuration>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: loadConfigurations");
            }

            @Override
            public void onError(Throwable e) {
                defaultErrorHandling(e);
            }

            @Override
            public void onNext(Configuration configuration) {
                if (hasViewAttached()) {
                    view.onConfigurationSet(configuration.images);
                }
            }
        });
    }


    private void loadGenres(final Movies movies, final boolean isRefresh) {
        getGenres.execute().subscribe(new Subscriber<Genres>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: loadGenres");
            }

            @Override
            public void onError(Throwable e) {
                defaultErrorHandling(e);
            }

            @Override
            public void onNext(Genres genres) {
                if (hasViewAttached()) {
                    List<Genre> genreList = genres.genres;
                    view.showContent(movies.movies, isRefresh, genreList);
                }
            }
        });
    }

    private void loadMovies(final boolean isRefresh) {
        getMovies.execute(getReleaseDate(), page).subscribe(new Subscriber<Movies>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: loadMovies");
            }

            @Override
            public void onError(Throwable e) {
                view.showError();
                defaultErrorHandling(e);
            }

            @Override
            public void onNext(Movies movies) {
                loadGenres(movies, isRefresh);
            }
        });
    }

    @Override
    public void onPullToRefresh() {
        page = 1; // reset
        view.showLoading(true);
        loadMovies(true);
    }

    @Override
    public void onScrollToBottom() {
        page++;
        view.showLoading(true);
        loadMovies(false);
    }

    private boolean hasViewAttached() {
        return view != null;
    }

    @Override
    public void onViewPaused(MainContract.View view) {
        detachView();
    }

    private void detachView() {
        this.view = null;
    }

    private void defaultErrorHandling(Throwable e) {
        Log.e(TAG, Log.getStackTraceString(e));
    }

    @VisibleForTesting
    public String getReleaseDate() {
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        return format1.format(cal.getTime());
    }

}
