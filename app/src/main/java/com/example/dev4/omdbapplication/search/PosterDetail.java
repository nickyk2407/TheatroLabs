package com.example.dev4.omdbapplication.search;

import java.util.List;

public class PosterDetail {
    private String Title;
    private String Poster;
    private String imdbRating;
    private String Actors;
    private String Genre;
    private List<Ratings> Ratings;

    public String getTitle() {
        return Title;
    }

    public List<PosterDetail.Ratings> getRatings() {
        return Ratings;
    }

    public String getPoster() {
        return Poster;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getActors() {
        return Actors;
    }

    public String getGenre() {
        return Genre;
    }

    public static class Ratings {
        private String Source;
        private String Value;

        public String getSource() {
            return Source;
        }

        public String getValue() {
            return Value;
        }
    }
}
