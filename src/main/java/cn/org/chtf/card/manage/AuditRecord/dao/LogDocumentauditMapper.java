package cn.org.chtf.card.manage.AuditRecord.dao;

import cn.org.chtf.card.manage.AuditRecord.model.LogDocumentaudit;

import java.util.List;
import java.util.Map;


/**
 * 证件审核记录DAO
 * @author ggwudivs
 */
public interface LogDocumentauditMapper {

    /**
     * 通过id查询单个证件审核记录
     */
    LogDocumentaudit findById(Integer id);

    /**
     * 通过map查询单个证件审核记录
     */
    List<LogDocumentaudit> findByMap(Map<String, Object> map);

    /**
     * 查询证件审核记录列表
     */
    List<LogDocumentaudit> list(Map<String, Object> map);

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

	/**
     * 通过map查询单个证件审核记录
     */
    int listcount(Map<String, Object> map);

}
