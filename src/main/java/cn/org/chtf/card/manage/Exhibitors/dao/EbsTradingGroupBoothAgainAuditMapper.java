package cn.org.chtf.card.manage.Exhibitors.dao;

import java.util.List;
import java.util.Map;


/**
 * 参展商管理-交易团展位分配DAO
 * @author ggwudivs
 */
public interface EbsTradingGroupBoothAgainAuditMapper {

	List<Map<String, Object>> list(Map<String, Object> map);
	
	int listCount(Map<String, Object> map);
	
	int insertAuditInfo(Map<String, Object> map);

	int updateAuditInfo(Map<String, Object> map);
}
