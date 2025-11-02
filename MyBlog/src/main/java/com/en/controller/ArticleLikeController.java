package com.en.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.en.entity.Article;
import com.en.server.ArticleLikeService;
import com.en.server.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleLikeController {
    
    @Autowired
    private ArticleLikeService articleLikeService;
    
    @Autowired
    private ArticleService articleService;
    
    /**
     * 点赞文章
     */
    @SaCheckLogin
    @PostMapping("/{id}/like")
    public SaResult likeArticle(@PathVariable("id") Long articleId) {
        // 检查文章是否存在
        Article article = articleService.getById(articleId);
        if (article == null) {
            return SaResult.error("文章不存在");
        }
        
        // 获取当前登录用户ID
        Long userId = StpUtil.getLoginIdAsLong();
        
        // 执行点赞操作
        return articleLikeService.likeArticle(articleId, userId);
    }
    
    /**
     * 取消点赞文章
     */
    @SaCheckLogin
    @DeleteMapping("/{id}/like")
    public SaResult unlikeArticle(@PathVariable("id") Long articleId) {
        // 检查文章是否存在
        Article article = articleService.getById(articleId);
        if (article == null) {
            return SaResult.error("文章不存在");
        }
        
        // 获取当前登录用户ID
        Long userId = StpUtil.getLoginIdAsLong();
        
        // 执行取消点赞操作
        return articleLikeService.unlikeArticle(articleId, userId);
    }
}
