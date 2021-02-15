package cn.org.chtf.card.manage.ReportManagement.dao;

import java.util.List;
import java.util.Map;

/**
 * 财务报表打印
 * @author lm
 */
public interface PersonnelCardGetOutReportMapper {

	List<Map<String, Object>> getTradingGroupList(Map<String, Object> map);

	int getTradingGroupListCount(Map<String, Object> map);

	List<Map<String, Object>> getScatteredExhibitorsList(Map<String, Object> map);

	int getScatteredExhibitorsListCount(Map<String, Object> map);

	List<Map<String, Object>> getForeignGuestList(Map<String, Object> map);

	int getForeignGuestListCount(Map<String, Object> map);

	List<Map<String, Object>> getMoveCompanyList(Map<String, Object> map);

	int getMoveCompanyListCount(Map<String, Object> map);

	int updateCardPrintNumber(Map<String, Object> map);

	List<Map<String, Object>> getCertificatTypeByAgentID(Map<String, Object> map);

	List<Map<String, Object>> getCertificatTypeByMemberID(Map<String, Object> map);

	List<Map<String, Object>> getPersonnelCardList(Map<String, Object> map);
}
