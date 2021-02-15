package cn.org.chtf.card.manage.ReportManagement.dao;

import java.util.List;
import java.util.Map;

/**
 * 财务报表打印
 * @author lm
 */
public interface VehicleCardGetOutReportMapper {

	List<Map<String, Object>> getAgentList(Map<String, Object> map);

	int getAgentListCount(Map<String, Object> map);

	List<Map<String, Object>> getVehicleCardList(Map<String, Object> map);
}
