package com.en.server;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.en.entity.User;
import com.en.entity.dto.UserLoginDTO;
import com.en.entity.dto.UserRegisterDTO;

/**
* @author 17764
* @description 针对表【user】的数据库操作Service
* @createDate 2025-10-16 16:27:30
*/
public interface UserService extends IService<User> {
    SaResult register(UserRegisterDTO registerDTO);
    SaResult login(UserLoginDTO loginDTO);
    SaResult logout();
}
