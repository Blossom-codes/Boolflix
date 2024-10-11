package com.core.bingehaven.entities;

import com.core.bingehaven.enums.BnhGenres;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    private String keywords;
    @Column(name = "release_date")
    private String releaseDate;
    @Column(name = "download_url")
    private String downloadUrl;
    @CreationTimestamp
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
}
