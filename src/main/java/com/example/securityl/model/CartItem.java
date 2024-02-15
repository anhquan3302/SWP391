//package com.example.securityl.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "cart_items")
//public class CartItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "cart_item_id")
//    private Long cartItemId;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id", nullable = false)
//    private Products product;
//
//    @ManyToOne
//    @JoinColumn(name = "cart_id", nullable = false)
//    private Cart cart;
//
//    @Column(name = "quantity", nullable = false)
//    private int quantity;
//
//}
