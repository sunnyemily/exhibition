package cn.org.chtf.card.manage.online.controller;

import cn.org.chtf.card.manage.online.model.OnlineReservationDetails;
import cn.org.chtf.card.manage.online.service.OnlineReservationDetailsService;
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

/**
 * 线上预约Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/online/onlineReservationDetails")
public class OnlineReservationDetailsController {

    @Autowired
    private OnlineReservationDetailsService onlineReservationDetailsService;
    
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
			List<OnlineReservationDetails> list = onlineReservationDetailsService.list(map);			
			int count = onlineReservationDetailsService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个线上预约
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<OnlineReservationDetails> onlineReservationDetails =onlineReservationDetailsService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", onlineReservationDetails);
    } 
    

    /**
     * 通过id查询单个线上预约
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        OnlineReservationDetails onlineReservationDetails =onlineReservationDetailsService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", onlineReservationDetails);
    }   

    /**
     * 添加线上预约
     */
    @PostMapping("/save")
    public R save(@RequestBody OnlineReservationDetails onlineReservationDetails,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	onlineReservationDetails.setSession(Integer.valueOf(strSessionid));
        onlineReservationDetailsService.save(onlineReservationDetails);
        
        sysOperationLogService.CreateEntity("添加线上预约信息", strSessionid, 0, user.getId(), 
				onlineReservationDetails.getId(), JSONObject.toJSONString(onlineReservationDetails));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改线上预约
     */
    @PostMapping("/update")
    public R update(@RequestBody OnlineReservationDetails onlineReservationDetails,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
        onlineReservationDetailsService.update(onlineReservationDetails);
        
        sysOperationLogService.CreateEntity("更新线上预约信息", strSessionid, 0, user.getId(), 
				onlineReservationDetails.getId(), JSONObject.toJSONString(onlineReservationDetails));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除线上预约
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	
    	OnlineReservationDetails com = onlineReservationDetailsService.findById(id);
    	
        onlineReservationDetailsService.deleteById(id);
        
        sysOperationLogService.CreateEntity("删除线上预约信息", strSessionid, 0, user.getId(), 
				com.getId(), JSONObject.toJSONString(com));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除线上预约
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
    			
    				OnlineReservationDetails com = onlineReservationDetailsService.findById(Integer.valueOf(id));
    				onlineReservationDetailsService.deleteById(Integer.valueOf(id));
    				
    				sysOperationLogService.CreateEntity("删除线上预约信息", strSessionid, 0, user.getId(), 
					com.getId(), JSONObject.toJSONString(com));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}