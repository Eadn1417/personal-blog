package com.en.server;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.en.entity.Comment;
import com.en.entity.dto.CommentCreateDTO;
import com.en.entity.dto.CommentUpdateDTO;

/**
* @author 17764
* @description 针对表【comment】的数据库操作Service
* @createDate 2025-10-16 16:27:29
*/
public interface CommentService extends IService<Comment> {
    SaResult getCommentList(Long articleId, Integer page, Integer size);
    SaResult createComment(Long articleId, CommentCreateDTO createDTO);
    SaResult updateComment(Long id, CommentUpdateDTO updateDTO);
    SaResult deleteComment(Long id);
}
