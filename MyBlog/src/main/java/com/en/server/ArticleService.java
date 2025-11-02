package com.en.server;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.en.entity.Article;
import com.en.entity.dto.ArticleCreateDTO;
import com.en.entity.dto.ArticleUpdateDTO;

/**
* @author 17764
* @description 针对表【article】的数据库操作Service
* @createDate 2025-10-16 16:27:29
*/
public interface ArticleService extends IService<Article> {
    SaResult getArticleList(Integer page, Integer size);
    SaResult getArticleDetail(Long id, Long userId);
    SaResult createArticle(ArticleCreateDTO createDTO, Long userId);
    SaResult updateArticle(Long id, ArticleUpdateDTO updateDTO, Long userId);
    SaResult deleteArticle(Long id, Long userId);
}
