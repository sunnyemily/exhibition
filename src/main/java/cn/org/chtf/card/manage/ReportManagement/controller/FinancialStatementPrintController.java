package cn.org.chtf.card.manage.ReportManagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.ReportManagement.service.FinancialStatementPrintService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;

/**
 * 报表管理Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/ReportManagement/FinancialStatementPrintController")
public class FinancialStatementPrintController {

    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private FinancialStatementPrintService financialStatementPrintService;
    
    @RequestMapping("/getScatteredExhibitorsList")
	public R getScatteredExhibitorsList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = financialStatementPrintService.getScatteredExhibitorsList(map);
    		list = StringUtil.calculateBoothRegion(list);//展位转换为区间展示
			int count = financialStatementPrintService.getScatteredExhibitorsListCount(map);
			Map<String,Object> totalRow = new HashMap<>();
			int boothsNumber = 0;
			int certificatesNumber = 0;
			for (Map<String, Object> map2 : list) {
				boothsNumber = boothsNumber+Integer.valueOf(map2.get("boothsNumber").toString());
				certificatesNumber = certificatesNumber+Integer.valueOf(map2.get("certificatesNumber").toString());
			}
			totalRow.put("chinesename","汇总");
			totalRow.put("booths","企业数--"+count);
			totalRow.put("boothsNumber",boothsNumber);
			totalRow.put("certificatesNumber",certificatesNumber);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count).put("totalRow", totalRow);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}
    
    @RequestMapping("/getTradingGroupList")
    public R getTradingGroupList(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map = ResultVOUtil.TiaoZhengFenYe(map);
    		List<Map<String,Object>> list = financialStatementPrintService.getTradingGroupList(map);
    		list = StringUtil.calculateBoothRegion(list);//展位转换为区间展示
    		int count = financialStatementPrintService.getTradingGroupListCount(map);
    		Map<String,Object> totalRow = new HashMap<>();
			int certificatesNumber = 0;
			for (Map<String, Object> map2 : list) {
				certificatesNumber = certificatesNumber+Integer.valueOf(map2.get("certificatesNumber").toString());
			}
			totalRow.put("name","证件总数--"+certificatesNumber);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count).put("totalRow", totalRow);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    @RequestMapping("/getCarCertificatesList")
    public R getCarCertificatesList(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map = ResultVOUtil.TiaoZhengFenYe(map);
    		List<Map<String,Object>> list = financialStatementPrintService.getCarCertificatesList(map);			
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
}