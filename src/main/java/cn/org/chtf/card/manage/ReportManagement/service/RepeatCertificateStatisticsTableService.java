package cn.org.chtf.card.manage.ReportManagement.service;

import java.util.List;
import java.util.Map;

public interface RepeatCertificateStatisticsTableService {
	//证件类型
	List<Map<String, Object>> getCardList();
	//届次
	List<Map<String, Object>> getSessionList();
//	数据表
	List<Map<String, Object>> selectInfoList(Map<String, Object> map);
//	重复率
	List<Map<String, Object>> repeatNumber(Map<String, Object> map);
//	分页
	int ListCount(Map<String, Object> map);
//	导出数据
	List<Map<String, Object>> queryExportInfo(Map<String, Object> map);
}
