package cn.org.chtf.card.manage.online.service.impl;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.common.service.CommonService;
import cn.org.chtf.card.manage.online.model.OnlineApplyMeeting;
import cn.org.chtf.card.manage.online.model.OnlineApplyMeetingDetails;
import cn.org.chtf.card.manage.online.service.OnlineApplyMeetingService;
import cn.org.chtf.card.manage.online.dao.OnlineApplyMeetingDetailsMapper;
import cn.org.chtf.card.manage.online.dao.OnlineApplyMeetingMapper;
import cn.org.chtf.card.support.util.SMSUtil;
import cn.org.chtf.card.support.util.WConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ServiceImpl
 * @author lm
 */
@Service
public class OnlineApplyMeetingServiceImpl implements OnlineApplyMeetingService {

    @Autowired
    private OnlineApplyMeetingMapper onlineApplyMeetingDao;
    
    @Autowired
    private OnlineApplyMeetingDetailsMapper onlineApplyMeetingDetailsDao;
    
    @Autowired
    private CommonService commonService;

    /**
     * 查询列表
     */
    @Override
    public List<OnlineApplyMeeting> list(Map<String, Object> map) {
        return onlineApplyMeetingDao.list(map);
    }
    
    /**
     * 数量
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return onlineApplyMeetingDao.listcount(map);
    }


    /**
     * 通过id查询单个
     */
    @Override
    public OnlineApplyMeeting findById(Integer id) {
        return onlineApplyMeetingDao.findById(id);
    }

    /**
     * 通过map查询单个
     */
    @Override
    public List<OnlineApplyMeeting> findByMap(Map<String, Object> map) {
        return onlineApplyMeetingDao.findByMap(map);
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(OnlineApplyMeeting onlineApplyMeeting) {
    	try{
    		String number = commonService.GetMeetingNumber();
    		onlineApplyMeeting.setMeetingnumber(number);
	        onlineApplyMeetingDao.save(onlineApplyMeeting);
	        String[] strArgs = onlineApplyMeeting.getPhones().split(",");
	        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	        Map<String,Object> par = new HashMap<String,Object>();
	        String hostPhone = onlineApplyMeeting.getContacttel();
	        par.put("mobilephone", onlineApplyMeeting.getContacttel());
	        par.put("meetingid", onlineApplyMeeting.getId());
	        par.put("pass", "");
	        par.put("ishost", 1);
	        list.add(par);
	    	
	    	if(list.size()>0){
	    		//onlineApplyMeetingService.deleteMeetingdetailsID(onlineApplyMeeting.getId());
	    		onlineApplyMeetingDao.addmeetingdetails(list);	    		
	    	}
    	} catch (Exception ee) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
    	return 1;
    }

    /**
     * 修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(OnlineApplyMeeting onlineApplyMeeting) {
    	try{
	        onlineApplyMeetingDao.update(onlineApplyMeeting);
	        int iMeetid = onlineApplyMeeting.getId();
	        //取主持人手机号
	        String ZCRSJH = onlineApplyMeetingDao.GetZhuChiRenShouJiHao(iMeetid);
	        String hostPhone = onlineApplyMeeting.getContacttel();
	        if(ZCRSJH.equals(hostPhone)){
	        	
	        }
	        else{
	        	Map<String,Object> map = new HashMap<String,Object>();
	        	map.put("mobilephone", hostPhone);
	        	map.put("meetingid", iMeetid);
	        	List<OnlineApplyMeetingDetails> list = onlineApplyMeetingDetailsDao.findByMap(map);
	        	onlineApplyMeetingDetailsDao.updateHost(iMeetid);
	        	if(list.size()>0){
	        		onlineApplyMeetingDetailsDao.updateToHost(iMeetid,hostPhone);
	        	}
	        	else{
	        		OnlineApplyMeetingDetails oamd = new OnlineApplyMeetingDetails();
	        		oamd.setMeetingid(iMeetid);
	        		oamd.setMobilephone(hostPhone);
	        		oamd.setIshost(1);
	        		onlineApplyMeetingDetailsDao.save(oamd);
	        	}
	        }
		       
		} catch (Exception ee) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
    	return 1;
    }

    /**
     * 删除
     */
    @Override
    public int deleteById(Integer id) {
        return onlineApplyMeetingDao.deleteById(id);
    }

	@Override
	
	public void addmeetingdetails(List<Map<String, Object>> list) {
		onlineApplyMeetingDao.addmeetingdetails(list);
	}

	@Override
	public void deleteMeetingdetailsID(Integer id) {
		onlineApplyMeetingDao.deleteMeetingdetailsID(id);
	}

}
