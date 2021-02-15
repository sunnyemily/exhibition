package cn.org.chtf.card.manage.online.service;

import cn.org.chtf.card.manage.online.model.OnlineActivityDetails;

import java.util.List;
import java.util.Map;

/**
 * Service
 * @author lm
 */
public interface OnlineActivityDetailsService {

	/**
	 * 查询列表
	 */
	List<OnlineActivityDetails> list(Map<String, Object> map);

	/**
	 * 通过id查询单个
	 */
    OnlineActivityDetails findById(Integer id);

	/**
	 * 通过map查询单个
	 */
	List<OnlineActivityDetails> findByMap(Map<String, Object> map);

	/**
	 * 新增
	 */
	int save(OnlineActivityDetails onlineActivityDetails);

	/**
	 * 修改
	 */
	int update(OnlineActivityDetails onlineActivityDetails);

	/**
	 * 删除
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);
}
