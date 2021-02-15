package cn.org.chtf.card.manage.system.service.impl;

import cn.org.chtf.card.manage.system.model.SysSurveyAnswer;
import cn.org.chtf.card.manage.system.service.SysSurveyAnswerService;
import cn.org.chtf.card.manage.system.dao.SysSurveyAnswerMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 调查涉及答案表ServiceImpl
 * @author lm
 */
@Service
public class SysSurveyAnswerServiceImpl implements SysSurveyAnswerService {

    @Autowired
    private SysSurveyAnswerMapper sysSurveyAnswerDao;

    /**
     * 查询调查涉及答案表列表
     */
    @Override
    public List<SysSurveyAnswer> list(Map<String, Object> map) {
        return sysSurveyAnswerDao.list(map);
    }
    
    /**
     * 数量调查涉及答案表
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return sysSurveyAnswerDao.listcount(map);
    }


    /**
     * 通过id查询单个调查涉及答案表
     */
    @Override
    public SysSurveyAnswer findById(Integer id) {
        return sysSurveyAnswerDao.findById(id);
    }

    /**
     * 通过map查询单个调查涉及答案表
     */
    @Override
    public List<SysSurveyAnswer> findByMap(Map<String, Object> map) {
        return sysSurveyAnswerDao.findByMap(map);
    }

    /**
     * 新增调查涉及答案表
     */
    @Override
    public int save(SysSurveyAnswer sysSurveyAnswer) {
        return sysSurveyAnswerDao.save(sysSurveyAnswer);
    }

    /**
     * 修改调查涉及答案表
     */
    @Override
    public int update(SysSurveyAnswer sysSurveyAnswer) {
        return sysSurveyAnswerDao.update(sysSurveyAnswer);
    }

    /**
     * 删除调查涉及答案表
     */
    @Override
    public int deleteById(Integer id) {
        return sysSurveyAnswerDao.deleteById(id);
    }

	@Override
	public void deleteByQuestionId(Integer id) {
		sysSurveyAnswerDao.deleteByQuestionId(id);
	}

}
