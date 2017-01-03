package com.android.nanodegree.popularmovies.business;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nanodegree.popularmovies.R;
import com.android.nanodegree.popularmovies.model.MovieDetail;
import com.android.nanodegree.popularmovies.model.MoviePoster;
import com.android.nanodegree.popularmovies.repository.MovieDBRepository;
import com.android.nanodegree.popularmovies.ui.adapter.MoviePosterAdapter;
import com.android.nanodegree.popularmovies.util.JsonUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by rhatori on 20/12/2016.
 */

public class MovieBusiness {

    private MoviePosterAdapter moviePosterAdapter = null;

    public MovieBusiness(Context context, MoviePosterAdapter moviePosterAdapter) {
        this.context = context;
        this.moviePosterAdapter = moviePosterAdapter;
    }

    Context context;

    AsyncMovieDBPosterList asyncMovieDBPosterList = new AsyncMovieDBPosterList(moviePosterAdapter);
    AsyncMovieDBMovieDetail asyncMovieDBMovieDetail = new AsyncMovieDBMovieDetail();

    final String BASE_POSTER_PATH_URL = "https://image.tmdb.org/t/p/";
    final String MOVIE_POSTER_SIZE_185 = "w185";

    public void getMovieListBySettings() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String defaultSort = context.getResources().getString(R.string.pref_the_movie_db_sort_by_popularity_value);
        String sortValue = preferences.getString(context.getString(R.string.pref_the_movie_db_sort_key), defaultSort);
        asyncMovieDBPosterList.execute(sortValue);
    }

    public void getMovieDetailByMovieID(String movieID) {
        this.asyncMovieDBMovieDetail.execute(movieID);
    }

    public class AsyncMovieDBPosterList extends AsyncTask<String, Void, ArrayList<MoviePoster>> {

        private MoviePosterAdapter moviePosterAdapter;

        public AsyncMovieDBPosterList(MoviePosterAdapter moviePosterAdapter) {
            this.moviePosterAdapter = moviePosterAdapter;
        }

        @Override
        protected ArrayList<MoviePoster> doInBackground(String... sortValues) {
            String sortValue = sortValues[0];
            MovieDBRepository repository = new MovieDBRepository();
            InputStream inputStream = repository.getMovieListBySettings(sortValue);
            JSONObject json = JsonUtil.getJSONObject(inputStream);
            ArrayList<MoviePoster> moviePosters = new ArrayList<>();
            MoviePoster moviePoster;
            try {
                JSONArray jsonArray = json.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    String movieId = jsonArray.getJSONObject(i).getString("id");
                    String posterPath = jsonArray.getJSONObject(i).getString("poster_path").replace("/", "");
                    String movieName = jsonArray.getJSONObject(i).getString("title");
                    Uri uri = Uri.parse(BASE_POSTER_PATH_URL)
                            .buildUpon()
                            .appendPath(MOVIE_POSTER_SIZE_185)
                            .appendPath(posterPath)
                            .build();

                    moviePoster = new MoviePoster();
                    moviePoster.setMovieID(movieId);
                    moviePoster.setPosterImageURL(uri.toString());
                    moviePoster.setMovieName(movieName);
                    moviePosters.add(moviePoster);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return moviePosters;
        }

        @Override
        protected void onPostExecute(ArrayList<MoviePoster> moviePosters) {
            moviePosterAdapter = new MoviePosterAdapter(context, moviePosters);
            GridView gridView = (GridView)((Activity)context).findViewById(R.id.gridview_movies);
            gridView.setAdapter(moviePosterAdapter);
            moviePosterAdapter.notifyDataSetChanged();
        }
    }

    public class AsyncMovieDBMovieDetail extends AsyncTask<String, Void, ArrayList<MovieDetail>> {
        @Override
        protected ArrayList<MovieDetail> doInBackground(String... movieIDs) {
            String movieID = movieIDs[0];
            MovieDBRepository repository = new MovieDBRepository();
            InputStream inputStream = repository.getMovieDetailByMovieID(movieID);
            JSONObject json = JsonUtil.getJSONObject(inputStream);
            ArrayList<MovieDetail> movieDetails = new ArrayList<>();
            MovieDetail movieDetail;
            try {
                    String movieId = json.getString("id");
                    String posterPath = json.getString("poster_path").replace("/", "");
                    String movieName = json.getString("title");
                    String releaseDate = json.getString("release_date");
                    String runtime = json.getString("runtime");
                    String voteAverage = json.getString("vote_average");
                    String overview = json.getString("overview");


                    Uri uri = Uri.parse(BASE_POSTER_PATH_URL)
                            .buildUpon()
                            .appendPath(MOVIE_POSTER_SIZE_185)
                            .appendPath(posterPath)
                            .build();

                    movieDetail = new MovieDetail();
                    movieDetail.setMovieID(movieId);
                    movieDetail.setPosterImageURL(uri.toString());
                    movieDetail.setTitle(movieName);
                    movieDetail.setReleaseDate(releaseDate);
                    movieDetail.setRuntime(runtime);
                    movieDetail.setVoteAverage(voteAverage);
                    movieDetail.setOverview(overview);

                    movieDetails.add(movieDetail);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return movieDetails;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieDetail> movieDetails) {
            MovieDetail movieDetail = movieDetails.get(0);
            Activity activity = ((Activity)context);

            activity.setTitle(movieDetail.getTitle());

            ImageView imageView = (ImageView)activity.findViewById(R.id.imageview_poster);
            Picasso.with(context).load(movieDetail.getPosterImageURL()).into(imageView);

            TextView movieYear = (TextView)activity.findViewById(R.id.textview_movie_year);
            movieYear.setText(movieDetail.getReleaseDate().substring(0,4));

            TextView runtime = (TextView)activity.findViewById(R.id.textview_movie_runtime);
            runtime.setText(String.format(activity.getString(R.string.format_runtime), movieDetail.getRuntime()));

            TextView voteAverage = (TextView)activity.findViewById(R.id.textview_movie_vote_average);
            voteAverage.setText(String.format(activity.getString(R.string.format_vote_average), movieDetail.getVoteAverage()));

            TextView overview = (TextView)activity.findViewById(R.id.textview_movie_overview);
            overview.setText(movieDetail.getOverview());
        }
    }

}
