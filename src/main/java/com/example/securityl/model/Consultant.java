package com.example.securityl.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "consultant")
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consultant_id")
    private int consultantId;  // Đổi tên trường để tuân theo quy tắc Java (camelCase)

    @Column(name = "consultant_name")
    private String name;

    @Column(name = "experience")
    private int experience;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "designer_id")  // Tên cột thực sự trong cơ sở dữ liệu
    @JsonManagedReference
    private Orders designer;
}
