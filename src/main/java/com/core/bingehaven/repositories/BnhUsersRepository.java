package com.core.bingehaven.repositories;

import com.core.bingehaven.entities.BnhUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BnhUsersRepository extends JpaRepository<BnhUsers,Long> {
    BnhUsers findByUsername(String username);
    BnhUsers findByEmail(String email);
    BnhUsers existsByEmail(String email);
    Optional<BnhUsers> findByUsernameOrEmail(String username, String email);
}
