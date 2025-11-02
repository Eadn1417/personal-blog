package com.en.entity.vo;

import lombok.Data;

@Data
// 评论列表项VO
public class CommentListItemVO {
    private Long id;
    private String content;
    private String username;
    private Boolean isOwn;
    
    // getter/setter...
}