package com.en.server.impl;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.en.entity.Article;
import com.en.entity.ArticleLike;
import com.en.entity.vo.LikeResultVO;
import com.en.mapper.ArticleLikeMapper;
import com.en.mapper.ArticleMapper;
import com.en.server.ArticleLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike> implements ArticleLikeService {
    
    @Autowired
    private ArticleMapper articleMapper;
    
    @Override
    @Transactional
    public SaResult likeArticle(Long articleId, Long userId) {
        // 检查是否已经点赞
        ArticleLike existingLike = this.getOne(new QueryWrapper<ArticleLike>()
                .eq("user_id", userId)
                .eq("article_id", articleId));
        
        if (existingLike != null) {
            return SaResult.error("您已经点赞过这篇文章");
        }
        
        // 创建点赞记录
        ArticleLike articleLike = new ArticleLike();
        articleLike.setUserId(userId);
        articleLike.setArticleId(articleId);
        this.save(articleLike);
        
        // 更新文章点赞数
        articleMapper.incrementLikeCount(articleId);
        
        // 获取最新的点赞数
        Article article = articleMapper.selectById(articleId);
        LikeResultVO resultVO = new LikeResultVO();
        resultVO.setLikeCount(article.getLikeCount());
        
        return SaResult.data(resultVO).setMsg("点赞成功");
    }
    
    @Override
    @Transactional
    public SaResult unlikeArticle(Long articleId, Long userId) {
        // 检查是否存在点赞记录
        ArticleLike existingLike = this.getOne(new QueryWrapper<ArticleLike>()
                .eq("user_id", userId)
                .eq("article_id", articleId));
        
        if (existingLike == null) {
            return SaResult.error("您还没有点赞过这篇文章");
        }
        
        // 删除点赞记录
        this.remove(new QueryWrapper<ArticleLike>()
                .eq("user_id", userId)
                .eq("article_id", articleId));
        
        // 更新文章点赞数
        articleMapper.decrementLikeCount(articleId);
        
        // 获取最新的点赞数
        Article article = articleMapper.selectById(articleId);
        LikeResultVO resultVO = new LikeResultVO();
        resultVO.setLikeCount(article.getLikeCount());
        
        return SaResult.data(resultVO).setMsg("取消点赞成功");
    }
    
    @Override
    public boolean isArticleLiked(Long articleId, Long userId) {
        ArticleLike existingLike = this.getOne(new QueryWrapper<ArticleLike>()
                .eq("user_id", userId)
                .eq("article_id", articleId));
        return existingLike != null;
    }
}
