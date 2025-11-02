package com.en.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.en.entity.dto.ArticleCreateDTO;
import com.en.entity.dto.ArticleUpdateDTO;
import com.en.server.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    
    @Autowired
    private ArticleService articleService;
    
    /**
     * 获取文章列表
     */
    @GetMapping
    public SaResult getArticleList(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size) {
        return articleService.getArticleList(page, size);
    }
    
    /**
     * 获取文章详情
     */
    @GetMapping("/{id}")
    public SaResult getArticleDetail(@PathVariable("id") Long id) {
        Long userId = StpUtil.isLogin() ? StpUtil.getLoginIdAsLong() : null;
        return articleService.getArticleDetail(id, userId);
    }
    
    /**
     * 创建文章（仅管理员）
     */
    @SaCheckRole("admin")
    @PostMapping
    public SaResult createArticle(@RequestBody ArticleCreateDTO createDTO) {
        Long userId = StpUtil.getLoginIdAsLong();
        return articleService.createArticle(createDTO, userId);
    }
    
    /**
     * 更新文章（仅管理员且仅文章作者）
     */
    @SaCheckRole("admin")
    @PutMapping("/{id}")
    public SaResult updateArticle(@PathVariable("id") Long id,
                                  @RequestBody ArticleUpdateDTO updateDTO) {
        Long userId = StpUtil.getLoginIdAsLong();
        return articleService.updateArticle(id, updateDTO, userId);
    }
    
    /**
     * 删除文章（仅管理员且仅文章作者）
     */
    @SaCheckRole("admin")
    @DeleteMapping("/{id}")
    public SaResult deleteArticle(@PathVariable("id") Long id) {
        Long userId = StpUtil.getLoginIdAsLong();
        return articleService.deleteArticle(id, userId);
    }
}
