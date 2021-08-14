package cn.org.chtf.card.support.util.tencentyun;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
@Component
public class TencentSMSUtil {
	public final static String secretId = "AKIDh97qJapS2FG5gELuRijZTGo84MkxPDWa";
	public final static String secretKey = "07wWpAHLG4f1GOHuIFyU2Mq54qTs1tYz";
	public final static String sdkAppId = "1400431762";
	public final static String endPoint = "sms.tencentcloudapi.com";
	/**
	 * 后台登录短信模板id
	 */
	public static String CONSOLE_LOGIN_TEMPLATE_ID = "737198";
	/**
	 * 手机验证码短信模板id
	 */
	//public static String PHONE_VALIDATE_TEMPLATE_ID = "737199";
	public static String PHONE_VALIDATE_TEMPLATE_NEWID = "741173";
	/**
	 * 零散展位分配模板id
	 */
	public static String LINGSAN_BOOTH_ALLOCATE_TEMPLATE_ID = "737263";
	/**
	 * 代办员取证报表模板id
	 */
	public static String JIAOYITUAN_TAKE_REPORT_TEMPLATE_ID = "738114";
	/**
	 * 记者取证报表模板id
	 */
	public static String JIZHE_TAKE_REPORT_TEMPLATE_ID = "737202";
	/**
	 * 零散取证报表模板id
	 */
	public static String LINGSAN_TAKE_REPORT_TEMPLATE_ID = "738117";
	/**
	 * 搭建商取证报表模板id
	 */
	public static String DAJIAN_TAKE_REPORT_TEMPLATE_ID = "738118";
	/**
	 * 外宾取证报表模板id
	 */
	public static String WAIBIN_TAKE_REPORT_TEMPLATE_ID = "738119";
	/**
	 * 采购商取证报表模板id
	 */
	public static String CAIGOUSHANG_TAKE_REPORT_TEMPLATE_ID = "738120";
	/**
	 * 交易团开通短信模板id
	 */
	public static String JIAOYITUAN_OPEN_TEMPLATE_ID = "737203";
	/**
	 * 记者开通短信模板id
	 */
	public static String JIZHE_OPEN_TEMPLATE_ID = "737242";
	/**
	 * 零散转交易团模板id
	 */
	public static String LINGSAN_TURN_JIAOYITUAN_TEMPLATE_ID = "737204";
	/**
	 * 密码找回模板id
	 */
	public static String FIND_PASSWORD_TEMPLATE_ID = "737205";
	/**
	 * 交易团展位分配模板id
	 */
	public static String JIAOYITUAN_BOOTH_ALLOCATE_TEMPLATE_ID = "737264";
	/**
	 * 零散注册成功短信模板id
	 */
	public static String LINGSAN_REGIST_SUCCESS_TEMPLATE_ID = "737250";
	/**
	 * 采购商注册成功短信模板id
	 */
	public static String CAIGOUSHANG_REGIST_SUCCESS_TEMPLATE_ID = "737251";
	/**
	 * 布撤展商注册成功短信模板id
	 */
	public static String BUCHEZHANSHANG_REGIST_SUCCESS_TEMPLATE_ID = "737252";
	/**
	 * 外宾注册成功短信模板id
	 */
	public static String WAIBIN_REGIST_SUCCESS_TEMPLATE_ID = "737253";
	/**
	 * 消息提醒模板id
	 */
	public static String MESSAGE_TEMPLATE_ID = "737256";
	
	/**
	 * 会议举办通知
	 */
	public static String  HUIYI_CONFERENCE_NOTICE="742985";

	/**
	 * 搭建商审核通过短信模板id
	 */
	public static String DECORATOR_AUDIT_AGREE_TEMPLATE_ID = "1080006";

	/**
	 * 搭建商审核不通过短信模板id
	 */
	public static String DECORATOR_AUDIT_REJECT_TEMPLATE_ID = "1080007";
	
	
	public final static String PHONE_VALIDATE_CODE = "手机验证码";
	public final static String REGIST_SUCCESS_CODE = "注册成功通知";
	public final static String BOOTH_CONFIRM_SUCCESS_CODE = "展位分配";
	public final static String TAKE_REPORT_CODE = "取证报表";
	public final static String AGENT_OPEN_CODE = "代办员开通通知";
	public final static String EXHIBITOR_TO_TRADDINGGROUP_NOTICE_CODE = "零散转交易团通知";
	public final static String FIND_PASSWORD_CODE = "找回密码短信";
	public final static String CONSOLE_VALIDATE_CODE = "后台登录验证短信";
	
	public final static String REGIST_CODEKEY = "PHONEVALIDATECODE";
	private static String randString = "023456789";
	private static int stringNum = 5;// 随机产生字符数量
	
	
	/**
	 * 获取指定位数的随机数
	 * @return
	 */
	public static String getRandomString() {
		Random random = new Random();
		  // 绘制随机字符
		  String randomString = "";
		  for (int i = 1; i <= stringNum; i++) {
		   randomString += String.valueOf(String.valueOf(randString.charAt((random.nextInt(randString
				    .length())))));
		  }
		  return randomString;
	}
	public static SendSmsResponse sendSMS(String[] receivers,String[] parameters,String templateId) throws TencentCloudSDKException {
		Credential cred = new Credential(secretId, secretKey);
		
		      HttpProfile httpProfile = new HttpProfile();
		      httpProfile.setEndpoint(endPoint);
		
		      ClientProfile clientProfile = new ClientProfile();
		      clientProfile.setHttpProfile(httpProfile);
		
		      SmsClient client = new SmsClient(cred, "", clientProfile);
		
		      SendSmsRequest req = new SendSmsRequest();
		      req.setPhoneNumberSet(receivers);
		
		      req.setTemplateID(templateId);
		      req.setSign("黑龙江省博览发展促进中心");
		
		      req.setTemplateParamSet(parameters);
		
		      req.setSmsSdkAppid(sdkAppId);
		
		      SendSmsResponse resp = client.SendSms(req);
	          System.out.println(SendSmsResponse.toJsonString(resp));
		      return resp;
	}

}
