package com.android.nanodegree.popularmovies.model;

/**
 * Created by rhatori on 22/12/2016.
 */

public class MoviePoster {
    private String movieID;
    private String movieName;
    private String posterImageURL;

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getPosterImageURL() {
        return posterImageURL;
    }

    public void setPosterImageURL(String posterImageURL) {
        this.posterImageURL = posterImageURL;
    }
}
