package cn.org.chtf.card.manage.online.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.online.dao.ReservationErhaltenZertifikatMapper;
import cn.org.chtf.card.manage.online.service.ReservationErhaltenZertifikatService;

@Service
public class ReservationErhaltenZertifikatImpl implements ReservationErhaltenZertifikatService{
	
	@Autowired
	private ReservationErhaltenZertifikatMapper reservationErhaltenZertifikatDao;
	
	/**
     * 查询线上预约列表
     */
    @Override
    public List<Map<String, Object>> list(Map<String, Object> map) {
        return reservationErhaltenZertifikatDao.list(map);
    }
    
    /**
     * 查询预约取证的统计数
     */
    @Override
    public int listcount(Map<String, Object> map) {
    	return reservationErhaltenZertifikatDao.listcount(map);
    }
    
    /**
     * 删除预约取证信息
     */
    @Override
    public int deleteById(int id) {
    	return reservationErhaltenZertifikatDao.deleteById(id);
    }
    /**
     * 查询预约取证的统计数
     */
    @Override
    public Map<String, Object> StatisticsInfo(Map<String, Object> map) {
    	return reservationErhaltenZertifikatDao.StatisticsInfo(map);
    }
    /**
     * 按时间段查询预约取证信息
     */
    @Override
    public int total(Map<String, Object> map) {
    	return reservationErhaltenZertifikatDao.total(map);
    }
}
