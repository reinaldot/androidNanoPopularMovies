package com.android.nanodegree.popularmovies.business;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.android.nanodegree.popularmovies.repository.MovieDBRepository;
import com.android.nanodegree.popularmovies.ui.adapter.MoviePosterAdapter;
import com.android.nanodegree.popularmovies.util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * Created by rhatori on 20/12/2016.
 */

public class MovieBusiness {

    AsyncMovieDBRepository asyncMovieDBRepository = new AsyncMovieDBRepository();
    final String BASE_POSTER_PATH_URL = "https://image.tmdb.org/t/p/";
    final String MOVIE_POSTER_SIZE_500 = "w500";
    private MoviePosterAdapter mPosterAdapter;
    private ImageView imageView;

    public MovieBusiness(ImageView imageView)
    {
        this.imageView = imageView;
    }

    public JSONObject getMovieListBySettings() {
        asyncMovieDBRepository.execute();
        return new JSONObject();
    }

    public class AsyncMovieDBRepository extends AsyncTask<Void, Void, HashMap<String, Bitmap>> {

        @Override
        protected HashMap<String, Bitmap> doInBackground(Void... voids) {
            MovieDBRepository repository = new MovieDBRepository();
            InputStream inputStream = repository.getMovieListBySettings();
            JSONObject json = JsonUtil.convertInputStreamToJson(inputStream);
            HashMap<String, Bitmap> posterMap = null;

            try {
                JSONArray jsonArray = json.getJSONArray("results");
                posterMap = new HashMap<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    String movieId = jsonArray.getJSONObject(i).getString("id");
                    String posterPath = jsonArray.getJSONObject(i).getString("poster_path");
                    Uri uri = Uri.parse(BASE_POSTER_PATH_URL)
                            .buildUpon()
                            .appendPath(MOVIE_POSTER_SIZE_500)
                            .appendPath(posterPath)
                            .build();

                    URL url = new URL(uri.toString());
                    URLConnection urlConnection = url.openConnection();
                    InputStream imageStream = urlConnection.getInputStream();
                    Bitmap image = BitmapFactory.decodeStream(imageStream);
                    posterMap.put(movieId, image);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                return posterMap;
            }
        }

        @Override
        protected void onPostExecute(HashMap<String, Bitmap> posters) {
            imageView.setImageBitmap(posters.get(1));
        }
    }
}
