package cn.org.chtf.card.manage.ReportManagement.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.ReportManagement.dao.PersonnelCardGetOutReportMapper;
import cn.org.chtf.card.manage.ReportManagement.service.PersonnelCardGetOutReportService;

/**
 * 报表管理
 * @author lm
 */
@Service
public class PersonnelCardGetOutReportServiceImpl implements PersonnelCardGetOutReportService {

    @Autowired
    private PersonnelCardGetOutReportMapper personnelCardGetOutReportMapper;

	@Override
	public List<Map<String, Object>> getTradingGroupList(Map<String, Object> map) {
		return personnelCardGetOutReportMapper.getTradingGroupList(map);
	}

	@Override
	public int getTradingGroupListCount(Map<String, Object> map) {
		return personnelCardGetOutReportMapper.getTradingGroupListCount(map);
	}

	@Override
	public List<Map<String, Object>> getScatteredExhibitorsList(Map<String, Object> map) {
		return personnelCardGetOutReportMapper.getScatteredExhibitorsList(map);
	}

	@Override
	public int getScatteredExhibitorsListCount(Map<String, Object> map) {
		return personnelCardGetOutReportMapper.getScatteredExhibitorsListCount(map);
	}

	@Override
	public List<Map<String, Object>> getForeignGuestList(Map<String, Object> map) {
		return personnelCardGetOutReportMapper.getForeignGuestList(map);
	}

	@Override
	public int getForeignGuestListCount(Map<String, Object> map) {
		return personnelCardGetOutReportMapper.getForeignGuestListCount(map);
	}

	@Override
	public List<Map<String, Object>> getMoveCompanyList(Map<String, Object> map) {
		return personnelCardGetOutReportMapper.getMoveCompanyList(map);
	}

	@Override
	public int getMoveCompanyListCount(Map<String, Object> map) {
		return personnelCardGetOutReportMapper.getMoveCompanyListCount(map);
	}

	@Override
	public int updateCardPrintNumber(Map<String, Object> map) {
		return personnelCardGetOutReportMapper.updateCardPrintNumber(map);
	}

	@Override
	public List<Map<String, Object>> getCertificatTypeByAgentID(Map<String, Object> map) {
		return personnelCardGetOutReportMapper.getCertificatTypeByAgentID(map);
	}

	@Override
	public List<Map<String, Object>> getCertificatTypeByMemberID(Map<String, Object> map) {
		return personnelCardGetOutReportMapper.getCertificatTypeByMemberID(map);
	}

	@Override
	public List<Map<String, Object>> getPersonnelCardList(Map<String, Object> map) {
		return personnelCardGetOutReportMapper.getPersonnelCardList(map);
	}
}
