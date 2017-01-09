package com.android.nanodegree.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nanodegree.popularmovies.contract.AsyncTaskDelegate;
import com.android.nanodegree.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements AsyncTaskDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_movie_detail);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_movie_detail, new MovieDetailFragment())
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void processFinish(Object output) {
        if (output != null) {
            List<Movie> movies = (List<Movie>) output;

            try {
                Movie movie = movies.get(0);

                setTitle(movie.getTitle());

                ImageView imageView = (ImageView) findViewById(R.id.imageview_poster);
                Picasso.with(this).load(movie.getPosterImageURL()).into(imageView);

                TextView movieYear = (TextView) findViewById(R.id.textview_movie_year);
                movieYear.setText(movie.getReleaseDate().substring(0, 4));

                TextView runtime = (TextView) findViewById(R.id.textview_movie_runtime);
                runtime.setText(String.format(getString(R.string.format_runtime), movie.getRuntime()));

                TextView voteAverage = (TextView) findViewById(R.id.textview_movie_vote_average);
                voteAverage.setText(String.format(getString(R.string.format_vote_average), movie.getVoteAverage()));

                TextView overview = (TextView) findViewById(R.id.textview_movie_overview);
                overview.setText(movie.getOverview());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, String.format(getString(R.string.format_error_base_message), "unhandled"), Toast.LENGTH_SHORT).show();
            }
        }
    }
}