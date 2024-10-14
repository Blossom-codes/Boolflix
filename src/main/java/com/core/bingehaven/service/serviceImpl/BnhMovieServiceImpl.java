package com.core.bingehaven.service.serviceImpl;

import com.core.bingehaven.dtos.global.ResponseDto;
import com.core.bingehaven.dtos.media.MovieRequestDto;
import com.core.bingehaven.entities.BnhMovies;
import com.core.bingehaven.repositories.BnhMoviesRepository;
import com.core.bingehaven.service.BnhMovieService;
import com.core.bingehaven.utils.BnhUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BnhMovieServiceImpl implements BnhMovieService {

    private final BnhMoviesRepository bnhMoviesRepository;

    @Override
    public ResponseDto saveMovie(MovieRequestDto movieRequestDto) {

        try {
            if (movieRequestDto == null) {
                throw new RuntimeException("Enter parameters to save movie");
            }

            BnhMovies movies = new BnhMovies();

            movies.setTitle(movieRequestDto.getTitle());
            movies.setGenre(movieRequestDto.getGenre());
            movies.setKeywords(movieRequestDto.getKeywords());
            movies.setDownloadUrl(movieRequestDto.getDownloadUrl());
            movies.setUploadedBy(movieRequestDto.getUploadedBy());
            movies.setReleaseDate(movieRequestDto.getReleaseDate());

            bnhMoviesRepository.save(movies);
            return ResponseDto.builder()
                    .responseCode(BnhUtils.SUCCESS_CODE)
                    .responseMessage(BnhUtils.UPLOAD_SUCCESS_MESSAGE)
                    .build();
        } catch (Exception ex) {
            return ResponseDto.builder()
                    .responseCode(BnhUtils.EXCEPTION_CODE)
                    .responseMessage(ex.getLocalizedMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto getMovies() {
        try {
            List<BnhMovies> moviesList = bnhMoviesRepository.findAll();

            return ResponseDto.builder()
                    .responseCode(BnhUtils.SUCCESS_CODE)
                    .info(moviesList)
                    .build();

        } catch (Exception ex) {
            return ResponseDto.builder()
                    .responseCode(BnhUtils.EXCEPTION_CODE)
                    .responseMessage(ex.getLocalizedMessage())
                    .build();
        }

    }
}
