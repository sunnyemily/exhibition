package cn.org.chtf.card.manage.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.system.dao.SysReservationCentificateMapper;
import cn.org.chtf.card.manage.system.service.SysReservationCentificateService;;

/**
 * 预约取证名额管理
 * @author guo
 *
 */
@Service
public class SysReservationCentificateServiceImpl implements SysReservationCentificateService {
	
	@Autowired
	private SysReservationCentificateMapper sysReservationCentificateMapper;
	
	 /**
     * 查询预约取证详细列表
     */
    @Override
    public List<Map<String, Object>> list(Map<String, Object> map) {
        return sysReservationCentificateMapper.list(map);
    }
    
    /**
     * 删除预约取证详细列表
     */
    @Override
    public int deleteById(int id) {
    	return sysReservationCentificateMapper.deleteById(id);
    }
    
    /**
     * 查询预约取证详细列表
     */
    @Override
    public int saveData(Map<String, Object> map) {
    	return sysReservationCentificateMapper.saveData(map);
    }
    
    /**
     * 查询预约取证详细列表
     */
    @Override
    public int updateData(Map<String, Object> map) {
    	return sysReservationCentificateMapper.updateData(map);
    }
    
    /**
     * 查询预约取证详细列表
     */
    @Override
    public Map<String, Object> findById(Map<String, Object> map) {
    	return sysReservationCentificateMapper.findById(map);
    }
    
    @Override
    public int listcount(Map<String, Object> map) {
    	return sysReservationCentificateMapper.listcount(map);
    }
    @Override
    public int selectDate(Map<String, Object> map) {
    	return sysReservationCentificateMapper.selectDate(map);
    }
}
