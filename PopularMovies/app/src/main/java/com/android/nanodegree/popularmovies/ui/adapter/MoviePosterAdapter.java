package com.android.nanodegree.popularmovies.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nanodegree.popularmovies.R;

/**
 * Created by rhatori on 21/12/2016.
 */

public class MoviePosterAdapter extends BaseAdapter {

    private Activity context;
    String[] urls;

//    public MoviePosterAdapter(Activity context, int resource, String[] objects) {
//        super(context, resource, objects);
//        this.context = context;
//        this.urls = objects;
//    }

    @Override
    public int getCount() {
        return 0;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageview_poster);
        imageView.setImageURI(Uri.parse(urls[i]));

        return rowView;
    }

//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = context.getLayoutInflater();
//        View rowView = inflater.inflate(R.layout.list_item, null, true);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageview_poster);
//        imageView.setImageURI(Uri.parse(urls[position]));
//
//        return rowView;
//    }
}
