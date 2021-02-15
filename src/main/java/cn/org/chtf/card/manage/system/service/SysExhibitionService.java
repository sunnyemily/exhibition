package cn.org.chtf.card.manage.system.service;

import cn.org.chtf.card.manage.system.model.SysExhibition;

import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.common.utils.RequestParamsUtil;

import java.util.List;
import java.util.Map;

/**
 * 展会信息Service
 * @author lm
 */
public interface SysExhibitionService {

	/**
	 * 查询展会信息页面
	 * @return 分页展会信息数据
	 */
	PageInfo<SysExhibition> page(RequestParamsUtil requestParamsUtil);

	/**
	 * 查询展会信息列表
	 */
	List<SysExhibition> list(Map<String, Object> map);

	/**
	 * 通过id查询单个展会信息
	 */
    SysExhibition findById(Integer id);

	/**
	 * 通过map查询单个展会信息
	 */
	SysExhibition findByMap(Map<String, Object> map);

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
	
	int listcount(Map<String, Object> map);

	List<Map<String, Object>> getList(Map<String, Object> map);
}
