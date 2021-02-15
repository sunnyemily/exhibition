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
import cn.org.chtf.card.manage.system.model.SysSurveyQuestion;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.system.service.SysSurveyAnswerService;
import cn.org.chtf.card.manage.system.service.SysSurveyQuestionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;

/**
 * 调查涉及问题表Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/system/sysSurveyQuestion")
public class SysSurveyQuestionController {

    @Autowired
    private SysSurveyQuestionService sysSurveyQuestionService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private SysSurveyAnswerService sysSurveyAnswerService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<SysSurveyQuestion> list = sysSurveyQuestionService.list(map);			
			int count = sysSurveyQuestionService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个调查涉及问题表
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<SysSurveyQuestion> sysSurveyQuestion =sysSurveyQuestionService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysSurveyQuestion);
    } 
    

    /**
     * 通过id查询单个调查涉及问题表
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        SysSurveyQuestion sysSurveyQuestion =sysSurveyQuestionService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysSurveyQuestion);
    }   

    /**
     * 添加调查涉及问题表
     */
    @PostMapping("/save")
    public R save(@RequestBody SysSurveyQuestion sysSurveyQuestion,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
        sysSurveyQuestionService.save(sysSurveyQuestion);
        
        sysOperationLogService.CreateEntity("添加调查问卷问题", strSessionid, 0, user.getId(), 
        		sysSurveyQuestion.getId(), JSONObject.toJSONString(sysSurveyQuestion));
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改调查涉及问题表
     */
    @PostMapping("/update")
    public R update(@RequestBody SysSurveyQuestion sysSurveyQuestion,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
        sysSurveyQuestionService.update(sysSurveyQuestion);
        
        sysOperationLogService.CreateEntity("更新调查问卷问题", strSessionid, 0, user.getId(), 
        		sysSurveyQuestion.getId(), JSONObject.toJSONString(sysSurveyQuestion));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除调查涉及问题表
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	SysSurveyQuestion ssq = sysSurveyQuestionService.findById(id);
        sysSurveyQuestionService.deleteById(id);
        sysSurveyAnswerService.deleteByQuestionId(id);
        
        sysOperationLogService.CreateEntity("删除调查问卷问题", strSessionid, 0, user.getId(), 
        		ssq.getId(), JSONObject.toJSONString(ssq));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除调查涉及问题表
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				SysSurveyQuestion ssq = sysSurveyQuestionService.findById(Integer.valueOf(id));
    				sysSurveyQuestionService.deleteById(Integer.valueOf(id));
    				
    				 sysOperationLogService.CreateEntity("删除调查问卷问题", strSessionid, 0, user.getId(), 
    			        		ssq.getId(), JSONObject.toJSONString(ssq));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}