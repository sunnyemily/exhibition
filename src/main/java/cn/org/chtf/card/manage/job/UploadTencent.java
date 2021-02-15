package cn.org.chtf.card.manage.job;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;

import cn.org.chtf.card.manage.common.dao.CommonMapper;

@Component
public class UploadTencent implements Runnable {	
	
	@Autowired
    private CommonMapper commonDao;
	
	@Value("${server.port}")
	private String port;
	
	public UploadTencent(){
		
	}
	
	@Override
	public void run() {
		//System.out.println("########################轮询########################"+port);
		if("7080".equals(port)){
			return;
		}	
		
		Map<String,Object> map = new HashMap<String,Object>();
		map = commonDao.GetWaitingUploadTencent();
		if(map!=null){
			System.out.println("*******开始*******");
			Map<String,Object> para = new HashMap<String,Object>();
			para.put("id", map.get("id"));
			para.put("zhuangtai", "W");
			commonDao.updateZhuangTai(para);
			ShuangChuanWenJian(map.get("id").toString(),map.get("localpath").toString());
		}
	}
	
	private String ShuangChuanWenJian(String id, String localpath) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("id", id);		
		String fileID="";		
		VodUploadClient client = new VodUploadClient("AKIDh97qJapS2FG5gELuRijZTGo84MkxPDWa", "07wWpAHLG4f1GOHuIFyU2Mq54qTs1tYz");
		VodUploadRequest request = new VodUploadRequest();
		request.setMediaFilePath("./static"+localpath);
		request.setProcedure("hls");
		request.setSessionContext(id);
		try {
		    VodUploadResponse response = client.upload("ap-chongqing", request);
		    fileID = response.getFileId();
		    para.put("fileid", fileID);		    
		} catch (Exception e) {
		    // 业务方进行异常处理	
			System.out.println(e.getMessage());
			para.put("zhuangtai", "E");			
		}
		commonDao.updateZhuangTai(para);
		return fileID;
	}	
	
}
