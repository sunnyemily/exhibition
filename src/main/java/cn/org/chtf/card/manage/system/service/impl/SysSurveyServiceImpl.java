package cn.org.chtf.card.manage.system.service.impl;

import cn.org.chtf.card.manage.system.model.SysSurvey;
import cn.org.chtf.card.manage.system.service.SysSurveyService;
import cn.org.chtf.card.manage.system.dao.SysSurveyMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 调查信息表ServiceImpl
 * @author lm
 */
@Service
public class SysSurveyServiceImpl implements SysSurveyService {

    @Autowired
    private SysSurveyMapper sysSurveyDao;

    /**
     * 查询调查信息表列表
     */
    @Override
    public List<SysSurvey> list(Map<String, Object> map) {
        return sysSurveyDao.list(map);
    }
    
    /**
     * 数量调查信息表
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return sysSurveyDao.listcount(map);
    }


    /**
     * 通过id查询单个调查信息表
     */
    @Override
    public SysSurvey findById(Integer id) {
        return sysSurveyDao.findById(id);
    }

    /**
     * 通过map查询单个调查信息表
     */
    @Override
    public List<SysSurvey> findByMap(Map<String, Object> map) {
        return sysSurveyDao.findByMap(map);
    }

    /**
     * 新增调查信息表
     */
    @Override
    public int save(SysSurvey sysSurvey) {
        return sysSurveyDao.save(sysSurvey);
    }

    /**
     * 修改调查信息表
     */
    @Override
    public int update(SysSurvey sysSurvey) {
        return sysSurveyDao.update(sysSurvey);
    }

    /**
     * 删除调查信息表
     */
    @Override
    public int deleteById(Integer id) {
        return sysSurveyDao.deleteById(id);
    }

	@Override
	public SysSurvey findByOneMap(Map<String, Object> map) {
		return sysSurveyDao.findByOneMap(map);
	}

}
