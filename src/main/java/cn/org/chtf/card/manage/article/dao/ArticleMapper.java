package cn.org.chtf.card.manage.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.org.chtf.card.manage.article.pojo.Article;
import cn.org.chtf.card.manage.common.pojo.AlterMenuParameter;
import cn.org.chtf.card.support.util.PageModel;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer articleId);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);
    
    List<Article> getArticles(PageModel page);
    
    int getTotal(PageModel page);
    
    int deleteArticles(@Param("articleIdList") List<Integer> articleIdList);
    
    int alterMenu(AlterMenuParameter alterMenu);

    List<Article> getHomeArticles(PageModel page);
}