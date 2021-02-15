package cn.org.chtf.card.manage.online.service;

import cn.org.chtf.card.manage.online.model.OnlineApplyMeeting;

import java.util.List;
import java.util.Map;

/**
 * Service
 * @author lm
 */
public interface OnlineApplyMeetingService {

	/**
	 * 查询列表
	 */
	List<OnlineApplyMeeting> list(Map<String, Object> map);

	/**
	 * 通过id查询单个
	 */
    OnlineApplyMeeting findById(Integer id);

	/**
	 * 通过map查询单个
	 */
	List<OnlineApplyMeeting> findByMap(Map<String, Object> map);

	/**
	 * 新增
	 */
	int save(OnlineApplyMeeting onlineApplyMeeting);

	/**
	 * 修改
	 */
	int update(OnlineApplyMeeting onlineApplyMeeting);

	/**
	 * 删除
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);

	void addmeetingdetails(List<Map<String, Object>> list);

	void deleteMeetingdetailsID(Integer id);
}
