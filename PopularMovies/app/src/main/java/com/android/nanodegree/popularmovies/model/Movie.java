package com.android.nanodegree.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rhatori on 09/01/2017.
 */

public class Movie implements Parcelable {
    private String movieID;
    private String movieName;
    private String title;
    private String posterImageURL;
    private String releaseDate;
    private String runtime;
    private String voteAverage;
    private String overview;

    public static final String MOVIE_ID_KEY = "MOVIE_ID_KEY";

    protected Movie(Parcel in) {
        movieID = in.readString();
        movieName = in.readString();
        title = in.readString();
        posterImageURL = in.readString();
        releaseDate = in.readString();
        runtime = in.readString();
        voteAverage = in.readString();
        overview = in.readString();
    }

    public Movie(){}

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(movieID);
        out.writeString(movieName);
        out.writeString(title);
        out.writeString(posterImageURL);
        out.writeString(releaseDate);
        out.writeString(runtime);
        out.writeString(voteAverage);
        out.writeString(overview);
    }
}