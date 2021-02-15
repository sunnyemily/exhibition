package cn.org.chtf.card.manage.memberlog.dao;

import cn.org.chtf.card.manage.memberlog.pojo.MemberLog;

public interface MemberLogDAO {
    int deleteByPrimaryKey(Integer mlogId);

    int insert(MemberLog record);

    MemberLog selectByPrimaryKey(Integer mlogId);

    int updateByPrimaryKeySelective(MemberLog record);

    int updateByPrimaryKeyWithBLOBs(MemberLog record);

    int updateByPrimaryKey(MemberLog record);
}