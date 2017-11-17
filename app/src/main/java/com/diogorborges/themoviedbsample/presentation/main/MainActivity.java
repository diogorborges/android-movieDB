package com.diogorborges.themoviedbsample.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.View;

import com.diogorborges.themoviedbsample.App;
import com.diogorborges.themoviedbsample.R;
import com.diogorborges.themoviedbsample.data.model.Genre;
import com.diogorborges.themoviedbsample.data.model.Images;
import com.diogorborges.themoviedbsample.data.model.Movie;
import com.diogorborges.themoviedbsample.presentation.detail.DetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.diogorborges.themoviedbsample.presentation.detail.DetailActivity.MOVIE_ID;
import static com.diogorborges.themoviedbsample.presentation.detail.DetailActivity.MOVIE_TITLE;

public class MainActivity extends FragmentActivity implements
        MainContract.View,
        SwipeRefreshLayout.OnRefreshListener, EndlessScrollListener.ScrollToBottomListener, MoviesAdapter.ItemClickListener {
    private static final String TAG = "MainActivity";

    @Inject
    MainContract.Presenter presenter;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView contentView;
    @BindView(R.id.textView)
    View errorView;
    @BindView(R.id.textView2)
    View errorNotFound;
    @BindView(R.id.progressBar)
    View loadingView;
    @BindView(R.id.searchViewComponent)
    SearchView searchViewComponent;

    private MoviesAdapter moviesAdapter;
    private EndlessScrollListener endlessScrollListener;
    private Images images;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((App) getApplication()).getAppComponent().inject(this);

        presenter.onViewStarted(this);

        setupContentView();
        setupSearchView();
    }

    private void setupSearchView() {
        searchViewComponent.setQueryHint("Search Movie");

        searchViewComponent.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 3) {
                    presenter.onSearchQueried(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        presenter.onViewStarted(this);
    }

    private void setupContentView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        endlessScrollListener = new EndlessScrollListener(linearLayoutManager, this);
        contentView.setLayoutManager(linearLayoutManager);
        contentView.addOnScrollListener(endlessScrollListener);
    }

    @Override
    public void onRefresh() {
        endlessScrollListener.onRefresh();
        presenter.onPullToRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewResumed(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onViewPaused(this);
    }

    @Override
    public void onScrollToBottom() {
        presenter.onScrollToBottom();
    }

    @Override
    public void showLoading(boolean isRefresh) {
        if (isRefresh) {
            if (!swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(true);
            }
        } else {
            loadingView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
            errorNotFound.setVisibility(View.GONE);
        }
    }

    @Override
    public void showContent(List<Movie> movies, boolean isRefresh, List<Genre> genreList) {
        if (moviesAdapter == null) {
            moviesAdapter = new MoviesAdapter(movies, this, images, this, genreList);
            contentView.setAdapter(moviesAdapter);
        } else {
            if (isRefresh) {
                moviesAdapter.clear();
            }
            moviesAdapter.setGenres(genreList);
            moviesAdapter.addAll(movies);
            moviesAdapter.notifyDataSetChanged();
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);

        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        errorNotFound.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        hiddeContent();
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNotFoundError() {
        hiddeContent();
        errorNotFound.setVisibility(View.VISIBLE);
    }

    private void hiddeContent() {
        swipeRefreshLayout.setRefreshing(false);
        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
    }

    @Override
    public void onConfigurationSet(Images images) {
        this.images = images;

        if (moviesAdapter != null) {
            moviesAdapter.setImages(images);
        }
    }

    @Override
    public void showGenres(List<Genre> genres) {
        moviesAdapter.setGenres(genres);
    }

    @Override
    public void onItemClick(int movieId, String movieTitle) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra(MOVIE_ID, movieId);
        i.putExtra(MOVIE_TITLE, movieTitle);
        startActivity(i);
    }

    @OnClick(R.id.textView)
    void onClickErrorView() {
        presenter.onViewStarted(this);
    }

}
