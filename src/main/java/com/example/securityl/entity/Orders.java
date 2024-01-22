package com.example.securityl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "address")
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "status")
    private boolean status;

    @Column(name = "total_money")
    private Double totalMoney;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // mỗi đơn hàng chỉ có thể áp dụng một voucher)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    private Voucher voucher;

    // mỗi đơn hàng chỉ có một trạng thái)
    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;
}
