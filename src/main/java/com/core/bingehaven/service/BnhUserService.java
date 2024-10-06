package com.core.bingehaven.service;

import com.core.bingehaven.dtos.LoginRequestDto;
import com.core.bingehaven.dtos.ResponseDto;
import com.core.bingehaven.dtos.UserRequestDto;

public interface BnhUserService {
    ResponseDto saveNewUser(UserRequestDto userRequestDto);
    ResponseDto updateUser(UserRequestDto userRequestDto, String type);
    ResponseDto deleteUser(Long id);
    ResponseDto fetchUser(Long id);
    ResponseDto login(LoginRequestDto loginRequestDto);
    ResponseDto addItemToMyList(Long movieId, Long userId, String type);
}
