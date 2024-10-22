package com.core.bingehaven.dtos.user;

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
    private String oldPassword;
    private String newPassword;

}
