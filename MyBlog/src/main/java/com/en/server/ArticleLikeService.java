package com.en.server;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.en.entity.ArticleLike;

/**
* @author 17764
* @description 针对表【article_like】的数据库操作Service
* @createDate 2025-10-16 16:27:29
*/

public interface ArticleLikeService extends IService<ArticleLike> {
    SaResult likeArticle(Long articleId, Long userId);
    SaResult unlikeArticle(Long articleId, Long userId);
    boolean isArticleLiked(Long articleId, Long userId);
}
