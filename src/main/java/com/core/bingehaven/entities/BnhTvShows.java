package com.core.bingehaven.entities;

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
@Table(name = "bnh_tv_shows")
public class BnhTvShows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    @Column(name = "tv_id")
    private String tvId;

    @NonNull
    private String keywords;

    @Column(name = "release_date")
    private String releaseDate;

    @NonNull
    @Column(name = "download_url")
    private String downloadUrl;

    @NonNull
    @Column(name = "title_type")
    private String titleType;        // Type of title (e.g., movie, series)

    @Column(name = "running_time_in_minutes")
    private Integer runningTimeInMinutes; // Running time in minutes

    private Integer year;

    @Column(name = "image_id")
    private String imageId;

    private String plot;

    private Double ratings;

    @Column(name = "certificate_rating")
    private String certificateRating;

    @Column(name = "rating_count")
    private Integer ratingCount;

    @Column(name = "is_movie")
    private boolean isMovie;

    @Column(name = "is_series")
    private boolean isSeries;

    private String genres;

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
