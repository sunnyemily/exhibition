package cn.org.chtf.card.manage.system.service.impl;

import cn.org.chtf.card.manage.system.model.SystemDictionaries;
import cn.org.chtf.card.manage.system.service.SystemDictionariesService;
import cn.org.chtf.card.manage.system.dao.SystemDictionariesMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 字典表ServiceImpl
 * @author ggwudivs
 */
@Service
public class SystemDictionariesServiceImpl implements SystemDictionariesService {

    @Autowired
    private SystemDictionariesMapper systemDictionariesDao;

    /**
     * 查询字典表列表
     */
    @Override
    public List<SystemDictionaries> list(Map<String, Object> map) {
        return systemDictionariesDao.list(map);
    }
    
    /**
     * 数量字典表
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return systemDictionariesDao.listcount(map);
    }


    /**
     * 通过dicid查询单个字典表
     */
    @Override
    public SystemDictionaries findById(Integer dicid) {
        return systemDictionariesDao.findById(dicid);
    }

    /**
     * 通过map查询单个字典表
     */
    @Override
    public List<SystemDictionaries> findByMap(Map<String, Object> map) {
        return systemDictionariesDao.findByMap(map);
    }

    /**
     * 新增字典表
     */
    @Override
    public int save(SystemDictionaries systemDictionaries) {
        return systemDictionariesDao.save(systemDictionaries);
    }

    /**
     * 修改字典表
     */
    @Override
    public int update(SystemDictionaries systemDictionaries) {
        return systemDictionariesDao.update(systemDictionaries);
    }

    /**
     * 删除字典表
     */
    @Override
    public int deleteById(Integer dicid) {
        return systemDictionariesDao.deleteById(dicid);
    }

	@Override
	public List<SystemDictionaries> getList(Map<String, Object> map) {
		return systemDictionariesDao.getList(map);
	}

}
