package cn.org.chtf.card.manage.system.service;

import cn.org.chtf.card.manage.system.model.SysSurvey;

import java.util.List;
import java.util.Map;

/**
 * 调查信息表Service
 * @author lm
 */
public interface SysSurveyService {

	/**
	 * 查询调查信息表列表
	 */
	List<SysSurvey> list(Map<String, Object> map);

	/**
	 * 通过id查询单个调查信息表
	 */
    SysSurvey findById(Integer id);

	/**
	 * 通过map查询单个调查信息表
	 */
	List<SysSurvey> findByMap(Map<String, Object> map);

	/**
	 * 新增调查信息表
	 */
	int save(SysSurvey sysSurvey);

	/**
	 * 修改调查信息表
	 */
	int update(SysSurvey sysSurvey);

	/**
	 * 删除调查信息表
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);

	SysSurvey findByOneMap(Map<String, Object> map);
}
