package cn.org.chtf.card.manage.ReportManagement.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.ReportManagement.dao.VehicleCardGetOutReportMapper;
import cn.org.chtf.card.manage.ReportManagement.service.VehicleCardGetOutReportService;

/**
 * 报表管理
 * @author lm
 */
@Service
public class VehicleCardGetOutReportServiceImpl implements VehicleCardGetOutReportService {

    @Autowired
    private VehicleCardGetOutReportMapper vehicleCardGetOutReportMapper;

	@Override
	public List<Map<String, Object>> getAgentList(Map<String, Object> map) {
		return vehicleCardGetOutReportMapper.getAgentList(map);
	}

	@Override
	public int getAgentListCount(Map<String, Object> map) {
		return vehicleCardGetOutReportMapper.getAgentListCount(map);
	}

	@Override
	public List<Map<String, Object>> getVehicleCardList(Map<String, Object> map) {
		return vehicleCardGetOutReportMapper.getVehicleCardList(map);
	}
}
