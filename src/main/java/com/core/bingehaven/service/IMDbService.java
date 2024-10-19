package com.core.bingehaven.service;


import com.core.bingehaven.dtos.media.FilterDto;
import com.core.bingehaven.dtos.media.MovieDto;
import com.core.bingehaven.enums.BnhGenres;

public interface IMDbService {

    MovieDto fetchMovieDetails(String movieId);
    MovieDto fetchMovieOverview(String movieId);
    Object fetchPopular(FilterDto filterDto);
    Object advanceSearch(FilterDto filterDto);
    Object fetchPopularByGenre(FilterDto filterDto, BnhGenres genres);
}
