package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.Exhibitors.dao.EbsTradingGroupBoothAuditMapper;
import cn.org.chtf.card.manage.Exhibitors.service.EbsTradingGroupBoothAuditService;

/**
 * 参展商管理-交易团展位审核ServiceImpl
 * @author ggwudivs
 */
@Service
public class EbsTradingGroupBoothAuditServiceImpl implements EbsTradingGroupBoothAuditService {

	@Autowired
    private EbsTradingGroupBoothAuditMapper ebsTradingGroupBoothAuditMapper;

	@Override
	public List<Map<String, Object>> getTradingGroupCompanyList(Map<String, Object> map) {
		return ebsTradingGroupBoothAuditMapper.getTradingGroupCompanyList(map);
	}

	@Override
	public int getTradingGroupCompanyListCount(Map<String, Object> map) {
		return ebsTradingGroupBoothAuditMapper.getTradingGroupCompanyListCount(map);
	}

	@Override
	public List<Map<String, Object>> selectTradingGroup(Map<String, Object> map) {
		return ebsTradingGroupBoothAuditMapper.selectTradingGroup(map);
	}

	@Override
	public Map<String, Object> selectCompanyInfo(Map<String, Object> map) {
		return ebsTradingGroupBoothAuditMapper.selectCompanyInfo(map);
	}

	@Override
	public List<Map<String, Object>> getPersonnelCardList(Map<String, Object> map) {
		return ebsTradingGroupBoothAuditMapper.getPersonnelCardList(map);
	}

	@Override
	public int getPersonnelCardListCount(Map<String, Object> map) {
		return ebsTradingGroupBoothAuditMapper.getPersonnelCardListCount(map);
	}
	
	@Override
	public Map<String, Object> loadCount(Map<String, Object> map) {
		return ebsTradingGroupBoothAuditMapper.loadCount(map);
	}

	@Override
	public List<Map<String, Object>> queryExportInfo(Map<String, Object> map) {
		return ebsTradingGroupBoothAuditMapper.queryExportInfo(map);
	}
}
