package cn.org.chtf.card.manage.Exhibitors.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.Exhibitors.service.EbsSendMessageLogService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsSendMessageToExhibitorService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.support.util.WConst;
import lombok.extern.slf4j.Slf4j;

/**
 * 展位申请表Controller
 * @author ggwudivs
 */
@Slf4j
@RestController
@RequestMapping("/manage/Exhibitors/ebsSendMessageToExhibitor")
public class EbsSendMessageToExhibitorController {

    @Autowired
    private EbsSendMessageToExhibitorService ebsSendMessageToExhibitorService;
    
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
			List<Map<String,Object>> list = ebsSendMessageToExhibitorService.list(map);			
			int count = ebsSendMessageToExhibitorService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
}