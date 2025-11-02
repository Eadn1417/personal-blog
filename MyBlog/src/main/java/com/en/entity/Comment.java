package com.en.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String content;

    private Long userId;

    private Long articleId;

    private Long parentId;
}