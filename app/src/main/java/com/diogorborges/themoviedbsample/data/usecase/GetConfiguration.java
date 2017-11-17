package com.diogorborges.themoviedbsample.data.usecase;

import com.diogorborges.themoviedbsample.data.model.Configuration;
import com.diogorborges.themoviedbsample.data.remote.ApiService;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetConfiguration {

    ApiService apiService;

    @Inject
    GetConfiguration(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<Configuration> execute() {
        return apiService.getConfiguration()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
