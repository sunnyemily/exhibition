package cn.org.chtf.card.manage.system.dao;

import cn.org.chtf.card.manage.system.model.SysOperationLog;

import java.util.List;
import java.util.Map;


/**
 * DAO
 * @author lm
 */
public interface SysOperationLogMapper {

    /**
     * 通过id查询单个
     */
    SysOperationLog findById(Integer id);

    /**
     * 通过map查询单个
     */
    List<SysOperationLog> findByMap(Map<String, Object> map);

    /**
     * 查询列表
     */
    List<SysOperationLog> list(Map<String, Object> map);

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

	/**
     * 通过map查询单个
     */
    int listcount(Map<String, Object> map);

}
