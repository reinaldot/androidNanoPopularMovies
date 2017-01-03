package com.android.nanodegree.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.nanodegree.popularmovies.business.MovieBusiness;
import com.android.nanodegree.popularmovies.model.MoviePoster;
import com.android.nanodegree.popularmovies.ui.adapter.MoviePosterAdapter;


public class MovieFragment extends Fragment {
    private MovieBusiness movieBusiness;
    public static MoviePosterAdapter moviePosterAdapter = null;
    public static GridView movieGridView = null;
    public static final String MOVIE_ID_KEY = "1";
    private final int SETTING_CHANGED_RESULT = 1;

    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        movieGridView = (GridView) view.findViewById(R.id.gridview_movies);
        movieGridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        GridView gridView = (GridView)adapterView;
                        MoviePoster poster = (MoviePoster)gridView.getAdapter().getItem(i);
                        String movieID = poster.getMovieID();
                        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                        intent.putExtra(MOVIE_ID_KEY, movieID);
                        startActivity(intent);
                    }
                }
        );
        movieBusiness = new MovieBusiness(getContext(), moviePosterAdapter);
        movieBusiness.getMovieListBySettings();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.moviefragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Activity rootActivity = getActivity();
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                rootActivity.startActivityForResult(intent, SETTING_CHANGED_RESULT);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
