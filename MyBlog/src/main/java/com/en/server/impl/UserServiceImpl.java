package com.en.server.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.en.entity.User;
import com.en.entity.dto.UserLoginDTO;
import com.en.entity.dto.UserRegisterDTO;
import com.en.entity.vo.LoginUserVO;
import com.en.mapper.UserMapper;
import com.en.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public SaResult register(UserRegisterDTO registerDTO) {
        // 检查用户名是否已存在
        User existingUser = this.getOne(new QueryWrapper<User>()
                .eq("username", registerDTO.getUsername()));
        
        if (existingUser != null) {
            return SaResult.error("用户名已存在");
        }
        
        // 创建新用户
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword()); // 实际项目中需要加密
        user.setRole("user"); // 默认角色为普通用户
        
        this.save(user);
        
        return SaResult.ok("注册成功");
    }
    
    @Override
    public SaResult login(UserLoginDTO loginDTO) {
        // 查询用户
        User user = this.getOne(new QueryWrapper<User>()
                .eq("username", loginDTO.getUsername()));
        
        if (user == null) {
            return SaResult.error("用户不存在");
        }
        
        // 验证密码
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            return SaResult.error("密码错误");
        }
        
        // 登录认证
        StpUtil.login(user.getId());
        
        // 返回token信息
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setTokenName(StpUtil.getTokenName());
        loginUserVO.setTokenValue(StpUtil.getTokenValue());
        Boolean t=StpUtil.hasRole("admin");
        System.out.println("当前用户角色是否是admin: "+t);
        return SaResult.data(loginUserVO).setMsg("登录成功");
    }
    
    @Override
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok("退出成功");
    }
}
