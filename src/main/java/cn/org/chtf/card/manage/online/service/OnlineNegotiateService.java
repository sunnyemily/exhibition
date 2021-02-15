package cn.org.chtf.card.manage.online.service;

import cn.org.chtf.card.manage.online.model.OnlineNegotiate;

import java.util.List;
import java.util.Map;

/**
 * 预约洽谈Service
 * @author lm
 */
public interface OnlineNegotiateService {

	/**
	 * 查询预约洽谈列表
	 */
	List<OnlineNegotiate> list(Map<String, Object> map);

	/**
	 * 通过id查询单个预约洽谈
	 */
    OnlineNegotiate findById(Integer id);

	/**
	 * 通过map查询单个预约洽谈
	 */
	List<OnlineNegotiate> findByMap(Map<String, Object> map);

	/**
	 * 新增预约洽谈
	 */
	int save(OnlineNegotiate onlineNegotiate);

	/**
	 * 修改预约洽谈
	 */
	int update(OnlineNegotiate onlineNegotiate);

	/**
	 * 删除预约洽谈
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);
}
