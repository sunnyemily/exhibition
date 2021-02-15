package cn.org.chtf.card.manage.system.service;

import cn.org.chtf.card.manage.system.model.SysSurveyLog;

import java.util.List;
import java.util.Map;

/**
 * 参与调查的日志Service
 * @author lm
 */
public interface SysSurveyLogService {

	/**
	 * 查询参与调查的日志列表
	 */
	List<SysSurveyLog> list(Map<String, Object> map);

	/**
	 * 通过id查询单个参与调查的日志
	 */
    SysSurveyLog findById(Integer id);

	/**
	 * 通过map查询单个参与调查的日志
	 */
	List<SysSurveyLog> findByMap(Map<String, Object> map);

	/**
	 * 新增参与调查的日志
	 */
	int save(SysSurveyLog sysSurveyLog);

	/**
	 * 修改参与调查的日志
	 */
	int update(SysSurveyLog sysSurveyLog);

	/**
	 * 删除参与调查的日志
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);
}
