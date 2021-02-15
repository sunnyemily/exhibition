package cn.org.chtf.card.manage.system.dao;

import cn.org.chtf.card.manage.system.model.SysSurveyResult;

import java.util.List;
import java.util.Map;


/**
 * 调查结果表DAO
 * @author lm
 */
public interface SysSurveyResultMapper {

    /**
     * 通过surveyid查询单个调查结果表
     */
    SysSurveyResult findById(Integer surveyid);

    /**
     * 通过map查询单个调查结果表
     */
    List<SysSurveyResult> findByMap(Map<String, Object> map);

    /**
     * 查询调查结果表列表
     */
    List<SysSurveyResult> list(Map<String, Object> map);

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

	/**
     * 通过map查询单个调查结果表
     */
    int listcount(Map<String, Object> map);

	List<Map<String, Object>> GetQuestionInfoBySurveyID(Map<String, Object> map);

	List<Map<String, Object>> GetAnswerInfoByQuestionID(Map<String, Object> map);

	List<Map<String, Object>> GetAnswerTongJiInfoByQuestionID(Map<String, Object> map);

}
