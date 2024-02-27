package com.example.securityl.request.BlogRequest;

public class BlogRequest {
    private String title;
    private String content;

    private  int userId;

    private String thumbnail;

    public BlogRequest() {
    }

    public BlogRequest(String title, String content,Integer userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;

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

    public Integer getUserId(){
        return userId;
    }
    public void setUserId(Integer userId){
        this.userId =userId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}