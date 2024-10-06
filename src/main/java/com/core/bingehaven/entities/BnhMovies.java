package com.core.bingehaven.entities;

import com.core.bingehaven.enums.BnhGenres;
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
@Table(name = "bnh_movies")
public class BnhMovies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private BnhGenres genre;
    private String director;
    private String releaseDate;
    private String downloadUrl;
    private LocalDateTime uploadDate;
}
