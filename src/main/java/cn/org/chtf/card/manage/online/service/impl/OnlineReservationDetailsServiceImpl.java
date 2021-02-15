package cn.org.chtf.card.manage.online.service.impl;

import cn.org.chtf.card.manage.online.model.OnlineReservationDetails;
import cn.org.chtf.card.manage.online.service.OnlineReservationDetailsService;
import cn.org.chtf.card.manage.online.dao.OnlineReservationDetailsMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 线上预约ServiceImpl
 * @author lm
 */
@Service
public class OnlineReservationDetailsServiceImpl implements OnlineReservationDetailsService {

    @Autowired
    private OnlineReservationDetailsMapper onlineReservationDetailsDao;

    /**
     * 查询线上预约列表
     */
    @Override
    public List<OnlineReservationDetails> list(Map<String, Object> map) {
        return onlineReservationDetailsDao.list(map);
    }
    
    /**
     * 数量线上预约
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return onlineReservationDetailsDao.listcount(map);
    }


    /**
     * 通过id查询单个线上预约
     */
    @Override
    public OnlineReservationDetails findById(Integer id) {
        return onlineReservationDetailsDao.findById(id);
    }

    /**
     * 通过map查询单个线上预约
     */
    @Override
    public List<OnlineReservationDetails> findByMap(Map<String, Object> map) {
        return onlineReservationDetailsDao.findByMap(map);
    }

    /**
     * 新增线上预约
     */
    @Override
    public int save(OnlineReservationDetails onlineReservationDetails) {
        return onlineReservationDetailsDao.save(onlineReservationDetails);
    }

    /**
     * 修改线上预约
     */
    @Override
    public int update(OnlineReservationDetails onlineReservationDetails) {
        return onlineReservationDetailsDao.update(onlineReservationDetails);
    }

    /**
     * 删除线上预约
     */
    @Override
    public int deleteById(Integer id) {
        return onlineReservationDetailsDao.deleteById(id);
    }

	@Override
	public void updateSecretKey(Map<String, Object> param) {
		onlineReservationDetailsDao.updateSecretKey(param);		
	}

}
