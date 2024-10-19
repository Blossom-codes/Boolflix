package com.core.bingehaven.dtos.media;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.List;

@Data
public class OverviewResponse {

    private DataSection data;

    @Data
    public static class DataSection {
        private Title title;
    }

    @Data
    public static class Title {
        private String id;
        private TitleText titleText;
        private TitleText originalTitleText;
        private ReleaseYear releaseYear;
        private ReleaseDate releaseDate;
        private TitleType titleType;
        private PrimaryImage primaryImage;
        private Certificate certificate;
        private RatingsSummary ratingsSummary;

        @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
        private List<InterestEdge> interests;

        private Plot plot;
        private Runtime runtime;
    }

    @Data
    public static class TitleText {
        private String text;
        private boolean isOriginalTitle;
    }

    @Data
    public static class ReleaseYear {
        private int year;
        private Integer endYear; // Nullable field
    }

    @Data
    public static class ReleaseDate {
        private int year;
        private int month;
        private int day;
        private Country country;
    }

    @Data
    public static class Country {
        private String id;
    }

    @Data
    public static class TitleType {
        private String id;
        private String text;
        private List<Category> categories;
        private boolean isEpisode;
        private boolean isSeries;
    }

    @Data
    public static class Category {
        private String id;
        private String text;
        private String value;
    }

    @Data
    public static class PrimaryImage {
        private String id;
        private String url;
        private int height;
        private int width;
    }

    @Data
    public static class RatingsSummary {
        private double aggregateRating;
        private int voteCount;
    }

    @Data
    public static class InterestEdge {
        private Interest node;
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
    public static class Interest {
        private String id;
        private PrimaryImage primaryImage;
        private Description description;
        private InterestText primaryText;
        private InterestText secondaryText;
    }

    @Data
    public static class InterestText {
        private String id;
        private String text;
    }

    @Data
    public static class Description {
        private Value value;
    }

    @Data
    public static class Value {
        private String plainText;
    }

    @Data
    public static class Runtime {
        private int seconds;
    }

    @Data
    public static class Certificate {
        private String rating;
    }
}

