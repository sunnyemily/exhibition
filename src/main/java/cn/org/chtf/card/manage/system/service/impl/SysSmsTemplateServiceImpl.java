package cn.org.chtf.card.manage.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsSendMessageLogMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsSendMessageLog;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.system.dao.SysSmsTemplateMapper;
import cn.org.chtf.card.manage.system.model.SysSmsTemplate;
import cn.org.chtf.card.manage.system.service.SysSmsTemplateService;
import cn.org.chtf.card.support.util.SMSUtil;
import cn.org.chtf.card.support.util.http.HttpResult;
import cn.org.chtf.card.support.util.tencentyun.TencentSMSUtil;

/**
 * ServiceImpl
 * @author lm
 */
@Service
@Slf4j
public class SysSmsTemplateServiceImpl implements SysSmsTemplateService {

    @Autowired
    private SysSmsTemplateMapper sysSmsTemplateDao;

    @Autowired
    private EbsSendMessageLogMapper ebsSendMessageLogDao;

    //是否启用腾讯短信
    private Boolean isNew = true;

	@Value("${exhibition.decorator.sms.auditAgree.date}")
	private String decoratorAuditAgreeDate;

	@Value("${exhibition.decorator.sms.auditAgree.deposit}")
	private String decoratorAuditAgreeDeposit;

	@Value("${exhibition.decorator.sms.auditAgree.contact.person}")
	private String decoratorAuditAgreePerson;

	@Value("${exhibition.decorator.sms.auditAgree.contact.telephone}")
	private String decoratorAuditAgreeTelephone;

    /**
     * 查询列表
     */
    @Override
    public List<SysSmsTemplate> list(Map<String, Object> map) {
        return sysSmsTemplateDao.list(map);
    }

    /**
     * 数量
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return sysSmsTemplateDao.listcount(map);
    }


    /**
     * 通过sms_id查询单个
     */
    @Override
    public SysSmsTemplate findById(Integer smsId) {
        return sysSmsTemplateDao.findById(smsId);
    }

    /**
     * 通过map查询单个
     */
    @Override
    public List<SysSmsTemplate> findByMap(Map<String, Object> map) {
        return sysSmsTemplateDao.findByMap(map);
    }

    /**
     * 新增
     */
    @Override
    public int save(SysSmsTemplate sysSmsTemplate) {
        return sysSmsTemplateDao.save(sysSmsTemplate);
    }

    /**
     * 修改
     */
    @Override
    public int update(SysSmsTemplate sysSmsTemplate) {
        return sysSmsTemplateDao.update(sysSmsTemplate);
    }

