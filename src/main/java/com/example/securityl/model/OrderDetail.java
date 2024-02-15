package com.example.securityl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Integer orderDetailId;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "price")
    private Double price;

    @Column(name = "number")
    private Integer number;

    @Column(name = "total_money")
    private Double totalMoney;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "product_id")
    private Products product;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "order_id")
    private Orders order;
}
