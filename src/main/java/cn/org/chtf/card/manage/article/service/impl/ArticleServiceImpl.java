package cn.org.chtf.card.manage.article.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.article.dao.ArticleMapper;
import cn.org.chtf.card.manage.article.pojo.Article;
import cn.org.chtf.card.manage.article.pojo.ArticlePageModel;
import cn.org.chtf.card.manage.article.service.ArticleService;
import cn.org.chtf.card.manage.common.pojo.AlterMenuParameter;
import cn.org.chtf.card.manage.system.service.SysExhibitionService;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

@Service("ArticleServiceImpl")

public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleDAO;
	@Autowired
	private SysExhibitionService exhibitionService;
	public ResultModel updateArticle(Article article) {
		ResultModel result = null;
		try {
			if(article.getArticleId()==0) {
				articleDAO.insertSelective(article);
			}
			else {
				articleDAO.updateByPrimaryKeySelective(article);
			}
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		}
		return result;
	}
	public ResultModel deleteArticles(Integer[] articleIdList) {
		ResultModel result = null;
		if(articleIdList.length==0) {//验证传入数组的长度
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,"没有传入要删除的文章");
			return result;
		}
		List<Integer> idList = Arrays.asList(articleIdList);
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);

			articleDAO.deleteArticles(idList);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		}
		
		return result;
	}
	public ResultModel getArticles(ArticlePageModel page) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(articleDAO.getArticles(page));;
			result.setCount(articleDAO.getTotal(page));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	public ResultModel getArticle(Integer articleId) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(articleDAO.selectByPrimaryKey(articleId));;
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	/**
	 * 首页培训课程
	 * @return
	 */
	public List<Article> getArticles(Integer menuId,Integer count,HttpServletRequest request){
		ArticlePageModel page = new ArticlePageModel();
		page.setMenuId(menuId);
		page.setLimit(count);
		String url= CryptographyUtil.GeCurrenttUrl(request);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("url", url);
		map.put("useable", 1);
		Integer exhibitionId = exhibitionService.findByMap(map).getId();
		page.setExhibitionId(exhibitionId);
		List<Article> allArticle = articleDAO.getHomeArticles(page);
		return allArticle;
	}
}
