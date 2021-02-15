package cn.org.chtf.card.manage.Volunteer.service.impl;

import cn.org.chtf.card.manage.Volunteer.model.VolVolunteerattendance;
import cn.org.chtf.card.manage.Volunteer.service.VolVolunteerattendanceService;
import cn.org.chtf.card.manage.Volunteer.dao.VolVolunteerattendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 志愿者考勤ServiceImpl
 * @author lm
 */
@Service
public class VolVolunteerattendanceServiceImpl implements VolVolunteerattendanceService {

    @Autowired
    private VolVolunteerattendanceMapper volVolunteerattendanceDao;

    /**
     * 查询志愿者考勤列表
     */
    @Override
    public List<VolVolunteerattendance> list(Map<String, Object> map) {
        return volVolunteerattendanceDao.list(map);
    }
    
    /**
     * 数量志愿者考勤
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return volVolunteerattendanceDao.listcount(map);
    }


    /**
     * 通过id查询单个志愿者考勤
     */
    @Override
    public VolVolunteerattendance findById(Integer id) {
        return volVolunteerattendanceDao.findById(id);
    }

    /**
     * 通过map查询单个志愿者考勤
     */
    @Override
    public List<VolVolunteerattendance> findByMap(Map<String, Object> map) {
        return volVolunteerattendanceDao.findByMap(map);
    }

    /**
     * 新增志愿者考勤
     */
    @Override
    public int save(VolVolunteerattendance volVolunteerattendance) {
        return volVolunteerattendanceDao.save(volVolunteerattendance);
    }

    /**
     * 修改志愿者考勤
     */
    @Override
    public int update(VolVolunteerattendance volVolunteerattendance) {
        return volVolunteerattendanceDao.update(volVolunteerattendance);
    }

    /**
     * 删除志愿者考勤
     */
    @Override
    public int deleteById(Integer id) {
        return volVolunteerattendanceDao.deleteById(id);
    }

}
