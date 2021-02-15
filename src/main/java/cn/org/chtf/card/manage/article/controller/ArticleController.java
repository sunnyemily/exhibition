package cn.org.chtf.card.manage.article.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.manage.Exhibitors.service.EbsVehiclecardService;
import cn.org.chtf.card.manage.article.pojo.Article;
import cn.org.chtf.card.manage.article.pojo.ArticlePageModel;
import cn.org.chtf.card.manage.article.service.ArticleService;
import cn.org.chtf.card.manage.common.pojo.AlterMenuParameter;
import cn.org.chtf.card.manage.system.service.SysExhibitionService;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

@RestController
public class ArticleController {

	@Resource(name="ArticleServiceImpl")
	private ArticleService articleService;
	@Autowired
	private SysExhibitionService exhibitionService;
	/**
	 * 更新文章，包括标题
	 * @param article 文章实体，根据主键是否为0，判断是插入还是更新
	 * @return
	 */
	@RequestMapping(value="/manage/article/updateArticle")
	public ResultModel updateArticle(Article article,HttpServletRequest request) {
		String url= CryptographyUtil.GeCurrenttUrl(request);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("url", url);
		map.put("useable", 1);
		Integer exhibitionId = exhibitionService.findByMap(map).getId();
		article.setExhibitionId(exhibitionId);
		return articleService.updateArticle(article);
	}
	/**
	 * 批量删除
	 * @param articleIdList主键列表
	 * @return
	 */
	@RequestMapping(value="/manage/article/deleteArticles")
	public ResultModel deleteArticles(@RequestParam(value = "articleIdList[]") Integer[] articleIdList) {
		return articleService.deleteArticles(articleIdList);
	}
	/**
	 * 获取文章列表，分页
	 * @param page 分页及搜索实体
	 * @return
	 */
	@RequestMapping(value="/manage/article/getArticles/{menuId}")
	public ResultModel getArticles(@PathVariable Integer menuId,ArticlePageModel page,HttpServletRequest request) {
		String url= CryptographyUtil.GeCurrenttUrl(request);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("url", url);
		map.put("useable", 1);
		Integer exhibitionId = exhibitionService.findByMap(map).getId();
		page.setMenuId(menuId);
		page.setExhibitionId(exhibitionId);
		return articleService.getArticles(page);
	}
	/**
	 * 获取文章实体
	 * @param articleId 文章主键
	 * @return
	 */
	@RequestMapping(value="/manage/article/getArticle")
	public ResultModel getArticle(Integer articleId) {
		return articleService.getArticle(articleId);
	}
	/**
	 * 更改信息所属菜单
	 * @param alterMenu 参数实体
	 * @return
	 */
	@RequestMapping(value="/manage/article/alterMenu")
	public ResultModel alterMenu(Integer menuId,@RequestParam(value="idList[]") List<Integer> idList){
		AlterMenuParameter alterMenu = new AlterMenuParameter();
		alterMenu.setMenuId(menuId);
		alterMenu.setIdList(idList);		
		return null;//articleService.alterMenu(alterMenu);
	}
}
