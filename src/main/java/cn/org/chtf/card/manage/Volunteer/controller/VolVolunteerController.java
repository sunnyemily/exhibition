package cn.org.chtf.card.manage.Volunteer.controller;

import cn.org.chtf.card.manage.Volunteer.model.VolVolunteer;
import cn.org.chtf.card.manage.Volunteer.service.VolVolunteerService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.support.util.WConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.List;

/**
 * 志愿者信息Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/Volunteer/volVolunteer")
public class VolVolunteerController {

    @Autowired
    private VolVolunteerService volVolunteerService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<VolVolunteer> list = volVolunteerService.list(map);			
			int count = volVolunteerService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个志愿者信息
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<VolVolunteer> volVolunteer =volVolunteerService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", volVolunteer);
    } 
    

    /**
     * 通过id查询单个志愿者信息
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        VolVolunteer volVolunteer =volVolunteerService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", volVolunteer);
    }   

    /**
     * 添加志愿者信息
     */
    @PostMapping("/save")
    public R save(@RequestBody VolVolunteer volVolunteer,HttpServletRequest request) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	volVolunteer.setSession(strSessionid);
        volVolunteerService.save(volVolunteer);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改志愿者信息
     */
    @PostMapping("/update")
    public R update(@RequestBody VolVolunteer volVolunteer) {
        volVolunteerService.update(volVolunteer);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除志愿者信息
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id) {
        volVolunteerService.deleteById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除志愿者信息
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr){
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				volVolunteerService.deleteById(Integer.valueOf(id));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}