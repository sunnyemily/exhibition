package cn.org.chtf.card.manage.system.controller;

import cn.org.chtf.card.manage.system.model.SysSmsTemplate;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSmsTemplateService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.support.util.WConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.List;

/**
 * Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/system/sysSmsTemplate")
public class SysSmsTemplateController {

    @Autowired
    private SysSmsTemplateService sysSmsTemplateService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("smsSessionId",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<SysSmsTemplate> list = sysSmsTemplateService.list(map);			
			int count = sysSmsTemplateService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过sms_id查询单个
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<SysSmsTemplate> sysSmsTemplate =sysSmsTemplateService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysSmsTemplate);
    } 
    

    /**
     * 通过sms_id查询单个
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "sms_id") Integer sms_id) {
        SysSmsTemplate sysSmsTemplate =sysSmsTemplateService.findById(sms_id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysSmsTemplate);
    }   

    /**
     * 添加
     */
    @PostMapping("/save")
    public R save(@RequestBody SysSmsTemplate sysSmsTemplate,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	sysSmsTemplate.setSmsSessionId(Integer.valueOf(strSessionid));
        sysSmsTemplateService.save(sysSmsTemplate);
        
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("添加短信模版", strSessionid, 0, user.getId(), 
				sysSmsTemplate.getSmsId(), JSONObject.toJSONString(sysSmsTemplate));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody SysSmsTemplate sysSmsTemplate,HttpServletRequest request, HttpSession session) {
        sysSmsTemplateService.update(sysSmsTemplate);
        String strSessionid = sysSessionService.getSessionID(request);
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("更新短信模版", strSessionid, 0, user.getId(), 
				sysSmsTemplate.getSmsId(), JSONObject.toJSONString(sysSmsTemplate));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "sms_id") Integer sms_id) {
        sysSmsTemplateService.deleteById(sms_id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr){
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				sysSmsTemplateService.deleteById(Integer.valueOf(id));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}