package com.core.bingehaven.service.serviceImpl;

import com.core.bingehaven.dtos.global.ResponseDto;
import com.core.bingehaven.dtos.media.MovieRequestDto;
import com.core.bingehaven.entities.BnhImages;
import com.core.bingehaven.entities.BnhMovies;
import com.core.bingehaven.entities.BnhTvShows;
import com.core.bingehaven.repositories.BnhImagesRepository;
import com.core.bingehaven.repositories.BnhMoviesRepository;
import com.core.bingehaven.repositories.BnhTvShowsRepository;
import com.core.bingehaven.service.BnhMovieService;
import com.core.bingehaven.utils.BnhUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BnhMovieServiceImpl implements BnhMovieService {

    private final BnhMoviesRepository bnhMoviesRepository;
    private final BnhTvShowsRepository bnhTvShowsRepository;
    private final BnhImagesRepository bnhImagesRepository;

    @Override
    public ResponseDto saveMovie(MovieRequestDto movieRequestDto) {
// save movies to database
        try {
            if (movieRequestDto == null) {
                throw new RuntimeException("Enter parameters to save movie");
            }

            BnhMovies movies = new BnhMovies();
            BnhImages image = new BnhImages();
            movies.setMovieId(movieRequestDto.getId());
            movies.setTitle(movieRequestDto.getTitle());
            movies.setTitleType(movieRequestDto.getTitleType());
            movies.setGenres(movieRequestDto.getGenres());
            movies.setKeywords(movieRequestDto.getKeywords());
            movies.setMovie(movieRequestDto.isMovie());
            movies.setSeries(movieRequestDto.isSeries());
            movies.setReleaseDate(movieRequestDto.getReleaseDate());
            movies.setRatings(movieRequestDto.getRatings());
            movies.setYear(movieRequestDto.getYear());
            movies.setRunningTimeInMinutes(movieRequestDto.getRunningTimeInMinutes());
            movies.setPlot(movieRequestDto.getPlot());
            movies.setCertificateRating(movieRequestDto.getCertificateRating());
            movies.setRatingCount(movieRequestDto.getRatingCount());
            movies.setUploadedBy(movieRequestDto.getUploadedBy());
            movies.setDownloadUrl(movieRequestDto.getDownloadUrl());

            movies.setImageId(movieRequestDto.getImage().getId());
            image.setId(movieRequestDto.getImage().getId());
            image.setUrl(movieRequestDto.getImage().getUrl());
            image.setHeight(movieRequestDto.getImage().getHeight());
            image.setWidth(movieRequestDto.getImage().getWidth());


             bnhMoviesRepository.save(movies);
            bnhImagesRepository.save(image);
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
    }  public ResponseDto saveTvShow(MovieRequestDto movieRequestDto) {
// save tv to database
        try {
            if (movieRequestDto == null) {
                throw new RuntimeException("Enter parameters to save movie");
            }

            BnhTvShows tv = new BnhTvShows();
            BnhImages image = new BnhImages();
            tv.setTvId(movieRequestDto.getId());
            tv.setTitle(movieRequestDto.getTitle());
            tv.setTitleType(movieRequestDto.getTitleType());
            tv.setGenres(movieRequestDto.getGenres());
            tv.setKeywords(movieRequestDto.getKeywords());
            tv.setMovie(movieRequestDto.isMovie());
            tv.setSeries(movieRequestDto.isSeries());
            tv.setReleaseDate(movieRequestDto.getReleaseDate());
            tv.setRatings(movieRequestDto.getRatings());
            tv.setYear(movieRequestDto.getYear());
            tv.setRunningTimeInMinutes(movieRequestDto.getRunningTimeInMinutes());
            tv.setPlot(movieRequestDto.getPlot());
            tv.setCertificateRating(movieRequestDto.getCertificateRating());
            tv.setRatingCount(movieRequestDto.getRatingCount());
            tv.setUploadedBy(movieRequestDto.getUploadedBy());
            tv.setDownloadUrl(movieRequestDto.getDownloadUrl());

            tv.setImageId(movieRequestDto.getImage().getId());
            image.setId(movieRequestDto.getImage().getId());
            image.setUrl(movieRequestDto.getImage().getUrl());
            image.setHeight(movieRequestDto.getImage().getHeight());
            image.setWidth(movieRequestDto.getImage().getWidth());


             bnhTvShowsRepository.save(tv);
            bnhImagesRepository.save(image);
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
