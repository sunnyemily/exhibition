package cn.org.chtf.card.manage.Volunteer.service;

import cn.org.chtf.card.manage.Volunteer.model.VolVolunteerattendance;

import java.util.List;
import java.util.Map;

/**
 * 志愿者考勤Service
 * @author lm
 */
public interface VolVolunteerattendanceService {

	/**
	 * 查询志愿者考勤列表
	 */
	List<VolVolunteerattendance> list(Map<String, Object> map);

	/**
	 * 通过id查询单个志愿者考勤
	 */
    VolVolunteerattendance findById(Integer id);

	/**
	 * 通过map查询单个志愿者考勤
	 */
	List<VolVolunteerattendance> findByMap(Map<String, Object> map);

	/**
	 * 新增志愿者考勤
	 */
	int save(VolVolunteerattendance volVolunteerattendance);

	/**
	 * 修改志愿者考勤
	 */
	int update(VolVolunteerattendance volVolunteerattendance);

	/**
	 * 删除志愿者考勤
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);
}
