package cn.org.chtf.card.manage.membersession.service;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.membersession.pojo.MemberSession;


/**
 * 参展商管理-展厅类型Service
 * @author lm
 */
public interface MemberSessionService {

	void insert(MemberSession ms);

	List<Map<String, Object>> getNonCurrentSession(Map<String, Object> map);

	void delete(Map<String, Object> map);

	List<MemberSession> findByMap(Map<String, Object> canshu);

	List<MemberSession> GetMemberSession(Map<String, Object> params);
	
}
