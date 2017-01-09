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
import com.android.nanodegree.popularmovies.model.Movie;
import com.android.nanodegree.popularmovies.util.Constants;
import com.android.nanodegree.popularmovies.util.NetworkUtil;


public class MovieFragment extends Fragment {
    private MovieBusiness movieBusiness;
    public static GridView movieGridView = null;

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
        final Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        movieGridView = (GridView) view.findViewById(R.id.gridview_movies);
        movieGridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        GridView gridView = (GridView) adapterView;
                        Movie movie = (Movie) gridView.getAdapter().getItem(i);
                        String movieID = movie.getMovieID();
                        Intent intent = new Intent(activity, MovieDetailActivity.class);
                        intent.putExtra(Constants.MOVIE_ID_KEY, movieID);
                        startActivity(intent);
                    }
                }
        );

        if (!NetworkUtil.isNetworkConnected(activity)) {
            NetworkUtil.showNetworkUnavailableError(activity, activity.findViewById(R.id.container));
            return view;
        }

        movieBusiness = new MovieBusiness((MainActivity) activity);
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
                rootActivity.startActivityForResult(intent, Constants.SETTING_CHANGED_RESULT);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
