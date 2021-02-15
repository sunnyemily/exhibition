package cn.org.chtf.card.manage.system.service;

import java.util.List;
import java.util.Map;

/**
 * 预约取证名额管理Service
 * @author guo
 *
 */
public interface SysReservationFrequencyService {
	/**
	 * 查询预约取证次数详细列表
	 */
	List<Map<String, Object>> list(Map<String, Object> map);
	
	/**
	 * 删除预约取证次数详情
	 */
	int deleteById(int id);
	
	/**
	 * 新增预约取证次数详情
	 */
	int saveData(Map<String, Object> map);
	
	/**
	 * 更新预约取证次数详情
	 */
	int updateData(Map<String, Object> map);
	
	/**
	 * 根据ID查询单条预约取证次数详情
	 */
	Map<String, Object> findById(Map<String, Object> map);
	
	/**
	 * 查询记录数
	 */
	int listcount(Map<String, Object> map);
	/**
	 * 判断预约时间是否重复
	 * @param map
	 * @return
	 */
	int selectTime(Map<String, Object> map);
}
