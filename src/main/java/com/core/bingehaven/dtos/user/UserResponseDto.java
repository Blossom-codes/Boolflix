package com.core.bingehaven.dtos.user;


import com.core.bingehaven.enums.BnhStatus;
import com.core.bingehaven.enums.BnhUserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private BnhStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BnhUserRoles role;

}

