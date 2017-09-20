package com.diogorborges.themoviedbsample.data.usecase;

import com.diogorborges.themoviedbsample.data.model.Genres;
import com.diogorborges.themoviedbsample.data.remote.ApiService;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetGenres {

    ApiService apiService;

    @Inject
    GetGenres(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<Genres> execute() {
        return apiService.getGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
