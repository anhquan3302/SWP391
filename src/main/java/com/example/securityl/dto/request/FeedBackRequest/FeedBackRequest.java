package com.example.securityl.dto.request.FeedBackRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data

@Builder
public class FeedBackRequest {

    private String content;


    private Date createdAt;


    private int userId;


    private int productId;

    public FeedBackRequest() {
    }

    public FeedBackRequest(String content, Date createdAt, int userId, int productId) {
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}