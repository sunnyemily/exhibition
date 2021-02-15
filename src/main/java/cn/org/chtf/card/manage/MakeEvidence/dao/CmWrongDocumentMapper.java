package cn.org.chtf.card.manage.MakeEvidence.dao;

import cn.org.chtf.card.manage.MakeEvidence.model.CmWrongDocument;

import java.util.List;
import java.util.Map;


/**
 * DAO
 * @author ggwudivs
 */
public interface CmWrongDocumentMapper {

    /**
     * 通过id查询单个
     */
    CmWrongDocument findById(Integer id);

    /**
     * 通过map查询单个
     */
    List<CmWrongDocument> findByMap(Map<String, Object> map);

    /**
     * 查询列表
     */
    List<CmWrongDocument> list(Map<String, Object> map);

    /**
     * 新增
     */
    int save(CmWrongDocument cmWrongDocument);

    /**
     * 修改
     */
    int update(CmWrongDocument cmWrongDocument);

    /**
     * 删除
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个
     */
    int listcount(Map<String, Object> map);

}
