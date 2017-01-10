package com.android.nanodegree.popularmovies.business;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcel;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.nanodegree.popularmovies.R;
import com.android.nanodegree.popularmovies.contract.AsyncTaskDelegate;
import com.android.nanodegree.popularmovies.model.Movie;
import com.android.nanodegree.popularmovies.repository.MovieDBRepository;
import com.android.nanodegree.popularmovies.ui.adapter.MovieAdapter;
import com.android.nanodegree.popularmovies.util.Constants;
import com.android.nanodegree.popularmovies.util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by rhatori on 20/12/2016.
 */

public class MovieBusiness {
    private Context context;
    private AsyncTaskDelegate delegate;


    public MovieBusiness(AsyncTaskDelegate delegate) {
        this.delegate = delegate;

        if (delegate != null) {
            this.context = (Context) delegate;
        }
    }

    AsyncMovieDBPosterList asyncMovieDBPosterList = new AsyncMovieDBPosterList();
    AsyncMovieDBMovieDetail asyncMovieDBMovieDetail = new AsyncMovieDBMovieDetail();

    public void getMovieListBySettings() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String defaultSort = context.getResources().getString(R.string.pref_the_movie_db_sort_by_popularity_value);
        String sortValue = preferences.getString(context.getString(R.string.pref_the_movie_db_sort_key), defaultSort);
        asyncMovieDBPosterList.execute(sortValue);
    }

    public void getMovieDetailByMovieID(String movieID) {
        this.asyncMovieDBMovieDetail.execute(movieID);
    }

    public class AsyncMovieDBPosterList extends AsyncTask<String, Void, ArrayList<Movie>> {
        @Override
        protected ArrayList<Movie> doInBackground(String... sortValues) {
            String sortValue = sortValues[0];
            MovieDBRepository repository = new MovieDBRepository();
            InputStream inputStream;

            try {
                inputStream = repository.getMovieListBySettings(sortValue);
                if (inputStream == null) {
                    return null;
                }
            } catch (MalformedURLException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, String.format(context.getString(R.string.format_error_base_message), "Malformed URL"), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            } catch (IOException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, String.format(context.getString(R.string.format_error_base_message), "Network connectivity"), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }

            ArrayList<Movie> movies;

            try {
                JSONObject json = JsonUtil.getJSONObject(inputStream);
                movies = new ArrayList<>();
                Movie movie;

                JSONArray jsonArray = json.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    String movieId = jsonArray.getJSONObject(i).optString("id");
                    String posterPath = jsonArray.getJSONObject(i).optString("poster_path").replace("/", "");
                    String movieName = jsonArray.getJSONObject(i).optString("title");
                    Uri uri = Uri.parse(Constants.BASE_POSTER_PATH_URL)
                            .buildUpon()
                            .appendPath(Constants.MOVIE_POSTER_SIZE_185)
                            .appendPath(posterPath)
                            .build();

                    movie = new Movie();
                    movie.setMovieID(movieId);
                    movie.setPosterImageURL(uri.toString());
                    movie.setMovieName(movieName);
                    movies.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, String.format(context.getString(R.string.format_error_base_message), "JSON"), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, String.format(context.getString(R.string.format_error_base_message), "unhandled"), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
            return movies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

            if (delegate != null) {
                delegate.processFinish(movies);
            }
        }
    }

    public class AsyncMovieDBMovieDetail extends AsyncTask<String, Void, ArrayList<Movie>> {
        @Override
        protected ArrayList<Movie> doInBackground(String... movieIDs) {
            String movieID = movieIDs[0];
            MovieDBRepository repository = new MovieDBRepository();
            InputStream inputStream;

            try {
                inputStream = repository.getMovieDetailByMovieID(movieID);

            } catch (MalformedURLException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, String.format(context.getString(R.string.format_error_base_message), "Malformed URL"), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            } catch (IOException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, String.format(context.getString(R.string.format_error_base_message), "Network connectivity"), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }

            ArrayList<Movie> movies;

            try {
                JSONObject json = JsonUtil.getJSONObject(inputStream);
                movies = new ArrayList<>();
                Movie movie;

                String movieId = json.optString("id");
                String posterPath = json.optString("poster_path").replace("/", "");
                String movieName = json.optString("title");
                String releaseDate = json.optString("release_date");
                String runtime = json.optString("runtime");
                String voteAverage = json.optString("vote_average");
                String overview = json.optString("overview");

                Uri uri = Uri.parse(Constants.BASE_POSTER_PATH_URL)
                        .buildUpon()
                        .appendPath(Constants.MOVIE_POSTER_SIZE_185)
                        .appendPath(posterPath)
                        .build();


                movie = new Movie();
                movie.setMovieID(movieId);
                movie.setPosterImageURL(uri.toString());
                movie.setTitle(movieName);
                movie.setReleaseDate(releaseDate);
                movie.setRuntime(runtime);
                movie.setVoteAverage(voteAverage);
                movie.setOverview(overview);

                movies.add(movie);
            } catch (JSONException e) {
                e.printStackTrace();
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, String.format(context.getString(R.string.format_error_base_message), "JSON"), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, String.format(context.getString(R.string.format_error_base_message), "unhandled"), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
            return movies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

            if (delegate != null) {
                delegate.processFinish(movies);
            }
        }
    }

}
