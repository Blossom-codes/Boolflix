package com.core.bingehaven.repositories;

import com.core.bingehaven.entities.BnhTokens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BnhTokenRepository extends JpaRepository<BnhTokens, Long> {
    BnhTokens findByToken(String token);
}
