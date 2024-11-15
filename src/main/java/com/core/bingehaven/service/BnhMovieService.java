package com.core.bingehaven.service;

import com.core.bingehaven.dtos.global.ResponseDto;
import com.core.bingehaven.dtos.media.MovieRequestDto;
import org.springframework.stereotype.Service;


public interface BnhMovieService {
    ResponseDto saveMovie(MovieRequestDto movieRequestDto);
    ResponseDto saveTvShow(MovieRequestDto movieRequestDto);
    ResponseDto getMovies();
}