    /**
     * 删除
     */
    @Override
    public int deleteById(Integer smsId) {
        return sysSmsTemplateDao.deleteById(smsId);
    }
    /**
     * 发送注册短信验证码
     * @param phone 接收人手机号
     * @param sessionId 届次id
     * @param session session
     * @return
     * @throws Exception
     */
    public Boolean sendRegistValidateSMS(String phone,String companyName,Integer sessionId,HttpSession session) throws Exception {
    	Boolean result = false;
    	if(isNew){
    		String validateCode = TencentSMSUtil.getRandomString();
	    	//1.1将验证码存入session
		    session.removeAttribute(SMSUtil.REGIST_CODEKEY);
		    session.setAttribute(SMSUtil.REGIST_CODEKEY,validateCode);
    		String BianHao = TencentSMSUtil.PHONE_VALIDATE_TEMPLATE_NEWID;
    		String[] params = new String[1];
		    params[0] = validateCode;
		    String[] receivers = {"86 "+phone};
			SendSmsResponse smsResult = TencentSMSUtil.sendSMS(receivers, params, BianHao);
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(BianHao);
	    	eml.setNumber(phone);
	    	eml.setContent(JSONObject.toJSONString(smsResult));
	    	eml.setReceiver(phone);
	    	eml.setSendcode(smsResult.getSendStatusSet()[0].getCode());
	    	ebsSendMessageLogDao.save(eml);

	    	if("Ok".equals(smsResult.getSendStatusSet()[0].getCode())) {
	    		result = true;
			}
    	}
    	else{
	    	String smsContent = sysSmsTemplateDao.findByTitleAndSessionId(TencentSMSUtil.PHONE_VALIDATE_CODE, sessionId).getSmsTemplate();
	    	Map<String,Object> param = new HashMap<String,Object>();
	    	//1.设置验证码
	    	String validateCode = TencentSMSUtil.getRandomString();
	    	param.put("phoneValidateCode", validateCode);
	    	//1.1将验证码存入session
		    session.removeAttribute(SMSUtil.REGIST_CODEKEY);
		    session.setAttribute(SMSUtil.REGIST_CODEKEY,validateCode);
	    	//2.设置接收通知的公司名字
	    	param.put("companyName", companyName);
	    	//3.处理参数
	    	smsContent = SMSUtil.processParameter(smsContent, param);
	    	//4.设置接收人
	    	String[] receivers = {phone};
	    	//5.发送短信
	    	HttpResult sendResult = SMSUtil.sendSMS(receivers, smsContent);
	    	//记录日志
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(TencentSMSUtil.PHONE_VALIDATE_CODE);
	    	eml.setNumber(phone);
	    	eml.setContent(smsContent);
	    	eml.setReceiver(companyName);
	    	eml.setSendcode(sendResult.getCode().toString());
	    	ebsSendMessageLogDao.save(eml);
	    	if(sendResult.getCode()==200&&Long.valueOf(sendResult.getBody())>0) {
	    		result = true;
			}
    	}
//以下代码为腾讯短信发送代码，待切换时统一切换
//    	//1.设置验证码
//    	Map<String,Object> param = new HashMap<String,Object>();
//
//    	String validateCode = TencentSMSUtil.getRandomString();
//    	param.put("phoneValidateCode", validateCode);
//    	//1.1将验证码存入session
//	    session.removeAttribute(TencentSMSUtil.REGIST_CODEKEY);
//	    session.setAttribute(TencentSMSUtil.REGIST_CODEKEY,validateCode);
//    	//2.参数
//	    String[] params = new String[3];
//	    params[0] = companyName;
//        params[1] = "您的";
//        params[2] = validateCode;
//    	//3.设置接收人
//    	String[] receivers = {phone};
//    	//4.发送短信
//    	SendSmsResponse smsResult = TencentSMSUtil.sendSMS(receivers, params,TencentSMSUtil.PHONE_VALIDATE_TEMPLATE_ID);
//    	//记录日志
//    	EbsSendMessageLog eml = new EbsSendMessageLog();
//    	eml.setType(TencentSMSUtil.PHONE_VALIDATE_CODE);
//    	eml.setNumber(phone);
//    	eml.setContent(SendSmsResponse.toJsonString(smsResult));
//    	eml.setReceiver(companyName);
//    	eml.setSendcode(smsResult.getRequestId());
//    	ebsSendMessageLogDao.save(eml);
//    	
//    	/**
//    	 * 有一个没发送成功都算失败了
//    	 */
//    	boolean smsSendResult = true;
//    	for(SendStatus status : smsResult.getSendStatusSet()) {
//    		if(!status.getCode().equals("OK")) {
//    			smsSendResult = false;
//    		}
//    	}
//    	result = smsSendResult;

    	return result;
    }
    /**
     * 发送注册成功短信
     * @param phone 接收人手机号码
     * @param companyName 注册公司名称
     * @param userName 用户名
     * @param password 密码
     * @param sessionId 届次id
     * @param memberType 会员类型id
     * @return
     * @throws Exception
     */
    public Boolean sendRegistSucessSMS(String phone,String companyName,String userName,String password,Integer sessionId, Integer memberType) throws Exception {
    	Boolean result = false;
    	if(isNew){
	    	String BianHao = "";
	    	switch(memberType) {
				case Member.MEMBER_TYPE_DECORATOR_CODE://布撤展
					BianHao = TencentSMSUtil.BUCHEZHANSHANG_REGIST_SUCCESS_TEMPLATE_ID;
					break;
				case Member.MEMBER_TYPE_EXHIBITOR_CODE://零散
					BianHao = TencentSMSUtil.LINGSAN_REGIST_SUCCESS_TEMPLATE_ID;
					break;
	//			case Member.MEMBER_TYPE_TRADE_CODE:
	//				BianHao = TencentSMSUtil.LINGSAN_REGIST_SUCCESS_TEMPLATE_ID;
	//				break;
				case Member.MEMBER_TYPE_FOREIGN_CODE://外宾
					BianHao = TencentSMSUtil.WAIBIN_REGIST_SUCCESS_TEMPLATE_ID;
					break;
	//			case Member.MEMBER_TYPE_REPORT_CODE:
	//				BianHao = TencentSMSUtil.jizhe;
	//				break;
				case Member.MEMBER_TYPE_PURCHASER_CODE://采购商
					BianHao = TencentSMSUtil.CAIGOUSHANG_REGIST_SUCCESS_TEMPLATE_ID;
					break;
			}

	    	String[] params = new String[3];
		    params[0] = companyName;
		    params[1] = userName;
		    params[2] = password;
		    String[] receivers = {"86 "+phone};
			SendSmsResponse smsResult = TencentSMSUtil.sendSMS(receivers, params, BianHao);
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(BianHao);
	    	eml.setNumber(phone);
	    	eml.setContent(JSONObject.toJSONString(smsResult));
	    	eml.setReceiver(companyName);
	    	eml.setSendcode(smsResult.getSendStatusSet()[0].getCode());
	    	ebsSendMessageLogDao.save(eml);

	    	if("Ok".equals(smsResult.getSendStatusSet()[0].getCode())) {
	    		result = true;
			}
    	}
	    else{
	    	String smsContent = sysSmsTemplateDao.findByTitleAndSessionId(SMSUtil.REGIST_SUCCESS_CODE, sessionId).getSmsTemplate();
	    	String loginUrl = "";
	    	switch(memberType) {
	    		case Member.MEMBER_TYPE_DECORATOR_CODE:
	    			loginUrl = "https://card.hljlbh.org.cn/cn/decorator-login.html";
	    			break;
	    		case Member.MEMBER_TYPE_EXHIBITOR_CODE:
	    			loginUrl = "https://card.hljlbh.org.cn/cn/exhibitor-login.html";
	    			break;
	    		case Member.MEMBER_TYPE_TRADE_CODE:
	    			loginUrl = "https://card.hljlbh.org.cn/cn/delegation-login.html";
	    			break;
	    		case Member.MEMBER_TYPE_FOREIGN_CODE:
	    			loginUrl = "https://card.hljlbh.org.cn/cn/foreign-login.html";
	    			break;
	    		case Member.MEMBER_TYPE_REPORT_CODE:
	    			loginUrl = "https://card.hljlbh.org.cn/cn/reporter-login.html";
	    			break;
	    		case Member.MEMBER_TYPE_PURCHASER_CODE:
	    			loginUrl = "https://card.hljlbh.org.cn/cn/purchaser-login.html";
	    			break;
	    	}
	    	smsContent = smsContent.replace("${loginUrl}", loginUrl);

	    	Map<String,Object> param = new HashMap<String,Object>();
	    	//1.用户名参数
	    	param.put("userName", userName);
	    	//2.设置密码参数
	    	param.put("password", password);
	    	//3.设置接收通知的公司名字
	    	param.put("companyName", companyName);
	    	//4.处理参数
	    	smsContent = SMSUtil.processParameter(smsContent, param);
	    	//5.设置接收人
	    	String[] receivers = {phone};
	    	//6.发送短信
	    	HttpResult sendResult = SMSUtil.sendSMS(receivers, smsContent);
	    	//记录日志
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(SMSUtil.REGIST_SUCCESS_CODE);
	    	eml.setNumber(phone);
	    	eml.setContent(smsContent);
	    	eml.setReceiver(companyName);
	    	eml.setSendcode(sendResult.getCode().toString());
	    	ebsSendMessageLogDao.save(eml);

	    	if(sendResult.getCode()==200&&Long.valueOf(sendResult.getBody())>0) {
	    		result = true;
			}
	    }
	    return result;
    }

