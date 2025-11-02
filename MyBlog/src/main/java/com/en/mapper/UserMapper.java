package com.en.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.en.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 17764
* @description 针对表【user】的数据库操作Mapper
* @createDate 2025-10-16 16:27:30
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




