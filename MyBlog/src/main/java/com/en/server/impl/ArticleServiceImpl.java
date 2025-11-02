package com.en.server.impl;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.en.entity.Article;

import com.en.entity.dto.ArticleCreateDTO;
import com.en.entity.dto.ArticleUpdateDTO;
import com.en.entity.vo.ArticleDetailVO;
import com.en.entity.vo.ArticleListItemVO;
import com.en.entity.vo.ArticlePageVO;
import com.en.mapper.ArticleMapper;
import com.en.server.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    
    @Autowired
    private ArticleMapper articleMapper;
    
    @Override
    public SaResult getArticleList(Integer page, Integer size) {
        // 分页查询
        Page<Article> pageParam = new Page<>(page, size);
        Page<Article> articlePage = this.page(pageParam, 
            new QueryWrapper<Article>().orderByDesc("id"));
        
        // 转换为VO
        List<ArticleListItemVO> records = articlePage.getRecords().stream().map(article -> {
            ArticleListItemVO vo = new ArticleListItemVO();
            vo.setId(article.getId());
            vo.setTitle(article.getTitle());
            vo.setSummary(article.getContent().length() > 100 ? 
                article.getContent().substring(0, 100) + "..." : article.getContent());
            vo.setAuthorName("作者" + article.getAuthorId()); // 实际应关联用户表获取用户名
            vo.setViewCount(article.getViewCount());
            vo.setLikeCount(article.getLikeCount());
            return vo;
        }).collect(Collectors.toList());
        
        ArticlePageVO pageVO = new ArticlePageVO();
        pageVO.setRecords(records);
        pageVO.setTotal(articlePage.getTotal());
        
        return SaResult.data(pageVO);
    }
    
    @Override
    public SaResult getArticleDetail(Long id, Long userId) {
        // 查询文章
        Article article = this.getById(id);
        if (article == null) {
            return SaResult.error("文章不存在");
        }
        
        // 增加浏览量
        articleMapper.incrementViewCount(id);
        article.setViewCount(article.getViewCount() + 1);
        
        // 构造返回数据
        ArticleDetailVO detailVO = new ArticleDetailVO();
        detailVO.setId(article.getId());
        detailVO.setTitle(article.getTitle());
        detailVO.setContent(article.getContent());
        detailVO.setAuthorName("作者" + article.getAuthorId());
        detailVO.setViewCount(article.getViewCount());
        detailVO.setLikeCount(article.getLikeCount());
        detailVO.setCurrentUserRole(StpUtil.hasRole("admin") ? "admin" : "user");
        
        // 检查当前用户是否已点赞
        detailVO.setIsLiked(false); // 实际应调用点赞服务检查
        
        return SaResult.data(detailVO);
    }
    
    @Override
    @SaCheckRole("admin")
    public SaResult createArticle(ArticleCreateDTO createDTO, Long userId) {
        Article article = new Article();
        article.setTitle(createDTO.getTitle());
        article.setContent(createDTO.getContent());
        article.setAuthorId(userId);
        article.setViewCount(0);
        article.setLikeCount(0);
        
        this.save(article);
        
        Map<String, Long> result = new HashMap<>();
        result.put("id", article.getId());
        
        return SaResult.data(result).setMsg("创建成功");
    }
    
    @Override
    @SaCheckRole("admin")
    public SaResult updateArticle(Long id, ArticleUpdateDTO updateDTO, Long userId) {
        // 检查文章是否存在
        Article article = this.getById(id);
        if (article == null) {
            return SaResult.error("文章不存在");
        }
        
        // 检查权限（只有文章作者可以编辑）
        if (!article.getAuthorId().equals(userId)) {
            return SaResult.error("您没有权限编辑此文章");
        }
        
        article.setTitle(updateDTO.getTitle());
        article.setContent(updateDTO.getContent());
        
        this.updateById(article);
        
        return SaResult.ok("更新成功");
    }
    
    @Override
    @SaCheckRole("admin")
    public SaResult deleteArticle(Long id, Long userId) {
        // 检查文章是否存在
        Article article = this.getById(id);
        if (article == null) {
            return SaResult.error("文章不存在");
        }
        
        // 检查权限（只有文章作者可以删除）
        if (!article.getAuthorId().equals(userId)) {
            return SaResult.error("您没有权限删除此文章");
        }
        
        this.removeById(id);
        
        return SaResult.ok("删除成功");
    }
}
