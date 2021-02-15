package cn.org.chtf.card.manage.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.manage.system.model.SysExhibition;
import cn.org.chtf.card.manage.system.service.SysExhibitionService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
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
 * 展会信息Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/system/sysExhibition")
public class SysExhibitionController {

    @Autowired
    private SysExhibitionService sysExhibitionService;    
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;

    /**
     * 查询展会信息页面
     * @return 分页展会信息数据
     */
    @GetMapping("/page")
    public PageInfo<SysExhibition> page() {
        return sysExhibitionService.page(new RequestParamsUtil());
    }    
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map){
		try {
			//String strSessionid = sysSessionService.getSessionID();
			//map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<SysExhibition> list = sysExhibitionService.list(map);			
			int a = sysExhibitionService.listcount(map);
			return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    

    /**
     * 通过id查询单个展会信息
     */
    @GetMapping("/findById")
    public ResultVO findById(@RequestParam(value = "id") Integer id) {
        SysExhibition sysExhibition =sysExhibitionService.findById(id);
        return ResultVOUtil.success(sysExhibition);
    }   

    /**
     * 通过map查询单个展会信息
     */
    @GetMapping("/findByMap")
    public ResultVO findByMap() {
        SysExhibition sysExhibition =sysExhibitionService.findByMap(new RequestParamsUtil().getParameters());
        return ResultVOUtil.success(sysExhibition);
    }

    /**
     * 添加展会信息
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody SysExhibition sysExhibition,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	//sysExhibition.setSession(strSessionid);
        sysExhibitionService.save(sysExhibition);
        
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("添加展会", strSessionid, 0, user.getId(), 
				sysExhibition.getId(), JSONObject.toJSONString(sysExhibition));
        
        return ResultVOUtil.success();
    }

    /**
     * 修改展会信息
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody SysExhibition sysExhibition,HttpServletRequest request, HttpSession session) {
        sysExhibitionService.update(sysExhibition);
        String strSessionid = sysSessionService.getSessionID(request);
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("更新展会", strSessionid, 0, user.getId(), 
				sysExhibition.getId(), JSONObject.toJSONString(sysExhibition));
        
        return ResultVOUtil.success();
    }

    /**
     * 删除展会信息
     */
    @GetMapping("/deleteById")
    public ResultVO deleteById(@RequestParam(value = "id") Integer id,HttpServletRequest request, HttpSession session) {
    	SysExhibition sysExhibition = sysExhibitionService.findById(id);
        sysExhibitionService.deleteById(id);
        String strSessionid = sysSessionService.getSessionID(request);
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("删除展会", strSessionid, 0, user.getId(), 
				sysExhibition.getId(), JSONObject.toJSONString(sysExhibition));
        
        return ResultVOUtil.success();

    }
    
    /**
     * 批量删除展会信息
     */
    @GetMapping("/delAll")
    public ResultVO delAll(@RequestParam(value = "isStr") String isStr,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				SysExhibition sysExhibition = sysExhibitionService.findById(Integer.valueOf(id));
    				sysExhibitionService.deleteById(Integer.valueOf(id));    				
    				
    		        //记录操作日志
    		        User user = (User)session.getAttribute("user");		
    				sysOperationLogService.CreateEntity("删除展会", strSessionid, 0, user.getId(), 
    						sysExhibition.getId(), JSONObject.toJSONString(sysExhibition));
    			}
    		}
    	}
    	return ResultVOUtil.success();
    }

}