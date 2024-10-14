package com.core.bingehaven.dtos.media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
    private String id;    // Image ID
    private String url;   // Image URL
    private int height;   // Image height
    private int width;    // Image width

}
