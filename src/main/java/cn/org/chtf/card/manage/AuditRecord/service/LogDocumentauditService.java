package cn.org.chtf.card.manage.AuditRecord.service;

import cn.org.chtf.card.manage.AuditRecord.model.LogDocumentaudit;

import java.util.List;
import java.util.Map;

/**
 * 证件审核记录Service
 * @author ggwudivs
 */
public interface LogDocumentauditService {

	/**
	 * 查询证件审核记录列表
	 */
	List<LogDocumentaudit> list(Map<String, Object> map);

	/**
	 * 通过id查询单个证件审核记录
	 */
    LogDocumentaudit findById(Integer id);

	/**
	 * 通过map查询单个证件审核记录
	 */
	List<LogDocumentaudit> findByMap(Map<String, Object> map);

	/**
	 * 新增证件审核记录
	 */
	int save(LogDocumentaudit logDocumentaudit);

	/**
	 * 修改证件审核记录
	 */
	int update(LogDocumentaudit logDocumentaudit);

	/**
	 * 删除证件审核记录
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);
}
