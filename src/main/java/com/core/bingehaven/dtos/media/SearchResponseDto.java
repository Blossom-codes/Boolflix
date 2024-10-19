package com.core.bingehaven.dtos.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponseDto {
    private DataWrapper data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)  // Ensure unknown properties are ignored
    public static class DataWrapper {
        private SearchConnection advancedTitleSearch;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)  // Ensure unknown properties are ignored
    public static class SearchConnection {
        private List<Edges> edges;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Edges {
        private Title node;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Title {
        private SearchData title;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SearchData {
        private String id;
        private PopularMoviesResponse.ReleaseDate releaseDate;
    }
}
