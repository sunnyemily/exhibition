package cn.org.chtf.card.manage.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.system.model.SysIndustry;
import cn.org.chtf.card.manage.system.service.SysIndustryService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;

/**
 * 行业表Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/system/sysIndustry")
public class SysIndustryController {

    @Autowired
    private SysIndustryService sysIndustryService;
    
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
			List<SysIndustry> list = sysIndustryService.list(map);			
			int count = sysIndustryService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
    

    /**
     * 通过id查询单个行业表
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        SysIndustry sysIndustry =sysIndustryService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysIndustry);
    }   

    /**
     * 添加行业表
     */
    @PostMapping("/save")
    public R save(@RequestBody SysIndustry sysIndustry,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	sysIndustry.setSession(strSessionid);
        sysIndustryService.save(sysIndustry);
        
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("添加行业分类", strSessionid, 0, user.getId(), 
				sysIndustry.getId(), JSONObject.toJSONString(sysIndustry));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改行业表
     */
    @PostMapping("/update")
    public R update(@RequestBody SysIndustry sysIndustry,HttpServletRequest request, HttpSession session) {
        sysIndustryService.update(sysIndustry);
        String strSessionid = sysSessionService.getSessionID(request);
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("更新行业分类", strSessionid, 0, user.getId(), 
				sysIndustry.getId(), JSONObject.toJSONString(sysIndustry));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除行业表
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id,HttpServletRequest request, HttpSession session) {
    	
    	SysIndustry sysIndustry = new SysIndustry();
    	sysIndustry.setId(id);
    	sysIndustry.setUseFlag("N");
        sysIndustryService.update(sysIndustry);
        String strSessionid = sysSessionService.getSessionID(request);
        SysIndustry si = sysIndustryService.findById(id);
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("删除行业分类", strSessionid, 0, user.getId(), 
				si.getId(), JSONObject.toJSONString(si));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除行业表
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				SysIndustry sysIndustry = new SysIndustry();
    		    	sysIndustry.setId(Integer.valueOf(id));
    		    	sysIndustry.setUseFlag("N");
    		        sysIndustryService.update(sysIndustry);
    		        
    		        
    		        SysIndustry si = sysIndustryService.findById(Integer.valueOf(id));
    		        //记录操作日志
    		        User user = (User)session.getAttribute("user");		
    				sysOperationLogService.CreateEntity("删除行业分类", strSessionid, 0, user.getId(), 
    						si.getId(), JSONObject.toJSONString(si));
    		        
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}