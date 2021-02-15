package cn.org.chtf.card.manage.system.service.impl;

import cn.org.chtf.card.manage.system.model.SysPrintTemplate;
import cn.org.chtf.card.manage.system.service.SysPrintTemplateService;
import cn.org.chtf.card.manage.system.dao.SysPrintTemplateMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 打印模版管理ServiceImpl
 * @author lm
 */
@Service
public class SysPrintTemplateServiceImpl implements SysPrintTemplateService {

    @Autowired
    private SysPrintTemplateMapper sysPrintTemplateDao;

    /**
     * 查询打印模版管理列表
     */
    @Override
    public List<SysPrintTemplate> list(Map<String, Object> map) {
        return sysPrintTemplateDao.list(map);
    }
    
    /**
     * 数量打印模版管理
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return sysPrintTemplateDao.listcount(map);
    }


    /**
     * 通过id查询单个打印模版管理
     */
    @Override
    public SysPrintTemplate findById(Integer id) {
        return sysPrintTemplateDao.findById(id);
    }

    /**
     * 通过map查询单个打印模版管理
     */
    @Override
    public List<SysPrintTemplate> findByMap(Map<String, Object> map,String type,String cardTypeName) {
    	List<SysPrintTemplate> sysPrintTemplate = null;
    	if(type.equals("person")){
    		if(cardTypeName.equals("参展证") || cardTypeName.equals("记者证")){
    			sysPrintTemplate =sysPrintTemplateDao.findByMap(map);
    		}
    		else{
    			map.put("cardtypeid","其他人员证件");
    			sysPrintTemplate =sysPrintTemplateDao.findByMap(map);
    		}
    	}
    	else{
    		if(cardTypeName.equals("贵宾车证") || cardTypeName.equals("机要车证")){
    			sysPrintTemplate =sysPrintTemplateDao.findByMap(map);
    		}
    		else{
    			map.put("cardtypeid","其他车辆证件");
    			sysPrintTemplate =sysPrintTemplateDao.findByMap(map);
    		}
    	}
        return sysPrintTemplate;
    }

    /**
     * 新增打印模版管理
     */
    @Override
    public int save(SysPrintTemplate sysPrintTemplate) {
        return sysPrintTemplateDao.save(sysPrintTemplate);
    }

    /**
     * 修改打印模版管理
     */
    @Override
    public int update(SysPrintTemplate sysPrintTemplate) {
        return sysPrintTemplateDao.update(sysPrintTemplate);
    }

    /**
     * 删除打印模版管理
     */
    @Override
    public int deleteById(Integer id) {
        return sysPrintTemplateDao.deleteById(id);
    }

	@Override
	public List<SysPrintTemplate> findByMap(Map<String, Object> par) {
		return sysPrintTemplateDao.findByMap(par);
	}

}
