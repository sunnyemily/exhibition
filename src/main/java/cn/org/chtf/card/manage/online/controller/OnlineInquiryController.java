package cn.org.chtf.card.manage.online.controller;

import cn.org.chtf.card.manage.online.model.OnlineInquiry;
import cn.org.chtf.card.manage.online.service.OnlineInquiryService;
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
 * 在线询盘Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/online/onlineInquiry")
public class OnlineInquiryController {

    @Autowired
    private OnlineInquiryService onlineInquiryService;
    
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
			List<OnlineInquiry> list = onlineInquiryService.list(map);			
			int count = onlineInquiryService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个在线询盘
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<OnlineInquiry> onlineInquiry =onlineInquiryService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", onlineInquiry);
    } 
    

    /**
     * 通过id查询单个在线询盘
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        OnlineInquiry onlineInquiry =onlineInquiryService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", onlineInquiry);
    }   

    /**
     * 添加在线询盘
     */
    @PostMapping("/save")
    public R save(@RequestBody OnlineInquiry onlineInquiry,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	onlineInquiry.setSession(Integer.valueOf(strSessionid));
    	
        onlineInquiryService.save(onlineInquiry);
        
        sysOperationLogService.CreateEntity("添加打印模版", strSessionid, 0, user.getId(), 
				onlineInquiry.getId(), JSONObject.toJSONString(onlineInquiry));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改在线询盘
     */
    @PostMapping("/update")
    public R update(@RequestBody OnlineInquiry onlineInquiry,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	
    	onlineInquiry.setAudituser(user.getId());
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	onlineInquiry.setAudittime(df.format(new Date()));
    	
        onlineInquiryService.update(onlineInquiry);
        
        sysOperationLogService.CreateEntity("更新打印模版", strSessionid, 0, user.getId(), 
				onlineInquiry.getId(), JSONObject.toJSONString(onlineInquiry));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除在线询盘
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	
    	OnlineInquiry com = onlineInquiryService.findById(id);
    	
        onlineInquiryService.deleteById(id);
        
        sysOperationLogService.CreateEntity("删除打印膜版", strSessionid, 0, user.getId(), 
				com.getId(), JSONObject.toJSONString(com));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除在线询盘
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
    			
    				OnlineInquiry com = onlineInquiryService.findById(Integer.valueOf(id));
    				onlineInquiryService.deleteById(Integer.valueOf(id));
    				
    				sysOperationLogService.CreateEntity("删除打印膜版", strSessionid, 0, user.getId(), 
					com.getId(), JSONObject.toJSONString(com));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}