package cn.org.chtf.card.manage.system.service;

import cn.org.chtf.card.manage.system.model.SysSurveyResult;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 调查结果表Service
 * @author lm
 */
public interface SysSurveyResultService {

	/**
	 * 查询调查结果表列表
	 */
	List<SysSurveyResult> list(Map<String, Object> map);

	/**
	 * 通过surveyid查询单个调查结果表
	 */
    SysSurveyResult findById(Integer surveyid);

	/**
	 * 通过map查询单个调查结果表
	 */
	List<SysSurveyResult> findByMap(Map<String, Object> map);

	/**
	 * 新增调查结果表
	 */
	int save(SysSurveyResult sysSurveyResult);

	/**
	 * 修改调查结果表
	 */
	int update(SysSurveyResult sysSurveyResult);

	/**
	 * 删除调查结果表
	 */
	int deleteById(Integer surveyid);
	
	int listcount(Map<String, Object> map);

	List<Map<String, Object>> GetQuestionInfoBySurveyID(Map<String, Object> map);

	List<Map<String, Object>> GetAnswerInfoByQuestionID(Map<String, Object> map);

	void saveSurveyAnswer(Map<String, Object> map, HttpSession session);

	List<Map<String, Object>> GetAnswerTongJiInfoByQuestionID(Map<String, Object> map);
}
