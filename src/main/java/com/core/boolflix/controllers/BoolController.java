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
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDto> saveUser(@RequestBody UserRequestDto request) {
        return ResponseEntity.ok(boolUserService.saveNewUser(request));
    }

    @Operation(summary = "Edit/Update user info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update user name, email or password",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequestDto.class))})})
    @PostMapping("/v1/update")
    public ResponseEntity<ResponseDto> updateUser(@RequestBody UserRequestDto request,
                                                  @RequestParam String type) {
        return ResponseEntity.ok(boolUserService.updateUser(request, type));
    }
}
