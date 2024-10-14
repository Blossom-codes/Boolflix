package com.core.bingehaven.dtos.global;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private String responseCode;
    private String responseMessage;
    private Object info;
}