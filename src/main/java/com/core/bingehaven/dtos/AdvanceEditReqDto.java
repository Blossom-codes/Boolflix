package com.core.bingehaven.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceEditReqDto {
    private Long id;
    private String email;
    private String password;
}
