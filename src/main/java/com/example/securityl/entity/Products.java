package com.example.securityl.entity;

import com.example.securityl.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer product_id;
    private String title;
    private String thumbnail;
    private Integer discount;
    private String description;
    private Date created_at;
    private Date updated_at;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Category> categories;

}
