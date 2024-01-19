package com.example.securityl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "gallery")
public class Galery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gallery_id;
    private String thumbnail;

    @ManyToOne
    @JoinColumn(name = "product")
    private Products product;

}
