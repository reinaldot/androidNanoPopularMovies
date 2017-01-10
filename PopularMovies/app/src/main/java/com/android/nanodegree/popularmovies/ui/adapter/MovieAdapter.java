package com.android.nanodegree.popularmovies.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nanodegree.popularmovies.R;
import com.android.nanodegree.popularmovies.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rhatori on 21/12/2016.
 */

public class MovieAdapter extends BaseAdapter {
    static class ViewHolderItem {
        ImageView imageView;
        TextView textView;
    }

    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies != null ? movies.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return this.movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHolderItem viewHolder;

        if (view == null) {
            viewHolder = new ViewHolderItem();
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, viewGroup, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageview_poster);
            viewHolder.textView = (TextView) view.findViewById(R.id.textview_poster_error);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }

        final Movie movie = movies.get(position);

        Picasso.with(context).load(movie.getPosterImageURL()).into(viewHolder.imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                viewHolder.imageView.setVisibility(View.GONE);
                TextView textViewError = viewHolder.textView;
                textViewError.setText(movie.getMovieName());
                textViewError.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}