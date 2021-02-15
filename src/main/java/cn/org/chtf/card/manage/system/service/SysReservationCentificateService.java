package cn.org.chtf.card.manage.system.service;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.system.model.SysReservationCentificate;

/**
 * 预约取证名额管理Service
 * @author guo
 *
 */
public interface SysReservationCentificateService {
	
	/**
	 * 查询预约取证详细列表
	 */
	List<Map<String, Object>> list(Map<String, Object> map);
	
	/**
	 * 删除预约取证详情
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
	 * 查询时间短是否重复
	 */
	int selectDate(Map<String, Object> map);
}
