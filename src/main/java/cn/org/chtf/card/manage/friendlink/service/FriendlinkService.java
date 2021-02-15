package cn.org.chtf.card.manage.friendlink.service;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.common.pojo.AlterMenuParameter;
import cn.org.chtf.card.manage.friendlink.pojo.Friendlink;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

public interface FriendlinkService {
	public ResultModel updateFriendlink(Friendlink friendlink);
	public ResultModel deleteFriendlinks(Integer[] friendlinkIdList);
	public ResultModel getFriendlinks(PageModel page);
	public List<Friendlink> getBanner(Integer menuId);
	public List<Friendlink> getCNHomeLink();
	public List<Friendlink> getENHomeLink();
	public List<Friendlink> getJPHomeLink();
	/**
	 * 更改信息所属菜单
	 * @param alterMenu
	 * @return
	 */
	public ResultModel alterMenu(AlterMenuParameter alterMenu);
	public ResultModel getFriendlink(Integer friendlinkId);
	public Map<String, Object> GetFirendLink(Integer fid);
}
