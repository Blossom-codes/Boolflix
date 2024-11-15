package com.core.bingehaven.service;


import com.core.bingehaven.dtos.media.*;
import com.core.bingehaven.enums.BnhGenres;

public interface IMDbService {

    MovieDto fetchMovieDetails(String movieId);
    MovieDto fetchMovieOverview(String movieId);
    MediaDto fetchPopular(FilterDto filterDto);
    KeywordDto fetchKeywords(String movieId);
    Object advanceSearch(FilterDto filterDto);
    Object fetchPopularByGenre(FilterDto filterDto, BnhGenres genres);
}
