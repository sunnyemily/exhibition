package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.Exhibitors.dao.EbsPersonnelcardMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsScatteredExhibitorsboothallocationMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsPersonnelcard;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsboothallocationService;

/**
 * 参展商管理-交易团展位分配ServiceImpl
 * @author ggwudivs
 */
@Service
public class EbsScatteredExhibitorsboothallocationServiceImpl implements EbsScatteredExhibitorsboothallocationService {
	
	@Autowired
	private EbsPersonnelcardMapper ebsPersonnelcardMapper;

	@Autowired
    private EbsScatteredExhibitorsboothallocationMapper ebsScatteredExhibitorsboothallocationMapper;
	
	@Override
	public List<Map<String, Object>> getScatteredExhibitorsList(Map<String, Object> map) {
		return ebsScatteredExhibitorsboothallocationMapper.getScatteredExhibitorsList(map);
	}

	@Override
	public int getScatteredExhibitorsListCount(Map<String, Object> map) {
		return ebsScatteredExhibitorsboothallocationMapper.getScatteredExhibitorsListCount(map);
	}

	@Override
	public List<Map<String, Object>> getApplyBoothInfo(Map<String, Object> map) {
		return ebsScatteredExhibitorsboothallocationMapper.getApplyBoothInfo(map);
	}

	@Override
	public int allocationBooth(Map<String, Object> map) {
		return ebsScatteredExhibitorsboothallocationMapper.allocationBooth(map);
	}

	@Override
	public int cancleAllocationBooth(Map<String, Object> map) {
		//重置人员证件审核信息
		Map<String,Object> map2 = new HashMap<>();
		map2.put("companyid", map.get("companyId"));
		map2.put("session", map.get("session"));
		List<EbsPersonnelcard> personList = ebsPersonnelcardMapper.findByMap(map2);
		for (EbsPersonnelcard ebsPersonnelcard : personList) {
			EbsPersonnelcard ebsPersonnelcard2 = new EbsPersonnelcard();
			ebsPersonnelcard2.setId(ebsPersonnelcard.getId());
			ebsPersonnelcard2.setStatus(0);
			ebsPersonnelcard2.setRemark("");
			ebsPersonnelcardMapper.update(ebsPersonnelcard);
		}
		return ebsScatteredExhibitorsboothallocationMapper.cancleAllocationBooth(map);
	}

	@Override
	public List<Map<String, Object>> selectScatteredTraddingGroup(Map<String, Object> map) {
		return ebsScatteredExhibitorsboothallocationMapper.selectScatteredTraddingGroup(map);
	}

	@Override
	public Map<String, Object> loadCount(Map<String, Object> map) {
		return ebsScatteredExhibitorsboothallocationMapper.loadCount(map);
	}

	@Override
	public List<Map<String, Object>> queryExportInfo(Map<String, Object> map) {
		return ebsScatteredExhibitorsboothallocationMapper.queryExportInfo(map);
	}

}
