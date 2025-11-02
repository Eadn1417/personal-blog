package com.en.server.impl;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.en.entity.Comment;
import com.en.entity.dto.CommentCreateDTO;
import com.en.entity.dto.CommentUpdateDTO;
import com.en.entity.vo.CommentListItemVO;
import com.en.entity.vo.CommentPageVO;
import com.en.mapper.CommentMapper;
import com.en.server.CommentService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    
    @Override
    public SaResult getCommentList(Long articleId, Integer page, Integer size) {
        // 分页查询评论
        Page<Comment> pageParam = new Page<>(page, size);
        Page<Comment> commentPage = this.page(pageParam, 
            new QueryWrapper<Comment>()
                .eq("article_id", articleId)
                .orderByDesc("id"));
        
        // 转换为VO
        List<CommentListItemVO> records = commentPage.getRecords().stream().map(comment -> {
            CommentListItemVO vo = new CommentListItemVO();
            vo.setId(comment.getId());
            vo.setContent(comment.getContent());
            vo.setUsername("用户" + comment.getUserId()); // 实际应关联用户表获取用户名
            
            // 检查是否是当前用户的评论
            Long currentUserId = StpUtil.getLoginIdAsLong();
            vo.setIsOwn(comment.getUserId().equals(currentUserId));
            
            return vo;
        }).collect(Collectors.toList());
        
        CommentPageVO pageVO = new CommentPageVO();
        pageVO.setRecords(records);
        pageVO.setTotal(commentPage.getTotal());
        
        return SaResult.data(pageVO);
    }
    
    @Override
    @SaCheckLogin
    public SaResult createComment(Long articleId, CommentCreateDTO createDTO) {
        Comment comment = new Comment();
        comment.setContent(createDTO.getContent());
        comment.setArticleId(articleId);
        comment.setUserId(StpUtil.getLoginIdAsLong());
        comment.setParentId(null); // 暂不支持回复功能
        
        this.save(comment);
        
        Map<String, Long> result = new HashMap<>();
        result.put("id", comment.getId());
        
        return SaResult.data(result).setMsg("评论成功");
    }
    
    @Override
    @SaCheckLogin
    public SaResult updateComment(Long id, CommentUpdateDTO updateDTO) {
        // 检查评论是否存在
        Comment comment = this.getById(id);
        if (comment == null) {
            return SaResult.error("评论不存在");
        }
        
        // 检查权限（只有评论作者可以编辑）
        Long currentUserId = StpUtil.getLoginIdAsLong();
        if (!comment.getUserId().equals(currentUserId)) {
            return SaResult.error("您没有权限编辑此评论");
        }
        
        comment.setContent(updateDTO.getContent());
        this.updateById(comment);
        
        return SaResult.ok("更新成功");
    }
    
    @Override
    @SaCheckLogin
    public SaResult deleteComment(Long id) {
        // 检查评论是否存在
        Comment comment = this.getById(id);
        if (comment == null) {
            return SaResult.error("评论不存在");
        }
        
        // 检查权限（只有评论作者可以删除）
        Long currentUserId = StpUtil.getLoginIdAsLong();
        if (!comment.getUserId().equals(currentUserId)) {
            return SaResult.error("您没有权限删除此评论");
        }
        
        this.removeById(id);
        
        return SaResult.ok("删除成功");
    }
}
