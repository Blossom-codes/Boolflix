package com.core.bingehaven.service;


import com.core.bingehaven.dtos.user.EmailDto;

public interface EmailService {
    void sendEmailAlert(EmailDto emailDto);
    void sendHtmlEmailAlert(EmailDto emailDto);
}