    /**
     *
     */
    public Boolean sendBoothConfirmSMS(String phone,String companyName,String boothNO,Integer sessionId,String type) throws Exception {
    	Boolean result = false;

    	if(isNew){
    		String BianHao = "";
    		if("零散参展商".equals(type)) {
    			BianHao = TencentSMSUtil.LINGSAN_BOOTH_ALLOCATE_TEMPLATE_ID;
        	}else if("交易团".equals(type)) {
        		BianHao = TencentSMSUtil.JIAOYITUAN_BOOTH_ALLOCATE_TEMPLATE_ID;
        	}else if("记者".equals(type)) {
        		return true;
        	}else {
        		return true;
        	}
    		String[] params = new String[2];
		    params[0] = companyName;
		    params[1] = boothNO;
		    String[] receivers = {"86 "+phone};
			SendSmsResponse smsResult = TencentSMSUtil.sendSMS(receivers, params, BianHao);
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(BianHao);
	    	eml.setNumber(phone);
	    	eml.setContent(JSONObject.toJSONString(smsResult));
	    	eml.setReceiver(companyName);
	    	eml.setSendcode(smsResult.getSendStatusSet()[0].getCode());
	    	ebsSendMessageLogDao.save(eml);

	    	if("Ok".equals(smsResult.getSendStatusSet()[0].getCode())) {
	    		result = true;
			}
    	}
    	else{
    		String smsContent = sysSmsTemplateDao.findByTitleAndSessionId(SMSUtil.BOOTH_CONFIRM_SUCCESS_CODE, sessionId).getSmsTemplate();
        	Map<String,Object> param = new HashMap<String,Object>();
        	//1.设置展位号
        	param.put("boothNO", boothNO);
        	//2.设置接收通知的公司名字
        	param.put("companyName", companyName);
        	//新增:发送短信类型
        	if("零散参展商".equals(type)) {
        		param.put("url", "https://card.hljlbh.org.cn/cn/exhibitor-login.html");
        	}else if("交易团".equals(type)) {
        		param.put("url", "https://card.hljlbh.org.cn/cn/delegation-login.html");
        	}else if("记者".equals(type)) {
        		param.put("url", "https://card.hljlbh.org.cn/cn/reporter-login.html");
        	}else {
        		param.put("url", "https://card.hljlbh.org.cn");
        	}
        	//3.处理参数
        	smsContent = SMSUtil.processParameter(smsContent, param);
        	//4.设置接收人
        	String[] receivers = {phone};
        	//5.发送短信
        	HttpResult sendResult = SMSUtil.sendSMS(receivers, smsContent);
        	//记录日志
        	EbsSendMessageLog eml = new EbsSendMessageLog();
        	eml.setType(SMSUtil.BOOTH_CONFIRM_SUCCESS_CODE);
        	eml.setNumber(phone);
        	eml.setContent(smsContent);
        	eml.setReceiver(companyName);
        	eml.setSendcode(sendResult.getCode().toString());
        	ebsSendMessageLogDao.save(eml);
        	if(sendResult.getCode()==200&&Long.valueOf(sendResult.getBody())>0) {
        		result = true;
    		}
    	}
    	return result;
    }
    /**
     * 发送取证报表短信
     * @param phone 接收人手机号码
     * @param companyName 注册公司名称
     * @param sessionId 届次id
     * @return
     * @throws Exception
     */
    public Boolean sendTakeReportSMS(String phone,String companyName,Integer sessionId) throws Exception {
    	Boolean result = false;
    	if(isNew){

    	}
    	else{
	    	String smsContent = sysSmsTemplateDao.findByTitleAndSessionId(SMSUtil.TAKE_REPORT_CODE, sessionId).getSmsTemplate();
	    	Map<String,Object> param = new HashMap<String,Object>();
	    	//1.设置接收通知的公司名字
	    	param.put("companyName", companyName);
	    	//2.处理参数
	    	smsContent = SMSUtil.processParameter(smsContent, param);
	    	//3.设置接收人
	    	String[] receivers = {phone};
	    	//4.发送短信
	    	HttpResult sendResult = SMSUtil.sendSMS(receivers, smsContent);
	    	//记录日志
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(SMSUtil.TAKE_REPORT_CODE);
	    	eml.setNumber(phone);
	    	eml.setContent(smsContent);
	    	eml.setReceiver(companyName);
	    	eml.setSendcode(sendResult.getCode().toString());
	    	ebsSendMessageLogDao.save(eml);
	    	if(sendResult.getCode()==200&&Long.valueOf(sendResult.getBody())>0) {
	    		result = true;
			}
    	}
    	return result;
    }
    /**
     * 发送代办员开通短信
     * @param phone 接收人手机号码
     * @param agentName 注册公司名称
     * @param userName 用户名
     * @param password 密码
     * @param sessionId 届次id
     * @return
     * @throws Exception
     */
    public Boolean sendOpenAgentSMS(String phone,String agentName,String userName,String password,Integer sessionId, Integer iType) throws Exception {
    	Boolean result = false;
    	if(isNew){
    		String BianHao = TencentSMSUtil.JIAOYITUAN_OPEN_TEMPLATE_ID;
    		if(iType==1){//记者登录地址替换
    			BianHao = TencentSMSUtil.JIZHE_OPEN_TEMPLATE_ID;
    		}
    		String[] params = new String[3];
		    params[0] = agentName;
		    params[1] = userName;
		    params[2] = password;
		    String[] receivers = {"86 "+phone};
			SendSmsResponse smsResult = TencentSMSUtil.sendSMS(receivers, params, BianHao);
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(BianHao);
	    	eml.setNumber(phone);
	    	eml.setContent(JSONObject.toJSONString(smsResult));
	    	eml.setReceiver(agentName);
	    	eml.setSendcode(smsResult.getSendStatusSet()[0].getCode());
	    	ebsSendMessageLogDao.save(eml);

	    	if("Ok".equals(smsResult.getSendStatusSet()[0].getCode())) {
	    		result = true;
			}
    	}
    	else{
	    	String smsContent = sysSmsTemplateDao.findByTitleAndSessionId(SMSUtil.AGENT_OPEN_CODE, sessionId).getSmsTemplate();
	    	Map<String,Object> param = new HashMap<String,Object>();
	    	//1.用户名参数
	    	param.put("userName", userName);
	    	//2.设置密码参数
	    	param.put("password", password);
	    	//3.设置接收通知的公司名字
	    	param.put("agentName", agentName);
	    	//4.处理参数
	    	smsContent = SMSUtil.processParameter(smsContent, param);
	    	if(iType==1){//记者登录地址替换
	    		smsContent=smsContent.replace("card.hljlbh.org.cn/cn/delegation-login.html", "card.hljlbh.org.cn/cn/reporter-login.html");
	    	}
	    	//5.设置接收人
	    	String[] receivers = {phone};
	    	//6.发送短信
	    	HttpResult sendResult = SMSUtil.sendSMS(receivers, smsContent);
	    	//记录日志
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(SMSUtil.AGENT_OPEN_CODE);
	    	eml.setNumber(phone);
	    	eml.setContent(smsContent);
	    	eml.setReceiver(agentName);
	    	eml.setSendcode(sendResult.getCode().toString());
	    	ebsSendMessageLogDao.save(eml);
	    	if(sendResult.getCode()==200&&Long.valueOf(sendResult.getBody())>0) {
	    		result = true;
			}
    	}
    	return result;
    }
    /**
     * 发送零散转交易团短信通知
     * @param phone 接收人手机号
     * @param companyName 公司名称
     * @param traddingGroupName 交易团名称
     * @param sessionId 届次id
     * @return
     * @throws Exception
     */
    public Boolean sendExhibitionToTraddingGroupSMS(String phone,String companyName,String traddingGroupName,String tradingGroupContactPerson,String tradingGroupContactPhone,Integer sessionId) throws Exception {
    	Boolean result = false;
    	if(isNew){
    		String BianHao = TencentSMSUtil.LINGSAN_TURN_JIAOYITUAN_TEMPLATE_ID;
    		String[] params = new String[2];
		    params[0] = companyName;
		    params[1] = traddingGroupName;
		    String[] receivers = {"86 "+phone};
			SendSmsResponse smsResult = TencentSMSUtil.sendSMS(receivers, params, BianHao);
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(BianHao);
	    	eml.setNumber(phone);
	    	eml.setContent(JSONObject.toJSONString(smsResult));
	    	eml.setReceiver(companyName);
	    	eml.setSendcode(smsResult.getSendStatusSet()[0].getCode());
	    	ebsSendMessageLogDao.save(eml);

	    	if("Ok".equals(smsResult.getSendStatusSet()[0].getCode())) {
	    		result = true;
			}
    	}
    	else{
	    	String smsContent = sysSmsTemplateDao.findByTitleAndSessionId(SMSUtil.EXHIBITOR_TO_TRADDINGGROUP_NOTICE_CODE, sessionId).getSmsTemplate();
	    	Map<String,Object> param = new HashMap<String,Object>();
	    	//1.设置公司名称
	    	param.put("companyName", companyName);
	    	//2.设置交易团名称
	    	param.put("traddingGroupName", traddingGroupName);
	    	//设置展区负责人
	    	param.put("tradingGroupContactPerson", tradingGroupContactPerson);
	    	//设置展区负责人电话
	    	param.put("tradingGroupContactPhone", tradingGroupContactPhone);
	    	//3.处理参数
	    	smsContent = SMSUtil.processParameter(smsContent, param);
	    	//4.设置接收人
	    	String[] receivers = {phone};
	    	//5.发送短信
	    	HttpResult sendResult = SMSUtil.sendSMS(receivers, smsContent);
	    	//记录日志
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(SMSUtil.EXHIBITOR_TO_TRADDINGGROUP_NOTICE_CODE);
	    	eml.setNumber(phone);
	    	eml.setContent(smsContent);
	    	eml.setReceiver(companyName);
	    	eml.setSendcode(sendResult.getCode().toString());
	    	ebsSendMessageLogDao.save(eml);
	    	if(sendResult.getCode()==200&&Long.valueOf(sendResult.getBody())>0) {
	    		result = true;
			}
    	}
    	return result;
    }
    /**
     * 发送后台验证码
     * @param safeCode 验证码
     * @param phone 接收人手机号
     * @param sessionId 届次id
     * @return
     * @throws Exception
     */
    @Override
    public Boolean sendConsoleValidateSMS(String safeCode,String phone,Integer sessionId) throws Exception {
    	Boolean result = false;
    	if(isNew){
    		String BianHao = TencentSMSUtil.CONSOLE_LOGIN_TEMPLATE_ID;
    		String[] params = new String[1];
		    params[0] = safeCode;
		    String[] receivers = {"86 "+phone};
			SendSmsResponse smsResult = TencentSMSUtil.sendSMS(receivers, params, BianHao);
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(BianHao);
	    	eml.setNumber(phone);
	    	eml.setContent(JSONObject.toJSONString(smsResult));
	    	eml.setReceiver(phone);
	    	eml.setSendcode(smsResult.getSendStatusSet()[0].getCode());
	    	ebsSendMessageLogDao.save(eml);

	    	if("Ok".equals(smsResult.getSendStatusSet()[0].getCode())) {
	    		result = true;
			}
    	}
    	else{
	    	String smsContent = sysSmsTemplateDao.findByTitleAndSessionId(SMSUtil.CONSOLE_VALIDATE_CODE, sessionId).getSmsTemplate();
	    	Map<String,Object> param = new HashMap<String,Object>();
	    	//1.验证码
	    	param.put("safeCode", safeCode);
	    	//3.处理参数
	    	smsContent = SMSUtil.processParameter(smsContent, param);
	    	//4.设置接收人
	    	String[] receivers = {phone};
	    	//5.发送短信
	    	HttpResult sendResult = SMSUtil.sendSMS(receivers, smsContent);
	    	//记录日志
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(SMSUtil.CONSOLE_VALIDATE_CODE);
	    	eml.setNumber(phone);
	    	eml.setContent(smsContent);
	    	eml.setReceiver(phone);
	    	eml.setSendcode(sendResult.getCode().toString());
	    	ebsSendMessageLogDao.save(eml);
	    	if(sendResult.getCode()==200&&Long.valueOf(sendResult.getBody())>0) {
	    		result = true;
			}
    	}
    	return result;
    }

