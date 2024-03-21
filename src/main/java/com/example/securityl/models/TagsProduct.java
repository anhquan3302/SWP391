package com.example.securityl.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagsProduct extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    private String code;
//    @OneToMany(mappedBy = "tagsProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<Product> products;
}
