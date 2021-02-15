package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.Exhibitors.dao.EbsBoothAuditMapper;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothAuditService;

/**
 * 参展商管理-展位ServiceImpl
 * @author ggwudivs
 */
@Service
public class EbsBoothAuditServiceImpl implements EbsBoothAuditService {

    @Autowired
    private EbsBoothAuditMapper ebsBoothAuditMapper;

	@Override
	public int deleteByCompany(int companyId, int sessionId) {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("sessionId", sessionId);
		return ebsBoothAuditMapper.deleteByCompany(map);
	}
}
