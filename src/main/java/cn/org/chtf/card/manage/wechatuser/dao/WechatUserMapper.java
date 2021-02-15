package cn.org.chtf.card.manage.wechatuser.dao;

import cn.org.chtf.card.manage.wechatuser.pojo.WechatUser;

public interface WechatUserMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(WechatUser record);

    int insertSelective(WechatUser record);

    WechatUser selectByPrimaryKey(Integer uid);

    WechatUser selectByRoutineOpenid(String routineOpenid);

    int updateByPrimaryKeySelective(WechatUser record);

    int updateByRoutineOpenidSelective(WechatUser record);

    int updateByPrimaryKey(WechatUser record);

	int isExists(Integer companyid);

	WechatUser getUserInfoByCompanyid(int companyid);
}