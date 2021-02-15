package cn.org.chtf.card.manage.Exhibitors.dao;

import java.util.List;
import java.util.Map;


/**
 * 参展商管理-交易团展位分配DAO
 * @author ggwudivs
 */
public interface EbsTradingGroupBoothAuditMapper {

	List<Map<String, Object>> getTradingGroupCompanyList(Map<String, Object> map);

	int getTradingGroupCompanyListCount(Map<String, Object> map);

	List<Map<String, Object>> selectTradingGroup(Map<String, Object> map);

	Map<String, Object> selectCompanyInfo(Map<String, Object> map);

	List<Map<String, Object>> getPersonnelCardList(Map<String, Object> map);

	int getPersonnelCardListCount(Map<String, Object> map);
	
	Map<String, Object> loadCount(Map<String, Object> map);

	List<Map<String, Object>> queryExportInfo(Map<String, Object> map);

}
