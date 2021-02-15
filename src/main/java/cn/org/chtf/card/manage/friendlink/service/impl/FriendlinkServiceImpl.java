package cn.org.chtf.card.manage.friendlink.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.common.pojo.AlterMenuParameter;
import cn.org.chtf.card.manage.file.service.FileService;
import cn.org.chtf.card.manage.friendlink.dao.FriendlinkMapper;
import cn.org.chtf.card.manage.friendlink.pojo.Friendlink;
import cn.org.chtf.card.manage.friendlink.service.FriendlinkService;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

@Service("FriendlinkServiceImpl")
public class FriendlinkServiceImpl implements FriendlinkService {
	@Autowired
	private FriendlinkMapper friendlinkDAO;
	
	@Autowired
	private FileService fileService;

	public ResultModel updateFriendlink(Friendlink friendlink) {
		ResultModel result = null;
		try {
			if(friendlink.getFriendlinkId()==0) {
				friendlink.setFriendlinkId(null);
				friendlinkDAO.insertSelective(friendlink);
			}
			else {
				friendlinkDAO.updateByPrimaryKey(friendlink);
			}
			if(friendlink.getMenuId()==265 || friendlink.getMenuId()==264){//活动直播回放				
				fileService.uploadTencent(friendlink.getFriendlinkId(),"web_friendlink","friendlink_id",friendlink.getFriendlinkPicture2());
			}
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		}
		return result;
	}
	public ResultModel deleteFriendlinks(Integer[] friendlinkIdList) {
		ResultModel result = null;
		if(friendlinkIdList.length==0) {//验证传入数组的长度
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,"没有传入要删除的链接");
			return result;
		}
		List<Integer> idList = Arrays.asList(friendlinkIdList);
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);

			friendlinkDAO.deleteFriendlinks(idList);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		}
		
		return result;
	}
	public ResultModel getFriendlinks(PageModel page) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(friendlinkDAO.getFriendlinks(page));;
			result.setCount(friendlinkDAO.getTotal(page));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	public List<Friendlink> getBanner(Integer menuId){
		return friendlinkDAO.getBanner(menuId);
	}
	public List<Friendlink> getCNHomeLink(){
		return friendlinkDAO.getHomeLink(6);
	}
	public List<Friendlink> getENHomeLink(){
		return friendlinkDAO.getHomeLink(76);
	}
	public List<Friendlink> getJPHomeLink(){
		return friendlinkDAO.getHomeLink(163);
	}
	
    /**
     * 更改信息所属栏目
     * @param alterMenu
     * @return
     */
	public ResultModel alterMenu(AlterMenuParameter alterMenu) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(friendlinkDAO.alterMenu(alterMenu));;
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	@Override
	public ResultModel getFriendlink(Integer friendlinkId) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(friendlinkDAO.selectByPrimaryKey(friendlinkId));;
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public Map<String, Object> GetFirendLink(Integer fid) {
		return friendlinkDAO.GetFirendLink(fid);
	}
}
