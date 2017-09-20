package com.diogorborges.themoviedbsample.injection.module;

import com.diogorborges.themoviedbsample.data.usecase.GetConfiguration;
import com.diogorborges.themoviedbsample.data.usecase.GetGenres;
import com.diogorborges.themoviedbsample.data.usecase.GetMovie;
import com.diogorborges.themoviedbsample.data.usecase.GetMovies;
import com.diogorborges.themoviedbsample.data.usecase.GetSearchResult;
import com.diogorborges.themoviedbsample.presentation.detail.DetailContract;
import com.diogorborges.themoviedbsample.presentation.detail.DetailPresenter;
import com.diogorborges.themoviedbsample.presentation.main.MainContract;
import com.diogorborges.themoviedbsample.presentation.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresentationModule {

    @Provides
    DetailContract.Presenter providesDetailPresenter(GetConfiguration getConfiguration, GetMovie getMovie) {
        return new DetailPresenter(getConfiguration, getMovie);
    }

    @Provides
    MainContract.Presenter providesMainPresenter(GetConfiguration getConfiguration, GetMovies getMovies, GetGenres getGenres, GetSearchResult getSearchResult) {
        return new MainPresenter(getConfiguration, getMovies, getGenres, getSearchResult);
    }

}