package com.en.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
// 用户注册DTO
public class UserRegisterDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    private String password;
    
    // getter/setter...
}