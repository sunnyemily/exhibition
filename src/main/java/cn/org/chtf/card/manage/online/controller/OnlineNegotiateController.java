package cn.org.chtf.card.manage.online.controller;

import cn.org.chtf.card.manage.online.model.OnlineNegotiate;
import cn.org.chtf.card.manage.online.service.OnlineNegotiateService;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.List;

import cn.org.chtf.card.manage.user.pojo.User;

import com.alibaba.fastjson.JSONObject;

/**
 * 预约洽谈Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/online/onlineNegotiate")
public class OnlineNegotiateController {

    @Autowired
    private OnlineNegotiateService onlineNegotiateService;
    
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
			List<OnlineNegotiate> list = onlineNegotiateService.list(map);			
			int count = onlineNegotiateService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个预约洽谈
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<OnlineNegotiate> onlineNegotiate =onlineNegotiateService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", onlineNegotiate);
    } 
    

    /**
     * 通过id查询单个预约洽谈
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        OnlineNegotiate onlineNegotiate =onlineNegotiateService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", onlineNegotiate);
    }   

    /**
     * 添加预约洽谈
     */
    @PostMapping("/save")
    public R save(@RequestBody OnlineNegotiate onlineNegotiate,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	onlineNegotiate.setSession(Integer.valueOf(strSessionid));
        onlineNegotiateService.save(onlineNegotiate);
        
        sysOperationLogService.CreateEntity("添加打印模版", strSessionid, 0, user.getId(), 
				onlineNegotiate.getId(), JSONObject.toJSONString(onlineNegotiate));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改预约洽谈
     */
    @PostMapping("/update")
    public R update(@RequestBody OnlineNegotiate onlineNegotiate,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	
    	onlineNegotiate.setAudituser(user.getId());
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	onlineNegotiate.setAudittime(df.format(new Date()));
    	
        onlineNegotiateService.update(onlineNegotiate);
        
        sysOperationLogService.CreateEntity("更新打印模版", strSessionid, 0, user.getId(), 
				onlineNegotiate.getId(), JSONObject.toJSONString(onlineNegotiate));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除预约洽谈
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	
    	OnlineNegotiate com = onlineNegotiateService.findById(id);
    	
        onlineNegotiateService.deleteById(id);
        
        sysOperationLogService.CreateEntity("删除打印膜版", strSessionid, 0, user.getId(), 
				com.getId(), JSONObject.toJSONString(com));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除预约洽谈
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
    			
    				OnlineNegotiate com = onlineNegotiateService.findById(Integer.valueOf(id));
    				onlineNegotiateService.deleteById(Integer.valueOf(id));
    				
    				sysOperationLogService.CreateEntity("删除打印膜版", strSessionid, 0, user.getId(), 
					com.getId(), JSONObject.toJSONString(com));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}