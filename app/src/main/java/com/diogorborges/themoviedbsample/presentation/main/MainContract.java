package com.diogorborges.themoviedbsample.presentation.main;

import com.diogorborges.themoviedbsample.data.model.Genre;
import com.diogorborges.themoviedbsample.data.model.Images;
import com.diogorborges.themoviedbsample.data.model.Movie;

import java.util.List;

public interface MainContract {

    interface View {

        void showLoading(boolean isRefresh);

        void showContent(List<Movie> movies, boolean isRefresh, List<Genre> genreList);

        void showError();

        void showNotFoundError();

        void onConfigurationSet(Images images);

        void showGenres(List<Genre> genres);
    }

    interface Presenter {

        void onViewStarted(View view);

        void onViewPaused(View view);

        void onPullToRefresh();

        void onScrollToBottom();

        void onViewResumed(View view);

        void onSearchQueried(String query);
    }

}
