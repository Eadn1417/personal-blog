package com.en.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.util.SaResult;
import com.en.entity.Article;
import com.en.entity.dto.CommentCreateDTO;
import com.en.entity.dto.CommentUpdateDTO;
import com.en.server.ArticleService;
import com.en.server.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private ArticleService articleService;
    
    /**
     * 获取文章评论列表
     */
    @GetMapping("/articles/{id}/comments")
    public SaResult getCommentList(@PathVariable("id") Long articleId,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size) {
        // 检查文章是否存在
        Article article = articleService.getById(articleId);
        if (article == null) {
            return SaResult.error("文章不存在");
        }
        
        return commentService.getCommentList(articleId, page, size);
    }
    
    /**
     * 发表评论
     */
    @SaCheckLogin
    @PostMapping("/articles/{id}/comments")
    public SaResult createComment(@PathVariable("id") Long articleId,
                                  @RequestBody CommentCreateDTO createDTO) {
        // 检查文章是否存在
        Article article = articleService.getById(articleId);
        if (article == null) {
            return SaResult.error("文章不存在");
        }
        
        return commentService.createComment(articleId, createDTO);
    }
    
    /**
     * 更新评论（仅评论作者）
     */
    @SaCheckLogin
    @PutMapping("/comments/{id}")
    public SaResult updateComment(@PathVariable("id") Long id,
                                  @RequestBody CommentUpdateDTO updateDTO) {
        return commentService.updateComment(id, updateDTO);
    }
    
    /**
     * 删除评论（仅评论作者）
     */
    @SaCheckLogin
    @DeleteMapping("/comments/{id}")
    public SaResult deleteComment(@PathVariable("id") Long id) {
        return commentService.deleteComment(id);
    }
}
