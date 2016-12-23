package com.android.nanodegree.popularmovies.model;

/**
 * Created by rhatori on 22/12/2016.
 */

public class MoviePoster {
    private String movieId;
    private String movieName;
    private String posterImageURL;

    public String getMovieId()
    {
        return movieId;
    }

    public void setMovieId(String movieId)
    {
        this.movieId = movieId;
    }

    public String getMovieName()
    {
        return movieName;
    }

    public void setMovieName(String movieName)
    {
        this.movieName = movieName;
    }

    public String getPosterImageURL()
    {
        return posterImageURL;
    }

    public void setPosterImageURL(String posterImageURL)
    {
        this.posterImageURL = posterImageURL;
    }
}
