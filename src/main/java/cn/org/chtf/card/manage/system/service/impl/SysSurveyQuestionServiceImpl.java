package cn.org.chtf.card.manage.system.service.impl;

import cn.org.chtf.card.manage.system.model.SysSurveyQuestion;
import cn.org.chtf.card.manage.system.service.SysSurveyQuestionService;
import cn.org.chtf.card.manage.system.dao.SysSurveyQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 调查涉及问题表ServiceImpl
 * @author lm
 */
@Service
public class SysSurveyQuestionServiceImpl implements SysSurveyQuestionService {

    @Autowired
    private SysSurveyQuestionMapper sysSurveyQuestionDao;

    /**
     * 查询调查涉及问题表列表
     */
    @Override
    public List<SysSurveyQuestion> list(Map<String, Object> map) {
        return sysSurveyQuestionDao.list(map);
    }
    
    /**
     * 数量调查涉及问题表
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return sysSurveyQuestionDao.listcount(map);
    }


    /**
     * 通过id查询单个调查涉及问题表
     */
    @Override
    public SysSurveyQuestion findById(Integer id) {
        return sysSurveyQuestionDao.findById(id);
    }

    /**
     * 通过map查询单个调查涉及问题表
     */
    @Override
    public List<SysSurveyQuestion> findByMap(Map<String, Object> map) {
        return sysSurveyQuestionDao.findByMap(map);
    }

    /**
     * 新增调查涉及问题表
     */
    @Override
    public int save(SysSurveyQuestion sysSurveyQuestion) {
        return sysSurveyQuestionDao.save(sysSurveyQuestion);
    }

    /**
     * 修改调查涉及问题表
     */
    @Override
    public int update(SysSurveyQuestion sysSurveyQuestion) {
        return sysSurveyQuestionDao.update(sysSurveyQuestion);
    }

    /**
     * 删除调查涉及问题表
     */
    @Override
    public int deleteById(Integer id) {
        return sysSurveyQuestionDao.deleteById(id);
    }

}
