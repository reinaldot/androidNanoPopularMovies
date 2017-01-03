package com.android.nanodegree.popularmovies.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.android.nanodegree.popularmovies.BuildConfig;
import com.android.nanodegree.popularmovies.R;
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

    public InputStream getMovieListBySettings(String sortValue) {
        HttpURLConnection urlConnection;
        InputStream inputStream = null;

        final String API_KEY = "api_key";

        Uri uri = Uri.parse(BASE_URL_THE_MOVIE_DB_API)
                .buildUpon()
                .appendPath(sortValue)
                .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        try {
            URL url = new URL(uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return inputStream;
        }
    }

    public InputStream getMovieDetailByMovieID(String movieID) {
        HttpURLConnection urlConnection;
        InputStream inputStream = null;

        final String API_KEY = "api_key";

        Uri uri = Uri.parse(BASE_URL_THE_MOVIE_DB_API)
                .buildUpon()
                .appendPath(movieID)
                .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        try {
            URL url = new URL(uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return inputStream;
        }
    }
}
