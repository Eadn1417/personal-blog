package com.en.entity.vo;

import lombok.Data;

import java.util.List;
@Data
// 文章分页VO
public class ArticlePageVO {
    private List<ArticleListItemVO> records;
    private Long total;
    
    // getter/setter...
}