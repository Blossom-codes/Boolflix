package com.core.bingehaven.dtos.media;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private String id;               // IMDb ID
    private String title;            // Movie title
    private String titleType;        // Type of title (e.g., movie, series)
    private int runningTimeInMinutes; // Running time in minutes
    private int year;                // Release year
    private ImageDto image;          // Nested Image DTO for poster image
    private String plot;
    private Double ratings;
    private boolean isMovie;
    private boolean isSeries;
    private List<String> genres;
    // Getters and setters
}
