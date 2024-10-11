package com.core.bingehaven.entities;

import com.core.bingehaven.enums.BnhGenres;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @NonNull
    private String title;
    @NonNull
    @Enumerated(EnumType.STRING)
    private BnhGenres genre;
    @NonNull
    private String keywords;
    @Column(name = "release_date")
    private String releaseDate;
    @NonNull
    @Column(name = "download_url")
    private String downloadUrl;
    @NonNull
    @Column(name = "uploaded_by")
    private String uploadedBy;
    @NonNull
    @CreationTimestamp
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;
    @NonNull
    @Column(name = "modified_by")
    private String modifiedBy;
    @NonNull
    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
}
