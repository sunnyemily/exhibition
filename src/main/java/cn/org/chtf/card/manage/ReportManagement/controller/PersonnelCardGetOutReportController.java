package cn.org.chtf.card.manage.ReportManagement.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.PreviousInformation.dao.PimAgentMapper;
import cn.org.chtf.card.manage.ReportManagement.service.PersonnelCardGetOutReportService;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.member.service.MemberService;
import cn.org.chtf.card.manage.system.model.SysSession;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.support.util.WConst;

/**
 * 报表管理Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/reportManagement/personnelCardGetOutReport")
public class PersonnelCardGetOutReportController {
	
	@Autowired
	private PimAgentMapper pimAgentMapper;
	
	@Autowired
	private MemberService memberService;

    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private PersonnelCardGetOutReportService personnelCardGetOutReportService;
    
    @RequestMapping("/getTradingGroupList")
	public R getTradingGroupCompanyList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			SysSession sysSession = sysSessionService.findById(Integer.valueOf(strSessionid));
			map.put("evidenceDelay", sysSession.getEvidenceDelay());
			List<Map<String,Object>> list = personnelCardGetOutReportService.getTradingGroupList(map);			
			int count = personnelCardGetOutReportService.getTradingGroupListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}
    
    //零散企业
    @RequestMapping("/getScatteredExhibitorsList")
    public R getScatteredExhibitorsList(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map = ResultVOUtil.TiaoZhengFenYe(map);
			SysSession sysSession = sysSessionService.findById(Integer.valueOf(strSessionid));
			map.put("evidenceDelay", sysSession.getEvidenceDelay());
    		List<Map<String,Object>> list = personnelCardGetOutReportService.getScatteredExhibitorsList(map);			
    		int count = personnelCardGetOutReportService.getScatteredExhibitorsListCount(map);
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    //外宾
    @RequestMapping("/getForeignGuestList")
    public R getForeignGuestList(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map = ResultVOUtil.TiaoZhengFenYe(map);
			SysSession sysSession = sysSessionService.findById(Integer.valueOf(strSessionid));
			map.put("evidenceDelay", sysSession.getEvidenceDelay());
    		List<Map<String,Object>> list = personnelCardGetOutReportService.getForeignGuestList(map);			
    		int count = personnelCardGetOutReportService.getForeignGuestListCount(map);
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    //布撤展企业
    @RequestMapping("/getMoveCompanyList")
    public R getMoveCompanyList(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map = ResultVOUtil.TiaoZhengFenYe(map);
			SysSession sysSession = sysSessionService.findById(Integer.valueOf(strSessionid));
			map.put("evidenceDelay", sysSession.getEvidenceDelay());
    		List<Map<String,Object>> list = personnelCardGetOutReportService.getMoveCompanyList(map);			
    		int count = personnelCardGetOutReportService.getMoveCompanyListCount(map);
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    //获取证件类型
    @RequestMapping("/certificateTypes")
    public R certificateTypes(@RequestParam Map<String,Object> map, HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		List<Map<String,Object>> list;
    		Member member = memberService.findById(Integer.valueOf(map.get("memberId").toString()));
    		if(member.getMemberType()==0 || member.getMemberType()==1) {
    			list = personnelCardGetOutReportService.getCertificatTypeByAgentID(map);
    		}else {
    			list = personnelCardGetOutReportService.getCertificatTypeByMemberID(map);
    		}
    		SysSession sysSession = sysSessionService.findById(Integer.valueOf(strSessionid));
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("exhibitionName", sysSession.getRemark()+sysSession.getExhibitionname());
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    //更新证件打印编号
    @RequestMapping("/updateCardPrintNumber")
    public R updateCardPrintNumber(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
			SysSession sysSession = sysSessionService.findById(Integer.valueOf(strSessionid));
			map.put("evidenceDelay", sysSession.getEvidenceDelay());
    		personnelCardGetOutReportService.updateCardPrintNumber(map);
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SUCCESS);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.ERROR);
    	}
    }
    
    //获取人员证件列表
    @RequestMapping("/getPersonnelCardList")
    public R getPersonnelCardList(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		SysSession sysSession = sysSessionService.findById(Integer.valueOf(strSessionid));
			map.put("evidenceDelay", sysSession.getEvidenceDelay());
    		List<Map<String,Object>> list = personnelCardGetOutReportService.getPersonnelCardList(map);
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.ERROR);
    	}
    }
}