package cn.org.chtf.card.manage.system.service;

import cn.org.chtf.card.manage.system.model.SysIndustry;

import java.util.List;
import java.util.Map;

/**
 * 行业表Service
 * @author ggwudivs
 */
public interface SysIndustryService {

	/**
	 * 查询行业表列表
	 */
	List<SysIndustry> list(Map<String, Object> map);

	/**
	 * 通过id查询单个行业表
	 */
    SysIndustry findById(Integer id);

	/**
	 * 通过map查询单个行业表
	 */
	SysIndustry findByMap(Map<String, Object> map);

	/**
	 * 新增行业表
	 */
	int save(SysIndustry sysIndustry);

	/**
	 * 修改行业表
	 */
	int update(SysIndustry sysIndustry);

	/**
	 * 删除行业表
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);
}
