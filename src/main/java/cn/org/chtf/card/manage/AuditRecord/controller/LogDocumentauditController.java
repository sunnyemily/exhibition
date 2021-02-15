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
import cn.org.chtf.card.manage.AuditRecord.model.LogDocumentaudit;
import cn.org.chtf.card.manage.AuditRecord.service.LogDocumentauditService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.support.util.WConst;

/**
 * 证件审核记录Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/AuditRecord/logDocumentaudit")
public class LogDocumentauditController {

    @Autowired
    private LogDocumentauditService logDocumentauditService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<LogDocumentaudit> list = logDocumentauditService.list(map);			
			int count = logDocumentauditService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个证件审核记录
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<LogDocumentaudit> logDocumentaudit =logDocumentauditService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", logDocumentaudit);
    } 
    

    /**
     * 通过id查询单个证件审核记录
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        LogDocumentaudit logDocumentaudit =logDocumentauditService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", logDocumentaudit);
    }   

    /**
     * 添加证件审核记录
     */
    @PostMapping("/save")
    public R save(@RequestBody LogDocumentaudit logDocumentaudit,HttpServletRequest request) {
    	
        logDocumentauditService.save(logDocumentaudit);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改证件审核记录
     */
    @PostMapping("/update")
    public R update(@RequestBody LogDocumentaudit logDocumentaudit) {
        logDocumentauditService.update(logDocumentaudit);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除证件审核记录
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id) {
        logDocumentauditService.deleteById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除证件审核记录
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr){
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				logDocumentauditService.deleteById(Integer.valueOf(id));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}