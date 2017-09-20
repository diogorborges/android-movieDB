package com.diogorborges.themoviedbsample.presentation.detail;

import com.diogorborges.themoviedbsample.data.model.Images;
import com.diogorborges.themoviedbsample.data.model.Movie;

public interface DetailContract {

    public interface View {

        void showLoading();

        void showContent(Movie movie);

        void showError();

        void onConfigurationSet(Images images);

    }

    public interface Presenter {

        void start(int movieId);

        void onViewResumed(View view);

        void onViewPaused(View view);

    }

}
