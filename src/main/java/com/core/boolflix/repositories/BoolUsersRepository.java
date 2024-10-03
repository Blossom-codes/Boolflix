package com.core.boolflix.repositories;

import com.core.boolflix.entities.BoolUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoolUsersRepository extends JpaRepository<BoolUsers,Long> {
    BoolUsers findByUsername(String username);
    BoolUsers findByEmail(String email);
    BoolUsers existsByEmail(String email);
    Optional<BoolUsers> findByUsernameOrEmail(String username, String email);
}
