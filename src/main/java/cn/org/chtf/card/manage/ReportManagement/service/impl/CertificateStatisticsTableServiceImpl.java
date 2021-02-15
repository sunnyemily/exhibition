package cn.org.chtf.card.manage.ReportManagement.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.ReportManagement.dao.CertificateStatisticsTableMapper;
import cn.org.chtf.card.manage.ReportManagement.service.CertificateStatisticsTableService;

@Service
public class CertificateStatisticsTableServiceImpl implements CertificateStatisticsTableService {
	
	@Autowired
	private CertificateStatisticsTableMapper certificateStatisticsTableMapper;

	@Override
	public Map<String, Object> getStatisticalInfo(Map<String, Object> map) {
		return certificateStatisticsTableMapper.getStatisticalInfo(map);
	}
}
