package cn.org.chtf.card.manage.system.dao;

import cn.org.chtf.card.manage.system.model.SysCompanyType;

import java.util.List;
import java.util.Map;


/**
 * 参展企业性质表DAO
 * @author ggwudivs
 */
public interface SysCompanyTypeMapper {

    /**
     * 通过id查询单个参展企业性质表
     */
    SysCompanyType findById(Integer id);

    /**
     * 通过map查询单个参展企业性质表
     */
    SysCompanyType findByMap(Map<String, Object> map);

    /**
     * 查询参展企业性质表列表
     */
    List<SysCompanyType> list(Map<String, Object> map);

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

	/**
     * 通过map查询单个参展企业性质表
     */
    int listcount(Map<String, Object> map);

}
