package com.diogorborges.themoviedbsample.data.usecase;

import com.diogorborges.themoviedbsample.data.model.Movie;
import com.diogorborges.themoviedbsample.data.model.Movies;
import com.diogorborges.themoviedbsample.data.remote.ApiService;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetSearchResult {

    ApiService apiService;

    @Inject
    GetSearchResult(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<Movies> execute(String query) {
        return apiService.getSearchResult(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
