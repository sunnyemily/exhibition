package cn.org.chtf.card.manage.Exhibitors.controller;

import java.util.HashMap;
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
import cn.org.chtf.card.manage.Exhibitors.model.EbsBooth;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;

/**
 * 参展商管理-展位Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsBooth")
public class EbsBoothController {

    @Autowired
    private EbsBoothService ebsBoothService;
    
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
			List<EbsBooth> list = ebsBoothService.list(map);
			int count = ebsBoothService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
    

    /**
     * 通过id查询单个参展商管理-展位
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        EbsBooth ebsBooth =ebsBoothService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", ebsBooth);
    }   

    /**
     * 添加参展商管理-展位
     */
    @PostMapping("/save")
    public R save(@RequestBody EbsBooth ebsBooth, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	ebsBooth.setSession(strSessionid);   	
        User user = (User)session.getAttribute("user");				
    	
    	if(ebsBooth.getName()!=null && ebsBooth.getName().indexOf("-")>0) {
    		String start = ebsBooth.getName().split("-")[0];
    		String end = ebsBooth.getName().split("-")[1];
    		int j = end.length();
    		for(int i=Integer.valueOf(start); i<=Integer.valueOf(end); i++) {
    			ebsBooth.setName(ebsBooth.getShowRoomName()+String.format("%0"+j+"d",i));
    			ebsBoothService.save(ebsBooth);
    			sysOperationLogService.CreateEntity("添加展位", strSessionid, 0, user.getId(), 
    					ebsBooth.getId(), JSONObject.toJSONString(ebsBooth));
    		}
    	}else {
    		ebsBooth.setName(ebsBooth.getShowRoomName()+ebsBooth.getName());
    		ebsBoothService.save(ebsBooth);
    		sysOperationLogService.CreateEntity("添加展位", strSessionid, 0, user.getId(), 
    				ebsBooth.getId(), JSONObject.toJSONString(ebsBooth));
    	}   
    	
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改参展商管理-展位
     */
    @PostMapping("/update")
    public R update(@RequestBody EbsBooth ebsBooth, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
        ebsBoothService.update(ebsBooth);
        
        sysOperationLogService.CreateEntity("更新展位", strSessionid, 0, user.getId(), 
				ebsBooth.getId(), JSONObject.toJSONString(ebsBooth));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除参展商管理-展位
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	EbsBooth ebh = ebsBoothService.findById(id);
        ebsBoothService.deleteById(id);
        sysOperationLogService.CreateEntity("删除展位", strSessionid, 0, user.getId(), 
        		ebh.getId(), JSONObject.toJSONString(ebh));
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除参展商管理-展位
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr, HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				EbsBooth ebh = ebsBoothService.findById(Integer.valueOf(id));
    				ebsBoothService.deleteById(Integer.valueOf(id));
    				sysOperationLogService.CreateEntity("删除展位", strSessionid, 0, user.getId(), 
    		        		ebh.getId(), JSONObject.toJSONString(ebh));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

    /**
     * 释放展位
     */
    @GetMapping("/releaseExhibitionLocation")
    public R releaseExhibitionLocation(@RequestParam(value = "id") Integer id, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	EbsBooth ebh = ebsBoothService.findById(id);
        ebsBoothService.releaseById(id);
        sysOperationLogService.CreateEntity("释放展位", strSessionid, 0, user.getId(), 
        		ebh.getId(), JSONObject.toJSONString(ebh));
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);

    }
    
    /**
     * 批量释放展位
     */
    @GetMapping("/batchReleaseExhibitionLocation")
    public R batchReleaseExhibitionLocation(@RequestParam(value = "isStr") String isStr, HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				EbsBooth ebh = ebsBoothService.findById(Integer.valueOf(id));
    				ebsBoothService.releaseById(Integer.valueOf(id));
    				sysOperationLogService.CreateEntity("释放展位", strSessionid, 0, user.getId(), 
    		        		ebh.getId(), JSONObject.toJSONString(ebh));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

	//加载所有展位名称
    @GetMapping("/loadAllBooth")
    public R loadAllBooth(HttpServletRequest request){
    	Map<String,Object> map = new HashMap<>();
    	String strSessionid = sysSessionService.getSessionID(request);
    	map.put("session", strSessionid);
    	List<String> list = ebsBoothService.queryAllBooth(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    }
    
    //加载所有展厅名称
    @GetMapping("/loadAllShowRoom")
    public R loadAllShowRoom(HttpServletRequest request){
    	Map<String,Object> map = new HashMap<>();
    	String strSessionid = sysSessionService.getSessionID(request);
    	map.put("session", strSessionid);
    	List<String> list = ebsBoothService.queryAllShowRoom(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    }
    
    //加载所有企业名称
    @GetMapping("/loadAllCompany")
    public R loadAllCompany(HttpServletRequest request){
    	Map<String,Object> map = new HashMap<>();
    	String strSessionid = sysSessionService.getSessionID(request);
    	map.put("session", strSessionid);
    	List<String> list = ebsBoothService.queryAllCompany(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    }
}