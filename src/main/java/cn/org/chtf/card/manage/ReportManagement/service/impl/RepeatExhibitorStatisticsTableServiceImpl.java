package cn.org.chtf.card.manage.ReportManagement.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.ReportManagement.dao.RepeatExhibitorStatisticsTableMapper;
import cn.org.chtf.card.manage.ReportManagement.service.RepeatExhibitorStatisticsTableService;

@Service
public class RepeatExhibitorStatisticsTableServiceImpl implements RepeatExhibitorStatisticsTableService {

	@Autowired
	private RepeatExhibitorStatisticsTableMapper repeatExhibitorStatisticsTableMapper;
	
//	数据表
	@Override
	public List<Map<String, Object>> selectInfoList(Map<String, Object> map) {
		return repeatExhibitorStatisticsTableMapper.selectInfoList(map);
	}
//	重复率
	@Override
	public List<Map<String, Object>> repeatNumber(Map<String, Object> map) {
		return repeatExhibitorStatisticsTableMapper.repeatNumber(map);
	}
//	导出
	@Override
	public List<Map<String, Object>> queryExportInfo(Map<String, Object> map) {
		return repeatExhibitorStatisticsTableMapper.queryExportInfo(map);
	}
//	分页
	@Override
	public int ListCount(Map<String, Object> map) {
		return repeatExhibitorStatisticsTableMapper.ListCount(map);
	}
	@Override
	public List<Map<String, Object>> repeatExhibitorTable(Map<String, Object> map) {
		return repeatExhibitorStatisticsTableMapper.repeatExhibitorTable(map);
	}
	@Override
	public int repeatExhibitorTableCount(Map<String, Object> map) {
		return repeatExhibitorStatisticsTableMapper.repeatExhibitorTableCount(map);
	}
	
}
