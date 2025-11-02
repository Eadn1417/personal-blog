package com.en.entity.vo;

import lombok.Data;

import java.util.List;
@Data
// 评论分页VO
public class CommentPageVO {
    private List<CommentListItemVO> records;
    private Long total;
    
    // getter/setter...
}