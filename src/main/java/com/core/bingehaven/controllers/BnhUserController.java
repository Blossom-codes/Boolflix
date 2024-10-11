package com.core.bingehaven.controllers;

import com.core.bingehaven.dtos.*;
import com.core.bingehaven.enums.BnhUpdateTypes;
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
@RequestMapping("bingehaven/v1/")
@RequiredArgsConstructor
public class BnhUserController {

    private final BnhUserService bnhUserService;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Registration",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))})})
    @PostMapping("save")
    public ResponseEntity<ResponseDto> saveUser(@RequestBody UserRequestDto request) {
        return ResponseEntity.ok(bnhUserService.saveNewUser(request));
    }

    @Operation(summary = "Update user's general details like First name, Last name and Username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful update",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))})})
    @PostMapping("update")
    public ResponseEntity<ResponseDto> updateUser(@RequestBody GeneralEditRequestDto request) {
        return ResponseEntity.ok(bnhUserService.updateUser(request));
    }
    @Operation(summary = "Update email or password only")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful update",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))})})
    @PostMapping("update/advanced")
    public ResponseEntity<ResponseDto> advanceUserUpdate(@RequestBody AdvanceEditReqDto request,
                                                  @RequestParam BnhUpdateTypes type) {
        return ResponseEntity.ok(bnhUserService.advanceUserUpdate(request,type));
    }
    @Operation(summary = "Endpoint for users to login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))})})
    @PostMapping("login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto loginDto) {
        return ResponseEntity.ok(bnhUserService.login(loginDto));
    }
    @Operation(summary = "Endpoint for fetching a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Query Successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))})})
    @GetMapping("fetch/{id}")
    public ResponseEntity<ResponseDto> fetchUser(@PathVariable Long id) {
            return ResponseEntity.ok(bnhUserService.fetchUser(id));
    }
}
