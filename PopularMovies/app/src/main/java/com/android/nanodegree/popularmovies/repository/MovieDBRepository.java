package com.android.nanodegree.popularmovies.repository;

import android.net.Uri;

import com.android.nanodegree.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rhatori on 20/12/2016.
 */

public class MovieDBRepository {

    private final String BASE_URL_THE_MOVIE_DB_API = "https://api.themoviedb.org/3/movie";

    public InputStream getMovieListBySettings(String sortValue) throws IOException {
        HttpURLConnection urlConnection;
        InputStream inputStream;

        final String API_KEY = "api_key";

        Uri uri = Uri.parse(BASE_URL_THE_MOVIE_DB_API)
                .buildUpon()
                .appendPath(sortValue)
                .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        URL url = new URL(uri.toString());
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        inputStream = urlConnection.getInputStream();

        return inputStream;
    }

    public InputStream getMovieDetailByMovieID(String movieID) throws IOException {
        HttpURLConnection urlConnection;
        InputStream inputStream = null;

        final String API_KEY = "api_key";

        Uri uri = Uri.parse(BASE_URL_THE_MOVIE_DB_API)
                .buildUpon()
                .appendPath(movieID)
                .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        URL url = new URL(uri.toString());
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        inputStream = urlConnection.getInputStream();

        return inputStream;
    }
}
