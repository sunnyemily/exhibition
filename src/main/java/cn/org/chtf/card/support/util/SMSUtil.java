package cn.org.chtf.card.support.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import cn.org.chtf.card.support.util.http.HttpResult;
import cn.org.chtf.card.support.util.http.HttpUtil;
@Component
public class SMSUtil {
	public final static String url = "http://sdk.entinfo.cn:8061/mdsmssend.ashx";
	public final static String sn = "DXX-JJJ-103-00622";
	public final static String pwd = "23D1B5532DDE4599AE951950EBF52C3E";
	public static final String REGIST_CODEKEY= "REGISTVALIDATECODEKEY";//放到session中的key
	public static final String ONLINE_REGIST_CODEKEY= "ONLINEREGISTVALIDATECODEKEY";//放到session中的key
	public static final String CONSOLE_LOGIN_CODEKEY= "CONSOLELOGINVALIDATECODEKEY";//放到session中的key
	
	public final static String PHONE_VALIDATE_CODE = "手机验证码";
	public final static String REGIST_SUCCESS_CODE = "注册成功通知";
	public final static String BOOTH_CONFIRM_SUCCESS_CODE = "展位分配";
	public final static String TAKE_REPORT_CODE = "取证报表";
	public final static String AGENT_OPEN_CODE = "代办员开通通知";
	public final static String EXHIBITOR_TO_TRADDINGGROUP_NOTICE_CODE = "零散转交易团通知";
	public final static String FIND_PASSWORD_CODE = "找回密码短信";
	public final static String CONSOLE_VALIDATE_CODE = "后台登录验证短信";
	
	private static String randString = "023456789ABCDEFGHIJKMNPQRSTUVWXYZ";
	private static int stringNum = 6;// 随机产生字符数量
	
	private final static String SERVER_IP = "218.9.77.82";//此IP必须为服务器对外IP，并将IP加入短信运营商信任列表
	@Resource
    private HttpUtil httpAPIService;
	public static HttpResult sendSMS(String[] receivers,String content) throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("sn", sn);
		map.put("pwd", pwd);
		map.put("mobile", StringUtils.join(receivers,","));
		map.put("content", content);
		map.put("ext", "");
		map.put("stime", "");
		map.put("rrid", "");
		map.put("msgfmt", "");
		// 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.addHeader("x-forwarded-for",SERVER_IP);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (map != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
	}
	/**
	 * 将短信内容中的参数进行替换
	 * @param content
	 * @param parameters
	 * @return
	 */
	public static String processParameter(String content,Map<String,Object> parameters) {
		String strKey;
		String strValue;
		for(String key:parameters.keySet()){
			strKey = "\\$\\{"+key+"\\}";
			strValue = parameters.get(key).toString();
			content = content.replaceAll(strKey, strValue);
		}

		return content;
	}
	/**
	 * 发送控制台登录验证短信
	 * @param phone手机号
	 * @param sessionId 届次ID
	 * @param session 
	 * @return
	 */
	public static ResultModel sendConsoleLoginCode(String phone,HttpSession session) {
		String randomString = getRandomString();
		  //将生成的随机字符串保存到session中
		  session.removeAttribute(CONSOLE_LOGIN_CODEKEY);
		  session.setAttribute(CONSOLE_LOGIN_CODEKEY,randomString);
		  System.out.println(randomString);
		  String[] arrayPhone = {phone};
		  try {
			HttpResult result = sendSMS(arrayPhone,"您正在登录管理后台，验证码是"+randomString+"。转发可能导致帐号安全问题，请您妥善保管。【黑龙江省国际博览发展促进中心】");
			if(result.getCode()==200&&Long.valueOf(result.getBody())>0) {
				return new ResultModel(WConst.SUCCESS,"发送成功，请输入手机验证码",null);
			}
			return new ResultModel(WConst.ERROR,"发送失败",null);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResultModel(WConst.ERROR,"发送失败",null);

		}
	}
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

}
