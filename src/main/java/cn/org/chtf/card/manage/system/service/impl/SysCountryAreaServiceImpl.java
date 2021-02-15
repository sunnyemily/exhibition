package cn.org.chtf.card.manage.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.system.dao.SysCountryAreaMapper;
import cn.org.chtf.card.manage.system.model.SysCountryArea;
import cn.org.chtf.card.manage.system.service.SysCountryAreaService;

/**
 * 国家地区表ServiceImpl
 * @author ggwudivs
 */
@Service
public class SysCountryAreaServiceImpl implements SysCountryAreaService {

    @Autowired
    private SysCountryAreaMapper sysCountryAreaDao;

    /**
     * 查询国家地区表列表
     */
    @Override
    public List<SysCountryArea> list(Map<String, Object> map) {
        return sysCountryAreaDao.list(map);
    }
    
    /**
     * 数量国家地区表
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return sysCountryAreaDao.listcount(map);
    }


    /**
     * 通过id查询单个国家地区表
     */
    @Override
    public SysCountryArea findById(String id) {
        return sysCountryAreaDao.findById(id);
    }

    /**
     * 通过map查询单个国家地区表
     */
    @Override
    public SysCountryArea findByMap(Map<String, Object> map) {
        return sysCountryAreaDao.findByMap(map);
    }

    /**
     * 新增国家地区表
     */
    @Override
    public int save(SysCountryArea sysCountryArea) {
        return sysCountryAreaDao.save(sysCountryArea);
    }

    /**
     * 修改国家地区表
     */
    @Override
    public int update(SysCountryArea sysCountryArea) {
        return sysCountryAreaDao.update(sysCountryArea);
    }

    /**
     * 删除国家地区表
     */
    @Override
    public int deleteById(String id) {
        return sysCountryAreaDao.deleteById(id);
    }

	@Override
	public List<Map<String, Object>> loadCity(String parentId) {
		return sysCountryAreaDao.loadCity(parentId);
	}

	@Override
	public List<Map<String, Object>> loadProvince(String parentId) {
		return sysCountryAreaDao.loadProvince(parentId);
	}

	@Override
	public List<Map<String, Object>> loadCountry() {
		return sysCountryAreaDao.loadCountry();
	}

}
