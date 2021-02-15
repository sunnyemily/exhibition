package cn.org.chtf.card.manage.membersession.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.membersession.dao.MemberSessionMapper;
import cn.org.chtf.card.manage.membersession.pojo.MemberSession;
import cn.org.chtf.card.manage.membersession.service.MemberSessionService;



@Service("MemberSessionServiceImpl")
public class MemberSessionServiceImpl implements MemberSessionService {

    @Autowired
    private MemberSessionMapper memberSessionMapper;

	@Override
	public void insert(MemberSession ms) {
		memberSessionMapper.insert(ms);
	}

	@Override
	public List<Map<String, Object>> getNonCurrentSession(Map<String, Object> map) {
		return memberSessionMapper.getNonCurrentSession(map);
	}

	@Override
	public void delete(Map<String, Object> map) {
		memberSessionMapper.delete(map);
	}

	@Override
	public List<MemberSession> findByMap(Map<String, Object> map) {
		return memberSessionMapper.findByMap(map);
	}

	@Override
	public List<MemberSession> GetMemberSession(Map<String, Object> params) {
		return memberSessionMapper.GetMemberSession(params);
	}
    
    

}
