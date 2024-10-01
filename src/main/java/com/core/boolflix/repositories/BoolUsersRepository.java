package com.core.boolflix.repositories;

import com.core.boolflix.entities.BoolUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoolUsersRepository extends JpaRepository<BoolUsers,Long> {
    BoolUsers findByUsername(String username);
    BoolUsers findByEmail(String email);
}
