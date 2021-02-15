package cn.org.chtf.card.manage.system.service;

import cn.org.chtf.card.manage.system.model.SysSurveyQuestion;

import java.util.List;
import java.util.Map;

/**
 * 调查涉及问题表Service
 * @author lm
 */
public interface SysSurveyQuestionService {

	/**
	 * 查询调查涉及问题表列表
	 */
	List<SysSurveyQuestion> list(Map<String, Object> map);

	/**
	 * 通过id查询单个调查涉及问题表
	 */
    SysSurveyQuestion findById(Integer id);

	/**
	 * 通过map查询单个调查涉及问题表
	 */
	List<SysSurveyQuestion> findByMap(Map<String, Object> map);

	/**
	 * 新增调查涉及问题表
	 */
	int save(SysSurveyQuestion sysSurveyQuestion);

	/**
	 * 修改调查涉及问题表
	 */
	int update(SysSurveyQuestion sysSurveyQuestion);

	/**
	 * 删除调查涉及问题表
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);
}
