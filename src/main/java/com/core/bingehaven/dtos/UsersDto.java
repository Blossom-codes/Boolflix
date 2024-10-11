package com.core.bingehaven.dtos;


import com.core.bingehaven.enums.BnhStatus;
import com.core.bingehaven.enums.BnhUserRoles;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
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

