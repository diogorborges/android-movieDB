package com.diogorborges.themoviedbsample;

import android.app.Application;

import com.diogorborges.themoviedbsample.injection.component.DaggerAppComponent;
import com.diogorborges.themoviedbsample.injection.module.PresentationModule;
import com.diogorborges.themoviedbsample.injection.module.NetModule;
import com.diogorborges.themoviedbsample.injection.component.AppComponent;
import com.diogorborges.themoviedbsample.injection.module.AppModule;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = initDaggerComponent();
    }

    private AppComponent initDaggerComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .presentationModule(new PresentationModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}

