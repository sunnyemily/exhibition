package cn.org.chtf.card.manage.system.service;

import cn.org.chtf.card.manage.system.model.SysCompanyType;

import java.util.List;
import java.util.Map;

/**
 * 参展企业性质表Service
 * @author ggwudivs
 */
public interface SysCompanyTypeService {

	/**
	 * 查询参展企业性质表列表
	 */
	List<SysCompanyType> list(Map<String, Object> map);

	/**
	 * 通过id查询单个参展企业性质表
	 */
    SysCompanyType findById(Integer id);

	/**
	 * 通过map查询单个参展企业性质表
	 */
	SysCompanyType findByMap(Map<String, Object> map);

	/**
	 * 新增参展企业性质表
	 */
	int save(SysCompanyType sysCompanyType);

	/**
	 * 修改参展企业性质表
	 */
	int update(SysCompanyType sysCompanyType);

	/**
	 * 删除参展企业性质表
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);
}
