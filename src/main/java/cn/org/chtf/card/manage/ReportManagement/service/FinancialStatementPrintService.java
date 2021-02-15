package cn.org.chtf.card.manage.ReportManagement.service;

import java.util.List;
import java.util.Map;


/**
 * 报表管理
 * @author lm
 */
public interface FinancialStatementPrintService {
	List<Map<String, Object>> getScatteredExhibitorsList(Map<String, Object> map);

	int getScatteredExhibitorsListCount(Map<String, Object> map);

	List<Map<String, Object>> getTradingGroupList(Map<String, Object> map);

	int getTradingGroupListCount(Map<String, Object> map);

	List<Map<String, Object>> getCarCertificatesList(Map<String, Object> map);
}
