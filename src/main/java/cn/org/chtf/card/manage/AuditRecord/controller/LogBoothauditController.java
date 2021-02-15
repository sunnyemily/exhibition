package cn.org.chtf.card.manage.AuditRecord.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.AuditRecord.model.LogBoothaudit;
import cn.org.chtf.card.manage.AuditRecord.service.LogBoothauditService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.support.util.WConst;

/**
 * 展位审核记录Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/AuditRecord/logBoothaudit")
public class LogBoothauditController {

    @Autowired
    private LogBoothauditService logBoothauditService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<LogBoothaudit> list = logBoothauditService.list(map);			
			int count = logBoothauditService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个展位审核记录
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<LogBoothaudit> logBoothaudit =logBoothauditService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", logBoothaudit);
    } 
    

    /**
     * 通过id查询单个展位审核记录
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        LogBoothaudit logBoothaudit =logBoothauditService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", logBoothaudit);
    }   

    /**
     * 添加展位审核记录
     */
    @PostMapping("/save")
    public R save(@RequestBody LogBoothaudit logBoothaudit,HttpServletRequest request) {    	
        logBoothauditService.save(logBoothaudit);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改展位审核记录
     */
    @PostMapping("/update")
    public R update(@RequestBody LogBoothaudit logBoothaudit) {
        logBoothauditService.update(logBoothaudit);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除展位审核记录
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id) {
        logBoothauditService.deleteById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除展位审核记录
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr){
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				logBoothauditService.deleteById(Integer.valueOf(id));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}