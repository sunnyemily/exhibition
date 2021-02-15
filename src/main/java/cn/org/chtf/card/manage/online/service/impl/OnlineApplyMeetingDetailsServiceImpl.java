package cn.org.chtf.card.manage.online.service.impl;

import cn.org.chtf.card.manage.Exhibitors.dao.EbsSendMessageLogMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsSendMessageLog;
import cn.org.chtf.card.manage.online.model.OnlineApplyMeeting;
import cn.org.chtf.card.manage.online.model.OnlineApplyMeetingDetails;
import cn.org.chtf.card.manage.online.service.OnlineApplyMeetingDetailsService;
import cn.org.chtf.card.manage.online.dao.OnlineApplyMeetingDetailsMapper;
import cn.org.chtf.card.manage.online.dao.OnlineApplyMeetingMapper;
import cn.org.chtf.card.support.util.SMSUtil;
import cn.org.chtf.card.support.util.tencentyun.TencentSMSUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ServiceImpl
 * @author lm
 */
@Service
public class OnlineApplyMeetingDetailsServiceImpl implements OnlineApplyMeetingDetailsService {

    @Autowired
    private OnlineApplyMeetingDetailsMapper onlineApplyMeetingDetailsDao;
    
    @Autowired
    private OnlineApplyMeetingMapper onlineApplyMeetingDao;

    @Autowired
    private EbsSendMessageLogMapper ebsSendMessageLogDao;
    
    /**
     * 查询列表
     */
    @Override
    public List<OnlineApplyMeetingDetails> list(Map<String, Object> map) {
        return onlineApplyMeetingDetailsDao.list(map);
    }
    
    /**
     * 数量
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return onlineApplyMeetingDetailsDao.listcount(map);
    }


    /**
     * 通过id查询单个
     */
    @Override
    public OnlineApplyMeetingDetails findById(Integer id) {
        return onlineApplyMeetingDetailsDao.findById(id);
    }

    /**
     * 通过map查询单个
     */
    @Override
    public List<OnlineApplyMeetingDetails> findByMap(Map<String, Object> map) {
        return onlineApplyMeetingDetailsDao.findByMap(map);
    }

    /**
     * 新增
     */
    @Override
    public int save(OnlineApplyMeetingDetails onlineApplyMeetingDetails) {
    	String[] strArgs = onlineApplyMeetingDetails.getMobilephone().split(",");
    	int imeetid = onlineApplyMeetingDetails.getMeetingid();
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	Map<String,Object> mapx = new HashMap<String,Object>();
    	mapx.put("meetingid", imeetid);
    	List<OnlineApplyMeetingDetails> lit = onlineApplyMeetingDetailsDao.findByMap(mapx);    	
    	for(int j=0;j<strArgs.length;j++){
    		String hostPhone = strArgs[j];
    		int sl = 0;
    		for(int k=0;k<lit.size();k++){	    		
	    		if(hostPhone.equals(lit.get(k).getMobilephone())){
	    			sl++;
	    			break;
	    		}	    		
    		}
    		if(sl==0){
    			Map<String,Object> map = new HashMap<String,Object>();
	    		map.put("mobilephone", strArgs[j]);
	    		map.put("meetingid", imeetid);
	    		map.put("pass", "");
	    		map.put("ishost", 0);
	    		list.add(map);
    		}    		
    	}
    	if(list.size()>0){
    		//onlineApplyMeetingService.deleteMeetingdetailsID(onlineApplyMeeting.getId());
    		onlineApplyMeetingDao.addmeetingdetails(list);	    		
    	}
    	return 1;
    }

    /**
     * 修改
     * @throws TencentCloudSDKException 
     */
    @Override
    public int update(OnlineApplyMeetingDetails onlineApplyMeetingDetails) throws TencentCloudSDKException {
    	Boolean result=false;
    	//密码为空则生成
    	if("".equals(onlineApplyMeetingDetails.getPass())){
    		onlineApplyMeetingDetails.setPass(TencentSMSUtil.getRandomString());
    	}
        onlineApplyMeetingDetailsDao.update(onlineApplyMeetingDetails);
        OnlineApplyMeeting oam = onlineApplyMeetingDao.findById(onlineApplyMeetingDetails.getMeetingid());
        //发送会议密码
        String phone = onlineApplyMeetingDetails.getMobilephone();
        String BianHao = TencentSMSUtil.HUIYI_CONFERENCE_NOTICE;    		
		String[] params = new String[4];
	    params[0] = oam.getMeetingstart();//时间
	    params[1] = oam.getMeetingtype();//主题
	    params[2] = onlineApplyMeetingDetails.getPass();//密码
	    params[3] = phone;//手机号
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
        return 1;
    }

    /**
     * 删除
     */
    @Override
    public int deleteById(Integer id) {
        return onlineApplyMeetingDetailsDao.deleteById(id);
    }

}
