package cn.org.chtf.card.manage.Exhibitors.service;

import java.util.List;
import java.util.Map;

/**
 * 短信通知参展商Service
 * @author ggwudivs
 */
public interface EbsSendMessageToExhibitorService {

	/**
	 * 查询零散参展商列表
	 */
	List<Map<String,Object>> list(Map<String, Object> map);

	int listcount(Map<String, Object> map);
}
