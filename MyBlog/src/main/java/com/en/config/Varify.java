package com.en.config;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.en.entity.User;
import com.en.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Component
public class Varify implements StpInterface {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return List.of();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        System.out.println("******************************************************************************");
        System.out.println("loginId: " + loginId + ", type: " + loginId.getClass().getName());

        try {
            // 安全地处理 loginId 类型
            Long userId;
            if (loginId instanceof String) {
                userId = Long.parseLong((String) loginId);
            } else if (loginId instanceof Long) {
                userId = (Long) loginId;
            } else {
                throw new IllegalArgumentException("Unsupported loginId type: " + loginId.getClass());
            }

            // 根据用户ID查询用户信息
            User user = userMapper.selectById(userId);
            if (user != null) {
                return Arrays.asList(user.getRole());
            }
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("获取用户角色失败: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
