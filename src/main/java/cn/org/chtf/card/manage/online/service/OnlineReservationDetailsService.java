package cn.org.chtf.card.manage.online.service;

import cn.org.chtf.card.manage.online.model.OnlineReservationDetails;

import java.util.List;
import java.util.Map;

/**
 * 线上预约Service
 * @author lm
 */
public interface OnlineReservationDetailsService {

	/**
	 * 查询线上预约列表
	 */
	List<OnlineReservationDetails> list(Map<String, Object> map);

	/**
	 * 通过id查询单个线上预约
	 */
    OnlineReservationDetails findById(Integer id);

	/**
	 * 通过map查询单个线上预约
	 */
	List<OnlineReservationDetails> findByMap(Map<String, Object> map);

	/**
	 * 新增线上预约
	 */
	int save(OnlineReservationDetails onlineReservationDetails);

	/**
	 * 修改线上预约
	 */
	int update(OnlineReservationDetails onlineReservationDetails);

	/**
	 * 删除线上预约
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);

	void updateSecretKey(Map<String, Object> param);
}
