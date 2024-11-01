package com.core.bingehaven.repositories;

import com.core.bingehaven.entities.BnhTokens;
import com.core.bingehaven.enums.BnhStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BnhTokenRepository extends JpaRepository<BnhTokens, Long> {
    BnhTokens findByToken(String token);
    List<BnhTokens> findByStatusAndType(BnhStatus status, String type);
    List<BnhTokens> findByStatus(BnhStatus status);
}
