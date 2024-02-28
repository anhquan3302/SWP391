package com.example.securityl.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productId")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "discount",nullable = false)
    private double discount;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "created_at",nullable = false)
    private Date createdAt;

    @Column(name = "updated_at",nullable = false)
    private Date updatedAt;

    @Column(name="size",nullable = false)
    private String size;

    @Column(name = "product_name",nullable = false)
    private String productName;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "favorite")
    private boolean favorite;

    @Column(name ="quantity")
    private Integer quantity;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "color",nullable = false)
    private String color;

    @Column(name ="price",nullable = false)
    private double price;

    @Column(name = "material",nullable = false)
    private String materials;

    @Column(name ="brand")
    private String brand;

    @ManyToMany(mappedBy = "products")
    private List<Category> categories ;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ImageProduct> imageProducts;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<OrderDetail> orderDetails;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<Notifications> notifications;





}