    /*
     * 找回密码
     *
     */
    @Override
    public Boolean sendFindPasswordSMS(String companyName,String passwordCode,String phone,Integer sessionId) throws Exception {
    	Boolean result = false;
    	if(isNew){
    		String BianHao = TencentSMSUtil.FIND_PASSWORD_TEMPLATE_ID;
    		String[] params = new String[2];
		    params[0] = companyName;
		    params[1] = passwordCode;
		    String[] receivers = {"86 "+phone};
			SendSmsResponse smsResult = TencentSMSUtil.sendSMS(receivers, params, BianHao);
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(BianHao);
	    	eml.setNumber(phone);
	    	eml.setContent(JSONObject.toJSONString(smsResult));
	    	eml.setReceiver(phone);
	    	eml.setSendcode(smsResult.getSendStatusSet()[0].getCode());
	    	ebsSendMessageLogDao.save(eml);

	    	if("Ok".equals(smsResult.getSendStatusSet()[0].getCode())) {
	    		result = true;
			}
    	}
    	else{
	    	String smsContent = sysSmsTemplateDao.findByTitleAndSessionId(SMSUtil.FIND_PASSWORD_CODE, sessionId).getSmsTemplate();
	    	Map<String,Object> param = new HashMap<String,Object>();
	    	//1.验证码
	    	param.put("companyName", companyName);
	    	param.put("passwordCode", passwordCode);
	    	//3.处理参数
	    	smsContent = SMSUtil.processParameter(smsContent, param);
	    	//4.设置接收人
	    	String[] receivers = {phone};
	    	//5.发送短信
	    	HttpResult sendResult = SMSUtil.sendSMS(receivers, smsContent);
	    	//记录日志
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(SMSUtil.FIND_PASSWORD_CODE);
	    	eml.setNumber(phone);
	    	eml.setContent(smsContent);
	    	eml.setReceiver(companyName);
	    	eml.setSendcode(sendResult.getCode().toString());
	    	ebsSendMessageLogDao.save(eml);
	    	if(sendResult.getCode()==200&&Long.valueOf(sendResult.getBody())>0) {
	    		result = true;
			}
    	}
    	return result;
    }

