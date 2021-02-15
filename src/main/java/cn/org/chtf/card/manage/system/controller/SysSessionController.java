package cn.org.chtf.card.manage.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.manage.system.model.SysSession;
import cn.org.chtf.card.manage.system.service.SysExhibitionService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.common.utils.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 届次信息Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/system/sysSession")
public class SysSessionController {

    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private SysExhibitionService sysExhibitionService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;

    /**
     * 查询届次信息页面
     * @return 分页届次信息数据
     */
    @GetMapping("/page")
    public PageInfo<SysSession> page() {
        return sysSessionService.page(new RequestParamsUtil());
    }    
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map){
		try {
			//String strSessionid = sysSessionService.getSessionID();
			//map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<SysSession> list = sysSessionService.list(map);			
			int a = sysSessionService.listcount(map);
			return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    

    /**
     * 通过id查询单个届次信息
     */
    @GetMapping("/findById")
    public ResultVO findById(@RequestParam(value = "id") Integer id) {
        SysSession sysSession =sysSessionService.findById(id);
        return ResultVOUtil.success(sysSession);
    }   

    /**
     * 通过map查询单个届次信息
     */
    @GetMapping("/findByMap")
    public ResultVO findByMap() {
        SysSession sysSession =sysSessionService.findByMap(new RequestParamsUtil().getParameters());
        return ResultVOUtil.success(sysSession);
    }

    /**
     * 添加届次信息
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody SysSession sysSession,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	//sysSession.setSession(strSessionid);
    	if(sysSession.getStatus()==1){
    		SysSession sys = new SysSession();
    		sys.setStatus(0);
    		sys.setExhibitionid(sysSession.getExhibitionid());
    		sysSessionService.updateStatus(sys);
    	}
        sysSessionService.save(sysSession);
        
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("添加届次", strSessionid, 0, user.getId(), 
				sysSession.getId(), JSONObject.toJSONString(sysSession));
        
        return ResultVOUtil.success();
    }

    /**
     * 修改届次信息
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody SysSession sysSession, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	if(sysSession.getStatus()==1){
    		SysSession sys = new SysSession();
    		sys.setStatus(0);
    		sys.setExhibitionid(sysSession.getExhibitionid());
    		sysSessionService.updateStatus(sys);
    	}   
        sysSessionService.update(sysSession);
        
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("更新届次", strSessionid, 0, user.getId(), 
				sysSession.getId(), JSONObject.toJSONString(sysSession));
        
        return ResultVOUtil.success();
    }

    /**
     * 删除届次信息
     */
    @GetMapping("/deleteById")
    public ResultVO deleteById(@RequestParam(value = "id") Integer id, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	SysSession ss = sysSessionService.findById(id);    	
        sysSessionService.deleteById(id);        
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("删除届次", strSessionid, 0, user.getId(), 
				ss.getId(), JSONObject.toJSONString(ss));
        
        return ResultVOUtil.success();

    }
    
    /**
     * 批量删除届次信息
     */
    @GetMapping("/delAll")
    public ResultVO delAll(@RequestParam(value = "isStr") String isStr, HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				SysSession ss = sysSessionService.findById(Integer.valueOf(id));      				
    				sysSessionService.deleteById(Integer.valueOf(id));
    				//记录操作日志
    		        User user = (User)session.getAttribute("user");		
    				sysOperationLogService.CreateEntity("删除届次", strSessionid, 0, user.getId(), 
    						ss.getId(), JSONObject.toJSONString(ss));
    			}
    		}
    	}
    	return ResultVOUtil.success();
    }
    
    /**
     * 得到当前展会下开放的届次
     * @param map
     * @return
     */
    @RequestMapping("/getlist")
	public ResultVO getlist(@RequestBody Map<String,Object> map){
		List<Map<String,Object>> lit = sysExhibitionService.getList(map);
		return ResultVOUtil.success(lit);
	} 
    
    /**
     * 得到指定展会最新的届次
     * @param map
     * @return
     */
    @RequestMapping("/GetLastSessionInfoByExhibitionid")
	public ResultVO GetLastSessionInfoByExhibitionid(@RequestBody Map<String,Object> map){
    	SysSession result = sysSessionService.GetLastSessionInfoByExhibitionid(map);
		if(result==null){
			return ResultVOUtil.error();
		}
		else{
			return ResultVOUtil.success(result);
		}
	} 

}