package com.core.bingehaven.dtos.media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterDto {
    private String numberPerPage;
    private String country;
    private final String language = "en-"+country;
}
