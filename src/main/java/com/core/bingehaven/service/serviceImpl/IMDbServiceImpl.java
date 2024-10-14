package com.core.bingehaven.service.serviceImpl;

import com.core.bingehaven.dtos.media.*;
import com.core.bingehaven.service.IMDbService;
import com.core.bingehaven.utils.BnhUtils;
import com.core.bingehaven.utils.LoggingUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
            MovieApiResponse movieApiResponse = objectMapper.readValue(responseBody, MovieApiResponse.class);
            List<MovieDto> movieDto = MovieMapper.mapToMovieDto(movieApiResponse);

            return movieDto;
        } catch (Exception ex) {
            LoggingUtils.ExceptionInfo(ex);
            return null;
        }
    }
}
