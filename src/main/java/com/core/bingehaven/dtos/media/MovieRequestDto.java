package com.core.bingehaven.dtos.media;

import com.core.bingehaven.enums.BnhGenres;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDto {
    private String title;
    private String keywords;
    private String releaseDate;
    private int year;
    private String downloadUrl;
    private String titleType;        // Type of title (e.g., movie, series)
    private int runningTimeInMinutes; // Running time in minutes
    private ImageDto image;          // Nested Image DTO for poster image
    private String plot;
    private Double ratings;
    private String certificateRating;
    private int ratingCount;
    private boolean isMovie;
    private boolean isSeries;
    private List<String> genres;
    private String uploadedBy;
    private String modifiedBy;

}
