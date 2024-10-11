package com.core.bingehaven.controllers;

import com.core.bingehaven.dtos.global.ResponseDto;
import com.core.bingehaven.dtos.media.MovieRequestDto;
import com.core.bingehaven.service.BnhMovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bingehaven/v1/admin/movie")
@RequiredArgsConstructor
public class BnhMovieController {
    private final BnhMovieService bnhMovieService;

    @Operation(summary = "Save a new movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Save a new movie",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))})})

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> saveMovie(@RequestBody MovieRequestDto movieRequestDto) {
        return ResponseEntity.ok(bnhMovieService.saveMovie(movieRequestDto));
    }
}
