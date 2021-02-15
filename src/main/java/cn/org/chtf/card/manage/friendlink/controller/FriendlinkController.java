package cn.org.chtf.card.manage.friendlink.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.manage.common.pojo.AlterMenuParameter;
import cn.org.chtf.card.manage.friendlink.pojo.Friendlink;
import cn.org.chtf.card.manage.friendlink.service.FriendlinkService;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

@RestController
public class FriendlinkController {
	@Resource(name="FriendlinkServiceImpl")
	private FriendlinkService firendlinkService;

	/**
	 * 更新链接
	 * @param Friendlink 链接实体，根据主键是否为0，判断是插入还是更新
	 * @return
	 */
	@RequestMapping(value="/manage/friendlink/updateFriendlink")
	public ResultModel updateArticle(Friendlink friendlink) {
		return firendlinkService.updateFriendlink(friendlink);
	}
	/**
	 * 批量删除
	 * @param articleIdList主键列表
	 * @return
	 */
	@RequestMapping(value="/manage/friendlink/deleteFriendlinks")
	public ResultModel deleteArticles(@RequestParam(value = "friendlinkIdList[]") Integer[] friendlinkIdList) {
		return firendlinkService.deleteFriendlinks(friendlinkIdList);
	}
	/**
	 * 获取文章列表，分页
	 * @param page 分页及搜索实体
	 * @return
	 */
	@RequestMapping(value="/manage/friendlink/getFriendlinks/{menuId}")
	public ResultModel getArticles(@PathVariable Integer menuId,PageModel page) {
		page.setMenuId(menuId);
		return firendlinkService.getFriendlinks(page);
	}
	
	@RequestMapping(value="/manage/friendlink/getFriendlink")
	public ResultModel getFriendlink(Integer friendlinkId) {
		return firendlinkService.getFriendlink(friendlinkId);
	}
	
	/**
	 * 更改信息所属菜单
	 * @param alterMenu 参数实体
	 * @return
	 */
	@RequestMapping(value="/manage/friendlink/alterMenu")
	public ResultModel alterMenu(Integer menuId,@RequestParam(value="idList[]") List<Integer> idList){
		AlterMenuParameter alterMenu = new AlterMenuParameter();
		alterMenu.setMenuId(menuId);
		alterMenu.setIdList(idList);		
		return firendlinkService.alterMenu(alterMenu);
	}
}
