package com.diogorborges.themoviedbsample.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.diogorborges.themoviedbsample.R;
import com.diogorborges.themoviedbsample.data.model.Genres;
import com.diogorborges.themoviedbsample.data.model.Movie;
import com.diogorborges.themoviedbsample.data.model.SpokenLanguage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StringFormatter {

    private Context context;

    public StringFormatter(Context context) {
        this.context = context;
    }

    public String getReleaseDate(Movie movie) {
        if (movie.releaseDate.equals("")) {
            return "...";
        }
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = form.parse(movie.releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat postFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        return postFormatter.format(date);
    }

    public String getLanguages(List<SpokenLanguage> spokenLanguages) {
        String languages = "";
        for (int i = 0; i < spokenLanguages.size(); i++) {
            SpokenLanguage language = spokenLanguages.get(i);
            languages += language.name + ", ";
        }

        languages = removeTrailingComma(languages);

        return languages.isEmpty() ? "-" : languages;
    }

    @NonNull
    private String removeTrailingComma(String text) {
        text = text.trim();
        if (text.endsWith(",")) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    public String getGenres(List<Genres> genres) {
        if (genres.isEmpty()) {
            return context.getResources().getString(R.string.genres_error);
        }
        String genresString = "";
        for (int i = 0; i < genres.size(); i++) {
            Genres genre = genres.get(i);
            genresString += genre.name + ", ";
        }

        genresString = removeTrailingComma(genresString);

        return genresString.isEmpty() ? "-" : genresString;
    }

    public String getDuration(int runtime) {
        return runtime <= 0 ? "-" : context.getResources().getQuantityString(R.plurals.duration, runtime, runtime);
    }

    public String getOverview(String overview) {
        return TextUtils.isEmpty(overview) ? "-" : overview;
    }

}
