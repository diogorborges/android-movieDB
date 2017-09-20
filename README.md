# TheMovieDBSample
Android App using The Movie DB API

# Description
Shows a list of the latest Movies sorted by upcoming movies, which could be drilled down to view the Detail.

Utilizes a simple MVP pattern, similar to [https://github.com/googlesamples/android-architecture/tree/todo-mvp-dagger](https://github.com/googlesamples/android-architecture/tree/todo-mvp-dagger) but without the use of Fragments. 
Using the Clean Architecture Use Cases in RxJava.

## Requirements
GitHub
Android Studio
Minimum API: 9

## Libraries Used
Dagger 2;
Retrofit 2; 
OkHttp3;
Glide;
Butterknife;
Stetho;
RxJava.

## Features

### Movie List (Title, Poster, Genres, Release Date)
  - Pull to Refresh
  - Endless Scroll

### Movie Detail (Genres, Duration, Language, Date, Title, Poster, Overview)
  - View in Custom Chrome Tab via "More Info" Button
  
### Search Component
  - Search a movie by the name
  
