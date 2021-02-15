package cn.org.chtf.card.manage.system.controller;

import cn.org.chtf.card.manage.system.model.SysSurveyAnswer;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSurveyAnswerService;
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
 * 调查涉及答案表Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/system/sysSurveyAnswer")
public class SysSurveyAnswerController {

    @Autowired
    private SysSurveyAnswerService sysSurveyAnswerService;
    
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
			List<SysSurveyAnswer> list = sysSurveyAnswerService.list(map);			
			int count = sysSurveyAnswerService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个调查涉及答案表
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<SysSurveyAnswer> sysSurveyAnswer =sysSurveyAnswerService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysSurveyAnswer);
    } 
    

    /**
     * 通过id查询单个调查涉及答案表
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        SysSurveyAnswer sysSurveyAnswer =sysSurveyAnswerService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysSurveyAnswer);
    }   

    /**
     * 添加调查涉及答案表
     */
    @PostMapping("/save")
    public R save(@RequestBody SysSurveyAnswer sysSurveyAnswer,HttpServletRequest request, HttpSession session) {  
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");  	
        sysSurveyAnswerService.save(sysSurveyAnswer);
        
        sysOperationLogService.CreateEntity("添加调查问卷问题答案", strSessionid, 0, user.getId(), 
        		sysSurveyAnswer.getId(), JSONObject.toJSONString(sysSurveyAnswer));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改调查涉及答案表
     */
    @PostMapping("/update")
    public R update(@RequestBody SysSurveyAnswer sysSurveyAnswer,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");
        sysSurveyAnswerService.update(sysSurveyAnswer);
        
        sysOperationLogService.CreateEntity("更新调查问卷问题答案", strSessionid, 0, user.getId(), 
        		sysSurveyAnswer.getId(), JSONObject.toJSONString(sysSurveyAnswer));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除调查涉及答案表
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");
    	SysSurveyAnswer ssa = sysSurveyAnswerService.findById(id);
        sysSurveyAnswerService.deleteById(id);
        
        sysOperationLogService.CreateEntity("删除调查问卷问题答案", strSessionid, 0, user.getId(), 
        		ssa.getId(), JSONObject.toJSONString(ssa));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除调查涉及答案表
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				SysSurveyAnswer ssa = sysSurveyAnswerService.findById(Integer.valueOf(id));
    		        sysSurveyAnswerService.deleteById(Integer.valueOf(id));
    		        
    		        sysOperationLogService.CreateEntity("删除调查问卷问题答案", strSessionid, 0, user.getId(), 
    		        		ssa.getId(), JSONObject.toJSONString(ssa));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}