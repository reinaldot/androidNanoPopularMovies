package com.android.nanodegree.popularmovies.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.nanodegree.popularmovies.MovieFragment;
import com.android.nanodegree.popularmovies.R;
import com.android.nanodegree.popularmovies.model.MoviePoster;
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
        return moviePosters.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View movieLayout = inflater.inflate(R.layout.list_item, null, true);
        ImageView imageView = (ImageView)movieLayout.findViewById(R.id.imageview_poster);
        Picasso.with(context).load(moviePosters.get(position).getPosterImageURL()).into(imageView);
        return movieLayout;
    }
}
