package cn.org.chtf.card.manage.Decorators.service.impl;

import cn.org.chtf.card.manage.Decorators.dao.DecoratorEbsStadiumManageMapper;
import cn.org.chtf.card.manage.Decorators.service.DecoratorEbsStadiumManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 搭建商报馆信息ServiceImpl
 * 
 * @author ggwudivs
 */
@Service
public class DecoratorEbsStadiumManageServiceImpl implements DecoratorEbsStadiumManageService {

	@Autowired
	private DecoratorEbsStadiumManageMapper decoratorEbsStadiumManageDao;

	/**
	 * 查询企业信息列表
	 */
	@Override
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return decoratorEbsStadiumManageDao.list(map);
	}

	/**
	 * 数量企业信息
	 */
	@Override
	public int listcount(Map<String, Object> map) {
		return decoratorEbsStadiumManageDao.listcount(map);
	}

	@Override
	public int updateStadiumInfo(Map<String, Object> map) {
		return decoratorEbsStadiumManageDao.updateStadiumInfo(map);
	}

	@Override
	public Map<String, Object> selectStadiumInfo(Map<String, Object> map) {
		return decoratorEbsStadiumManageDao.selectStadiumInfo(map);
	}
}
