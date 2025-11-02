package com.en.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
// 评论更新DTO
public class CommentUpdateDTO {
    @NotBlank(message = "评论内容不能为空")
    private String content;
    
    // getter/setter...
}