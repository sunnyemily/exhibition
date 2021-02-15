package cn.org.chtf.card.manage.system.service.impl;

import cn.org.chtf.card.manage.system.model.SysSurveyLog;
import cn.org.chtf.card.manage.system.service.SysSurveyLogService;
import cn.org.chtf.card.manage.system.dao.SysSurveyLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 参与调查的日志ServiceImpl
 * @author lm
 */
@Service
public class SysSurveyLogServiceImpl implements SysSurveyLogService {

    @Autowired
    private SysSurveyLogMapper sysSurveyLogDao;

    /**
     * 查询参与调查的日志列表
     */
    @Override
    public List<SysSurveyLog> list(Map<String, Object> map) {
        return sysSurveyLogDao.list(map);
    }
    
    /**
     * 数量参与调查的日志
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return sysSurveyLogDao.listcount(map);
    }


    /**
     * 通过id查询单个参与调查的日志
     */
    @Override
    public SysSurveyLog findById(Integer id) {
        return sysSurveyLogDao.findById(id);
    }

    /**
     * 通过map查询单个参与调查的日志
     */
    @Override
    public List<SysSurveyLog> findByMap(Map<String, Object> map) {
        return sysSurveyLogDao.findByMap(map);
    }

    /**
     * 新增参与调查的日志
     */
    @Override
    public int save(SysSurveyLog sysSurveyLog) {
        return sysSurveyLogDao.save(sysSurveyLog);
    }

    /**
     * 修改参与调查的日志
     */
    @Override
    public int update(SysSurveyLog sysSurveyLog) {
        return sysSurveyLogDao.update(sysSurveyLog);
    }

    /**
     * 删除参与调查的日志
     */
    @Override
    public int deleteById(Integer id) {
        return sysSurveyLogDao.deleteById(id);
    }

}
