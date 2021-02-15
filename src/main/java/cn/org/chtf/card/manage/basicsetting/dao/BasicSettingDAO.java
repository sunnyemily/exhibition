package cn.org.chtf.card.manage.basicsetting.dao;

import cn.org.chtf.card.manage.basicsetting.pojo.BasicSetting;

public interface BasicSettingDAO {

    int insert(BasicSetting record);
    
    int delete(Integer bsId);

    int update(BasicSetting record);

    BasicSetting select(Integer bsId);
}