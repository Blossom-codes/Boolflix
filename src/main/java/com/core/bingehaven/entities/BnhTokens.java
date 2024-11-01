package com.core.bingehaven.entities;

import com.core.bingehaven.enums.BnhStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bnh_tokens")
public class BnhTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String token;
    @NonNull
    private String email;
    @NonNull
    private String type;
    @NonNull
    @Enumerated(EnumType.STRING)
    private BnhStatus status;
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
}
