package cn.org.chtf.card.manage.AuditRecord.dao;

import cn.org.chtf.card.manage.AuditRecord.model.LogBoothaudit;

import java.util.List;
import java.util.Map;


/**
 * 展位审核记录DAO
 * @author ggwudivs
 */
public interface LogBoothauditMapper {

    /**
     * 通过id查询单个展位审核记录
     */
    LogBoothaudit findById(Integer id);

    /**
     * 通过map查询单个展位审核记录
     */
    List<LogBoothaudit> findByMap(Map<String, Object> map);

    /**
     * 查询展位审核记录列表
     */
    List<LogBoothaudit> list(Map<String, Object> map);

    /**
     * 新增展位审核记录
     */
    int save(LogBoothaudit logBoothaudit);

    /**
     * 修改展位审核记录
     */
    int update(LogBoothaudit logBoothaudit);

    /**
     * 删除展位审核记录
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个展位审核记录
     */
    int listcount(Map<String, Object> map);

}
