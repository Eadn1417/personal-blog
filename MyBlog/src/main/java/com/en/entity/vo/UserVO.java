package com.en.entity.vo;

import lombok.Data;

@Data
// 用户基本信息VO
public class UserVO {
    private Long id;
    private String username;
    private String role;
    
    // getter/setter...
}