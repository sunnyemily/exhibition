package cn.org.chtf.card.manage.article.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.org.chtf.card.manage.article.pojo.Article;
import cn.org.chtf.card.manage.article.pojo.ArticlePageModel;
import cn.org.chtf.card.manage.common.pojo.AlterMenuParameter;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

public interface ArticleService {

	/**
	 * 更新文章，包括标题
	 * @param article 文章实体，根据主键是否为0，判断是插入还是更新
	 * @return
	 */
	public ResultModel updateArticle(Article article);
	/**
	 * 批量删除
	 * @param articleIdList主键列表
	 * @return
	 */
	public ResultModel deleteArticles(Integer[] articleIdList);
	/**
	 * 获取文章列表，分页
	 * @param page 分页及搜索实体
	 * @return
	 */
	public ResultModel getArticles(ArticlePageModel page);
	/**
	 * 获取文章实体
	 * @param articleId 文章主键
	 * @return
	 */
	public ResultModel getArticle(Integer articleId);
	public List<Article> getArticles(Integer menuId,Integer count,HttpServletRequest request);
}
