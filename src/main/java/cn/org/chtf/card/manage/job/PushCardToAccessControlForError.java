package cn.org.chtf.card.manage.job;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;

import cn.org.chtf.card.manage.common.dao.CommonMapper;
import cn.org.chtf.card.support.util.WebFileUtil;
import cn.org.chtf.card.support.util.http.HttpResult;
import cn.org.chtf.card.support.util.http.HttpUtil;

@Component
public class PushCardToAccessControlForError implements Runnable {	
	
	@Autowired
    private CommonMapper commonDao;
	
	@Resource
    private HttpUtil httpAPIService;
	
	@Value("${server.port}")
	private String port;
	
	public PushCardToAccessControlForError(){
		
	}
	
	private String type;
	public PushCardToAccessControlForError(String type,CommonMapper commonDao){
		this.type=type;		
		this.commonDao=commonDao;
	}
	
	@Override
	public void run() {
		//System.out.println("########################轮询########################"+port);
		if("7080".equals(port)){
			return;
		}		
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> par = new HashMap<String,Object>();
		par.put("type", "E");
		map = commonDao.GetWaitingToPush(par);
		if(map!=null){
			int pushcount = Integer.valueOf(map.get("pushcount").toString());
			if(pushcount>5){
				return;
			}
			System.out.println("*******开始PUSH*******");	
			Map<String,Object> para = new HashMap<String,Object>();
			para.put("id", map.get("id"));
			para.put("zhuangtai", "W");
			commonDao.updateCardPushZhuangTai(para);
			try{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		        String time = df.format(new Date());
				//推送文本
				boolean  isok = cardPush(map,time);
				//推送照片
				isok = photoPush(map,time);
				if(isok){
					para.put("zhuangtai", "Y");		
					commonDao.updateCardPushZhuangTai(para);
				}
			}
			catch(Exception e){
				para.put("zhuangtai", "E");
				commonDao.updateCardPushZhuangTai(para);
				return;
			}			
		}
	}

	private boolean photoPush(Map<String, Object> map, String time) throws Exception {
		System.out.println("#####开始推照片");
		String url = "http://222.32.108.21:1001/api/identityTicket/upload-photo";
		ObjectMapper mapper = new ObjectMapper();
		String strJson;
		
			String sessionId=map.get("session").toString();
			String photo=map.get("picture").toString();
			Map<String,Object> mapJson = new HashMap<String,Object>();
			mapJson.put("ticketNum", map.get("ticketNum"));
			String strBase = WebFileUtil.turnToJPGBASE64("./static"+photo);
			mapJson.put("photo", strBase);
			strJson = mapper.writeValueAsString(mapJson);
			System.out.println("准备推送照片信息：");
			//System.out.println(strJson);
			HttpResult result = httpAPIService.postJson_new(url, strJson);
			Map strResult = mapper.readValue(result.getBody(), Map.class);
			String returnStrJson = mapper.writeValueAsString(strResult);
			System.out.println("接收到的返回信息：");
			System.out.println(returnStrJson);
			JSONObject object = JSON.parseObject(returnStrJson);			
		     
		    FileOutputStream fileOutputStream = null;
		    File file = new File("./log/card/"+map.get("ticketNum")+"-"+time+"-photo.txt");
		    if(file.exists()){
		        //判断文件是否存在，如果不存在就新建一个txt
		        file.createNewFile();
		    }
		    fileOutputStream = new FileOutputStream(file);
		    fileOutputStream.write(("准备推送照片信息："+strJson+"接收到的返回信息："+returnStrJson).getBytes());
		    fileOutputStream.flush();
		    fileOutputStream.close();
		    if(!"0".equals(object.get("code"))){//code不为0，代表失败
				return false;
			}
		    return true;		
	}

	private boolean cardPush(Map<String, Object> map, String time) throws Exception {
		System.out.println("#####开始推文本");
		String url = "http://222.32.108.21:1001/api/identityTicket/add";
		ObjectMapper mapper = new ObjectMapper();
		String strJson;
		
			String sessionId=map.get("session").toString();
			Map<String,Object> mapJson = new HashMap<String,Object>();
			mapJson.put("idno", map.get("idno"));
			mapJson.put("ticketNum", map.get("ticketNum"));
			mapJson.put("name", map.get("name"));			
			mapJson.put("isLimit", map.get("isLimit"));
			mapJson.put("limit", map.get("limits"));
			mapJson.put("isVip", map.get("isVip"));
			mapJson.put("startDate", map.get("startDate"));
			mapJson.put("endDate", map.get("endDate"));
			strJson = mapper.writeValueAsString(mapJson);
			System.out.println("准备推送票证信息：");
			//System.out.println(strJson);
			HttpResult result = httpAPIService.postJson_new(url, strJson);
			Map strResult = mapper.readValue(result.getBody(), Map.class);
			String returnStrJson = mapper.writeValueAsString(strResult);
			System.out.println("接收到的返回信息：");
			System.out.println(returnStrJson);
			
			JSONObject object = JSON.parseObject(returnStrJson);
			
			
           FileOutputStream fileOutputStream = null;
           File file = new File("./log/card/"+map.get("ticketNum")+"-"+time+"-text.txt");
           if(file.exists()){
               //判断文件是否存在，如果不存在就新建一个txt
               file.createNewFile();
           }
           fileOutputStream = new FileOutputStream(file);
           fileOutputStream.write(("准备推送证件信息："+strJson+"接收到的返回信息："+returnStrJson).getBytes());
           fileOutputStream.flush();
           fileOutputStream.close();
           if(!"0".equals(object.get("code"))){//code不为0，代表失败
				return false;
		   }
           return true;		
	}	
	
}
