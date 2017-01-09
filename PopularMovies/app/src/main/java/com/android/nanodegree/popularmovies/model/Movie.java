package com.android.nanodegree.popularmovies.model;

/**
 * Created by rhatori on 09/01/2017.
 */

public class Movie {
    private String movieID;
    private String movieName;
    private String title;
    private String posterImageURL;
    private String releaseDate;
    private String runtime;
    private String voteAverage;
    private String overview;

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getMovieID() {
        return movieID;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}