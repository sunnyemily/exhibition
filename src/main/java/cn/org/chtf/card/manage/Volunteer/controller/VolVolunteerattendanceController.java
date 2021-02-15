package cn.org.chtf.card.manage.Volunteer.controller;

import cn.org.chtf.card.manage.Volunteer.model.VolVolunteerattendance;
import cn.org.chtf.card.manage.Volunteer.service.VolVolunteerattendanceService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
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

/**
 * 志愿者考勤Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/Volunteer/volVolunteerattendance")
public class VolVolunteerattendanceController {

    @Autowired
    private VolVolunteerattendanceService volVolunteerattendanceService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<VolVolunteerattendance> list = volVolunteerattendanceService.list(map);			
			int count = volVolunteerattendanceService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个志愿者考勤
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<VolVolunteerattendance> volVolunteerattendance =volVolunteerattendanceService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", volVolunteerattendance);
    } 
    

    /**
     * 通过id查询单个志愿者考勤
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        VolVolunteerattendance volVolunteerattendance =volVolunteerattendanceService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", volVolunteerattendance);
    }   

    /**
     * 添加志愿者考勤
     */
    @PostMapping("/save")
    public R save(@RequestBody VolVolunteerattendance volVolunteerattendance,HttpServletRequest request, HttpSession session) {
    	User user = (User)session.getAttribute("user"); 
    	volVolunteerattendance.setCreateby(user.getId());
    	String strSessionid = sysSessionService.getSessionID(request);
    	volVolunteerattendance.setSession(strSessionid);
        volVolunteerattendanceService.save(volVolunteerattendance);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改志愿者考勤
     */
    @PostMapping("/update")
    public R update(@RequestBody VolVolunteerattendance volVolunteerattendance) {
        volVolunteerattendanceService.update(volVolunteerattendance);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除志愿者考勤
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id) {
        volVolunteerattendanceService.deleteById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除志愿者考勤
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr){
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				volVolunteerattendanceService.deleteById(Integer.valueOf(id));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}