package com.en.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.en.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
* @author 17764
* @description 针对表【article】的数据库操作Mapper
* @createDate 2025-10-16 16:27:29
* @Entity generator.domain.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    @Update("UPDATE article SET like_count = like_count + 1 WHERE id = #{articleId}")
    void incrementLikeCount(@Param("articleId") Long articleId);

    @Update("UPDATE article SET like_count = like_count - 1 WHERE id = #{articleId}")
    void decrementLikeCount(@Param("articleId") Long articleId);

    @Update("UPDATE article SET view_count = view_count + 1 WHERE id = #{articleId}")
    void incrementViewCount(@Param("articleId") Long articleId);

}