    public Boolean sendMessage(String number, String smsContent, String receiver, String type) throws Exception {
    	Boolean result = false;
    	String[] receivers = {number};
    	//发送短信
    	HttpResult sendResult = SMSUtil.sendSMS(receivers, smsContent);
    	//记录日志
    	EbsSendMessageLog eml = new EbsSendMessageLog();
    	eml.setType(type);
    	eml.setNumber(number);
    	eml.setContent(smsContent);
    	eml.setReceiver(receiver);
    	eml.setSendcode(sendResult.getCode().toString());
    	ebsSendMessageLogDao.save(eml);
    	if(sendResult.getCode()==200&&Long.valueOf(sendResult.getBody())>0) {
    		result = true;
    	}
    	return result;
    }

    /*
     * 手机验证码
     *
     */
	@Override
	public boolean sendRegistValidateSMS(String validateCode, String phone,	Integer sessionId) throws Exception {
		Boolean result = false;
		if(isNew){
    		String BianHao = TencentSMSUtil.PHONE_VALIDATE_TEMPLATE_NEWID;
    		String[] params = new String[1];
		    params[0] = validateCode;
		    String[] receivers = {"86 "+phone};
			SendSmsResponse smsResult = TencentSMSUtil.sendSMS(receivers, params, BianHao);
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(BianHao);
	    	eml.setNumber(phone);
	    	eml.setContent(JSONObject.toJSONString(smsResult));
	    	eml.setReceiver(phone);
	    	eml.setSendcode(smsResult.getSendStatusSet()[0].getCode());
	    	ebsSendMessageLogDao.save(eml);

	    	if("Ok".equals(smsResult.getSendStatusSet()[0].getCode())) {
	    		result = true;
			}
    	}
    	else{
	    	String companyName = "";
	    	String smsContent = sysSmsTemplateDao.findByTitleAndSessionId(SMSUtil.PHONE_VALIDATE_CODE, sessionId).getSmsTemplate();
	    	Map<String,Object> param = new HashMap<String,Object>();
	    	//1.设置验证码
	    	param.put("phoneValidateCode", validateCode);
	    	//2.设置接收通知的公司名字
	    	param.put("companyName", companyName);
	    	//3.处理参数
	    	smsContent = SMSUtil.processParameter(smsContent, param);
	    	//4.设置接收人
	    	String[] receivers = {phone};
	    	//5.发送短信
	    	HttpResult sendResult = SMSUtil.sendSMS(receivers, smsContent);
	    	//记录日志
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(SMSUtil.PHONE_VALIDATE_CODE);
	    	eml.setNumber(phone);
	    	eml.setContent(smsContent);
	    	eml.setReceiver(companyName);
	    	eml.setSendcode(sendResult.getCode().toString());
	    	ebsSendMessageLogDao.save(eml);
	    	if(sendResult.getCode()==200&&Long.valueOf(sendResult.getBody())>0) {
	    		result = true;
			}
    	}
    	return result;
	}

