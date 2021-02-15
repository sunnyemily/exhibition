package cn.org.chtf.card.manage.system.service;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.system.model.SysCountryArea;

/**
 * 国家地区表Service
 * @author ggwudivs
 */
public interface SysCountryAreaService {

	/**
	 * 查询国家地区表列表
	 */
	List<SysCountryArea> list(Map<String, Object> map);

	/**
	 * 通过id查询单个国家地区表
	 */
    SysCountryArea findById(String id);

	/**
	 * 通过map查询单个国家地区表
	 */
	SysCountryArea findByMap(Map<String, Object> map);

	/**
	 * 新增国家地区表
	 */
	int save(SysCountryArea sysCountryArea);

	/**
	 * 修改国家地区表
	 */
	int update(SysCountryArea sysCountryArea);

	/**
	 * 删除国家地区表
	 */
	int deleteById(String id);
	
	int listcount(Map<String, Object> map);

	List<Map<String, Object>> loadProvince(String parentId);
	
	List<Map<String, Object>> loadCity(String parentId);

	List<Map<String, Object>> loadCountry();
}
