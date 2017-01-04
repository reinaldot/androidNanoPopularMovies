package com.android.nanodegree.popularmovies.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nanodegree.popularmovies.R;
import com.android.nanodegree.popularmovies.model.MoviePoster;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rhatori on 21/12/2016.
 */

public class MoviePosterAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MoviePoster> moviePosters;

    public MoviePosterAdapter(Context context, ArrayList<MoviePoster> moviePosters) {
        this.context = context;
        this.moviePosters = moviePosters;
    }

    @Override
    public int getCount() {
        return moviePosters != null ? moviePosters.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return this.moviePosters.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        final ImageView imageView;
        final View rootView;

        if (view == null) {
            rootView = LayoutInflater.from(context).inflate(R.layout.grid_item, viewGroup,false);
            imageView = (ImageView)rootView.findViewById(R.id.imageview_poster);
        }
        else
        {
            rootView = null;
            imageView = (ImageView)view;
        }

        final MoviePoster poster = moviePosters.get(position);

        Picasso.with(context).load(poster.getPosterImageURL()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                        imageView.setVisibility(View.GONE);
                        TextView textViewErrorRight = (TextView)rootView.findViewById(R.id.textview_poster_error);
                        textViewErrorRight.setText(poster.getMovieName());
                        textViewErrorRight.setVisibility(View.VISIBLE);
            }
        });

        return imageView;
    }
}