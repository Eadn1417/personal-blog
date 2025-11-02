package com.en.entity.vo;

import lombok.Data;

@Data
// 文章列表项VO
public class ArticleListItemVO {
    private Long id;
    private String title;
    private String summary;
    private String authorName;
    private Integer viewCount;
    private Integer likeCount;
    
    // getter/setter...
}