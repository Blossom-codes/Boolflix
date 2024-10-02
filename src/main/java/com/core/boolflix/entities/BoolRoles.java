package com.core.boolflix.entities;

import com.core.boolflix.enums.BoolGenres;
import com.core.boolflix.enums.BoolUserRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bool_roles")
public class BoolRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private BoolUserRoles userRole;
}
