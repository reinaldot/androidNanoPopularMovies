package com.android.nanodegree.popularmovies.business;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ListView;

import com.android.nanodegree.popularmovies.model.MoviePoster;
import com.android.nanodegree.popularmovies.repository.MovieDBRepository;
import com.android.nanodegree.popularmovies.ui.adapter.MoviePosterAdapter;
import com.android.nanodegree.popularmovies.util.JsonUtil;

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

    public MovieBusiness(Context context, ListView listView, MoviePosterAdapter moviePosterAdapter) {
        this.context = context;
        this.listView = listView;
        this.moviePosterAdapter = moviePosterAdapter;
    }

    Context context;
    AsyncMovieDBRepository asyncMovieDBRepository = new AsyncMovieDBRepository(this.listView, moviePosterAdapter);
    final String BASE_POSTER_PATH_URL = "https://image.tmdb.org/t/p/";
    final String MOVIE_POSTER_SIZE_185 = "w185";
    private ListView listView;

    public void getMovieListBySettings() {
        asyncMovieDBRepository.execute(listView);
    }

    public class AsyncMovieDBRepository extends AsyncTask<ListView, Void, ArrayList<MoviePoster>> {

        private MoviePosterAdapter moviePosterAdapter;

        public AsyncMovieDBRepository(ListView listView, MoviePosterAdapter moviePosterAdapter) {
            this.listView = listView;
            this.moviePosterAdapter = moviePosterAdapter;
        }

        private ListView listView;

        @Override
        protected ArrayList<MoviePoster> doInBackground(ListView... listViews) {
            this.listView = listViews[0];
            MovieDBRepository repository = new MovieDBRepository(context);
            InputStream inputStream = repository.getMovieListBySettings();
            JSONObject json = JsonUtil.convertInputStreamToJson(inputStream);
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
                    moviePoster.setMovieId(movieId);
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
            listView.setAdapter(moviePosterAdapter);
            moviePosterAdapter.notifyDataSetChanged();
        }
    }
}
