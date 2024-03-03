package com.example.securityl.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    private Integer orderStatusId;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "update_date")
    private Date updateDate;

    @OneToMany(mappedBy = "orderStatus")
    @JsonManagedReference
    private List<Orders> orders;
}
