package cn.org.chtf.card.manage.system.controller;

import cn.org.chtf.card.manage.system.model.SysReservationSetting;
import cn.org.chtf.card.manage.system.service.SysReservationSettingService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.support.util.WConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.List;

import cn.org.chtf.card.manage.user.pojo.User;

import com.alibaba.fastjson.JSONObject;

/**
 * 线上预约详细Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/system/sysReservationSetting")
public class SysReservationSettingController {

    @Autowired
    private SysReservationSettingService sysReservationSettingService;
    
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
			List<SysReservationSetting> list = sysReservationSettingService.list(map);			
			int count = sysReservationSettingService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个线上预约详细
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<SysReservationSetting> sysReservationSetting =sysReservationSettingService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysReservationSetting);
    } 
    

    /**
     * 通过id查询单个线上预约详细
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        SysReservationSetting sysReservationSetting =sysReservationSettingService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysReservationSetting);
    }   

    /**
     * 添加线上预约详细
     */
    @PostMapping("/save")
    public R save(@RequestBody SysReservationSetting sysReservationSetting,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	sysReservationSetting.setSession(Integer.valueOf(strSessionid));
        sysReservationSettingService.save(sysReservationSetting);
        
        sysOperationLogService.CreateEntity("添加预约详细", strSessionid, 0, user.getId(), 
				sysReservationSetting.getId(), JSONObject.toJSONString(sysReservationSetting));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改线上预约详细
     */
    @PostMapping("/update")
    public R update(@RequestBody SysReservationSetting sysReservationSetting,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
        sysReservationSettingService.update(sysReservationSetting);
        
        sysOperationLogService.CreateEntity("更新预约详细", strSessionid, 0, user.getId(), 
				sysReservationSetting.getId(), JSONObject.toJSONString(sysReservationSetting));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除线上预约详细
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	
    	SysReservationSetting com = sysReservationSettingService.findById(id);
    	
        sysReservationSettingService.deleteById(id);
        
        sysOperationLogService.CreateEntity("删除预约详细", strSessionid, 0, user.getId(), 
				com.getId(), JSONObject.toJSONString(com));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除线上预约详细
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr,
			HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    			
    				SysReservationSetting com = sysReservationSettingService.findById(Integer.valueOf(id));
    				sysReservationSettingService.deleteById(Integer.valueOf(id));
    				
    				sysOperationLogService.CreateEntity("删除预约详细", strSessionid, 0, user.getId(), 
					com.getId(), JSONObject.toJSONString(com));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }   
    
    @RequestMapping("/SearchByDateTime")
	public R SearchByDateTime(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = sysReservationSettingService.SearchByDateTimeList(map);			
			int count = sysReservationSettingService.SearchByDateTimeListCount(map);
			
			SysReservationSetting srs = sysReservationSettingService.findById(Integer.valueOf(map.get("id").toString()));
			int itotal= srs.getTotalvotes();
			
			String strmsg = "展会日期："+srs.getExhibitiondate()+"  可预约票数："+itotal+"，已预约："+count+"，剩余票数："+(itotal-count);
			
			return R.ok().put("remark", strmsg).put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
    

}