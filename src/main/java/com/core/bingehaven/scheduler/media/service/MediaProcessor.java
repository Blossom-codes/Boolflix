package com.core.bingehaven.scheduler.media.service;

import com.core.bingehaven.dtos.media.*;
import com.core.bingehaven.entities.BnhMovies;
import com.core.bingehaven.repositories.BnhMoviesRepository;
import com.core.bingehaven.service.BnhMovieService;
import com.core.bingehaven.service.IMDbService;
import com.core.bingehaven.utils.LoggingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaProcessor {
    private final IMDbService imDbService;
    private final BnhMovieService bnhMovieService;
    private final BnhMoviesRepository bnhMoviesRepository;

    public void fetchPopularMoviesFromImdb() {
        try {

            MediaDto data = imDbService.fetchPopular(
                    FilterDto.builder()
                            .numberPerPage("10")
                            .country("US")
                            .build());

            List<MovieDto> movieDto = data.getMovies();
            List<MovieDto> tvDto = data.getTv();

            LoggingUtils.DebugInfo("Fetch Successful, " + movieDto.size() + " movies, " + tvDto.size() + " tv shows");
            LoggingUtils.DebugInfo("Saving movies and tv shows now >>>>");
            int i = 1;
            int j = 1;
            for (MovieDto movie : movieDto) {
                BnhMovies ifExists = bnhMoviesRepository.findByMovieId(movie.getId());
                if (ifExists != null) {
                    continue;
                }
                MovieDto movieOverview = imDbService.fetchMovieOverview(movie.getId());
                Integer runtime = null;
                Integer ratingCount = null;
                if (movieOverview != null) {

                    runtime = movieOverview.getRunningTimeInMinutes();
                    ratingCount = movieOverview.getRatingCount();
                }
                StringBuilder keywords = new StringBuilder();
                List<String> keywordsList = imDbService.fetchKeywords(movie.getId()).getKeywords();
                if (keywordsList != null) {
                    for (int k = 0; k < keywordsList.size(); k++) {
                        keywords.append(keywordsList.get(k));
                        if (k < keywordsList.size() - 1) { // Check if it's not the last element
                            keywords.append(",");
                        }
                    }
                }

                StringBuilder genres = new StringBuilder();
                List<String> genresList = movie.getGenres();
                if (genresList != null) {
                    for (int l = 0; l < genresList.size(); l++) {
                        genres.append(genresList.get(l));
                        if (l < genresList.size() - 1) { // Check if it's not the last element
                            genres.append(",");
                        }
                    }
                }
                bnhMovieService.saveMovie(
                        MovieRequestDto.builder()
                                .id(movie.getId())
                                .title(movie.getTitle())
                                .titleType(movie.getTitleType())
                                .genres(String.valueOf(genres))
                                .keywords(String.valueOf(keywords))
                                .isMovie(movie.isMovie())
                                .isSeries(movie.isSeries())
                                .releaseDate(movie.getReleaseDate())
                                .ratings(movie.getRatings())
                                .year(movie.getYear())
                                .runningTimeInMinutes(runtime)
                                .plot(movie.getPlot())
                                .certificateRating(movie.getCertificateRating())
                                .ratingCount(ratingCount)
                                .uploadedBy("SYSTEM")
                                .downloadUrl("NONE")
                                .image(movie.getImage())
                                .build());

                LoggingUtils.DebugInfo("Completed " + i++ + " of " + movieDto.size() + " movies");

            }
            for (MovieDto tv : tvDto) {
                BnhMovies ifExists = bnhMoviesRepository.findByMovieId(tv.getId());
                if (ifExists != null) {
                    continue;
                }
                MovieDto tvOverview = imDbService.fetchMovieOverview(tv.getId());
                Integer runtime = null;
                Integer ratingCount = null;
                if (tvOverview != null) {

                    runtime = tvOverview.getRunningTimeInMinutes();
                    ratingCount = tvOverview.getRatingCount();
                }
                StringBuilder keywords = new StringBuilder();
                List<String> keywordsList = imDbService.fetchKeywords(tv.getId()).getKeywords();
                if (keywordsList != null) {
                    for (int k = 0; k < keywordsList.size(); k++) {
                        keywords.append(keywordsList.get(k));
                        if (k < keywordsList.size() - 1) { // Check if it's not the last element
                            keywords.append(",");
                        }
                    }
                }
                StringBuilder genres = new StringBuilder();
                List<String> genresList = tv.getGenres();
                if (genresList != null) {
                    for (int l = 0; l < genresList.size(); l++) {
                        genres.append(genresList.get(l));
                        if (l < genresList.size() - 1) { // Check if it's not the last element
                            genres.append(",");
                        }
                    }
                }
                bnhMovieService.saveTvShow(
                        MovieRequestDto.builder()
                                .id(tv.getId())
                                .title(tv.getTitle())
                                .titleType(tv.getTitleType())
                                .genres(String.valueOf(genres))
                                .keywords(String.valueOf(keywords))
                                .isMovie(tv.isMovie())
                                .isSeries(tv.isSeries())
                                .releaseDate(tv.getReleaseDate())
                                .ratings(tv.getRatings())
                                .year(tv.getYear())
                                .runningTimeInMinutes(runtime)
                                .plot(tv.getPlot())
                                .certificateRating(tv.getCertificateRating())
                                .ratingCount(ratingCount)
                                .uploadedBy("SYSTEM")
                                .downloadUrl("NONE")
                                .image(tv.getImage())
                                .build());
                LoggingUtils.DebugInfo("Completed " + j++ + " of " + tvDto.size() + " tv shows");

            }
        } catch (Exception ex) {
            LoggingUtils.DebugInfo("An error occurred while fetching movies {}>>> " + ex.getLocalizedMessage());
        }

    }
}
