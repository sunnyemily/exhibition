package cn.org.chtf.card.manage.Exhibitors.dao;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroup;


/**
 * 参展商管理-交易团DAO
 * @author lm
 */
public interface EbsTradinggroupMapper {

    /**
     * 通过id查询单个参展商管理-交易团
     */
    EbsTradinggroup findById(Integer id);

    /**
     * 通过map查询单个参展商管理-交易团
     */
    EbsTradinggroup findByMap(Map<String, Object> map);

    /**
     * 查询参展商管理-交易团列表
     */
    List<EbsTradinggroup> list(Map<String, Object> map);

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

	/**
     * 通过map查询单个参展商管理-交易团
     */
    int listcount(Map<String, Object> map);

	List<Map<String, Object>> GetBoothByTradingGroupId(Map<String, Object> map);
	
	List<Map<String, Object>> selectByType(Map<String, Object> map);

}
