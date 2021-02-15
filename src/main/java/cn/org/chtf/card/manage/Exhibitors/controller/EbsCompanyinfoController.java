package cn.org.chtf.card.manage.Exhibitors.controller;

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

import com.alibaba.fastjson.JSONObject;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.service.EbsCompanyinfoService;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.MakeEvidence.service.CmCertificateTypeService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;

/**
 * 企业信息Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsCompanyinfo")
public class EbsCompanyinfoController {

    @Autowired
    private EbsCompanyinfoService ebsCompanyinfoService;
    
    @Autowired
    private CmCertificateTypeService cmCertificateTypeService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			
			if(map.get("GeenPass").equals("ok")){
				//找当前届次证件类型为参展证ID
		    	Map<String,Object> czz = new HashMap<String,Object>();
		    	czz.put("type", 0);
		    	czz.put("useable", 1);
		    	czz.put("isexhibitor", 1);
		    	czz.put("session", strSessionid);
		    	CmCertificateType result = cmCertificateTypeService.findByMap(czz);
		    	map.put("cardtype",result.getId());
			}	
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsCompanyinfo> list = ebsCompanyinfoService.list(map);			
			int count = ebsCompanyinfoService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
    
    @RequestMapping("/PreviousEnterpriseList")
	public R PreviousEnterpriseList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {	
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("newsession", strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsCompanyinfo> list = ebsCompanyinfoService.PreviousEnterpriseList(map);			
			int count = ebsCompanyinfoService.PreviousEnterpriseListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
    
    @RequestMapping("/Greenlist")
	public R Greenlist(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
		try {
			User user = (User)session.getAttribute("user");	
			String strSessionid = sysSessionService.getSessionID(request);		
			map.put("session",strSessionid);
			Map<String,Object> czzx = new HashMap<String,Object>();
			czzx.put("type", 0);
			czzx.put("useable", 1);
			czzx.put("isexhibitor", 1);
			czzx.put("session", strSessionid);
	    	CmCertificateType result = cmCertificateTypeService.findByMap(czzx);
	    	map.put("cardtype",result.getId());
	    	map.put("userId", user.getId());
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsCompanyinfo> list = ebsCompanyinfoService.Greenlist(map);			
			int count = ebsCompanyinfoService.Greenlistcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
    
    
    @RequestMapping("/EnterpriseWithdrawallist")
	public R EnterpriseWithdrawallist(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {			
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String, Object>> list = ebsCompanyinfoService.EnterpriseWithdrawallist(map);			
			int count = ebsCompanyinfoService.EnterpriseWithdrawallistcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
    
	
	/**
     * 通过id查询单个企业信息
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam  Map<String,Object> map) {
        List<EbsCompanyinfo> ebsCompanyinfo =ebsCompanyinfoService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", ebsCompanyinfo);
    } 
    

    /**
     * 通过id查询单个企业信息
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        EbsCompanyinfo ebsCompanyinfo =ebsCompanyinfoService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", ebsCompanyinfo);
    }   
    
    /**
     * 通过id查询单个企业信息
     */
    @GetMapping("/findOneById")
    public R findOneById(@RequestParam(value = "id") Integer id) {
        EbsCompanyinfo ebsCompanyinfo = ebsCompanyinfoService.findOneById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", ebsCompanyinfo);
    }   
    
    /**
     * 通过id查询单个企业信息
     */
    @GetMapping("/findFirstById")
    public R findFirstById(@RequestParam(value = "id") Integer id) {
        EbsCompanyinfo ebsCompanyinfo = ebsCompanyinfoService.findFirstById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", ebsCompanyinfo);
    }   

    /**
     * 添加企业信息
     */
    @PostMapping("/save")
    public R save(@RequestBody EbsCompanyinfo ebsCompanyinfo, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	ebsCompanyinfo.setSession(strSessionid);
        ebsCompanyinfoService.save(ebsCompanyinfo);
        sysOperationLogService.CreateEntity("添加企业信息", strSessionid, 0, user.getId(), 
        		ebsCompanyinfo.getId(), JSONObject.toJSONString(ebsCompanyinfo));
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改企业信息
     */
    @PostMapping("/update")
    public R update(@RequestBody EbsCompanyinfo ebsCompanyinfo, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
        ebsCompanyinfoService.update(ebsCompanyinfo);
        sysOperationLogService.CreateEntity("更新企业信息", strSessionid, 0, user.getId(), 
        		ebsCompanyinfo.getId(), JSONObject.toJSONString(ebsCompanyinfo));
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除企业信息
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	EbsCompanyinfo eci = ebsCompanyinfoService.findById(id);
        ebsCompanyinfoService.deleteById(id);
        sysOperationLogService.CreateEntity("删除企业信息", strSessionid, 0, user.getId(), 
        		eci.getId(), JSONObject.toJSONString(eci));
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除企业信息
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr, HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				EbsCompanyinfo eci = ebsCompanyinfoService.findById(Integer.valueOf(id));
    				ebsCompanyinfoService.deleteById(Integer.valueOf(id));
    				sysOperationLogService.CreateEntity("删除企业信息", strSessionid, 0, user.getId(), 
    		        		eci.getId(), JSONObject.toJSONString(eci));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }
    
    @RequestMapping("/UseCompany")
	public R UseCompany(@RequestBody Map<String,Object> map,HttpServletRequest request) {
    	String strSessionid = sysSessionService.getSessionID(request);
		map.put("Sessionid", strSessionid);
		return ebsCompanyinfoService.UseCompany(map);
		/*if(iRet==1){
			return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
		}
		else{
			return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
		}*/
	}

}