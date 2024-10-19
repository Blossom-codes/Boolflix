package com.core.bingehaven.service.serviceImpl;

import com.core.bingehaven.dtos.media.*;
import com.core.bingehaven.enums.BnhGenres;
import com.core.bingehaven.service.IMDbService;
import com.core.bingehaven.utils.BnhUtils;
import com.core.bingehaven.utils.LoggingUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IMDbServiceImpl implements IMDbService {


    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapi.host}")
    private String rapidApiHost;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper;

    @Override
    public MovieDto fetchMovieDetails(String movieId) {
        Request request = new Request.Builder()
                .url("https://imdb8.p.rapidapi.com/title/get-details?tconst=" + movieId)
                .get()
                .addHeader("X-RapidAPI-Key", rapidApiKey)
                .addHeader("X-RapidAPI-Host", rapidApiHost)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();  // Raw JSON response
            return objectMapper.readValue(responseBody, MovieDto.class);

        } catch (Exception ex) {
            LoggingUtils.ExceptionInfo(ex);
            return null;
        }

    }

    @Override
    public MovieDto fetchMovieOverview(String movieId) {
        Request request = new Request.Builder()
                .url("https://imdb8.p.rapidapi.com/title/v2/get-overview?tconst=" + movieId)
                .get()
                .addHeader("X-RapidAPI-Key", rapidApiKey)
                .addHeader("X-RapidAPI-Host", rapidApiHost)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();  // Raw JSON response
            OverviewResponse overviewResponse = objectMapper.readValue(responseBody, OverviewResponse.class);
            MovieDto movieDto = OverviewMapper.mapToMovieDto(overviewResponse);

            return movieDto;
        } catch (Exception ex) {
            ex.printStackTrace();
            LoggingUtils.ExceptionInfo(ex);
            return null;
        }

    }

    @Override
    public Object fetchPopular(FilterDto filterDto) {
        Request request = new Request.Builder()
                .url("https://imdb8.p.rapidapi.com/title/v2/get-popular?first=" +
                        (StringUtils.isNotEmpty(filterDto.getNumberPerPage()) ? filterDto.getNumberPerPage() : "10")
                        + "&country=" +
                        filterDto.getCountry()
                        + "&language=" +
                        filterDto.getLanguage())
                .get()
                .addHeader("X-RapidAPI-Key", rapidApiKey)
                .addHeader("X-RapidAPI-Host", rapidApiHost)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();  // Raw JSON response
            PopularMoviesResponse popularMoviesResponse = objectMapper.readValue(responseBody, PopularMoviesResponse.class);
            MediaDto media = MovieMapper.mapToMovieDto(popularMoviesResponse);

//            List<MediaDto> returnMovies = new ArrayList<>();
//            for (MediaDto movie : media) {
//
//                if (movie.getYear() >= 2020 && movie.getYear() <= 2024) {
//                    returnMovies.add(movie);
//                }
//            }
            return media;
        } catch (Exception ex) {
            LoggingUtils.ExceptionInfo(ex);
            return null;
        }
    }

    @Override
    public Object fetchPopularByGenre(FilterDto filterDto, BnhGenres genres) {
        Request request = new Request.Builder()
                .url("https://imdb8.p.rapidapi.com/title/v2/get-popular-movies-by-genre?" +
                        "genre=" + genres.toString().toLowerCase() +
                        "&limit=" + filterDto.getNumberPerPage())
                .get()
                .addHeader("X-RapidAPI-Key", rapidApiKey)
                .addHeader("X-RapidAPI-Host", rapidApiHost)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();  // Raw JSON response
            List<String> list = objectMapper.readValue(responseBody, List.class);
//            List<MovieDto> movieDto = MovieMapper.mapToMovieDto(mediaContainerDto);
            List<MovieDto> movieDto = new ArrayList<>();
//            return list;
            for (String id : list) {

                String movieId = BnhUtils.returnMovieId(id);

                movieDto.add(fetchMovieDetails(movieId));
            }
            List<MovieDto> returnMovies = new ArrayList<>();
            for (MovieDto movie : movieDto) {
                if (movie.getYear() >= 2020 && movie.getYear() <= 2024) {
                    returnMovies.add(movie);
                }
            }
            return returnMovies;

        } catch (Exception ex) {
            LoggingUtils.ExceptionInfo(ex);
            return null;
        }
    }

    @Override
    public Object advanceSearch(FilterDto filterDto) {
        try {


            OkHttpClient client = new OkHttpClient();
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert FilterDto to JSON string
            String jsonPayload = objectMapper.writeValueAsString(filterDto.getBody());

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,jsonPayload);
            Request request = new Request.Builder()
                    .url("https://imdb8.p.rapidapi.com/v2/search-advance?country=US&language=en-US")
                    .post(body)
                    .addHeader("x-rapidapi-key", "56f70971b9mshce047f5b1a4db1fp1d2d25jsn4db2259900bd")
                    .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();  // Raw JSON response
            SearchResponseDto list = objectMapper.readValue(responseBody, SearchResponseDto.class);

            return list;

        } catch (Exception ex) {
            LoggingUtils.ExceptionInfo(ex);
            return null;
        }
    }
}
