package com.core.boolflix.service;

import com.core.boolflix.dtos.ResponseDto;
import com.core.boolflix.dtos.UserRequestDto;

public interface UserService {
    ResponseDto saveNewUser(UserRequestDto userRequestDto);
}
