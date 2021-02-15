package cn.org.chtf.card.manage.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.support.util.PageModel;

public interface MemberDAO {

    int insert(Member record);
    
    int delete(Integer memberId);
    int deletes(@Param("memberIdList") List<Integer> memberIdList);

    int update(Member record);

    Member select(Integer memberId);
    
    int exist(String memberUsername);

    Member login(Member record);

    Member historyAccount(Member record);
    
    Member getForgotMember(@Param("companyName") String companyName,@Param("email") String email,@Param("phone") String phone,@Param("sessionId") String sessionId);
    
    Member getMemberByEmail(String memberEmail);
    
    List<Member> getMembers(PageModel page);
    
    int getTotal(PageModel page);

	void updateMemberByLoginname(Member hy);
    
    Map<String,Object> getBoothProcess(@Param("memberId") Integer memberId,@Param("sessionId") Integer sessionId);
    
    List<Map<String,Object>> getCardProcess(@Param("ids") List<Integer> ids,@Param("sessionId") Integer sessionId,
    		@Param("memberId") Integer memberId,@Param("status") Integer status,
    		@Param("printstatus") Integer printstatus,@Param("isplastic") Integer isplastic);

	List<Member> findByMap(Map<String, Object> map);
	/**
	 * 获取零散参展商可办证件数量
	 * @param memberId 会员id
	 * @param sessionId 届次id
	 * @return exhibitionCount 可办参展证总数
	 * @return decoratorCount 可办布撤展总数
	 */
	Map<String,Object> getExhibitorCertificationCount(@Param("memberId") Integer memberId,@Param("sessionId") Integer sessionId);
	/**
	 * 获取交易团类型的会员可参展证数量
	 * @param memberId 会员id
	 * @param sessionId 届次id
	 * @return exhibitionCount 可办参展证总数
	 * @return decoratorCount 可办布撤展总数
	 */
	Integer getTraddingGroupExhibitionCount(@Param("memberId") Integer memberId,@Param("sessionId") Integer sessionId);
    /**
     * 获取指定届次指定企业的会员
     * @param companyId
     * @param sessionId
     * @return
     */
    List<Member> getRegistedMembers(@Param("companyId") Integer companyId,@Param("sessionId") Integer sessionId);
    /**获取最新零散参展商会员信息
     * @param companyId
     * @param sessionId
     * @return
     */
    Map<String,Object> getScatterExhibition(@Param("companyId") Integer companyId,@Param("sessionId") Integer sessionId);
    /**
     * 获取不包含指定届次的指定企业的会员
     * @param companyId
     * @param sessionId
     * @return
     */
    List<Member> getHistoryRegistedMembers(@Param("companyId") Integer companyId,@Param("sessionId") Integer sessionId);

    List<Map<String,Object>> getMemberByTradingGroupId(Map<String, Object> map);
}