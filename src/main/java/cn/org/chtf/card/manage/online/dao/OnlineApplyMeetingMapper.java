package cn.org.chtf.card.manage.online.dao;

import cn.org.chtf.card.manage.online.model.OnlineApplyMeeting;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * DAO
 * @author lm
 */
public interface OnlineApplyMeetingMapper {

    /**
     * 通过id查询单个
     */
    OnlineApplyMeeting findById(Integer id);

    /**
     * 通过map查询单个
     */
    List<OnlineApplyMeeting> findByMap(Map<String, Object> map);

    /**
     * 查询列表
     */
    List<OnlineApplyMeeting> list(Map<String, Object> map);

    /**
     * 新增
     */
    int save(OnlineApplyMeeting onlineApplyMeeting);

    /**
     * 修改
     */
    int update(OnlineApplyMeeting onlineApplyMeeting);

    /**
     * 删除
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个
     */
    int listcount(Map<String, Object> map);

	int deleteMeetingdetailsID(Integer id);

	int addmeetingdetails(List<Map<String, Object>> list);

	String GetZhuChiRenShouJiHao(@Param("iMeetid") int iMeetid);

}
