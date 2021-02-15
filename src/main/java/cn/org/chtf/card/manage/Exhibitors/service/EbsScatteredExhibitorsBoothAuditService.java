package cn.org.chtf.card.manage.Exhibitors.service;

import java.util.List;
import java.util.Map;

/**
 * 参展商管理-交易团展位分配Service
 * @author ggwudivs
 */
public interface EbsScatteredExhibitorsBoothAuditService {

	List<Map<String, Object>> getScatteredExhibitorsList(Map<String, Object> map);

	int getScatteredExhibitorsListCount(Map<String, Object> map);

	List<Map<String, Object>> getPersonnelCardList(Map<String, Object> map);

	int getPersonnelCardListCount(Map<String, Object> map);

	Map<String, Object> getAuditInfo(Map<String, Object> map);

	int audit(Map<String, Object> map);

	int deleteBoothAuditInfo(int companyId, int sessionId);

	int returnInfo(Map<String, Object> map);
	
	Map<String, Object> loadCount(Map<String, Object> map);
	
}
