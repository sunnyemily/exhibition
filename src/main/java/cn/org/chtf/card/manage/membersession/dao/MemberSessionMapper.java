package cn.org.chtf.card.manage.membersession.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.membersession.pojo.MemberSession;

public interface MemberSessionMapper {
    int insert(MemberSession record);

    int insertSelective(MemberSession record);
    
    Integer getMemberIdByOrgnizationId(Integer orgnizationId);

	List<MemberSession> getMemberSessionsByMemberId(Map<String, Object> params);

	List<MemberSession> findByMap(Map<String, Object> par);

	List<Map<String, Object>> getNonCurrentSession(Map<String, Object> map);
	
	List<Map<String, Object>> getMemberSessionsByMemId(int memberId);

	void delete(Map<String, Object> map);

	void delete(int memberid);

	List<MemberSession> GetMemberSession(Map<String, Object> params);

	String GetLoginName(Map<String, Object> obj);

	String GetJYTLoginName(Map<String, Object> obj);
}