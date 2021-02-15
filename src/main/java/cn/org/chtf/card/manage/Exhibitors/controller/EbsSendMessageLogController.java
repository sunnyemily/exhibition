package cn.org.chtf.card.manage.Exhibitors.controller;

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
import cn.org.chtf.card.manage.Exhibitors.model.EbsSendMessageLog;
import cn.org.chtf.card.manage.Exhibitors.service.EbsSendMessageLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.support.util.WConst;

/**
 * 发送短信日志Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsSendMessageLog")
public class EbsSendMessageLogController {

    @Autowired
    private EbsSendMessageLogService ebsSendMessageLogService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsSendMessageLog> list = ebsSendMessageLogService.list(map);
			int count = ebsSendMessageLogService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
    

    /**
     * 通过id查询单个发送短信日志
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        EbsSendMessageLog ebsSendMessageLog =ebsSendMessageLogService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", ebsSendMessageLog);
    }   

    /**
     * 添加发送短信日志
     */
    @PostMapping("/save")
    public R save(@RequestBody EbsSendMessageLog ebsSendMessageLog,HttpServletRequest request) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	ebsSendMessageLog.setSession(strSessionid);
        ebsSendMessageLogService.save(ebsSendMessageLog);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改发送短信日志
     */
    @PostMapping("/update")
    public R update(@RequestBody EbsSendMessageLog ebsSendMessageLog) {
        ebsSendMessageLogService.update(ebsSendMessageLog);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除发送短信日志
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id) {
        ebsSendMessageLogService.deleteById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除发送短信日志
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr){
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				ebsSendMessageLogService.deleteById(Integer.valueOf(id));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}