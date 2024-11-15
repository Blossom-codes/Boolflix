package com.core.bingehaven.repositories;

import com.core.bingehaven.entities.BnhMovies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BnhMoviesRepository extends JpaRepository<BnhMovies, Long> {
    BnhMovies findByMovieId(String movieId);
}
