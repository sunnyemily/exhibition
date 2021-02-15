package cn.org.chtf.card.manage.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.system.model.SysPrintTemplate;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysPrintTemplateService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.WConst;

import com.alibaba.fastjson.JSONObject;

/**
 * 打印模版管理Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/system/sysPrintTemplate")
public class SysPrintTemplateController {

    @Autowired
    private SysPrintTemplateService sysPrintTemplateService;
    
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
			List<SysPrintTemplate> list = sysPrintTemplateService.list(map);			
			int count = sysPrintTemplateService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个打印模版管理
     */
    @RequestMapping("/findByMap")
    public R findByMap(@RequestBody Map<String,Object> map,HttpServletRequest request) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	String url = CryptographyUtil.GeCurrenttUrl(request);
    	map.put("session", strSessionid);
    	String cardTypeName = map.get("cardtypeid").toString();
    	String type = map.get("type").toString();
    	List<SysPrintTemplate> sysPrintTemplate = sysPrintTemplateService.findByMap(map);//,type,cardTypeName);    	
        if(sysPrintTemplate.size()==0){
        	return R.error().put("code", WConst.ERROR).put("msg", "未发现打印模板");
        }
        sysPrintTemplate.get(0).setCardtypename(url);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysPrintTemplate);
    } 
    

    /**
     * 通过id查询单个打印模版管理
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        SysPrintTemplate sysPrintTemplate =sysPrintTemplateService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", sysPrintTemplate);
    }       
    
    /**
     * 添加打印模版管理
     */
    @RequestMapping("/save")
    public R save(@RequestBody Map<String,Object> map,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	String isStr = map.get("ids").toString();
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
			    	SysPrintTemplate sysPrintTemplate = new SysPrintTemplate();
			    	sysPrintTemplate.setSession(strSessionid);
			    	sysPrintTemplate.setCardtypeid(id);
			    	sysPrintTemplate.setPrintTemplate(map.get("printtemplate").toString());
			        sysPrintTemplateService.save(sysPrintTemplate);
			        sysOperationLogService.CreateEntity("添加打印模版", strSessionid, 0, user.getId(), 
							sysPrintTemplate.getId(), JSONObject.toJSONString(sysPrintTemplate));
    			}
    		}       
    	}        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改打印模版管理
     */
    @RequestMapping("/update")
    public R update(@RequestBody Map<String,Object> map,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	SysPrintTemplate sysPrintTemplate = new SysPrintTemplate();    	
    	//sysPrintTemplate.setCardtypeid(map.get("cardtypeid").toString());
    	sysPrintTemplate.setPrintTemplate(map.get("printtemplate").toString());
    	sysPrintTemplate.setId(Integer.valueOf(map.get("id").toString()));
        sysPrintTemplateService.update(sysPrintTemplate);
        
        sysOperationLogService.CreateEntity("更新打印模版", strSessionid, 0, user.getId(), 
				sysPrintTemplate.getId(), JSONObject.toJSONString(sysPrintTemplate));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除打印模版管理
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id,
			HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");		
    	
    	SysPrintTemplate com = sysPrintTemplateService.findById(id);
    	
        sysPrintTemplateService.deleteById(id);
        
        sysOperationLogService.CreateEntity("删除打印膜版", strSessionid, 0, user.getId(), 
				com.getId(), JSONObject.toJSONString(com));
        
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除打印模版管理
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
    			
    				SysPrintTemplate com = sysPrintTemplateService.findById(Integer.valueOf(id));
    				sysPrintTemplateService.deleteById(Integer.valueOf(id));
    				
    				sysOperationLogService.CreateEntity("删除打印膜版", strSessionid, 0, user.getId(), 
					com.getId(), JSONObject.toJSONString(com));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}