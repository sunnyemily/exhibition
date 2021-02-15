package cn.org.chtf.card.manage.system.dao;

import cn.org.chtf.card.manage.system.model.SysExhibition;

import java.util.List;
import java.util.Map;


/**
 * 展会信息DAO
 * @author lm
 */
public interface SysExhibitionMapper {

    /**
     * 通过id查询单个展会信息
     */
    SysExhibition findById(Integer id);

    /**
     * 通过map查询单个展会信息
     */
    SysExhibition findByMap(Map<String, Object> map);

    /**
     * 查询展会信息列表
     */
    List<SysExhibition> list(Map<String, Object> map);

    /**
     * 新增展会信息
     */
    int save(SysExhibition sysExhibition);

    /**
     * 修改展会信息
     */
    int update(SysExhibition sysExhibition);

    /**
     * 删除展会信息
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个展会信息
     */
    int listcount(Map<String, Object> map);

	List<Map<String, Object>> getList(Map<String, Object> map);

}