	/*
	 * 线上展厅消息提醒
	 *
	 */
	@Override
	public boolean sendMessageForOnline(String phone, String name, Integer sessionId) throws Exception {
		Boolean result = false;
		if(isNew){
    		String BianHao = TencentSMSUtil.MESSAGE_TEMPLATE_ID;
    		String[] params = new String[1];
		    params[0] = name;
		    String[] receivers = {"86 "+phone};
			SendSmsResponse smsResult = TencentSMSUtil.sendSMS(receivers, params, BianHao);
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(BianHao);
	    	eml.setNumber(phone);
	    	eml.setContent(JSONObject.toJSONString(smsResult));
	    	eml.setReceiver(phone);
	    	eml.setSendcode(smsResult.getSendStatusSet()[0].getCode());
	    	ebsSendMessageLogDao.save(eml);

	    	if("Ok".equals(smsResult.getSendStatusSet()[0].getCode())) {
	    		result = true;
			}
    	}
    	else{
	    	String smsContent = name+" 您好，贵公司收到新的咨询信息，请登录线上展厅会员中心查看。【绿博会】";//sysSmsTemplateDao.findByTitleAndSessionId(SMSUtil.FIND_PASSWORD_CODE, sessionId).getSmsTemplate();
	    	Map<String,Object> param = new HashMap<String,Object>();
	    	//1.验证码
	    	//param.put("companyName", companyName);
	    	//param.put("passwordCode", passwordCode);
	    	//3.处理参数
	    	//smsContent = SMSUtil.processParameter(smsContent, param);
	    	//4.设置接收人
	    	String[] receivers = {phone};
	    	//5.发送短信
	    	HttpResult sendResult = SMSUtil.sendSMS(receivers, smsContent);
	    	//记录日志
	    	EbsSendMessageLog eml = new EbsSendMessageLog();
	    	eml.setType(SMSUtil.FIND_PASSWORD_CODE);
	    	eml.setNumber(phone);
	    	eml.setContent(smsContent);
	    	eml.setReceiver("");
	    	eml.setSendcode(sendResult.getCode().toString());
	    	ebsSendMessageLogDao.save(eml);
	    	if(sendResult.getCode()==200&&Long.valueOf(sendResult.getBody())>0) {
	    		result = true;
			}
    	}
    	return result;
	}

