package cn.org.chtf.card.manage.Exhibitors.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.AuditRecord.service.LogDocumentauditService;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsCompanyinfoService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsPersonnelcardService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsBoothAuditService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsManageService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsboothallocationService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.system.service.SysSmsTemplateService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;

/**
 * 参展商管理-零散参展商展位审核Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsScatteredExhibitorsBoothAuditController")
public class EbsScatteredExhibitorsBoothAuditController {
	
	@Autowired
	private SysSmsTemplateService sysSmsTemplateService;
	
	@Autowired
	private EbsCompanyinfoService ebsCompanyinfoService;
	
	@Autowired
	private EbsBoothService ebsBoothService;
	
	@Autowired
	private EbsScatteredExhibitorsManageService ebsScatteredExhibitorsManageService;
	
	@Autowired
	private EbsScatteredExhibitorsboothallocationService ebsScatteredExhibitorsboothallocationService;

    @Autowired
    private EbsScatteredExhibitorsBoothAuditService ebsScatteredExhibitorsBoothAuditService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private EbsPersonnelcardService ebsPersonnelcardService;
    
    @Autowired
    private LogDocumentauditService logDocumentauditService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    
    
    /**查询零散参展商信息
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/getScatteredExhibitorsList")
    public R getScatteredExhibitorsList(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			User user = (User) session.getAttribute("user");
			map.put("userId",user.getId());
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = ebsScatteredExhibitorsBoothAuditService.getScatteredExhibitorsList(map);			
			list = StringUtil.calculateBoothRegion(list);
			int count = ebsScatteredExhibitorsBoothAuditService.getScatteredExhibitorsListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
    }
    
    @RequestMapping("/getPersonnelCardList")
    public R getPersonnelCardList(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map = ResultVOUtil.TiaoZhengFenYe(map);
    		List<Map<String,Object>> list = ebsScatteredExhibitorsBoothAuditService.getPersonnelCardList(map);			
    		int count = ebsScatteredExhibitorsBoothAuditService.getPersonnelCardListCount(map);
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    @RequestMapping("/getAuditInfo")
    public R getAuditInfo(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("sessionId",strSessionid);
    		Map<String,Object> m = ebsScatteredExhibitorsBoothAuditService.getAuditInfo(map);
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED).put("data", m);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
    }
    
    @RequestMapping(value = {"/exhibitionOfficeAudit","/financeOfficeAudit"})
    @Transactional(rollbackFor = Exception.class)
    public R audit(@RequestParam Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("sessionId",strSessionid);
    		String act ="";
    		if("exhibitionOfficeAudit".equals(map.get("type"))) {
    			if("2".equals(map.get("status"))) {//审核未通过时，释放已分配的展位
    				map.put("session",strSessionid);
    				ebsScatteredExhibitorsboothallocationService.cancleAllocationBooth(map);
    				act="零散参展商规划展览部审核未通过";
    			}else if("1".equals(map.get("status"))) {
    				Map<String,Object> map2 = new HashMap<>();
    				map2.put("id",map.get("companyId"));
    				map2.put("buslicensepath",map.get("buslicensepath"));
    				map2.put("relateddocumentspath",map.get("relateddocumentspath"));
					ebsScatteredExhibitorsManageService.updateCompanyInfo(map2);
					//根据企业id查询展位信息
					Map<String,Object> map3 = new HashMap<>();
					map3.put("session",strSessionid);
					map3.put("useable",1);
					map3.put("companyId",map.get("companyId"));
					List<Map<String,Object>> list = ebsBoothService.GetBoothByMap(map3);
					//更新企业表审核状态
	        		EbsCompanyinfo ebsCompanyinfo = new EbsCompanyinfo();
	        		ebsCompanyinfo.setId(Integer.valueOf(map.get("companyId").toString()));
	        		ebsCompanyinfo.setSession(strSessionid);
	        		ebsCompanyinfo.setAuditStatus(2);
	        		ebsCompanyinfo.setAuditRemark("");
	        		ebsCompanyinfoService.update(ebsCompanyinfo);
					//发送展位分配短信
		    		String boothNO = "";
		    		for (Map<String,Object> ebsBooth : list) {
		    			boothNO += ","+ebsBooth.get("name");
		    		}
		    		if(boothNO.length()>0) boothNO = boothNO.substring(1);
		    		EbsCompanyinfo ebsCompanyinfo2 = ebsCompanyinfoService.findById(Integer.valueOf(map.get("companyId").toString()));
		    		if(ebsCompanyinfo2.getPhone()==null || "".equals(ebsCompanyinfo2.getPhone())) return R.error().put("code", WConst.ERROR).put("msg", "该企业手机号为空，请先确认企业信息");
		    		sysSmsTemplateService.sendBoothConfirmSMS(ebsCompanyinfo2.getPhone(), ebsCompanyinfo2.getChinesename(), boothNO, Integer.valueOf(strSessionid), "零散参展商");
		    		act="零散参展商规划展览部审核通过";
    			}
    			map.put("exhibitionOfficeAuditor", user.getId());
    			map.put("exhibitionOfficeAuditStatus", map.get("status"));
    			map.put("exhibitionOfficeAuditRemark", map.get("remark"));
    		}else if("financeOfficeAudit".equals(map.get("type"))) {
    			map.put("financeOfficeAuditor", user.getId());
    			map.put("financeOfficeAuditStatus", map.get("status"));
    			map.put("financeOfficeAuditRemark", map.get("remark"));
    			act="零散参展商财务部审核通过";
    		}
    		ebsScatteredExhibitorsBoothAuditService.audit(map);
    		sysOperationLogService.CreateEntity(act, strSessionid, 0, user.getId(), Integer.valueOf(map.get("companyId").toString()), map.toString());
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    	} catch (Exception e) {
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
    }
    
    /**
     * 撤回
     * @param map
     * @param request
     * @return
     */
    @GetMapping("/returnInfo")
    @Transactional(rollbackFor = Exception.class)
    public R returnInfo(@RequestParam Map<String,Object> map,HttpServletRequest request,HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("companyId", map.get("id"));
    		map.put("sessionId", strSessionid);
    		ebsScatteredExhibitorsBoothAuditService.returnInfo(map);
    		EbsCompanyinfo ebsCompanyinfo = new EbsCompanyinfo();
    		ebsCompanyinfo.setId(Integer.valueOf(map.get("companyId").toString()));
    		ebsCompanyinfo.setSession(strSessionid);
    		ebsCompanyinfo.setAuditStatus(1);
    		ebsCompanyinfo.setAuditRemark("");
    		ebsCompanyinfoService.update(ebsCompanyinfo);
    		sysOperationLogService.CreateEntity("零散参展商展位撤回", strSessionid, 0, user.getId(), 0, JSONObject.toJSONString(map));
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    	} catch (Exception e) {
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    } 
    
    /**统计展位审核数量和产品数量
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/loadCount")
    public R loadCount(@RequestParam Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map.put("userId",user.getId());
    		Map<String,Object> data = ebsScatteredExhibitorsBoothAuditService.loadCount(map);
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", data);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
}