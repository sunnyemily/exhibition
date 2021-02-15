package cn.org.chtf.card.manage.basicsetting.controller;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.manage.basicsetting.pojo.BasicSetting;
import cn.org.chtf.card.manage.basicsetting.service.BasicSettingService;

import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WMail;

@RestController
public class BasicSettingController {

	@Resource(name = "BasicSettingServiceImpl")
	BasicSettingService basicSetting;
	@RequestMapping(value="/manage/system/getBasicSetting")
	public ResultModel getBasicSetting(int bsid) {
		return basicSetting.getBasicSetting(bsid);
	}
	@RequestMapping(value="/manage/system/updateBasicSetting")
	public ResultModel updateBasicSetting(BasicSetting basic) {
		return basicSetting.updateBasicSetting(basic);
	}
}
