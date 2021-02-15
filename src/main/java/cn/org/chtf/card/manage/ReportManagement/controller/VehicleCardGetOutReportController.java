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
import cn.org.chtf.card.manage.ReportManagement.service.VehicleCardGetOutReportService;
import cn.org.chtf.card.manage.system.model.SysSession;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.support.util.WConst;

/**
 * 报表管理Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/reportManagement/vehicleCardGetOutReport")
public class VehicleCardGetOutReportController {
	
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private VehicleCardGetOutReportService vehicleCardGetOutReportService;
    
    @RequestMapping("/getAgentList")
	public R getAgentList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			SysSession sysSession = sysSessionService.findById(Integer.valueOf(strSessionid));
			map.put("evidenceDelay", sysSession.getEvidenceDelay());
			List<Map<String,Object>> list = vehicleCardGetOutReportService.getAgentList(map);			
			int count = vehicleCardGetOutReportService.getAgentListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}
    
    @RequestMapping("/getVehicleCardList")
    public R getVehicleCardList(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map = ResultVOUtil.TiaoZhengFenYe(map);
    		SysSession sysSession = sysSessionService.findById(Integer.valueOf(strSessionid));
    		map.put("evidenceDelay", sysSession.getEvidenceDelay());
    		List<Map<String,Object>> list = vehicleCardGetOutReportService.getVehicleCardList(map);			
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
}