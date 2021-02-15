package cn.org.chtf.card.manage.Exhibitors.dao;

import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroomtype;

import java.util.List;
import java.util.Map;


/**
 * 参展商管理-展厅类型DAO
 * @author lm
 */
public interface EbsShowroomtypeMapper {

    /**
     * 通过id查询单个参展商管理-展厅类型
     */
    EbsShowroomtype findById(Integer id);

    /**
     * 通过map查询单个参展商管理-展厅类型
     */
    EbsShowroomtype findByMap(Map<String, Object> map);

    /**
     * 查询参展商管理-展厅类型列表
     */
    List<EbsShowroomtype> list(Map<String, Object> map);

    /**
     * 新增参展商管理-展厅类型
     */
    int save(EbsShowroomtype ebsShowroomtype);

    /**
     * 修改参展商管理-展厅类型
     */
    int update(EbsShowroomtype ebsShowroomtype);

    /**
     * 删除参展商管理-展厅类型
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个参展商管理-展厅类型
     */
    int listcount(Map<String, Object> map);

	List<Map<String, Object>> getList(Map<String, Object> map);

}
