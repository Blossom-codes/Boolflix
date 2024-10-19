package com.core.bingehaven.dtos.media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {
    private List<MovieDto> movies;
    private List<MovieDto> tv;
    private List<MovieDto> animes;
}
