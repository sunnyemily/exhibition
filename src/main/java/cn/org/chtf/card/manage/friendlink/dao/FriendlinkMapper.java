package cn.org.chtf.card.manage.friendlink.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.org.chtf.card.manage.common.pojo.AlterMenuParameter;
import cn.org.chtf.card.manage.friendlink.pojo.Friendlink;
import cn.org.chtf.card.support.util.PageModel;

public interface FriendlinkMapper {
    int deleteByPrimaryKey(Integer friendlinkId);

    int insert(Friendlink record);

    int insertSelective(Friendlink record);

    Friendlink selectByPrimaryKey(Integer friendlinkId);

    int updateByPrimaryKeySelective(Friendlink record);

    int updateByPrimaryKey(Friendlink record);
    
    List<Friendlink> getFriendlinks(PageModel page);
    
    int getTotal(PageModel page);
    
    int deleteFriendlinks(@Param("friendlinkIdList") List<Integer> friendlinkIdList);
    
	public List<Friendlink> getHomeLink(Integer menuId);
    
	public List<Friendlink> getBanner(Integer menuId);
    
    int alterMenu(AlterMenuParameter alterMenu);

	Map<String, Object> GetFirendLink(Integer fid);
}