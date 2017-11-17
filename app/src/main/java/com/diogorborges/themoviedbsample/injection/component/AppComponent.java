package com.diogorborges.themoviedbsample.injection.component;

import com.diogorborges.themoviedbsample.data.remote.ApiService;
import com.diogorborges.themoviedbsample.injection.module.AppModule;
import com.diogorborges.themoviedbsample.injection.module.NetModule;
import com.diogorborges.themoviedbsample.injection.module.PresentationModule;
import com.diogorborges.themoviedbsample.presentation.detail.DetailActivity;
import com.diogorborges.themoviedbsample.presentation.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class, PresentationModule.class})
public interface AppComponent {

    void inject(DetailActivity detailActivity);

    void inject(MainActivity mainActivity);

    ApiService apiService();

}

