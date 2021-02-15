package cn.org.chtf.card.manage.Exhibitors.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.manage.Exhibitors.model.EbsGuestbexhibition;

/**
 * 参展商管理-嘉宾B-布撤展企业管理Service
 * @author lm
 */
public interface EbsGuestbexhibitionService {

	/**
	 * 查询参展商管理-嘉宾B-布撤展企业管理页面
	 * @return 分页参展商管理-嘉宾B-布撤展企业管理数据
	 */
	PageInfo<EbsGuestbexhibition> page(RequestParamsUtil requestParamsUtil);

	/**
	 * 查询参展商管理-嘉宾B-布撤展企业管理列表
	 */
	List<EbsGuestbexhibition> list(Map<String, Object> map);

	/**
	 * 通过id查询单个参展商管理-嘉宾B-布撤展企业管理
	 */
    EbsGuestbexhibition findById(Integer id);

	/**
	 * 通过map查询单个参展商管理-嘉宾B-布撤展企业管理
	 */
	EbsGuestbexhibition findByMap(Map<String, Object> map);

	/**
	 * 新增参展商管理-嘉宾B-布撤展企业管理
	 */
	int save(EbsGuestbexhibition ebsGuestbexhibition);

	/**
	 * 修改参展商管理-嘉宾B-布撤展企业管理
	 */
	int update(EbsGuestbexhibition ebsGuestbexhibition);

	/**
	 * 删除参展商管理-嘉宾B-布撤展企业管理
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);

	void ResetPassword(EbsGuestbexhibition ebsGuestbexhibition);
}
