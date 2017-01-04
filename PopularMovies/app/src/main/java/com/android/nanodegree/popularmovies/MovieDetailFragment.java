package com.android.nanodegree.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.nanodegree.popularmovies.business.MovieBusiness;

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

        Intent intent = getActivity().getIntent();
        final String movieID = intent.getStringExtra(MovieFragment.MOVIE_ID_KEY);

        movieBusiness = new MovieBusiness(getActivity(), null);
        movieBusiness.getMovieDetailByMovieID(movieID);

        setRetainInstance(true);

        return view;
    }
}
