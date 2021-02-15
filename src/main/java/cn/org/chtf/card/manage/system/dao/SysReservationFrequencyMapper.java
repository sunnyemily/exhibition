package cn.org.chtf.card.manage.system.dao;

import java.util.List;
import java.util.Map;

public interface SysReservationFrequencyMapper {
	/**
	 * 查询线上预约详细列表
	 */
	List<Map<String, Object>> list(Map<String, Object> map);
	/**
	 * 查询线上预约详细列表
	 */
	int deleteById(int id);
	/**
	 * 新增预约取证详情
	 */
	int saveData(Map<String, Object> map);
	
	/**
	 * 更新预约取证详情
	 */
	int updateData(Map<String, Object> map);
	
	/**
	 * 根据ID查询单条预约取证详情
	 */
	Map<String, Object> findById(Map<String, Object> map);
	
	/**
	 * 查询记录数
	 */
	int listcount(Map<String, Object> map);
	/**
	 * 判断预约时间是否重复
	 */
	int selectTime(Map<String, Object> map);
}
