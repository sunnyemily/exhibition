package cn.org.chtf.card.support.util.wechat;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import cn.org.chtf.card.support.util.http.HttpResult;
import cn.org.chtf.card.support.util.http.HttpUtil;

/**
 * 微信公众平台操作接口
 * @author wushixing
 * @date 2018/3/8
 */
@Component
public class wechatApi {
	public final static String createMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	public final static String queryMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";
	public final static String delMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";
	public final static String qrcodeUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
	
	public final static String SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=";
	public final static String NEW_MESSAGE_TEMPLATE_ID = "-sHhDeJd7tPmEu3FDemYL9gbzMs5JMkDoxOzviTRwxA";
	@Resource Account account;
	@Resource
    private HttpUtil httpAPIService;
	
	/**
	 * 创建公众号菜单
	 * @param menus
	 * @return
	 */
	public boolean createMenu(Map<String,Object> menus) {
		ObjectMapper mapper = new ObjectMapper();
		String strJson;
		try {
			strJson = mapper.writeValueAsString(menus);			
			HttpResult result = httpAPIService.postJson(createMenuUrl+account.getWechatToken(), strJson);
			Map strResult = mapper.readValue(result.getBody(), Map.class);
			if(strResult.get("errcode").equals(0)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 创建公众号菜单
	 * @param strMenus
	 * @return
	 */
	public boolean createMenu(String strMenus) {
		ObjectMapper mapper = new ObjectMapper();
		String strJson;
		try {
			HttpResult result = httpAPIService.postJson(createMenuUrl+account.getWechatToken(), strMenus);
			Map strResult = mapper.readValue(result.getBody(), Map.class);
			if(strResult.get("errcode").equals(0)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 返回已设置菜单
	 * @return
	 */
	public String queryMenus() {
		try {
			return httpAPIService.doGet(queryMenuUrl+account.getWechatToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 发送订阅消息
	 * @param menus
	 * @return
	 */
	public boolean subscribeMessageSend(String toUser,String templateId,String page,Map<String,Object> data) {
		data.put("miniprogram_state", "developer");//developer、trial、formal
		data.put("lang", "zh_CN");
		ObjectMapper mapper = new ObjectMapper();
		String strJson;
		try {
			Map<String,Object> postBody = new HashMap<String,Object>();
			postBody.put("touser", toUser);
			postBody.put("template_id", templateId);
			postBody.put("page", page);
			postBody.put("data", data);
			strJson = mapper.writeValueAsString(postBody);			
			HttpResult result = httpAPIService.postJson(SEND_MESSAGE_URL+account.getWechatToken(), strJson);
			Map strResult = mapper.readValue(result.getBody(), Map.class);
			if(strResult.get("errcode").equals(0)) {
				return true;
			}
			else {
				System.out.println(strResult.get("errcode"));
				System.out.println(strResult.get("errmsg"));
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
