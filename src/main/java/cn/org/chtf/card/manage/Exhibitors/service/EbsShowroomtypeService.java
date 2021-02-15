package cn.org.chtf.card.manage.Exhibitors.service;

import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroomtype;

import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.common.utils.RequestParamsUtil;

import java.util.List;
import java.util.Map;

/**
 * 参展商管理-展厅类型Service
 * @author lm
 */
public interface EbsShowroomtypeService {

	/**
	 * 查询参展商管理-展厅类型页面
	 * @return 分页参展商管理-展厅类型数据
	 */
	PageInfo<EbsShowroomtype> page(RequestParamsUtil requestParamsUtil);

	/**
	 * 查询参展商管理-展厅类型列表
	 */
	List<EbsShowroomtype> list(Map<String, Object> map);

	/**
	 * 通过id查询单个参展商管理-展厅类型
	 */
    EbsShowroomtype findById(Integer id);

	/**
	 * 通过map查询单个参展商管理-展厅类型
	 */
	EbsShowroomtype findByMap(Map<String, Object> map);

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
	
	int listcount(Map<String, Object> map);

	List<Map<String, Object>> getList(Map<String, Object> map);
}
