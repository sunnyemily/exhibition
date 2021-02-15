package cn.org.chtf.card.manage.ReportManagement.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.ReportManagement.dao.RepeatCertificateStatisticsTableMapper;
import cn.org.chtf.card.manage.ReportManagement.service.RepeatCertificateStatisticsTableService;

@Service
public class RepeatCertificateStatisticsTableServiceImpl implements RepeatCertificateStatisticsTableService {

	@Autowired
	private RepeatCertificateStatisticsTableMapper repeatCertificateStatisticsTableMapper;
	
//	证件类型
	@Override
	public List<Map<String, Object>> getCardList() {
		return repeatCertificateStatisticsTableMapper.getCardList();
	}
//	届次
	@Override
	public List<Map<String, Object>> getSessionList() {
		return repeatCertificateStatisticsTableMapper.getSessionList();
	}
//	数据表
	@Override
	public List<Map<String, Object>> selectInfoList(Map<String, Object> map) {
		return repeatCertificateStatisticsTableMapper.selectInfoList(map);
	}
//	重复率
	@Override
	public List<Map<String, Object>> repeatNumber(Map<String, Object> map) {
		return repeatCertificateStatisticsTableMapper.repeatNumber(map);
	}
//	导出
	@Override
	public List<Map<String, Object>> queryExportInfo(Map<String, Object> map) {
		return repeatCertificateStatisticsTableMapper.queryExportInfo(map);
	}
//	分页
	@Override
	public int ListCount(Map<String, Object> map) {
		return repeatCertificateStatisticsTableMapper.ListCount(map);
	}
	
}
