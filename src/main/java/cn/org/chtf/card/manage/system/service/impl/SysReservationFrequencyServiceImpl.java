package cn.org.chtf.card.manage.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.system.dao.SysReservationFrequencyMapper;
import cn.org.chtf.card.manage.system.service.SysReservationFrequencyService;

@Service
public class SysReservationFrequencyServiceImpl implements SysReservationFrequencyService{
	 
	@Autowired
	private SysReservationFrequencyMapper sysReservationFrequencyDao;
	
	/**
     * 查询预约取证详细列表
     */
    @Override
    public List<Map<String, Object>> list(Map<String, Object> map) {
        return sysReservationFrequencyDao.list(map);
    }
    
    /**
     * 删除预约取证详细列表
     */
    @Override
    public int deleteById(int id) {
    	return sysReservationFrequencyDao.deleteById(id);
    }
    
    /**
     * 查询预约取证详细列表
     */
    @Override
    public int saveData(Map<String, Object> map) {
    	return sysReservationFrequencyDao.saveData(map);
    }
    
    /**
     * 查询预约取证详细列表
     */
    @Override
    public int updateData(Map<String, Object> map) {
    	return sysReservationFrequencyDao.updateData(map);
    }
    
    /**
     * 查询预约取证详细列表
     */
    @Override
    public Map<String, Object> findById(Map<String, Object> map) {
    	return sysReservationFrequencyDao.findById(map);
    }
    /**
     * 查询预约取证记录总数
     */
    @Override
    public int listcount(Map<String, Object> map) {
    	return sysReservationFrequencyDao.listcount(map);
    }
    /**
     * 判断预约时间是否重复
     */
    @Override
    public int selectTime(Map<String, Object> map) {
    	return sysReservationFrequencyDao.selectTime(map);
    }
    
}
