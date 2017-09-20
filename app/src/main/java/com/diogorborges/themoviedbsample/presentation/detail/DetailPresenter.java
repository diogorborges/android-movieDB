package com.diogorborges.themoviedbsample.presentation.detail;

import android.util.Log;

import com.diogorborges.themoviedbsample.data.model.Configuration;
import com.diogorborges.themoviedbsample.data.model.Images;
import com.diogorborges.themoviedbsample.data.model.Movie;
import com.diogorborges.themoviedbsample.data.usecase.GetConfiguration;
import com.diogorborges.themoviedbsample.data.usecase.GetMovie;

import javax.inject.Inject;

import rx.Subscriber;

public class DetailPresenter implements DetailContract.Presenter {

    private static final String TAG = "DetailPresenter";

    private GetConfiguration getConfiguration;
    private GetMovie getMovie;

    private DetailContract.View view;
    private Images images;

    @Inject
    public DetailPresenter(GetConfiguration getConfiguration, GetMovie getMovie) {
        this.getConfiguration = getConfiguration;
        this.getMovie = getMovie;
    }

    @Override
    public void start(int movieId) {
        view.showLoading();

        if (images == null) {
            loadConfiguration(movieId);
        } else {
            view.onConfigurationSet(images);
            loadMovie(movieId);
        }
    }

    @Override
    public void onViewResumed(DetailContract.View view) {
        attachedView(view);
    }

    private void attachedView(DetailContract.View view) {
        this.view = view;
    }

    private void loadConfiguration(final int movieId) {
        getConfiguration.execute().subscribe(new Subscriber<Configuration>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: loadConfigurations");
            }
            @Override
            public void onError(Throwable e) {
                view.showError();
                defaultErrorHandling(e);
            }
            @Override
            public void onNext(Configuration configuration) {
                if (hasViewAttached()) {
                    view.onConfigurationSet(configuration.images);
                    loadMovie(movieId);
                } else {
                    view.showError();
                }
            }
        });
    }

    private void loadMovie(int movieId) {
        getMovie.execute(movieId).subscribe(new Subscriber<Movie>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: loadMovie");
            }
            @Override
            public void onError(Throwable e) {
                defaultErrorHandling(e);
                view.showError();
            }
            @Override
            public void onNext(Movie movie) {
                if (hasViewAttached()) {
                    view.showContent(movie);
                } else {
                    view.showError();
                }
            }
        });
    }

    @Override
    public void onViewPaused(DetailContract.View view) {
        detachView();
    }

    private void detachView() {
        this.view = null;
    }

    private void defaultErrorHandling(Throwable e) {
        Log.e(TAG, Log.getStackTraceString(e));
    }


    private boolean hasViewAttached() {
        return view != null;
    }

}
