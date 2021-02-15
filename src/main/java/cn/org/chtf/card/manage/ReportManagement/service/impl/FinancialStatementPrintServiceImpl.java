package cn.org.chtf.card.manage.ReportManagement.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.ReportManagement.dao.FinancialStatementPrintMapper;
import cn.org.chtf.card.manage.ReportManagement.service.FinancialStatementPrintService;

/**
 * 报表管理
 * @author lm
 */
@Service
public class FinancialStatementPrintServiceImpl implements FinancialStatementPrintService {

    @Autowired
    private FinancialStatementPrintMapper financialStatementPrintMapper;

	@Override
	public List<Map<String, Object>> getScatteredExhibitorsList(Map<String, Object> map) {
		return financialStatementPrintMapper.getScatteredExhibitorsList(map);
	}

	@Override
	public int getScatteredExhibitorsListCount(Map<String, Object> map) {
		return financialStatementPrintMapper.getScatteredExhibitorsListCount(map);
	}

	@Override
	public List<Map<String, Object>> getTradingGroupList(Map<String, Object> map) {
		return financialStatementPrintMapper.getTradingGroupList(map);
	}

	@Override
	public int getTradingGroupListCount(Map<String, Object> map) {
		return financialStatementPrintMapper.getTradingGroupListCount(map);
	}

	@Override
	public List<Map<String, Object>> getCarCertificatesList(Map<String, Object> map) {
		return financialStatementPrintMapper.getCarCertificatesList(map);
	}
}
