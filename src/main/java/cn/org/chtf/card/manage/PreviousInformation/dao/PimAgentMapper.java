package cn.org.chtf.card.manage.PreviousInformation.dao;

import cn.org.chtf.card.manage.PreviousInformation.model.PimAgent;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgenttype;
import cn.org.chtf.card.manage.member.pojo.Member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * 历届信息管理-代办员信息DAO
 * @author ggwudivs
 */
public interface PimAgentMapper {

    /**
     * 通过id查询单个历届信息管理-代办员信息
     * @param session 
     */
    PimAgent findById(@Param("id") Integer id,@Param("session")String session);

    /**
     * 通过map查询单个历届信息管理-代办员信息
     */
    List<PimAgent> findByMap(Map<String, Object> map);

    /**
     * 查询历届信息管理-代办员信息列表
     */
    List<PimAgent> list(Map<String, Object> map);

    /**
     * 新增历届信息管理-代办员信息
     */
    int save(PimAgent pimAgent);

    /**
     * 修改历届信息管理-代办员信息
     */
    int update(PimAgent pimAgent);

    /**
     * 删除历届信息管理-代办员信息
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个历届信息管理-代办员信息
     */
    int listcount(Map<String, Object> map);

	int saveAgentType(List<PimAgenttype> list);

	void deleteTypeByAgentId(Integer id);

	List<PimAgenttype> getPimAgentTypeByAgentID(Integer id);	
	/**
	 * 获取登录用户指定届次的代办员信息
	 * @author wushixing
	 * @param memberId
	 * @param sessionId
	 * @return
	 */
	PimAgent getAgentByMemberIdAndSessionId(@Param("memberId")Integer memberId,@Param("sessionId")Integer sessionId);

	void deleteTypeByAgentIdAndSession(Map<String, Object> para);

	List<PimAgent> Previouslist(Map<String, Object> map);

	int PreviouslistCount(Map<String, Object> map);

}
