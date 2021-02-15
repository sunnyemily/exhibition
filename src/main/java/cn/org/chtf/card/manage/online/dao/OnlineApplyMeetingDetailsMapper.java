package cn.org.chtf.card.manage.online.dao;

import cn.org.chtf.card.manage.online.model.OnlineApplyMeetingDetails;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * DAO
 * @author lm
 */
public interface OnlineApplyMeetingDetailsMapper {

    /**
     * 通过id查询单个
     */
    OnlineApplyMeetingDetails findById(Integer id);

    /**
     * 通过map查询单个
     */
    List<OnlineApplyMeetingDetails> findByMap(Map<String, Object> map);

    /**
     * 查询列表
     */
    List<OnlineApplyMeetingDetails> list(Map<String, Object> map);

    /**
     * 新增
     */
    int save(OnlineApplyMeetingDetails onlineApplyMeetingDetails);

    /**
     * 修改
     */
    int update(OnlineApplyMeetingDetails onlineApplyMeetingDetails);

    /**
     * 删除
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个
     */
    int listcount(Map<String, Object> map);

	void updateHost(@Param("iMeetid") int iMeetid);

	void updateToHost(@Param("iMeetid") int iMeetid, @Param("hostPhone") String hostPhone);

}
