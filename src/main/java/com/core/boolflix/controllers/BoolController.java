package com.core.boolflix.controllers;

import com.core.boolflix.dtos.ResponseDto;
import com.core.boolflix.dtos.UserRequestDto;
import com.core.boolflix.service.BoolUserService;
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
@RequestMapping("/boolflix")
@RequiredArgsConstructor
public class BoolController {

    private final BoolUserService boolUserService;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Save new user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequestDto.class))})})
    @PostMapping("/v1/save")
    public ResponseEntity<ResponseDto> saveUser(@RequestBody UserRequestDto user) {
        return ResponseEntity.ok(boolUserService.saveNewUser(user));
    }

    @PostMapping("/v1/check")
    public String ok(){
        return "ok";
    }
}
