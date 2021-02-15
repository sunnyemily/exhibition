package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.Exhibitors.dao.EbsBoothMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsBooth;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothService;

/**
 * 参展商管理-展位ServiceImpl
 * @author ggwudivs
 */
@Service
public class EbsBoothServiceImpl implements EbsBoothService {

    @Autowired
    private EbsBoothMapper ebsBoothDao;

    /**
     * 查询参展商管理-展位列表
     */
    @Override
    public List<EbsBooth> list(Map<String, Object> map) {
        return ebsBoothDao.list(map);
    }
    
    /**
     * 数量参展商管理-展位
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return ebsBoothDao.listcount(map);
    }


    /**
     * 通过id查询单个参展商管理-展位
     */
    @Override
    public EbsBooth findById(Integer id) {
        return ebsBoothDao.findById(id);
    }

    /**
     * 通过map查询单个参展商管理-展位
     */
    @Override
    public EbsBooth findByMap(Map<String, Object> map) {
        return ebsBoothDao.findByMap(map);
    }

    /**
     * 新增参展商管理-展位
     */
    @Override
    public int save(EbsBooth ebsBooth) {
        return ebsBoothDao.save(ebsBooth);
    }

    /**
     * 修改参展商管理-展位
     */
    @Override
    public int update(EbsBooth ebsBooth) {
        return ebsBoothDao.update(ebsBooth);
    }

    /**
     * 删除参展商管理-展位
     */
    @Override
    public int deleteById(Integer id) {
        return ebsBoothDao.deleteById(id);
    }

	@Override
	public List<Map<String, Object>> GetBoothByMap(Map<String, Object> map) {
		return ebsBoothDao.GetBoothByMap(map);
	}

	@Override
	public List<String> queryAllBooth(Map<String, Object> map) {
		return ebsBoothDao.queryAllBooth(map);
	}

	@Override
	public List<String> queryAllShowRoom(Map<String, Object> map) {
		return ebsBoothDao.queryAllShowRoom(map);
	}

	@Override
	public List<String> queryAllCompany(Map<String, Object> map) {
		return ebsBoothDao.queryAllCompany(map);
	}

	@Override
	public int releaseById(Integer id) {
		return ebsBoothDao.releaseById(id);
	}

	@Override
	public List<EbsBooth> getBoothList(String id) {
		return ebsBoothDao.getBoothList(id);
	}

	@Override
	public int releaseCompanyBooth(int companyId, int sessionId) {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("sessionId", sessionId);
		return ebsBoothDao.releaseCompanyBooth(map);
	}

	@Override
	public List<EbsBooth> GetBoothListByTradingGrounIdAndSession(
			Map<String, Object> map) {
		return ebsBoothDao.GetBoothListByTradingGrounIdAndSession(map);
	}

}
