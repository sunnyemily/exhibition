package cn.org.chtf.card.manage.online.service;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.online.model.OnlineReservationDetails;

public interface ReservationErhaltenZertifikatService {
	/**
	 * 查询线上预约取证列表
	 */
	List<Map<String, Object>> list(Map<String, Object> map);
	
	/**
	 * 查询预约取证信息总数
	 */
	int listcount(Map<String, Object> map);
	
	/**
	 * 删除数据
	 */
	int deleteById(int id);
	
	/**
	 * 查询线上预约信息列表
	 */
	Map<String, Object> StatisticsInfo(Map<String, Object> map);
	/**
	 * 按时间段查询线上预约信息列表
	 */
	int total(Map<String, Object> map);
	
}
