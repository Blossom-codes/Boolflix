package com.core.bingehaven.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bnh_images")
public class BnhImages {
    @Id
    private String id;    // Image ID
    private String url;   // Image URL
    private int height;   // Image height
    private int width;    // Image width

}
