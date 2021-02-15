package cn.org.chtf.card.manage.system.service;

import cn.org.chtf.card.manage.system.model.SystemDictionaries;

import java.util.List;
import java.util.Map;

/**
 * 字典表Service
 * @author ggwudivs
 */
public interface SystemDictionariesService {

	/**
	 * 查询字典表列表
	 */
	List<SystemDictionaries> list(Map<String, Object> map);

	/**
	 * 通过dicid查询单个字典表
	 */
    SystemDictionaries findById(Integer dicid);

	/**
	 * 通过map查询单个字典表
	 */
	List<SystemDictionaries> findByMap(Map<String, Object> map);

	/**
	 * 新增字典表
	 */
	int save(SystemDictionaries systemDictionaries);

	/**
	 * 修改字典表
	 */
	int update(SystemDictionaries systemDictionaries);

	/**
	 * 删除字典表
	 */
	int deleteById(Integer dicid);
	
	int listcount(Map<String, Object> map);

	List<SystemDictionaries> getList(Map<String, Object> map);
}
