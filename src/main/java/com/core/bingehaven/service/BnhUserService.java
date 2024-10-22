package com.core.bingehaven.service;

import com.core.bingehaven.dtos.global.ResponseDto;
import com.core.bingehaven.dtos.user.*;
import com.core.bingehaven.enums.BnhUpdateTypes;

public interface BnhUserService {
    ResponseDto saveNewUser(UserRequestDto userRequestDto);
    ResponseDto updateUser(GeneralEditRequestDto editRequestDto);
    ResponseDto advanceUserUpdate(AdvanceEditReqDto advanceEditReqDto);
    ResponseDto forgotPassword(ForgotPasswordReq forgotPasswordReq, String token);
    ResponseDto sendForgotPasswordMail(String recipient);
    ResponseDto deleteUser(Long id);
    ResponseDto fetchUser(Long id);
    ResponseDto login(LoginRequestDto loginRequestDto);
    ResponseDto addItemToMyList(Long movieId, Long userId, String type);
      String generateTerminalId();
}
