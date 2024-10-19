package com.core.bingehaven.dtos.media;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {

    //    return MediaDto
    public static MediaDto mapToMovieDto(PopularMoviesResponse response) {
        if (response == null || response.getData() == null) {
            return null;
        }
        MediaDto mediaDto = new MediaDto();
        List<MovieDto> moviesList = new ArrayList<>();
        int movieListSize = response.getData().getMovies().getEdges().size();
        for (int i = 0; i < movieListSize; i++) {
            // Assuming we are mapping from the first "movie" edge in the response
            PopularMoviesResponse.MovieData movieData = response.getData().getMovies().getEdges().get(i).getNode();
            MovieDto movieDto = new MovieDto();
            movieDto.setId(movieData.getId());// IMDb ID
            movieDto.setTitle(movieData.getTitleText().getText());// Movie title
            movieDto.setTitleType(movieData.getTitleType().getText()); // Type of title (e.g., movie, series)
            movieDto.setRunningTimeInMinutes(0); // No running time available in your response example, set to 0 or map accordingly
            movieDto.setYear(movieData.getReleaseYear().getYear());// Release year
            movieDto.setImage(mapToImageDto(movieData.getPrimaryImage())); // Map ImageDto for the poster image
            movieDto.setPlot(movieData.getPlot().getPlotText().getPlainText()); // Plot
            movieDto.setRatings(movieData.getRatingsSummary().getAggregateRating());// Ratings
            boolean is = movieData.getTitleType().isSeries();
            movieDto.setMovie(!movieData.getTitleType().isSeries()); // Check if it is a movie
            movieDto.setSeries(movieData.getTitleType().isSeries()); // Check if it is a series
            movieDto.setGenres(mapGenres(movieData)); // List of genres

            moviesList.add(movieDto);
        }
        mediaDto.setMovies(moviesList);

        List<MovieDto> tvList = new ArrayList<>();
        int tvListSize = response.getData().getTv().getEdges().size();
        for (int i = 0; i < tvListSize; i++) {
            // Assuming we are mapping from the first "movie" edge in the response
            PopularMoviesResponse.MovieData tvData = response.getData().getTv().getEdges().get(i).getNode();
            MovieDto movieDto = new MovieDto();
            movieDto.setId(tvData.getId());// IMDb ID
            movieDto.setTitle(tvData.getTitleText().getText());// Movie title
            movieDto.setTitleType(tvData.getTitleType().getText()); // Type of title (e.g., movie, series)
            movieDto.setRunningTimeInMinutes(0); // No running time available in your response example, set to 0 or map accordingly
            movieDto.setYear(tvData.getReleaseYear().getYear());// Release year
            movieDto.setImage(mapToImageDto(tvData.getPrimaryImage())); // Map ImageDto for the poster image
            movieDto.setPlot(tvData.getPlot().getPlotText().getPlainText()); // Plot
            movieDto.setRatings(tvData.getRatingsSummary().getAggregateRating());// Ratings
            boolean is = tvData.getTitleType().isSeries();
            movieDto.setMovie(!tvData.getTitleType().isSeries()); // Check if it is a movie
            movieDto.setSeries(tvData.getTitleType().isSeries()); // Check if it is a series
            movieDto.setGenres(mapGenres(tvData)); // List of genres

            tvList.add(movieDto);
        }
        mediaDto.setTv(tvList);


        return mediaDto;
    }

    private static ImageDto mapToImageDto(PopularMoviesResponse.Image image) {
        if (image == null) {
            return null;
        }

        return ImageDto.builder()
                .url(image.getUrl()) // Poster image URL
                .height(image.getHeight()) // Image height
                .width(image.getWidth()) // Image width
                .build();
    }

    private static List<String> mapGenres(PopularMoviesResponse.MovieData movieData) {
        return movieData.getTitleGenres().getGenres().stream()
                .map(genre -> genre.getGenre().getText()) // Extract genre names
                .collect(Collectors.toList());
    }
}
