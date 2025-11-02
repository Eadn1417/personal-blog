package com.en.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName article_like
 */
@TableName(value ="article_like")
@Data
public class ArticleLike {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long articleId;
}