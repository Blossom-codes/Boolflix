package com.core.bingehaven.service;


import com.core.bingehaven.dtos.media.FilterDto;
import com.core.bingehaven.dtos.media.MovieDto;

public interface IMDbService {

    MovieDto fetchMovieDetails(String movieId);
    Object fetchPopular(FilterDto filterDto);
}
