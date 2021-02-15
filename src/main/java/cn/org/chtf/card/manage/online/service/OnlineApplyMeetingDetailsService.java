package cn.org.chtf.card.manage.online.service;

import cn.org.chtf.card.manage.online.model.OnlineApplyMeetingDetails;

import java.util.List;
import java.util.Map;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;

/**
 * Service
 * @author lm
 */
public interface OnlineApplyMeetingDetailsService {

	/**
	 * 查询列表
	 */
	List<OnlineApplyMeetingDetails> list(Map<String, Object> map);

	/**
	 * 通过id查询单个
	 */
    OnlineApplyMeetingDetails findById(Integer id);

	/**
	 * 通过map查询单个
	 */
	List<OnlineApplyMeetingDetails> findByMap(Map<String, Object> map);

	/**
	 * 新增
	 */
	int save(OnlineApplyMeetingDetails onlineApplyMeetingDetails);

	/**
	 * 修改
	 * @throws TencentCloudSDKException 
	 */
	int update(OnlineApplyMeetingDetails onlineApplyMeetingDetails) throws TencentCloudSDKException;

	/**
	 * 删除
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);
}
