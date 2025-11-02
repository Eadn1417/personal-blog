package com.en.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.en.entity.User;
import com.en.entity.dto.UserLoginDTO;
import com.en.entity.dto.UserRegisterDTO;
import com.en.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public SaResult register(@RequestBody UserRegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public SaResult login(@RequestBody UserLoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/current")
    public SaResult getCurrentUser() {
        // 检查是否已登录
        if (!StpUtil.isLogin()) {
            return SaResult.error("未登录");
        }

        try {
            // 获取当前登录用户ID
            Long userId = StpUtil.getLoginIdAsLong();

            // 查询用户信息
            User user = userService.getById(userId);

            if (user != null) {
                // 创建返回的用户信息对象（不包含密码）
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("id", user.getId());
                userInfo.put("username", user.getUsername());
                userInfo.put("role", user.getRole());

                return SaResult.data(userInfo);
            }

            return SaResult.error("用户不存在");
        } catch (NotLoginException e) {
            return SaResult.error("认证已过期，请重新登录");
        } catch (Exception e) {
            return SaResult.error("获取用户信息失败");
        }
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public SaResult logout() {
        return userService.logout();
    }
}
