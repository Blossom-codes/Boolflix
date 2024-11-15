package com.core.bingehaven.dtos.media;

import lombok.Data;

import java.util.List;

@Data
public class PopularMoviesResponse {
        private DataWrapper data;

        @Data
        public static class DataWrapper {
            private MovieConnection movies;
            private TvConnection tv;
        }

        @Data
        public static class MovieConnection {
            private List<Edge> edges;
        }
        @Data
        public static class TvConnection {
            private List<Edge> edges;
        }

        @Data
        public static class Edge {
            private MovieData node;
        }

        @Data
        public static class MovieData {
            private String id; // IMDb ID
            private TitleText titleText;
            private TitleType titleType;
            private YearRange releaseYear;
            private ReleaseDate releaseDate;
            private Image primaryImage;
            private Plot plot;
            private TitleGenres titleGenres;
            private RatingsSummary ratingsSummary;
            private Certificate certificate;
        }

        @Data
        public static class TitleText {
            private String text;
            private boolean isOriginalTitle;
        }

        @Data
        public static class TitleType {
            private String text;
            private boolean isSeries;
            private boolean isEpisode;
            private boolean canHaveEpisodes;
        }

        @Data
        public static class YearRange {
            private int year;
            private Integer endYear;
        }

        @Data
        public static class ReleaseDate {
            private int day;
            private int month;
            private int year;
        }

        @Data
        public static class Image {
            private String id;
            private String url;
            private int height;
            private int width;
        }

        @Data
        public static class Plot {
            private PlotText plotText;
        }

        @Data
        public static class PlotText {
            private String plainText;
        }

        @Data
        public static class TitleGenres {
            private List<GenreWrapper> genres;
        }

        @Data
        public static class GenreWrapper {
            private Genre genre;
        }

        @Data
        public static class Genre {
            private String text;
        }

        @Data
        public static class RatingsSummary {
            private double aggregateRating;
        }

    @Data
    public static class Certificate {
        private String rating;
    }
    }


