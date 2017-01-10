package com.android.nanodegree.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.nanodegree.popularmovies.business.MovieBusiness;
import com.android.nanodegree.popularmovies.model.Movie;
import com.android.nanodegree.popularmovies.util.Constants;
import com.android.nanodegree.popularmovies.util.NetworkUtil;

/**
 * Created by rhatori on 03/01/2017.
 */

public class MovieDetailFragment extends Fragment {

    private MovieBusiness movieBusiness;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Activity activity = getActivity();

        Intent intent = activity.getIntent();
        final Movie movie = intent.getParcelableExtra(Movie.MOVIE_ID_KEY);


        if (!NetworkUtil.isNetworkConnected(activity)) {
            NetworkUtil.showNetworkUnavailableError(activity, activity.findViewById(R.id.container_movie_detail));
            return view;
        }

        movieBusiness = new MovieBusiness((MovieDetailActivity) activity);
        movieBusiness.getMovieDetailByMovieID(movie.getMovieID());

        setRetainInstance(true);

        return view;
    }
}
