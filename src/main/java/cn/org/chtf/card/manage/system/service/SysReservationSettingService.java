package cn.org.chtf.card.manage.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.org.chtf.card.manage.system.model.SysReservationSetting;

/**
 * 线上预约详细Service
 * @author lm
 */
public interface SysReservationSettingService {

	/**
	 * 查询线上预约详细列表
	 */
	List<SysReservationSetting> list(Map<String, Object> map);

	/**
	 * 通过id查询单个线上预约详细
	 */
    SysReservationSetting findById(Integer id);

	/**
	 * 通过map查询单个线上预约详细
	 */
	List<SysReservationSetting> findByMap(Map<String, Object> map);

	/**
	 * 新增线上预约详细
	 */
	int save(SysReservationSetting sysReservationSetting);

	/**
	 * 修改线上预约详细
	 */
	int update(SysReservationSetting sysReservationSetting);

	/**
	 * 删除线上预约详细
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);

	List<Map<String, Object>> SearchByDateTimeList(Map<String, Object> map);

	int SearchByDateTimeListCount(Map<String, Object> map);
	/**
	 * 验证当前是否可以预约指定届次指定日期的门票
	 * @param map
	 * @return
	 */
	int canReserve(@Param("sessionId") Integer sessionId,@Param("exhibitiondate") String exhibitiondate);

	

}