	/**
	 * 发送搭建商审核通过短信
	 * @param phone 接收人手机号
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean sendDecoratorAuditAgree(String phone) throws Exception {
		Boolean result = false;
		if(isNew){
			String BianHao = TencentSMSUtil.DECORATOR_AUDIT_AGREE_TEMPLATE_ID;
			String[] params = new String[4];
			params[0] = decoratorAuditAgreeDate;
			params[1] = decoratorAuditAgreeDeposit;
			params[2] = decoratorAuditAgreePerson;
			params[3] = decoratorAuditAgreeTelephone;
			String[] receivers = {"86 "+ phone};
			SendSmsResponse smsResult = TencentSMSUtil.sendSMS(receivers, params, BianHao);
			log.info("发送搭建商审核通过短信，手机号:{}，发送结果:{}", phone, JSONUtil.toJsonStr(smsResult));
			EbsSendMessageLog eml = new EbsSendMessageLog();
			eml.setType(BianHao);
			eml.setNumber(phone);
			eml.setContent(JSONObject.toJSONString(smsResult));
			eml.setReceiver(phone);
			eml.setSendcode(smsResult.getSendStatusSet()[0].getCode());
			ebsSendMessageLogDao.save(eml);

			if("Ok".equals(smsResult.getSendStatusSet()[0].getCode())) {
				result = true;
			}
		}
		else{
			String smsContent = sysSmsTemplateDao.findByTitle(SMSUtil.DECORATOR_AUDIT_AGREE).getSmsTemplate();
			Map<String,Object> param = new HashMap<>();
			//1.日期、保证金、联系人、联系电话
			param.put("date", decoratorAuditAgreeDate);
			param.put("deposit", decoratorAuditAgreeDeposit);
			param.put("contactPerson", decoratorAuditAgreePerson);
			param.put("contactTelephone", decoratorAuditAgreeTelephone);
			//3.处理参数
			smsContent = SMSUtil.processParameter(smsContent, param);
			//4.设置接收人
			String[] receivers = {phone};
			//5.发送短信
			HttpResult sendResult = SMSUtil.sendSMS(receivers, smsContent);
			//记录日志
			EbsSendMessageLog eml = new EbsSendMessageLog();
			eml.setType(SMSUtil.DECORATOR_AUDIT_AGREE);
			eml.setNumber(phone);
			eml.setContent(smsContent);
			eml.setReceiver(phone);
			eml.setSendcode(sendResult.getCode().toString());
			ebsSendMessageLogDao.save(eml);
			if(sendResult.getCode()==200&&Long.valueOf(sendResult.getBody())>0) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 发送搭建商审核不通过短信
	 * @param phone 接收人手机号
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean sendDecoratorAuditReject(String phone) throws Exception {
		Boolean result = false;
		if(isNew){
			String smsCode = TencentSMSUtil.DECORATOR_AUDIT_REJECT_TEMPLATE_ID;
			String[] params = new String[0];
			String[] receivers = {"86 "+ phone};
			SendSmsResponse smsResult = TencentSMSUtil.sendSMS(receivers, params, smsCode);
			log.info("发送搭建商审核不通过短信，手机号:{}，发送结果:{}", phone, JSONUtil.toJsonStr(smsResult));
			EbsSendMessageLog eml = new EbsSendMessageLog();
			eml.setType(smsCode);
			eml.setNumber(phone);
			eml.setContent(JSONObject.toJSONString(smsResult));
			eml.setReceiver(phone);
			eml.setSendcode(smsResult.getSendStatusSet()[0].getCode());
			ebsSendMessageLogDao.save(eml);

			if("Ok".equals(smsResult.getSendStatusSet()[0].getCode())) {
				result = true;
			}
		}
		else{
			String smsContent = sysSmsTemplateDao.findByTitle(SMSUtil.DECORATOR_AUDIT_REJECT).getSmsTemplate();
			//4.设置接收人
			String[] receivers = {phone};
			//5.发送短信
			HttpResult sendResult = SMSUtil.sendSMS(receivers, smsContent);
			//记录日志
			EbsSendMessageLog eml = new EbsSendMessageLog();
			eml.setType(SMSUtil.DECORATOR_AUDIT_REJECT);
			eml.setNumber(phone);
			eml.setContent(smsContent);
			eml.setReceiver(phone);
			eml.setSendcode(sendResult.getCode().toString());
			ebsSendMessageLogDao.save(eml);
			if(sendResult.getCode()==200&&Long.valueOf(sendResult.getBody())>0) {
				result = true;
			}
		}
		return result;
	}
}
