package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.Exhibitors.dao.EbsScatteredExhibitorsBoothAuditMapper;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsBoothAuditService;

/**
 * 参展商管理-交易团展位分配ServiceImpl
 * @author ggwudivs
 */
@Service
public class EbsScatteredExhibitorsBoothAuditServiceImpl implements EbsScatteredExhibitorsBoothAuditService {

	@Autowired
    private EbsScatteredExhibitorsBoothAuditMapper ebsScatteredExhibitorsBoothAuditMapper;
	
	
	@Override
	public List<Map<String, Object>> getScatteredExhibitorsList(Map<String, Object> map) {
		return ebsScatteredExhibitorsBoothAuditMapper.getScatteredExhibitorsList(map);
	}

	@Override
	public int getScatteredExhibitorsListCount(Map<String, Object> map) {
		return ebsScatteredExhibitorsBoothAuditMapper.getScatteredExhibitorsListCount(map);
	}

	@Override
	public List<Map<String, Object>> getPersonnelCardList(Map<String, Object> map) {
		return ebsScatteredExhibitorsBoothAuditMapper.getPersonnelCardList(map);
	}

	@Override
	public int getPersonnelCardListCount(Map<String, Object> map) {
		return ebsScatteredExhibitorsBoothAuditMapper.getPersonnelCardListCount(map);
	}

	@Override
	public Map<String, Object> getAuditInfo(Map<String, Object> map) {
		return ebsScatteredExhibitorsBoothAuditMapper.getAuditInfo(map);
	}

	@Override
	public int audit(Map<String, Object> map) {
		Map<String, Object> selectAuditInfo = ebsScatteredExhibitorsBoothAuditMapper.selectAuditInfo(map);
		int i;
		if(selectAuditInfo == null) {
			i = ebsScatteredExhibitorsBoothAuditMapper.insertAuditInfo(map);
		}else {
			i = ebsScatteredExhibitorsBoothAuditMapper.audit(map);
		}
		return i;
	}

	@Override
	public int deleteBoothAuditInfo(int companyId, int sessionId) {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("sessionId", sessionId);
		return ebsScatteredExhibitorsBoothAuditMapper.deleteBoothAuditInfo(map);
	}

	@Override
	public int returnInfo(Map<String, Object> map) {
		return ebsScatteredExhibitorsBoothAuditMapper.returnInfo(map);
	}
	
	@Override
	public Map<String, Object> loadCount(Map<String, Object> map) {
		return ebsScatteredExhibitorsBoothAuditMapper.loadCount(map);
	}
}
