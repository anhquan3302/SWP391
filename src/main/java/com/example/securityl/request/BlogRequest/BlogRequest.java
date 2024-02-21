package com.example.securityl.request.BlogRequest;

import com.example.securityl.model.User;

public class BlogRequest {
    private String title;
    private String content;


    public BlogRequest() {
    }

    public BlogRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}