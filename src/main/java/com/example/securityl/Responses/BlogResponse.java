package com.example.securityl.Responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogResponse {
    private Long id;
    private String title;
    private String content;
    private String thumbnail;
    private String imageUrls;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long userBlogId;
    private String userFullName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime updatedAt;
    private List<String> categoryNames;
    private List<String> tagsBlogName;

    private List<Long> categoryBlogIds;
    private List<Long> tagBlogIds;
    private boolean active;


}
