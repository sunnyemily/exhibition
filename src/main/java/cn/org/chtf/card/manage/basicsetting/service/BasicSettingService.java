package cn.org.chtf.card.manage.basicsetting.service;

import cn.org.chtf.card.manage.basicsetting.pojo.BasicSetting;

import cn.org.chtf.card.support.util.ResultModel;

public interface BasicSettingService {
	public ResultModel getBasicSetting(int bsid);
	public ResultModel updateBasicSetting(BasicSetting basic);
}
