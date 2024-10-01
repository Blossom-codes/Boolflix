package com.core.boolflix.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bool_users_watch_list")
public class BoolUsersWatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "show_id")
    private Long showId;

    @Column(name = "anime_id")
    private Long animeId;

    @Column(name = "added_at")
    @CreationTimestamp
    private LocalDateTime addedAt;


}
