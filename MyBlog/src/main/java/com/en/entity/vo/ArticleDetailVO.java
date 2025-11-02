package com.en.entity.vo;

import lombok.Data;

@Data
// 文章详情VO
public class ArticleDetailVO {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private Integer viewCount;
    private Integer likeCount;
    private Boolean isLiked;
    private String currentUserRole;
    
    // getter/setter...
}