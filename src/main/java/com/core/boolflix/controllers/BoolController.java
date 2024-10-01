package com.core.boolflix.controllers;

import com.core.boolflix.dtos.ResponseDto;
import com.core.boolflix.dtos.UserRequestDto;
import com.core.boolflix.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("boolflix/")
@AllArgsConstructor
public class BoolController {

    private final UserService userService;

    @PostMapping(name = "v1/save")
    public ResponseDto saveUser(@RequestBody UserRequestDto user) {

        userService.saveNewUser(user);
        return null;
    }
}
