package cn.org.chtf.card.manage.online.controller;

import cn.org.chtf.card.manage.online.model.OnlineActivityManage;
import cn.org.chtf.card.manage.online.service.OnlineActivityManageService;
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
 * Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/online/onlineActivityManage")
public class OnlineActivityManageController {

    @Autowired
    private OnlineActivityManageService onlineActivityManageService;
    
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
			List<OnlineActivityManage> list = onlineActivityManageService.list(map);			
			int count = onlineActivityManageService.listcount(map);
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
        List<OnlineActivityManage> onlineActivityManage =onlineActivityManageService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", onlineActivityManage);
    } 
    

    /**
     * 通过id查询单个
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        OnlineActivityManage onlineActivityManage =onlineActivityManageService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", onlineActivityManage);
    }   

    /**
     * 添加
     */
    @PostMapping("/save")
    public R save(@RequestBody OnlineActivityManage onlineActivityManage,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	onlineActivityManage.setSession(strSessionid);
        onlineActivityManageService.save(onlineActivityManage);
        
        sysOperationLogService.CreateEntity("添加在线活动管理", strSessionid, 0, user.getId(), 
				onlineActivityManage.getId(), JSONObject.toJSONString(onlineActivityManage));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody OnlineActivityManage onlineActivityManage,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
        onlineActivityManageService.update(onlineActivityManage);
        
        sysOperationLogService.CreateEntity("更新在线活动管理", strSessionid, 0, user.getId(), 
				onlineActivityManage.getId(), JSONObject.toJSONString(onlineActivityManage));
        
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
    	
    	OnlineActivityManage com = onlineActivityManageService.findById(id);
    	
        onlineActivityManageService.deleteById(id);
        
        sysOperationLogService.CreateEntity("删除在线活动管理", strSessionid, 0, user.getId(), 
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
    			
    				OnlineActivityManage com = onlineActivityManageService.findById(Integer.valueOf(id));
    				onlineActivityManageService.deleteById(Integer.valueOf(id));
    				
    				sysOperationLogService.CreateEntity("删除在线活动管理", strSessionid, 0, user.getId(), 
					com.getId(), JSONObject.toJSONString(com));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}