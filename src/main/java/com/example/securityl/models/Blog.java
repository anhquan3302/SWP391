package com.example.securityl.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "blogs")

public class Blog extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "thumbnail",  columnDefinition = "TEXT", nullable = true)
    private String thumbnail;

    @Column(name = "image_urls", columnDefinition = "TEXT", nullable = true)
    private String imageUrls;


    @Column(name = "is_active")
    // dm phu le
    private boolean active;



}
