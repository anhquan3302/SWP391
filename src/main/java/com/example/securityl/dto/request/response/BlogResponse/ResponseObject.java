package com.example.securityl.dto.request.response.BlogResponse;

import com.example.securityl.model.Feedback;

public class ResponseObject {
    private String status;
    private String message;
    private Object payload;

    public ResponseObject(String feedbackCreatedSuccessfully, Feedback feedback) {
    }

    public ResponseObject(String status, String message, Object payload) {
        this.status = status;
        this.message = message;
        this.payload = payload;
    }

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
