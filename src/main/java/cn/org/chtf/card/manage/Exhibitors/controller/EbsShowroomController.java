package cn.org.chtf.card.manage.Exhibitors.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroom;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsShowroomService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsShowroomtypeService;
import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.common.utils.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 参展商管理-展厅管理Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsShowroom")
public class EbsShowroomController {

    @Autowired
    private EbsShowroomService ebsShowroomService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private EbsBoothService ebsBoothService;
    
    @Autowired
    private EbsShowroomtypeService ebsShowroomtypeService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    

    /**
     * 查询参展商管理-展厅管理页面
     * @return 分页参展商管理-展厅管理数据
     */
    @GetMapping("/page")
    public PageInfo<EbsShowroom> page() {
        return ebsShowroomService.page(new RequestParamsUtil());
    }    
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsShowroom> list = ebsShowroomService.list(map);			
			int a = ebsShowroomService.listcount(map);
			return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    
    @RequestMapping("/Previouslist")
	public R Previouslist(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			//String strSessionid = sysSessionService.getSessionID(request);
			//map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsShowroom> list = ebsShowroomService.list(map);			
			int a = ebsShowroomService.listcount(map);
			return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    

    /**
     * 通过id查询单个参展商管理-展厅管理
     */
    @GetMapping("/findById")
    public ResultVO findById(@RequestParam(value = "id") Integer id) {
        EbsShowroom ebsShowroom =ebsShowroomService.findById(id);
        return ResultVOUtil.success(ebsShowroom);
    }   

    /**
     * 通过map查询单个参展商管理-展厅管理
     */
    @GetMapping("/findByMap")
    public ResultVO findByMap() {
        EbsShowroom ebsShowroom =ebsShowroomService.findByMap(new RequestParamsUtil().getParameters());
        return ResultVOUtil.success(ebsShowroom);
    }

    /**
     * 添加参展商管理-展厅管理
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody EbsShowroom ebsShowroom, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	ebsShowroom.setSession(strSessionid);
        ebsShowroomService.save(ebsShowroom);
        sysOperationLogService.CreateEntity("添加展厅", strSessionid, 0, user.getId(), 
        		ebsShowroom.getId(), JSONObject.toJSONString(ebsShowroom));
        return ResultVOUtil.success();
    }

    /**
     * 修改参展商管理-展厅管理
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody EbsShowroom ebsShowroom, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
        ebsShowroomService.update(ebsShowroom);
        sysOperationLogService.CreateEntity("更新展厅", strSessionid, 0, user.getId(), 
        		ebsShowroom.getId(), JSONObject.toJSONString(ebsShowroom));
        return ResultVOUtil.success();
    }

    /**
     * 删除参展商管理-展厅管理
     */
    @GetMapping("/deleteById")
    public ResultVO deleteById(@RequestParam(value = "id") Integer id, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	EbsShowroom es = ebsShowroomService.findById(id);
        ebsShowroomService.deleteById(id);
        sysOperationLogService.CreateEntity("删除展厅", strSessionid, 0, user.getId(), 
        		es.getId(), JSONObject.toJSONString(es));
        return ResultVOUtil.success();

    }
    
    /**
     * 批量删除参展商管理-展厅管理
     */
    @GetMapping("/delAll")
    public ResultVO delAll(@RequestParam(value = "isStr") String isStr, HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				EbsShowroom es = ebsShowroomService.findById(Integer.valueOf(id));
    				ebsShowroomService.deleteById(Integer.valueOf(id));
    				sysOperationLogService.CreateEntity("删除展厅", strSessionid, 0, user.getId(), 
    		        		es.getId(), JSONObject.toJSONString(es));
    			}
    		}
    	}
    	return ResultVOUtil.success();
    }
    
    /**
     * 通过map查询参展商管理-展厅展位
     */
    @RequestMapping("/GetBoothByRoomID")
    public R GetBoothByRoomID(@RequestParam Map<String,Object> map) {
        List<Map<String,Object>> list = ebsBoothService.GetBoothByMap(map);
    	return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", list.size());
    }
    
    @RequestMapping("/UseShowRoom")
	public R UseShowRoom(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
		map.put("strSessionid", strSessionid);
		ebsShowroomService.UseShowRoom(map);
		sysOperationLogService.CreateEntity("提取历届展厅", strSessionid, 0, user.getId(), 
        		0, JSONObject.toJSONString(map));
		
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}

}