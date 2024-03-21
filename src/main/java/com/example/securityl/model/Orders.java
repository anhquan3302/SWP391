package com.example.securityl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "email")
    private String email;

    @Column(name = "code")
    private String code;

    @Column(name = "custormer")
    private String custormer;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @Column(name = "total_money")
    private Double totalMoney;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "history")
    private Date history;


//    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Payment> payments = new ArrayList<>();

//    @OneToMany(mappedBy = "designer", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Consultant> consultants = new ArrayList<>();

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
//    private Voucher voucher;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;
}
