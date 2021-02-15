package cn.org.chtf.card.manage.system.service.impl;

import cn.org.chtf.card.manage.system.model.SysIndustry;
import cn.org.chtf.card.manage.system.service.SysIndustryService;
import cn.org.chtf.card.manage.system.dao.SysIndustryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 行业表ServiceImpl
 * @author ggwudivs
 */
@Service
public class SysIndustryServiceImpl implements SysIndustryService {

    @Autowired
    private SysIndustryMapper sysIndustryDao;

    /**
     * 查询行业表列表
     */
    @Override
    public List<SysIndustry> list(Map<String, Object> map) {
        return sysIndustryDao.list(map);
    }
    
    /**
     * 数量行业表
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return sysIndustryDao.listcount(map);
    }


    /**
     * 通过id查询单个行业表
     */
    @Override
    public SysIndustry findById(Integer id) {
        return sysIndustryDao.findById(id);
    }

    /**
     * 通过map查询单个行业表
     */
    @Override
    public SysIndustry findByMap(Map<String, Object> map) {
        return sysIndustryDao.findByMap(map);
    }

    /**
     * 新增行业表
     */
    @Override
    public int save(SysIndustry sysIndustry) {
        return sysIndustryDao.save(sysIndustry);
    }

    /**
     * 修改行业表
     */
    @Override
    public int update(SysIndustry sysIndustry) {
        return sysIndustryDao.update(sysIndustry);
    }

    /**
     * 删除行业表
     */
    @Override
    public int deleteById(Integer id) {
        return sysIndustryDao.deleteById(id);
    }

}
