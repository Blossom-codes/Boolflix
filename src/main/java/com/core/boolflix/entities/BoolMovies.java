package com.core.boolflix.entities;

import com.core.boolflix.enums.BoolGenres;
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
@Table(name = "bool_movies")
public class BoolMovies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private BoolGenres genre;
    private String director;
    private String releaseDate;
    private String downloadUrl;
    private LocalDateTime uploadDate;
}
