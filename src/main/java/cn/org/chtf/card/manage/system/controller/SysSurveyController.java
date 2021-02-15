package cn.org.chtf.card.manage.system.controller;

import cn.org.chtf.card.manage.system.model.SysSurvey;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSurveyResultService;
import cn.org.chtf.card.manage.system.service.SysSurveyService;
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

import java.util.Map;
import java.util.List;

/**
 * 调查信息表Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/system/sysSurvey")
public class SysSurveyController {

    @Autowired
    private SysSurveyService sysSurveyService;
    
    @Autowired
    private SysSurveyResultService sysSurveyResultService;
    
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
			List<SysSurvey> list = sysSurveyService.list(map);			
			int count = sysSurveyService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个调查信息表
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<SysSurvey> sysSurvey =sysSurveyService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysSurvey);
    } 
    

    /**
     * 通过id查询单个调查信息表
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        SysSurvey sysSurvey =sysSurveyService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysSurvey);
    }   

    /**
     * 添加调查信息表
     */
    @PostMapping("/save")
    public R save(@RequestBody SysSurvey sysSurvey,HttpServletRequest request, HttpSession session) {
    	User user = (User)session.getAttribute("user"); 
    	sysSurvey.setCreateby(user.getId());
    	String strSessionid = sysSessionService.getSessionID(request);
    	sysSurvey.setSession(strSessionid);
        sysSurveyService.save(sysSurvey);
        
        sysOperationLogService.CreateEntity("添加调查表", strSessionid, 0, user.getId(), 
        		sysSurvey.getId(), JSONObject.toJSONString(sysSurvey));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改调查信息表
     */
    @PostMapping("/update")
    public R update(@RequestBody SysSurvey sysSurvey,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user"); 
        sysSurveyService.update(sysSurvey);
        
        sysOperationLogService.CreateEntity("更新调查表", strSessionid, 0, user.getId(), 
        		sysSurvey.getId(), JSONObject.toJSONString(sysSurvey));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除调查信息表
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user"); 
    	SysSurvey ss = sysSurveyService.findById(id);
        sysSurveyService.deleteById(id);
        
        sysOperationLogService.CreateEntity("删除调查表", strSessionid, 0, user.getId(), 
        		ss.getId(), JSONObject.toJSONString(ss));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除调查信息表
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user"); 
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				SysSurvey ss = sysSurveyService.findById(Integer.valueOf(id));
    				sysSurveyService.deleteById(Integer.valueOf(id));
    				sysOperationLogService.CreateEntity("删除调查表", strSessionid, 0, user.getId(), 
    		        		ss.getId(), JSONObject.toJSONString(ss));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }
    
    @RequestMapping("/saveSurveyAnswer")
	public R saveSurveyAnswer(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session){ 
    	if(String.valueOf(map.get("chkvalue")).equals("")){
    		return R.error().put("code", WConst.ERROR).put("msg", "请完整填写调查表");
    	}    	
    	User user = (User)session.getAttribute("user"); 
    	String strSessionid = sysSessionService.getSessionID(request);
		map.put("session",strSessionid);
		sysSurveyResultService.saveSurveyAnswer(map, session);
		
		sysOperationLogService.CreateEntity("保存调查表结果", strSessionid, 0, user.getId(), 
        		Integer.valueOf(String.valueOf(map.get("surveyid"))), JSONObject.toJSONString(map));
		
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 

}