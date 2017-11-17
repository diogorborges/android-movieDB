package com.diogorborges.themoviedbsample.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Named("applicationContext")
    public Context providesApplication() {
        return application;
    }
}

