package com.diogorborges.themoviedbsample.data.usecase;

import com.diogorborges.themoviedbsample.data.model.Movie;
import com.diogorborges.themoviedbsample.data.remote.ApiService;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetMovie {

    ApiService apiService;

    @Inject
    GetMovie(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<Movie> execute(int movieId) {
        return apiService.getMovie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
