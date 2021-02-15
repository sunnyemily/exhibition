package cn.org.chtf.card.manage.online.controller;

import cn.org.chtf.card.manage.online.model.OnlineApplyMeetingDetails;
import cn.org.chtf.card.manage.online.service.OnlineApplyMeetingDetailsService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.support.util.WConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.List;

import cn.org.chtf.card.manage.user.pojo.User;

import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

/**
 * Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/online/onlineApplyMeetingDetails")
public class OnlineApplyMeetingDetailsController {

    @Autowired
    private OnlineApplyMeetingDetailsService onlineApplyMeetingDetailsService;
    
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
			List<OnlineApplyMeetingDetails> list = onlineApplyMeetingDetailsService.list(map);			
			int count = onlineApplyMeetingDetailsService.listcount(map);
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
        List<OnlineApplyMeetingDetails> onlineApplyMeetingDetails =onlineApplyMeetingDetailsService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", onlineApplyMeetingDetails);
    } 
    

    /**
     * 通过id查询单个
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        OnlineApplyMeetingDetails onlineApplyMeetingDetails =onlineApplyMeetingDetailsService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", onlineApplyMeetingDetails);
    }   

    /**
     * 添加
     */
    @PostMapping("/save")
    public R save(@RequestBody OnlineApplyMeetingDetails onlineApplyMeetingDetails,
			HttpServletRequest request, HttpSession session) {
    	//String strSessionid = sysSessionService.getSessionID(request);
    	//User user = (User)session.getAttribute("user");		
    	//onlineApplyMeetingDetails.setSession(strSessionid);
    	if("".equals(onlineApplyMeetingDetails.getMobilephone())){
    		return R.error().put("code", WConst.ERROR).put("msg", "请输入参会人手机号");
    	}
    	onlineApplyMeetingDetails.setMobilephone(onlineApplyMeetingDetails.getMobilephone().replace("，", ","));
        onlineApplyMeetingDetailsService.save(onlineApplyMeetingDetails);
        
        //sysOperationLogService.CreateEntity("添加打印模版", strSessionid, 0, user.getId(), 
				//onlineApplyMeetingDetails.getId(), JSONObject.toJSONString(onlineApplyMeetingDetails));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改
     * @throws TencentCloudSDKException 
     */
    @PostMapping("/update")
    public R update(@RequestBody OnlineApplyMeetingDetails onlineApplyMeetingDetails,
			HttpServletRequest request, HttpSession session) throws TencentCloudSDKException {
    	//String strSessionid = sysSessionService.getSessionID(request);
    	//User user = (User)session.getAttribute("user");		
        onlineApplyMeetingDetailsService.update(onlineApplyMeetingDetails);
        
        //sysOperationLogService.CreateEntity("更新打印模版", strSessionid, 0, user.getId(), 
		//		onlineApplyMeetingDetails.getId(), JSONObject.toJSONString(onlineApplyMeetingDetails));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	
    	//OnlineApplyMeetingDetails com = onlineApplyMeetingDetailsService.findById(id);
    	
        onlineApplyMeetingDetailsService.deleteById(id);
        
        //sysOperationLogService.CreateEntity("删除打印膜版", strSessionid, 0, user.getId(), 
		//		com.getId(), JSONObject.toJSONString(com));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr,
			HttpServletRequest request, HttpSession session){
    	//String strSessionid = sysSessionService.getSessionID(request);
    	//User user = (User)session.getAttribute("user");		
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){    				
    				onlineApplyMeetingDetailsService.deleteById(Integer.valueOf(id));
    				//sysOperationLogService.CreateEntity("删除打印膜版", strSessionid, 0, user.getId(), 
					//com.getId(), JSONObject.toJSONString(com));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }
    
    /**
     * 发送短信
     * @throws TencentCloudSDKException 
     */
    @GetMapping("/SendSms")
    public R SendSms(@RequestParam(value = "id") Integer id,
			HttpServletRequest request, HttpSession session) throws TencentCloudSDKException {
    	//String strSessionid = sysSessionService.getSessionID(request);
    	//User user = (User)session.getAttribute("user");		    	
    	
    	OnlineApplyMeetingDetails oam = onlineApplyMeetingDetailsService.findById(Integer.valueOf(id));
		onlineApplyMeetingDetailsService.update(oam);
        
        //sysOperationLogService.CreateEntity("删除打印膜版", strSessionid, 0, user.getId(), 
		//		com.getId(), JSONObject.toJSONString(com));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量发送短信
     * @throws TencentCloudSDKException 
     */
    @GetMapping("/SendSmsAll")
    public R SendSmsAll(@RequestParam(value = "isStr") String isStr,
			HttpServletRequest request, HttpSession session) throws TencentCloudSDKException{
    	//String strSessionid = sysSessionService.getSessionID(request);
    	//User user = (User)session.getAttribute("user");		
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    			
    				OnlineApplyMeetingDetails oam = onlineApplyMeetingDetailsService.findById(Integer.valueOf(id));
    				onlineApplyMeetingDetailsService.update(oam);
    				
    				//sysOperationLogService.CreateEntity("删除打印膜版", strSessionid, 0, user.getId(), 
					//com.getId(), JSONObject.toJSONString(com));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}