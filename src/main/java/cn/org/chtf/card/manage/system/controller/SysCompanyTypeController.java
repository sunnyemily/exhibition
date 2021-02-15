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
import cn.org.chtf.card.manage.system.model.SysCompanyType;
import cn.org.chtf.card.manage.system.service.SysCompanyTypeService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;

/**
 * 参展企业性质表Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/system/sysCompanyType")
public class SysCompanyTypeController {

    @Autowired
    private SysCompanyTypeService sysCompanyTypeService;
    
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
			List<SysCompanyType> list = sysCompanyTypeService.list(map);			
			int count = sysCompanyTypeService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
    

    /**
     * 通过id查询单个参展企业性质表
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        SysCompanyType sysCompanyType =sysCompanyTypeService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysCompanyType);
    }   

    /**
     * 添加参展企业性质表
     */
    @PostMapping("/save")
    public R save(@RequestBody SysCompanyType sysCompanyType,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	sysCompanyType.setSession(strSessionid);
        sysCompanyTypeService.save(sysCompanyType);
        
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("添加企业性质", strSessionid, 0, user.getId(), 
				sysCompanyType.getId(), JSONObject.toJSONString(sysCompanyType));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改参展企业性质表
     */
    @PostMapping("/update")
    public R update(@RequestBody SysCompanyType sysCompanyType,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
        sysCompanyTypeService.update(sysCompanyType);
        
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("更新企业性质", strSessionid, 0, user.getId(), 
				sysCompanyType.getId(), JSONObject.toJSONString(sysCompanyType));
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除参展企业性质表
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	SysCompanyType sct = sysCompanyTypeService.findById(id);
    	SysCompanyType sysCompanyType = new SysCompanyType();
    	sysCompanyType.setId(id);
    	sysCompanyType.setUseFlag("N");
        sysCompanyTypeService.update(sysCompanyType);
        
       //记录操作日志        	
		sysOperationLogService.CreateEntity("删除企业性质", strSessionid, 0, user.getId(), 
				sct.getId(), JSONObject.toJSONString(sct));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除参展企业性质表
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				SysCompanyType sct = sysCompanyTypeService.findById(Integer.valueOf(id));
    				SysCompanyType sysCompanyType = new SysCompanyType();
    		    	sysCompanyType.setId(Integer.valueOf(id));
    		    	sysCompanyType.setUseFlag("N");
    		        sysCompanyTypeService.update(sysCompanyType);   
    		        
    		        //记录操作日志    		         		
    		 		sysOperationLogService.CreateEntity("删除企业性质", strSessionid, 0, user.getId(), 
    		 				sct.getId(), JSONObject.toJSONString(sct));
    		        
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}