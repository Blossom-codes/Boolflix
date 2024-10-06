package com.core.bingehaven.controllers;

import com.core.bingehaven.dtos.LoginRequestDto;
import com.core.bingehaven.dtos.ResponseDto;
import com.core.bingehaven.dtos.UserRequestDto;
import com.core.bingehaven.service.BnhUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bingehaven/")
@RequiredArgsConstructor
public class BnhUserController {

    private final BnhUserService bnhUserService;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Registration",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequestDto.class))})})
    @PostMapping("v1/save")
    public ResponseEntity<ResponseDto> saveUser(@RequestBody UserRequestDto request) {
        return ResponseEntity.ok(bnhUserService.saveNewUser(request));
    }

    @Operation(summary = "Update user name, email or password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful update",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequestDto.class))})})
    @PostMapping("v1/update")
    public ResponseEntity<ResponseDto> updateUser(@RequestBody UserRequestDto request,
                                                  @RequestParam String type) {
        return ResponseEntity.ok(bnhUserService.updateUser(request, type));
    }
    @Operation(summary = "Endpoint for users to login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequestDto.class))})})
    @PostMapping("v1/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto loginDto) {
        return ResponseEntity.ok(bnhUserService.login(loginDto));
    }
}
