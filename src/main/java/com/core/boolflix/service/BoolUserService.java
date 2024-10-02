package com.core.boolflix.service;

import com.core.boolflix.dtos.ResponseDto;
import com.core.boolflix.dtos.UserRequestDto;

public interface BoolUserService {
    ResponseDto saveNewUser(UserRequestDto userRequestDto);
    ResponseDto updateUser(UserRequestDto userRequestDto, String type);
    ResponseDto deleteUser(Long id);
    ResponseDto getUsers(Long id);

    ResponseDto addItemToMyList(Long movieId, Long userId, String type);
}
