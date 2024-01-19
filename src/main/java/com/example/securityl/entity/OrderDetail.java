package com.example.securityl.entity;

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
@Table(name = "oders_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_details;
    private String fullname;
    private Double price;
    private Integer number;
    private Double total_money;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products productss;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orderss;
}
