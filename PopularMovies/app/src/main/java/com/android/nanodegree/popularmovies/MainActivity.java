package com.android.nanodegree.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import com.android.nanodegree.popularmovies.contract.AsyncTaskDelegate;
import com.android.nanodegree.popularmovies.model.Movie;
import com.android.nanodegree.popularmovies.ui.adapter.MovieAdapter;
import com.android.nanodegree.popularmovies.util.Constants;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncTaskDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieFragment())
                    .commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.SETTING_CHANGED_RESULT:
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recreate();
                        }
                    }, 0);
            }
        }
    }

    @Override
    public void processFinish(Object output) {
        if (output != null) {
            ArrayList<Movie> movies = (ArrayList<Movie>) output;
            MovieAdapter movieAdapter = new MovieAdapter(this, movies);
            GridView gridView = (GridView) findViewById(R.id.gridview_movies);
            gridView.setAdapter(movieAdapter);
            movieAdapter.notifyDataSetChanged();
        }
    }
}

