package cn.org.chtf.card.manage.ReportManagement.dao;

import java.util.List;
import java.util.Map;

/**
 * 财务报表打印
 * @author lm
 */
public interface FinancialStatementPrintMapper {

	List<Map<String, Object>> getScatteredExhibitorsList(Map<String, Object> map);

	int getScatteredExhibitorsListCount(Map<String, Object> map);

	List<Map<String, Object>> getTradingGroupList(Map<String, Object> map);

	int getTradingGroupListCount(Map<String, Object> map);

	List<Map<String, Object>> getCarCertificatesList(Map<String, Object> map);
}
