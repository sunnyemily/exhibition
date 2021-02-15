package cn.org.chtf.card.support.util.tencentyun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.org.chtf.card.manage.wechatuser.pojo.WechatUser;
import cn.org.chtf.card.support.util.http.HttpResult;
import cn.org.chtf.card.support.util.http.HttpUtil;
@Component
public class Util {
	public final static Integer IM_APP_ID = 1400428240;
	public final static String IM_APP_KEY = "bad4e456acd456732c810e016b4c26bc6f5e269c944811a3187dc9646bf50591";
	public final static String PROFILE_ADD_URL = "https://console.tim.qq.com/v4/im_open_login_svc/account_import?sdkappid="+IM_APP_ID+"&identifier=administrator&usersig=eJwtjNEKgjAYRt9l1yG-axMVuggquoiIEqtLcTN-bG7NEYvo3Vvm5Xe*w3mTYneKntKSnNAIyGzcKGTvsMERV0Jhj4OzldN2EgbRVcagIHnMABhNKYP-I71BKwPnnFOAiTpUP5ZAPGeUp3yq4C30l0pmbbk-oGDrjX5ty3tRu8tR6Pqxyhq8euNVcJJz2y3I5wtpNzX9&contenttype=json&random=";
	public final static String PROFILE_UPDATE_URL = "https://console.tim.qq.com/v4/profile/portrait_set?sdkappid="+IM_APP_ID+"&identifier=administrator&usersig=eJwtjNEKgjAYRt9l1yG-axMVuggquoiIEqtLcTN-bG7NEYvo3Vvm5Xe*w3mTYneKntKSnNAIyGzcKGTvsMERV0Jhj4OzldN2EgbRVcagIHnMABhNKYP-I71BKwPnnFOAiTpUP5ZAPGeUp3yq4C30l0pmbbk-oGDrjX5ty3tRu8tR6Pqxyhq8euNVcJJz2y3I5wtpNzX9&contenttype=json&random=";

	@Resource
    private HttpUtil httpAPIService;
	public boolean updateIMUserProfile(WechatUser profile) {
		ObjectMapper mapper = new ObjectMapper();
		String strJson;
		try {
			strJson = turnToProfileJson(profile);	 
			int max=1000000,min=1;
			int ran2 = (int) (Math.random()*(max-min)+min); 		
			HttpResult result = httpAPIService.postJson(PROFILE_UPDATE_URL+ran2, strJson);
			Map strResult = mapper.readValue(result.getBody(), Map.class);
			if(strResult.get("ActionStatus").equals("OK")) {
				return true;
			}
			else if(strResult.get("ErrorCode").equals(40003)) {
				return importIMUserProfile(profile);
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean importIMUserProfile(WechatUser profile) {
		Map<String,Object> mapProfile = new HashMap<String,Object>();
		mapProfile.put("Identifier", profile.getUid().toString());
		mapProfile.put("Nick", profile.getNickname());
		mapProfile.put("FaceUrl", profile.getHeadimgurl());
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			String strJson = mapper.writeValueAsString(mapProfile);	 
			int max=1000000,min=1;
			int ran2 = (int) (Math.random()*(max-min)+min); 		
			HttpResult result = httpAPIService.postJson(PROFILE_ADD_URL+ran2, strJson);
			Map strResult = mapper.readValue(result.getBody(), Map.class);
			if(strResult.get("ActionStatus").equals("OK")) {
				return true;
			}
			else {
				System.out.println("账号导入失败："+strResult.get("ErrorCode")+strResult.get("ErrorInfo"));
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private String turnToProfileJson(WechatUser profile) throws JsonProcessingException {
		Map<String,Object> mapProfile = new HashMap<String,Object>();
		mapProfile.put("From_Account", profile.getUid().toString());
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//昵称
		Map<String,Object> mapNick = new HashMap<String,Object>();
		mapNick.put("Tag", "Tag_Profile_IM_Nick");
		mapNick.put("Value", profile.getNickname());
		list.add(mapNick);

		//性别
		Map<String,Object> mapGender = new HashMap<String,Object>();
		mapGender.put("Tag", "Tag_Profile_IM_Gender");
		String sexName = "Gender_Type_Unknown";
		if(profile.getSex().equals(1))
		{
			sexName = "Gender_Type_Male";
		}
		else if(profile.getSex().equals(2)) {
			sexName = "Gender_Type_Female";
		}
		mapGender.put("Value", sexName);
		list.add(mapGender);
		//头像
		Map<String,Object> mapImage = new HashMap<String,Object>();
		mapImage.put("Tag", "Tag_Profile_IM_Image");
		mapImage.put("Value", profile.getHeadimgurl());
		list.add(mapImage);
		//角色
		Map<String,Object> mapRole = new HashMap<String,Object>();
		mapRole.put("Tag", "Tag_Profile_IM_Role");
		mapRole.put("Value", profile.getUserTypeId());
		list.add(mapRole);
		
		mapProfile.put("ProfileItem", list);
		
		ObjectMapper mapper = new ObjectMapper();

		String strJson = mapper.writeValueAsString(mapProfile);
		return strJson;
	}
	
}
