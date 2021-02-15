package cn.org.chtf.card.support.util.wechat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.org.chtf.card.manage.wechatuser.pojo.WechatUser;
import cn.org.chtf.card.support.util.http.HttpUtil;

@Configuration
public class Account {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private HttpUtil httpAPIService;

	@Value("${appID}")
	private String appID;
	@Value("${appSecret}")
	private String appSecret;

	private String wechatToken;

	/**
	 * 获取微信公众号的token，利用redis做全局缓存
	 * 
	 * @author wushixing
	 * @date 2018/3/8 17:46
	 * @return
	 */
	public String getWechatToken() {
		if (stringRedisTemplate.opsForValue().get("wechatToken") == null) {
			String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ this.appID + "&secret=" + this.appSecret;
			Object token;
			try {
				token = httpAPIService.doGet(url);
				if (token != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					Map tokenObject = objectMapper.readValue(token.toString(),
							Map.class);
					if (tokenObject.get("access_token") != null) {
						long expireSecond = (Integer) tokenObject
								.get("expires_in") - 1;// 为网络延迟提供1秒的时间
						String tokenStr = tokenObject.get("access_token")
								.toString();
						stringRedisTemplate.opsForValue().set("wechatToken",
								tokenStr, expireSecond, TimeUnit.SECONDS);
						this.wechatToken = tokenStr;
					}
				}
			} catch (Exception e) {
				this.wechatToken = null;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.wechatToken = stringRedisTemplate.opsForValue().get(
					"wechatToken");
		}
		return wechatToken;
	}

	/**
	 * 根据code从腾讯获取OpenId
	 * 
	 * @param code
	 * @return
	 */
	public WechatUser getWechatOpenIDByCode(String code) {
		WechatUser user = new WechatUser();
		String strOpenId = "";
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+ this.appID
				+ "&secret="+ this.appSecret
				+ "&js_code=" + code + "&grant_type=authorization_code";
		String token;
		try {
			token = httpAPIService.SendByGet(url);
			if (token != null) {
				ObjectMapper objectMapper = new ObjectMapper();
				Map tokenObject = objectMapper.readValue(token.toString(), Map.class);
				if (tokenObject.get("openid") != null) {					
					user.setRoutineOpenid(tokenObject.get("openid").toString());			
				}
				if (tokenObject.get("session_key") != null) {					
					user.setSessionKey(tokenObject.get("session_key").toString());			
				}
				if (tokenObject.get("unionid") != null) {					
					user.setUnionid(tokenObject.get("unionid").toString());	
				}
				if(tokenObject.get("errcode")!=null) {
					Integer errCode = Integer.parseInt(tokenObject.get("errcode").toString());
					switch(errCode) {
						case -1:			
							throw new Exception("code:"+code+",系统繁忙，此时请开发者稍候再试");
						case 40029:			
							throw new Exception("code:"+code+",code 无效");
						case 45011:			
							throw new Exception("code:"+code+",频率限制，每个用户每分钟100次");
					}
				}
			}
		} catch (Exception e) {			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

}
