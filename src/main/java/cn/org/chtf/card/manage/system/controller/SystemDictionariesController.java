package cn.org.chtf.card.manage.system.controller;

import cn.org.chtf.card.manage.system.model.SystemDictionaries;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SystemDictionariesService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.support.util.WConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * 字典表Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/system/systemDictionaries")
public class SystemDictionariesController {

    @Autowired
    private SystemDictionariesService systemDictionariesService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map){
		try {			
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<SystemDictionaries> list = systemDictionariesService.list(map);			
			int count = systemDictionariesService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
    

    /**
     * 通过dicid查询单个字典表
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "dicid") Integer dicid) {
        SystemDictionaries systemDictionaries =systemDictionariesService.findById(dicid);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", systemDictionaries);
    }   

    /**
     * 添加字典表
     */
    @PostMapping("/save")
    public R save(@RequestBody SystemDictionaries systemDictionaries,HttpServletRequest request, HttpSession session) {    
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
        systemDictionariesService.save(systemDictionaries);
        
        sysOperationLogService.CreateEntity("添加字典项", strSessionid, 0, user.getId(), 
        		systemDictionaries.getDicid(), JSONObject.toJSONString(systemDictionaries));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改字典表
     */
    @PostMapping("/update")
    public R update(@RequestBody SystemDictionaries systemDictionaries,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
        systemDictionariesService.update(systemDictionaries);
        
        sysOperationLogService.CreateEntity("编辑字典项", strSessionid, 0, user.getId(), 
        		systemDictionaries.getDicid(), JSONObject.toJSONString(systemDictionaries));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除字典表
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "dicid") Integer dicid,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");
    	Map<String,Object> par = new HashMap<String,Object>();
    	par.put("dicParentid", dicid);
    	List<SystemDictionaries> lit = systemDictionariesService.findByMap(par);
    	if(lit.size()>0){
    		return R.error().put("code", WConst.ERROR).put("msg", "此栏目下存在子集，清先删除子集后");
    	}
    	
    	SystemDictionaries sd = systemDictionariesService.findById(dicid);
        systemDictionariesService.deleteById(dicid);
        
        sysOperationLogService.CreateEntity("删除字典项", strSessionid, 0, user.getId(), 
        		sd.getDicid(), JSONObject.toJSONString(sd));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除字典表
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				SystemDictionaries sd = systemDictionariesService.findById(Integer.valueOf(id));
    				systemDictionariesService.deleteById(Integer.valueOf(id));
    				
    				sysOperationLogService.CreateEntity("删除字典项", strSessionid, 0, user.getId(), 
    		        		sd.getDicid(), JSONObject.toJSONString(sd));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }
    
    @RequestMapping("/findByMap")
	public R findByMap(@RequestBody Map<String,Object> map,HttpServletRequest request){  
		List<SystemDictionaries> list = systemDictionariesService.findByMap(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 

}