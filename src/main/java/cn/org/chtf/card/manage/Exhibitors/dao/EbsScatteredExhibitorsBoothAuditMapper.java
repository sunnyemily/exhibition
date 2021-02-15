package cn.org.chtf.card.manage.Exhibitors.dao;

import java.util.List;
import java.util.Map;


/**
 * 参展商管理-交易团展位分配DAO
 * @author ggwudivs
 */
public interface EbsScatteredExhibitorsBoothAuditMapper {

	List<Map<String, Object>> getScatteredExhibitorsList(Map<String, Object> map);

	int getScatteredExhibitorsListCount(Map<String, Object> map);

	List<Map<String, Object>> getPersonnelCardList(Map<String, Object> map);

	int getPersonnelCardListCount(Map<String, Object> map);

	Map<String, Object> getAuditInfo(Map<String, Object> map);

	int audit(Map<String, Object> map);

	int deleteBoothAuditInfo(Map<String, Object> map);
	
	int insertAuditInfo(Map<String, Object> map);
	
	Map<String, Object> selectAuditInfo(Map<String, Object> map);
	
	int returnInfo(Map<String, Object> map);
	
	Map<String, Object> loadCount(Map<String, Object> map);
}
