package cn.org.chtf.card.manage.Exhibitors.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsScatteredExhibitorsBoothAgainAuditMapper;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothAuditService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsPersonnelcardService;
import cn.org.chtf.card.manage.product.service.WebProductService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;

/**
 * 参展商管理-零散参展商展位分配Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsScatteredExhibitorsBoothAgainAuditController")
public class EbsScatteredExhibitorsBoothAgainAuditController {
	
	@Autowired
	private SysSessionService sysSessionService;
	
	@Autowired
	private EbsPersonnelcardService ebsPersonnelcardService;
	
	@Autowired
	private EbsBoothService ebsBoothService;
	
	@Autowired
	private EbsBoothAuditService ebsBoothAuditService;
	
	@Autowired
	private SysOperationLogService sysOperationLogService;
	
    @Autowired
    private EbsScatteredExhibitorsBoothAgainAuditMapper ebsScatteredExhibitorsBoothAgainAuditMapper;
    
    @Autowired
    private WebProductService webProductService;
    
    /**查询零散参展商信息
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			User user = (User) session.getAttribute("user");
			map.put("userId",user.getId());
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = ebsScatteredExhibitorsBoothAgainAuditMapper.list(map);			
			list = StringUtil.calculateBoothRegion(list);
			int count = ebsScatteredExhibitorsBoothAgainAuditMapper.listCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
    }
    
    /**申请展位重审
     * @param map
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/applyAgainAudit")
    @Transactional(rollbackFor = Exception.class)
    public R applyAgainAudit(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		ebsScatteredExhibitorsBoothAgainAuditMapper.insertAuditInfo(map);
    		sysOperationLogService.CreateEntity("零散参展商——展区负责人申请展位重审", strSessionid, 0, user.getId(), Integer.valueOf(map.get("companyId").toString()), JSONObject.toJSONString(map.toString()));
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
    }
    
    /**财务负责人和制证中心负责人重审确认
     * @param map
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/financialOfficeAgainAudit","/makeEvidenceCenterAgainAudit"})
    @Transactional(rollbackFor = Exception.class)
    public R againAuditConfirm(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		ebsScatteredExhibitorsBoothAgainAuditMapper.updateAuditInfo(map);
    		sysOperationLogService.CreateEntity("零散参展商——"+map.get("act").toString(), strSessionid, 0, user.getId(), Integer.valueOf(map.get("companyId").toString()), JSONObject.toJSONString(map.toString()));
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
    }
    
    /**展区负责人重审展位
     * @param map
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/againAudit")
    @Transactional(rollbackFor = Exception.class)
    public R againAudit(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		webProductService.resetStatusByCompanyId(map);//清空产品审核信息
    		ebsPersonnelcardService.resetStatusByCompanyId(map);//清空参展证审核信息
    		ebsBoothService.releaseCompanyBooth(Integer.valueOf(map.get("companyId").toString()), Integer.valueOf(strSessionid));//清空展位分配信息
    		ebsBoothAuditService.deleteByCompany(Integer.valueOf(map.get("companyId").toString()), Integer.valueOf(strSessionid));//清空展位审核信息
    		sysOperationLogService.CreateEntity("零散参展商——展区负责人确认重审", strSessionid, 0, user.getId(), Integer.valueOf(map.get("companyId").toString()), JSONObject.toJSONString(map.toString()));
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
    }
}