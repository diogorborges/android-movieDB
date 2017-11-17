package com.diogorborges.themoviedbsample.injection.module;

import android.content.Context;

import com.diogorborges.themoviedbsample.BuildConfig;
import com.diogorborges.themoviedbsample.R;
import com.diogorborges.themoviedbsample.data.remote.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class NetModule {

    @Provides
    @Singleton
    Interceptor provideAuthenticationInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                        .build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Interceptor authenticationInterceptor) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .addInterceptor(authenticationInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    JacksonConverterFactory provideJacksonConverterFactory() {
        ObjectMapper objectMapper = new ObjectMapper();
        return JacksonConverterFactory
                .create(objectMapper);
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(JacksonConverterFactory jacksonConverterFactory,
                                    OkHttpClient okHttpClient,
                                    @Named("applicationContext") Context context) {
        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .client(okHttpClient)
                .addConverterFactory(jacksonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

}

