package cn.org.chtf.card.manage.system.service.impl;

import cn.org.chtf.card.manage.system.model.SysCompanyType;
import cn.org.chtf.card.manage.system.service.SysCompanyTypeService;
import cn.org.chtf.card.manage.system.dao.SysCompanyTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 参展企业性质表ServiceImpl
 * @author ggwudivs
 */
@Service
public class SysCompanyTypeServiceImpl implements SysCompanyTypeService {

    @Autowired
    private SysCompanyTypeMapper sysCompanyTypeDao;

    /**
     * 查询参展企业性质表列表
     */
    @Override
    public List<SysCompanyType> list(Map<String, Object> map) {
        return sysCompanyTypeDao.list(map);
    }
    
    /**
     * 数量参展企业性质表
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return sysCompanyTypeDao.listcount(map);
    }


    /**
     * 通过id查询单个参展企业性质表
     */
    @Override
    public SysCompanyType findById(Integer id) {
        return sysCompanyTypeDao.findById(id);
    }

    /**
     * 通过map查询单个参展企业性质表
     */
    @Override
    public SysCompanyType findByMap(Map<String, Object> map) {
        return sysCompanyTypeDao.findByMap(map);
    }

    /**
     * 新增参展企业性质表
     */
    @Override
    public int save(SysCompanyType sysCompanyType) {
        return sysCompanyTypeDao.save(sysCompanyType);
    }

    /**
     * 修改参展企业性质表
     */
    @Override
    public int update(SysCompanyType sysCompanyType) {
        return sysCompanyTypeDao.update(sysCompanyType);
    }

    /**
     * 删除参展企业性质表
     */
    @Override
    public int deleteById(Integer id) {
        return sysCompanyTypeDao.deleteById(id);
    }

}
