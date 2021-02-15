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
import cn.org.chtf.card.manage.system.model.SysCountryArea;
import cn.org.chtf.card.manage.system.service.SysCountryAreaService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;

/**
 * 国家地区表Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/system/sysCountryArea")
public class SysCountryAreaController {

    @Autowired
    private SysCountryAreaService sysCountryAreaService;
    
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
			List<SysCountryArea> list = sysCountryAreaService.list(map);			
			int count = sysCountryAreaService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
    

    /**
     * 通过id查询单个国家地区表
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") String id) {
        SysCountryArea sysCountryArea =sysCountryAreaService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysCountryArea);
    }   

    /**
     * 添加国家地区表
     */
    @PostMapping("/save")
    public R save(@RequestBody SysCountryArea sysCountryArea,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	sysCountryArea.setSession(strSessionid);
        sysCountryAreaService.save(sysCountryArea);
        
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("添加国家地区", strSessionid, 0, user.getId(), 
				Integer.valueOf(sysCountryArea.getId()), JSONObject.toJSONString(sysCountryArea));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改国家地区表
     */
    @PostMapping("/update")
    public R update(@RequestBody SysCountryArea sysCountryArea,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
        sysCountryAreaService.update(sysCountryArea);
        
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("更新国家地区", strSessionid, 0, user.getId(), 
				Integer.valueOf(sysCountryArea.getId()), JSONObject.toJSONString(sysCountryArea));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除国家地区表
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") String id,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	SysCountryArea sca = sysCountryAreaService.findById(id);
        sysCountryAreaService.deleteById(id);
        
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("删除国家地区", strSessionid, 0, user.getId(), 
				Integer.valueOf(sca.getId()), JSONObject.toJSONString(sca));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除国家地区表
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null && !id.equals("")){
    				SysCountryArea sca = sysCountryAreaService.findById(id);
    				sysCountryAreaService.deleteById(id);
    				
    				//记录操作日志    		        
    				sysOperationLogService.CreateEntity("删除国家地区", strSessionid, 0, user.getId(), 
    						Integer.valueOf(sca.getId()), JSONObject.toJSONString(sca));
    				
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }
    
    @GetMapping("/loadCountry")
    public List<Map<String, Object>> loadCountry(){
    	return sysCountryAreaService.loadCountry();
    }

    @GetMapping("/loadProvince")
    public List<Map<String, Object>> loadProvince(@RequestParam(value = "parentId") String parentId){
    	return sysCountryAreaService.loadProvince(parentId);
    }
    
    @GetMapping("/loadCity")
    public List<Map<String, Object>> loadCity(@RequestParam(value = "parentId") String parentId){
    	return sysCountryAreaService.loadCity(parentId);
    }
}