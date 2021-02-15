package cn.org.chtf.card.manage.Exhibitors.service;

import cn.org.chtf.card.manage.Exhibitors.model.EbsSendMessageLog;

import java.util.List;
import java.util.Map;

/**
 * 发送短信日志Service
 * @author ggwudivs
 */
public interface EbsSendMessageLogService {

	/**
	 * 查询发送短信日志列表
	 */
	List<EbsSendMessageLog> list(Map<String, Object> map);

	/**
	 * 通过id查询单个发送短信日志
	 */
    EbsSendMessageLog findById(Integer id);

	/**
	 * 通过map查询单个发送短信日志
	 */
	EbsSendMessageLog findByMap(Map<String, Object> map);

	/**
	 * 新增发送短信日志
	 */
	int save(EbsSendMessageLog ebsSendMessageLog);

	/**
	 * 修改发送短信日志
	 */
	int update(EbsSendMessageLog ebsSendMessageLog);

	/**
	 * 删除发送短信日志
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);
}
