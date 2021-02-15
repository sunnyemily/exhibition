package cn.org.chtf.card.manage.online.service;

import cn.org.chtf.card.manage.online.model.OnlineActivityManage;

import java.util.List;
import java.util.Map;

/**
 * Service
 * @author lm
 */
public interface OnlineActivityManageService {

	/**
	 * 查询列表
	 */
	List<OnlineActivityManage> list(Map<String, Object> map);

	/**
	 * 通过id查询单个
	 */
    OnlineActivityManage findById(Integer id);

	/**
	 * 通过map查询单个
	 */
	List<OnlineActivityManage> findByMap(Map<String, Object> map);

	/**
	 * 新增
	 */
	int save(OnlineActivityManage onlineActivityManage);

	/**
	 * 修改
	 */
	int update(OnlineActivityManage onlineActivityManage);

	/**
	 * 删除
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);
}
