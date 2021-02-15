package cn.org.chtf.card.manage.ReportManagement.dao;

import java.util.Map;

public interface CertificateStatisticsTableMapper {
	Map<String, Object> getStatisticalInfo(Map<String, Object> map);
}
