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

    Context context;

    public MovieDBRepository(Context context)
    {
        this.context = context;
    }


    private final String BASE_URL_THE_MOVIE_DB_API = "https://api.themoviedb.org/3/discover/movie?";

    public InputStream getMovieListBySettings() {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        final String API_KEY = "api_key";
        final String LANGUAGE = "language";
        final String SORT_BY = "sort_by";

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String defaultSort = context.getResources().getString(R.string.pref_the_movie_db_sort_by_popularity_value);
        String sortValue = preferences.getString(context.getString(R.string.pref_the_movie_db_sort_key), defaultSort);

        String defaultOrder = context.getResources().getString(R.string.pref_the_movie_db_order_by_descending_value);
        String orderValue = preferences.getString(context.getString(R.string.pref_the_movie_db_order_key), defaultOrder);

        String sortByValue = String.format(context.getString(R.string.pref_the_movie_db_sort_order_format_string), sortValue, orderValue);

        Uri uri = Uri.parse(BASE_URL_THE_MOVIE_DB_API)
                .buildUpon()
                .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                .appendQueryParameter(LANGUAGE, "EN_US")
                .appendQueryParameter(SORT_BY, sortByValue)
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
