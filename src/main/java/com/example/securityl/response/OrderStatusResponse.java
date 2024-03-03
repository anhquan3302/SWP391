package com.example.securityl.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class OrderStatusResponse {
    private int orderStatusId;
    private Date createDate;
    private String description;
    private String name;
    private Date updateDate;
}