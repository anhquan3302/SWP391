package com.example.securityl.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UpdateOrderStatusResponse {
    private String status;
    private String message;
    private Integer orderStatusId;
    private Date createDate;
    private Date updateDate;
}
