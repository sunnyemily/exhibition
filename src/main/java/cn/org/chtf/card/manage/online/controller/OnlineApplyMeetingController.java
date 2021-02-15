package cn.org.chtf.card.manage.online.controller;

import cn.org.chtf.card.manage.online.model.OnlineApplyMeeting;
import cn.org.chtf.card.manage.online.service.OnlineApplyMeetingService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.support.util.SMSUtil;
import cn.org.chtf.card.support.util.WConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import cn.org.chtf.card.manage.user.pojo.User;

import com.alibaba.fastjson.JSONObject;

/**
 * Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/online/onlineApplyMeeting")
public class OnlineApplyMeetingController {

    @Autowired
    private OnlineApplyMeetingService onlineApplyMeetingService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<OnlineApplyMeeting> list = onlineApplyMeetingService.list(map);			
			int count = onlineApplyMeetingService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<OnlineApplyMeeting> onlineApplyMeeting =onlineApplyMeetingService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", onlineApplyMeeting);
    } 
    

    /**
     * 通过id查询单个
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        OnlineApplyMeeting onlineApplyMeeting =onlineApplyMeetingService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", onlineApplyMeeting);
    }   

    /**
     * 添加
     */
    @PostMapping("/save")
    public R save(@RequestBody OnlineApplyMeeting onlineApplyMeeting,
			HttpServletRequest request, HttpSession session) {
    	
	    	String strSessionid = sysSessionService.getSessionID(request);
	    	User user = (User)session.getAttribute("user");		
	    	onlineApplyMeeting.setSession(strSessionid);
	    	onlineApplyMeeting.setPhones(onlineApplyMeeting.getPhones().replace("，", ","));
	        int iRet = onlineApplyMeetingService.save(onlineApplyMeeting);	        
	       
	        if(iRet==1){
	        	sysOperationLogService.CreateEntity("添加会议申请", strSessionid, 0, user.getId(), 
					onlineApplyMeeting.getId(), JSONObject.toJSONString(onlineApplyMeeting));
	        	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
	        }
	        else{
	        	return R.error().put("code", WConst.ERROR).put("msg", WConst.SAVEDERROR);
	        }
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody OnlineApplyMeeting onlineApplyMeeting,
			HttpServletRequest request, HttpSession session) {
    	
	    	String strSessionid = sysSessionService.getSessionID(request);
	    	User user = (User)session.getAttribute("user");		
	    	onlineApplyMeeting.setAudituser(user.getId());
	    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	    	onlineApplyMeeting.setAudittime(df.format(new Date()));
	    	onlineApplyMeeting.setPhones(onlineApplyMeeting.getPhones().replace("，", ","));
	        int iRet = onlineApplyMeetingService.update(onlineApplyMeeting);
	        
	        if(iRet==1){
	        	sysOperationLogService.CreateEntity("更新会议申请", strSessionid, 0, user.getId(), 
					onlineApplyMeeting.getId(), JSONObject.toJSONString(onlineApplyMeeting));
	        	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
	        }
	        else{
	        	return R.error().put("code", WConst.ERROR).put("msg", WConst.SAVEDERROR);
	        }
    }

    /**
     * 删除
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	
    	OnlineApplyMeeting com = onlineApplyMeetingService.findById(id);
    	
        onlineApplyMeetingService.deleteById(id);
        
        sysOperationLogService.CreateEntity("删除会议申请", strSessionid, 0, user.getId(), 
				com.getId(), JSONObject.toJSONString(com));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr,
			HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    			
    				OnlineApplyMeeting com = onlineApplyMeetingService.findById(Integer.valueOf(id));
    				onlineApplyMeetingService.deleteById(Integer.valueOf(id));
    				
    				sysOperationLogService.CreateEntity("删除会议申请", strSessionid, 0, user.getId(), 
					com.getId(), JSONObject.toJSONString(com));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}