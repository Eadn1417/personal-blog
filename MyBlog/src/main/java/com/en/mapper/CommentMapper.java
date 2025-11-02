package com.en.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.en.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 17764
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2025-10-16 16:27:29
* @Entity generator.domain.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




