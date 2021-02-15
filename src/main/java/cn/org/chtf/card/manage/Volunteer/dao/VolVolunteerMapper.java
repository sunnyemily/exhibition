package cn.org.chtf.card.manage.Volunteer.dao;

import cn.org.chtf.card.manage.Volunteer.model.VolVolunteer;

import java.util.List;
import java.util.Map;


/**
 * 志愿者信息DAO
 * @author lm
 */
public interface VolVolunteerMapper {

    /**
     * 通过id查询单个志愿者信息
     */
    VolVolunteer findById(Integer id);

    /**
     * 通过map查询单个志愿者信息
     */
    List<VolVolunteer> findByMap(Map<String, Object> map);

    /**
     * 查询志愿者信息列表
     */
    List<VolVolunteer> list(Map<String, Object> map);

    /**
     * 新增志愿者信息
     */
    int save(VolVolunteer volVolunteer);

    /**
     * 修改志愿者信息
     */
    int update(VolVolunteer volVolunteer);

    /**
     * 删除志愿者信息
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个志愿者信息
     */
    int listcount(Map<String, Object> map);

}
