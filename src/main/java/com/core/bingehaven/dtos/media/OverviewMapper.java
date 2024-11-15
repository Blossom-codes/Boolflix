package com.core.bingehaven.dtos.media;

public class OverviewMapper {


    public static MovieDto mapToMovieDto(OverviewResponse response) {
        if (response == null || response.getData() == null) {
            return null;
        }

        OverviewResponse.Title movieData = response.getData().getTitle();
        MovieDto movieDto = new MovieDto();

        movieDto.setId(movieData.getId() != null ? movieData.getId() : "N/A"); // IMDb ID, default to "N/A"
        movieDto.setTitle(movieData.getTitleText() != null && movieData.getTitleText().getText() != null
                ? movieData.getTitleText().getText() : "Unknown Title"); // Movie title, default to "Unknown Title"
        movieDto.setTitleType(movieData.getTitleType() != null && movieData.getTitleType().getText() != null
                ? movieData.getTitleType().getText() : "Unknown Type"); // Title type, default to "Unknown Type"

// Check if running time is present, else default to 0
        movieDto.setRunningTimeInMinutes(movieData.getRuntime() != null
                ? (movieData.getRuntime().getSeconds() / 60) : 0); // Running time, default to 0 minutes

// Set the release year, with a default of 0 if null
        movieDto.setYear(movieData.getReleaseYear() != null
                ? movieData.getReleaseYear().getYear() : 0); // Release year, default to 0

        String releaseDate = (movieData.getReleaseDate().getYear()
                + "-" + movieData.getReleaseDate().getMonth()
                + "-" + movieData.getReleaseDate().getDay());
        movieDto.setReleaseDate(releaseDate); // Release Date

// Map primary image, or set to null if no image is provided
        movieDto.setImage(movieData.getPrimaryImage() != null
                ? mapToImageDto(movieData.getPrimaryImage()) : null); // Image, default to null

// Plot text, default to "No plot available"
        movieDto.setPlot(movieData.getPlot() != null && movieData.getPlot().getPlotText() != null
                ? movieData.getPlot().getPlotText().getPlainText() : "No plot available");

// Ratings summary, default to 0.0 if null
        movieDto.setRatings(movieData.getRatingsSummary() != null
                ? movieData.getRatingsSummary().getAggregateRating() : 0.0); // Ratings, default to 0.0
        movieDto.setRatingCount(movieData.getRatingsSummary().getVoteCount() != 0
                ? movieData.getRatingsSummary().getVoteCount() : 0);
// Certificate rating, default to "Unrated" if null
        movieDto.setCertificateRating(movieData.getCertificate() != null
                ? movieData.getCertificate().getRating() : "Unrated"); // Certificate rating, default to "Unrated"

// Determine if it's a movie or series, with defaults
        boolean isSeries = movieData.getTitleType() != null && movieData.getTitleType().isSeries();
        movieDto.setMovie(!isSeries); // Movie, default to true if not a series
        movieDto.setSeries(isSeries); // Series, default to false if not a series
        // Check if it is a series
//            movieDto.setGenres(mapGenres(movieData)); // List of genres

        return movieDto;
    }

    private static ImageDto mapToImageDto(OverviewResponse.PrimaryImage primaryImage) {
        if (primaryImage == null) {
            return null;
        }

        return ImageDto.builder()
                .url(primaryImage.getUrl()) // Poster image URL
                .height(primaryImage.getHeight()) // Image height
                .width(primaryImage.getWidth()) // Image width
                .build();
    }

//    private static List<String> mapGenres(OverviewResponse.Ge movieData) {
//        return movieData.getTitleGenres().getGenres().stream()
//                .map(genre -> genre.getGenre().getText()) // Extract genre names
//                .collect(Collectors.toList());
//    }

}
