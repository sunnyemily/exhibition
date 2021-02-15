package cn.org.chtf.card.manage.ReportManagement.service;

import java.util.List;
import java.util.Map;


/**
 * 报表管理
 * @author lm
 */
public interface VehicleCardGetOutReportService {
	List<Map<String, Object>> getAgentList(Map<String, Object> map);

	int getAgentListCount(Map<String, Object> map);

	List<Map<String, Object>> getVehicleCardList(Map<String, Object> map);
}
