package com.core.bingehaven.controllers;

import com.core.bingehaven.dtos.global.ResponseDto;
import com.core.bingehaven.dtos.media.FilterDto;
import com.core.bingehaven.dtos.media.MovieDto;
import com.core.bingehaven.dtos.media.MovieRequestDto;
import com.core.bingehaven.dtos.media.SearchResponseDto;
import com.core.bingehaven.enums.BnhGenres;
import com.core.bingehaven.service.BnhMovieService;
import com.core.bingehaven.service.IMDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bingehaven/v1/admin/movie")
@RequiredArgsConstructor
public class BnhMovieController {
    private final BnhMovieService bnhMovieService;
    private final IMDbService imdbService;


    @Operation(summary = "Save a new movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Save a new movie",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))})})

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> saveMovie(@RequestBody MovieRequestDto movieRequestDto) {
        return ResponseEntity.ok(bnhMovieService.saveMovie(movieRequestDto));
    }

    @Operation(summary = "Fetch all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "return all movies",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))})})

    @GetMapping("/fetchMovies")
    public ResponseEntity<ResponseDto> fetchMovies() {
        return ResponseEntity.ok(bnhMovieService.getMovies());
    }

    @Cacheable("movieDetails")
    @GetMapping()
    public ResponseEntity<MovieDto> getMovieDetails(@RequestParam String movieId) {
        MovieDto movieDetails = imdbService.fetchMovieDetails(movieId);
        return ResponseEntity.ok(movieDetails);
    }

    //    @Cacheable("movieDetails")
    @GetMapping("/popular")
    public ResponseEntity<?> getPopular(@RequestBody FilterDto filterDto) {
        Object movieDetails = imdbService.fetchPopular(filterDto);
        return ResponseEntity.ok(movieDetails);
    }

    @GetMapping("/popular/{genre}")
    public ResponseEntity<?> getPopularByGenre(@RequestBody FilterDto filterDto, @PathVariable String genre) {
        Object movieDetails = imdbService.fetchPopularByGenre(filterDto, BnhGenres.valueOf(genre.toUpperCase()));
        return ResponseEntity.ok(movieDetails);
    }

    @GetMapping("/overview")
    public ResponseEntity<?> getMovieOverview(@RequestParam String movieId) {
        Object movieDetails = imdbService.fetchMovieOverview(movieId);
        return ResponseEntity.ok(movieDetails);
    }

    @Operation(summary = "Advance Movie Search")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search movies",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SearchResponseDto.class))})})

    @PostMapping("/advanced/search")
    public ResponseEntity<?> advanceSearch(@RequestBody FilterDto filterDto) {
        return ResponseEntity.ok(imdbService.advanceSearch(filterDto));
    }

}
