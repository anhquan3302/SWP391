package com.example.securityl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_design")
@Builder
public class OrderDesign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_design_id")
    private Integer orderdesignid;

    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "order_method")
    private String orderMethod;

    @Column(name = "payment_date")
    private Date orderDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "design_project_id")
    @JsonBackReference
    private DesignProjects designProject;
}
