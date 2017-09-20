package com.diogorborges.themoviedbsample.presentation.main;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diogorborges.themoviedbsample.R;
import com.diogorborges.themoviedbsample.data.model.Genre;
import com.diogorborges.themoviedbsample.data.model.Images;
import com.diogorborges.themoviedbsample.data.model.Movie;
import com.diogorborges.themoviedbsample.util.StringFormatter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private List<Movie> movies;
    private Activity activity;
    private Images images;
    private ItemClickListener itemClickListener;
    private List<Genre> genres;
    private StringFormatter stringFormatter;


    MoviesAdapter(List<Movie> movies, Activity activity, Images images, ItemClickListener itemClickListener, List<Genre> genreList) {
        this.movies = movies;
        this.activity = activity;
        this.images = images;
        this.itemClickListener = itemClickListener;
        this.genres = genreList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        stringFormatter = new StringFormatter(activity);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = movies.get(position);

        String fullImageUrl = getFullImageUrl(movie);
        if (!fullImageUrl.isEmpty()) {
            Glide.with(activity)
                    .load(fullImageUrl)
                    .centerCrop()
                    .crossFade()
                    .into(holder.imageView);
        }


        holder.releaseTextView.setText(stringFormatter.getReleaseDate(movie));
        holder.titleTextView.setText(movie.title);
        holder.genreTextView.setText(getMainGenres(movie));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(movie.id, movie.title);
            }
        });
    }

    private String getMainGenres(Movie movie) {
        if (movie.genresId.isEmpty()) {
            return activity.getResources().getString(R.string.genres_error);
        }
        List<String> genresString = new ArrayList<>();
        for (Genre genre : genres) {
            for (Integer id : movie.genresId) {
                if (genre.id.equals(id)) {
                    genresString.add(genre.name);
                }
            }
        }
        return StringUtils.join(genresString, ", ");
    }

    @NonNull
    private String getFullImageUrl(Movie movie) {
        String imagePath;

        if (movie.posterPath != null && !movie.posterPath.isEmpty()) {
            imagePath = movie.posterPath;
        } else {
            imagePath = movie.backdropPath;
        }
        if (images != null && images.baseUrl != null && !images.baseUrl.isEmpty()) {
            if (images.posterSizes != null) {
                if (images.posterSizes.size() > 4) {
                    return images.baseUrl + images.posterSizes.get(4) + imagePath;
                } else {
                    return images.baseUrl + "w500" + imagePath;
                }
            }
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void clear() {
        movies.clear();
    }

    public void addAll(List<Movie> movies) {
        this.movies.addAll(movies);
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.releaseTextView)
        TextView releaseTextView;
        @BindView(R.id.titleTextView)
        TextView titleTextView;
        @BindView(R.id.genreTextView)
        TextView genreTextView;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

    }

    interface ItemClickListener {

        void onItemClick(int movieId, String title);

    }

}
