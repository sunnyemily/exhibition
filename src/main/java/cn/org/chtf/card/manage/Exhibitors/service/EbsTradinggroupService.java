package cn.org.chtf.card.manage.Exhibitors.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroup;

/**
 * 参展商管理-交易团Service
 * @author lm
 */
public interface EbsTradinggroupService {

	/**
	 * 查询参展商管理-交易团页面
	 * @return 分页参展商管理-交易团数据
	 */
	PageInfo<EbsTradinggroup> page(RequestParamsUtil requestParamsUtil);

	/**
	 * 查询参展商管理-交易团列表
	 */
	List<EbsTradinggroup> list(Map<String, Object> map);

	/**
	 * 通过id查询单个参展商管理-交易团
	 */
    EbsTradinggroup findById(Integer id);

	/**
	 * 通过map查询单个参展商管理-交易团
	 */
	EbsTradinggroup findByMap(Map<String, Object> map);

	/**
	 * 新增参展商管理-交易团
	 */
	int save(EbsTradinggroup ebsTradinggroup);

	/**
	 * 修改参展商管理-交易团
	 */
	int update(EbsTradinggroup ebsTradinggroup);

	/**
	 * 删除参展商管理-交易团
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);

	int UseTradingGroup(Map<String, Object> map);

	List<Map<String, Object>> GetBoothByTradingGroupId(Map<String, Object> map);
	
	List<Map<String, Object>> selectByType(Map<String, Object> map);
}
