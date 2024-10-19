package com.core.bingehaven.enums;

import lombok.Getter;

@Getter
public enum BnhGenres {
    COMEDY("Comedy"),
    HORROR("Horror"),
    ROMANCE("Romance"),
    THRILLER("Thriller"),
    SCI_FI("Sci-Fi"),
    DRAMA("Drama"),
    ACTION("Action"),
    ADVENTURE("Adventure"),
    ANIMATION("Animation"),
    BIOGRAPHY("Biography"),
    CRIME("Crime"),
    DOCUMENTARY("Documentary"),
    FAMILY("Family"),
    FANTASY("Fantasy"),
    FILM_NOIR("Film-Noir"),
    GAME_SHOW("Game-Show"),
    HISTORY("History"),
    MUSIC("Music"),
    MUSICAL("Musical"),
    MYSTERY("Mystery"),
    NEWS("News"),  // Fixed "Mews" to "News"
    REALITY_TV("Reality-Tv"),
    SPORT("Sport"),
    TALK_SHOW("Talk-Show"),
    WAR("War"),
    WESTERN("Western");

    private final String genre;

    BnhGenres(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
