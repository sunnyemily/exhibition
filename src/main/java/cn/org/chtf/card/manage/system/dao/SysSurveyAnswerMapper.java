package cn.org.chtf.card.manage.system.dao;

import cn.org.chtf.card.manage.system.model.SysSurveyAnswer;

import java.util.List;
import java.util.Map;


/**
 * 调查涉及答案表DAO
 * @author lm
 */
public interface SysSurveyAnswerMapper {

    /**
     * 通过id查询单个调查涉及答案表
     */
    SysSurveyAnswer findById(Integer id);

    /**
     * 通过map查询单个调查涉及答案表
     */
    List<SysSurveyAnswer> findByMap(Map<String, Object> map);

    /**
     * 查询调查涉及答案表列表
     */
    List<SysSurveyAnswer> list(Map<String, Object> map);

    /**
     * 新增调查涉及答案表
     */
    int save(SysSurveyAnswer sysSurveyAnswer);

    /**
     * 修改调查涉及答案表
     */
    int update(SysSurveyAnswer sysSurveyAnswer);

    /**
     * 删除调查涉及答案表
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个调查涉及答案表
     */
    int listcount(Map<String, Object> map);

	void deleteByQuestionId(Integer id);

}
