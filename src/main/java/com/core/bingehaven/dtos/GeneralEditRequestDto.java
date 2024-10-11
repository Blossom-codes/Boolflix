package com.core.bingehaven.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralEditRequestDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
}
