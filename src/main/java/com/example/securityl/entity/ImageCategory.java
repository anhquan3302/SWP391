package com.example.securityl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "image_category")
public class ImageCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gallery_id")
    private Integer galleryId;

    @Column(name = "thumbnail")
    private String thumbnail;


    @ManyToOne
    @JoinColumn(name = "product")
    private Products product;

}
