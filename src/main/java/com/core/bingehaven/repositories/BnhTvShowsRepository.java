package com.core.bingehaven.repositories;

import com.core.bingehaven.entities.BnhMovies;
import com.core.bingehaven.entities.BnhTvShows;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BnhTvShowsRepository extends JpaRepository<BnhTvShows, Long> {
    BnhMovies findByTvId(String tvId);
}
