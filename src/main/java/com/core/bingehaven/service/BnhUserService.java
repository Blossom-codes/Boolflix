package com.core.bingehaven.service;

import com.core.bingehaven.dtos.*;
import com.core.bingehaven.enums.BnhUpdateTypes;

public interface BnhUserService {
    ResponseDto saveNewUser(UserRequestDto userRequestDto);
    ResponseDto updateUser(GeneralEditRequestDto editRequestDto);
    ResponseDto advanceUserUpdate(AdvanceEditReqDto advanceEditReqDto, BnhUpdateTypes type);
    ResponseDto deleteUser(Long id);
    ResponseDto fetchUser(Long id);
    ResponseDto login(LoginRequestDto loginRequestDto);
    ResponseDto addItemToMyList(Long movieId, Long userId, String type);
    String generateTerminalId();
}
