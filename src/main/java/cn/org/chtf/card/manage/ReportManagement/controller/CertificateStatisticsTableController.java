package cn.org.chtf.card.manage.ReportManagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.ReportManagement.service.CertificateStatisticsTableService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.support.util.WConst;

/**
 * 
 * 证件统计表Controller
 * @author guo
 */
@RestController
@RequestMapping("/manage/certificateStatisticsTable")
public class CertificateStatisticsTableController {

	@Autowired
	private CertificateStatisticsTableService certificateStatisticsTableService;
	
	@Autowired
	private SysSessionService sysSessionService;
	
	/**
	 * 查询届次
	 * @return
	 */
	@RequestMapping("/getAllSession")
	public R getAllSession() {
		Map<String,Object> map = new HashMap<>();
	    List<Map<String,Object>> list = sysSessionService.getAllSession(map);
	    return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}
	
	/**查询统计信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getStatisticalInfo")
	public R getStatisticalInfo(@RequestParam Map<String,Object> map) {
		Map<String,Object> result = certificateStatisticsTableService.getStatisticalInfo(map);
		return R.ok().put("data", result).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}
}