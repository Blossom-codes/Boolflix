package com.core.bingehaven.repositories;

import com.core.bingehaven.entities.BnhImages;
import com.core.bingehaven.entities.BnhMovies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BnhImagesRepository extends JpaRepository<BnhImages, String> {
}
