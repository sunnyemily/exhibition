package cn.org.chtf.card.manage.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.org.chtf.card.manage.online.model.ReservationModel;
import cn.org.chtf.card.manage.system.model.SysReservationSetting;


/**
 * 线上预约详细DAO
 * @author lm
 */
public interface SysReservationSettingMapper {

    /**
     * 通过id查询单个线上预约详细
     */
    SysReservationSetting findById(Integer id);

    /**
     * 通过map查询单个线上预约详细
     */
    List<SysReservationSetting> findByMap(Map<String, Object> map);

    /**
     * 查询线上预约详细列表
     */
    List<SysReservationSetting> list(Map<String, Object> map);

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

	/**
     * 通过map查询单个线上预约详细
     */
    int listcount(Map<String, Object> map);

	List<Map<String, Object>> SearchByDateTimeList(Map<String, Object> map);

	int SearchByDateTimeListCount(Map<String, Object> map);

	int canReserve(@Param("sessionId") Integer sessionId, @Param("exhibitiondate") String exhibitiondate);	

}
