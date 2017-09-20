package com.diogorborges.themoviedbsample.data.remote;

import com.diogorborges.themoviedbsample.data.model.Configuration;
import com.diogorborges.themoviedbsample.data.model.Genres;
import com.diogorborges.themoviedbsample.data.model.Movie;
import com.diogorborges.themoviedbsample.data.model.Movies;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    @GET("discover/movie")
    Observable<Movies> getMovies(@Query("primary_release_date.gte") String releaseDate,
                                 @Query("sort_by") String sortBy, @Query("page") int page);

    @GET("movie/{id}")
    Observable<Movie> getMovie(@Path("id") int id);

    @GET("search/movie")
    Observable<Movies> getSearchResult(@Query("query") String query);

    @Headers("Cache-Control: public, max-stale=2419200")
    @GET("configuration")
    Observable<Configuration> getConfiguration();

    @GET("genre/movie/list")
    Observable<Genres> getGenres();

}
