package cn.org.chtf.card.manage.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.system.dao.SysReservationSettingMapper;
import cn.org.chtf.card.manage.system.model.SysReservationSetting;
import cn.org.chtf.card.manage.system.service.SysReservationSettingService;

/**
 * 线上预约详细ServiceImpl
 * @author lm
 */
@Service
public class SysReservationSettingServiceImpl implements SysReservationSettingService {

    @Autowired
    private SysReservationSettingMapper sysReservationSettingDao;

    /**
     * 查询线上预约详细列表
     */
    @Override
    public List<SysReservationSetting> list(Map<String, Object> map) {
        return sysReservationSettingDao.list(map);
    }
    
    /**
     * 数量线上预约详细
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return sysReservationSettingDao.listcount(map);
    }


    /**
     * 通过id查询单个线上预约详细
     */
    @Override
    public SysReservationSetting findById(Integer id) {
        return sysReservationSettingDao.findById(id);
    }

    /**
     * 通过map查询单个线上预约详细
     */
    @Override
    public List<SysReservationSetting> findByMap(Map<String, Object> map) {
        return sysReservationSettingDao.findByMap(map);
    }

    /**
     * 新增线上预约详细
     */
    @Override
    public int save(SysReservationSetting sysReservationSetting) {
        return sysReservationSettingDao.save(sysReservationSetting);
    }

    /**
     * 修改线上预约详细
     */
    @Override
    public int update(SysReservationSetting sysReservationSetting) {
        return sysReservationSettingDao.update(sysReservationSetting);
    }

    /**
     * 删除线上预约详细
     */
    @Override
    public int deleteById(Integer id) {
        return sysReservationSettingDao.deleteById(id);
    }

	@Override
	public List<Map<String, Object>> SearchByDateTimeList(
			Map<String, Object> map) {
		return sysReservationSettingDao.SearchByDateTimeList(map);
	}

	@Override
	public int SearchByDateTimeListCount(Map<String, Object> map) {
		return sysReservationSettingDao.SearchByDateTimeListCount(map);
	}

	@Override
	public int canReserve(Integer sessionId, String exhibitiondate) {
		return sysReservationSettingDao.canReserve(sessionId,exhibitiondate);
	}

	
}
