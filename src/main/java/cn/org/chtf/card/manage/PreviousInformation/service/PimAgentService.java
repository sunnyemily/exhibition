package cn.org.chtf.card.manage.PreviousInformation.service;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgent;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgenttype;
import cn.org.chtf.card.manage.member.pojo.Member;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 历届信息管理-代办员信息Service
 * @author ggwudivs
 */
public interface PimAgentService {

	/**
	 * 查询历届信息管理-代办员信息列表
	 */
	List<PimAgent> list(Map<String, Object> map);

	/**
	 * 通过id查询单个历届信息管理-代办员信息
	 */
    //PimAgent findById(Integer id);
	PimAgent findById(Integer id, String session);
	
	/**
	 * 通过map查询单个历届信息管理-代办员信息
	 */
	List<PimAgent> findByMap(Map<String, Object> map);

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
	
	int listcount(Map<String, Object> map);

	int saveAgentType(List<PimAgenttype> list);

	void deleteTypeByAgentId(Integer valueOf);

	void addMember(Member hy);

	void updateMemberByLoginname(Member hy);

	void UsePimAgent(Map<String, Object> map);
	/**
	 * @author wushixing
	 * @apiNote 根据会员id和届次id获取代理
	 * @param memberId
	 * @param sessionId
	 * @return
	 */
	PimAgent getAgentByMemberIdAndSessionId(Integer memberId,Integer sessionId);

	/**
	 * @author wushixing
	 * @apiNote 根据会员id和届次id获取可办证件类型
	 * @param memberId
	 * @param sessionId
	 * @return
	 */
	List<PimAgenttype> getAgenttypeByMemberIdAndSessionId(Integer memberId, Integer sessionId);

	R UseAgent(Map<String, Object> map);

	void deleteTypeByAgentIdAndSession(Map<String, Object> para);

	List<PimAgent> Previouslist(Map<String, Object> map);

	int PreviouslistCount(Map<String, Object> map);

	
}
