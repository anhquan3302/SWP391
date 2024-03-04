package com.example.securityl.request;

public class CreateOrderStatusRequest {
    private String description;
    private String name;

    public CreateOrderStatusRequest() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
