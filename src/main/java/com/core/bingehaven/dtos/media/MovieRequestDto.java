package com.core.bingehaven.dtos.media;

import com.core.bingehaven.enums.BnhGenres;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDto {
    private String title;
    private BnhGenres genre;
    private String keywords;
    private String releaseDate;
    private String downloadUrl;
    private String uploadedBy;
    private String modifiedBy;
}