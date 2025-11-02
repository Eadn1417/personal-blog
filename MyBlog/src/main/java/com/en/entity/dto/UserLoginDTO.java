package com.en.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
// 用户登录DTO
public class UserLoginDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    private String password;
    
    // getter/setter...
}