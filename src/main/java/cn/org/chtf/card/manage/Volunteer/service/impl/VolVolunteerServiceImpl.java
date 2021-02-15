package cn.org.chtf.card.manage.Volunteer.service.impl;

import cn.org.chtf.card.manage.Volunteer.model.VolVolunteer;
import cn.org.chtf.card.manage.Volunteer.service.VolVolunteerService;
import cn.org.chtf.card.manage.Volunteer.dao.VolVolunteerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 志愿者信息ServiceImpl
 * @author lm
 */
@Service
public class VolVolunteerServiceImpl implements VolVolunteerService {

    @Autowired
    private VolVolunteerMapper volVolunteerDao;

    /**
     * 查询志愿者信息列表
     */
    @Override
    public List<VolVolunteer> list(Map<String, Object> map) {
        return volVolunteerDao.list(map);
    }
    
    /**
     * 数量志愿者信息
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return volVolunteerDao.listcount(map);
    }


    /**
     * 通过id查询单个志愿者信息
     */
    @Override
    public VolVolunteer findById(Integer id) {
        return volVolunteerDao.findById(id);
    }

    /**
     * 通过map查询单个志愿者信息
     */
    @Override
    public List<VolVolunteer> findByMap(Map<String, Object> map) {
        return volVolunteerDao.findByMap(map);
    }

    /**
     * 新增志愿者信息
     */
    @Override
    public int save(VolVolunteer volVolunteer) {
        return volVolunteerDao.save(volVolunteer);
    }

    /**
     * 修改志愿者信息
     */
    @Override
    public int update(VolVolunteer volVolunteer) {
        return volVolunteerDao.update(volVolunteer);
    }

    /**
     * 删除志愿者信息
     */
    @Override
    public int deleteById(Integer id) {
        return volVolunteerDao.deleteById(id);
    }

}
