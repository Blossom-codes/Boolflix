package com.core.bingehaven.dtos.media;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {

    public static List<MovieDto> mapToMovieDto(MovieApiResponse response) {
        if (response == null || response.getData() == null) {
            return null;
        }
        List<MovieDto> list = new ArrayList<>();
        int size = response.getData().getMovies().getEdges().size();
        for (int i = 0; i < size; i++) {
            // Assuming we are mapping from the first "movie" edge in the response
            MovieApiResponse.MovieData movieData = response.getData().getMovies().getEdges().get(i).getNode();
            MovieDto movieDto = new MovieDto();
            movieDto.setId(movieData.getId());// IMDb ID
            movieDto.setTitle(movieData.getTitleText().getText());// Movie title
            movieDto.setTitleType(movieData.getTitleType().getText()); // Type of title (e.g., movie, series)
            movieDto.setRunningTimeInMinutes(0); // No running time available in your response example, set to 0 or map accordingly
            movieDto.setYear(movieData.getReleaseYear().getYear());// Release year
            movieDto.setImage(mapToImageDto(movieData.getPrimaryImage())); // Map ImageDto for the poster image
            movieDto.setPlot(movieData.getPlot().getPlotText().getPlainText()); // Plot
            movieDto.setRatings(movieData.getRatingsSummary().getAggregateRating());// Ratings
            movieDto.setMovie(!movieData.getTitleType().isSeries()); // Check if it is a movie
            movieDto.setSeries(movieData.getTitleType().isSeries()); // Check if it is a series
            movieDto.setGenres(mapGenres(movieData)); // List of genres

            list.add(movieDto);
        }
        return list;
    }

    private static ImageDto mapToImageDto(MovieApiResponse.Image image) {
        if (image == null) {
            return null;
        }

        return ImageDto.builder()
                .url(image.getUrl()) // Poster image URL
                .height(image.getHeight()) // Image height
                .width(image.getWidth()) // Image width
                .build();
    }

    private static List<String> mapGenres(MovieApiResponse.MovieData movieData) {
        return movieData.getTitleGenres().getGenres().stream()
                .map(genre -> genre.getGenre().getText()) // Extract genre names
                .collect(Collectors.toList());
    }
}
