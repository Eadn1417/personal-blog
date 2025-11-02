package com.en.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
// 文章更新DTO
public class ArticleUpdateDTO {
    @NotBlank(message = "文章标题不能为空")
    private String title;
    
    @NotBlank(message = "文章内容不能为空")
    private String content;
    
    // getter/setter...
}