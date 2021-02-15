package cn.org.chtf.card.manage.system.service;

import cn.org.chtf.card.manage.system.model.SysOperationLog;

import java.util.List;
import java.util.Map;

/**
 * Service
 * @author lm
 */
public interface SysOperationLogService {

	/**
	 * 查询列表
	 */
	List<SysOperationLog> list(Map<String, Object> map);

	/**
	 * 通过id查询单个
	 */
    SysOperationLog findById(Integer id);

	/**
	 * 通过map查询单个
	 */
	List<SysOperationLog> findByMap(Map<String, Object> map);

	/**
	 * 新增
	 */
	int save(SysOperationLog sysOperationLog);

	/**
	 * 修改
	 */
	int update(SysOperationLog sysOperationLog);

	/**
	 * 删除
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);

	/**
     * 创建操作日志实体保存 
     * 【 act 操作类型	】
     * 【session  当前届次	】
     * 【isback 0后台 1前台	】
     * 【operator 操作人id	】
     * 【primarykey 操作数据的主键	】
     * 【remark   备注】
     */
	void CreateEntity(String act, String session, int isback, int operator, int primarykey, String remark);
}
