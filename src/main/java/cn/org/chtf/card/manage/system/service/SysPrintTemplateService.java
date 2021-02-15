package cn.org.chtf.card.manage.system.service;

import cn.org.chtf.card.manage.system.model.SysPrintTemplate;

import java.util.List;
import java.util.Map;

/**
 * 打印模版管理Service
 * @author lm
 */
public interface SysPrintTemplateService {

	/**
	 * 查询打印模版管理列表
	 */
	List<SysPrintTemplate> list(Map<String, Object> map);

	/**
	 * 通过id查询单个打印模版管理
	 */
    SysPrintTemplate findById(Integer id);

	/**
	 * 通过map查询单个打印模版管理
	 * @param cardTypeName 
	 * @param type 
	 */
	List<SysPrintTemplate> findByMap(Map<String, Object> map, String type, String cardTypeName);

	/**
	 * 新增打印模版管理
	 */
	int save(SysPrintTemplate sysPrintTemplate);

	/**
	 * 修改打印模版管理
	 */
	int update(SysPrintTemplate sysPrintTemplate);

	/**
	 * 删除打印模版管理
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);

	List<SysPrintTemplate> findByMap(Map<String, Object> par);
}
