package cn.org.chtf.card.manage.online.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.online.service.ReservationErhaltenZertifikatService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;

/**
 * 预约取证Controller
 * @author guoyichen
 */
@RestController
@RequestMapping("/manage/online/reservationErhaltenZertifikat")
public class ReservationErhaltenZertifikatController {
	
	@Autowired
    private SysSessionService sysSessionService;
	@Autowired
	private SysOperationLogService sysOperationLogService;
	
	@Autowired
	private ReservationErhaltenZertifikatService reservationErhaltenZertifikatService;
	
	
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> map,HttpServletRequest request,HttpSession session){
		try {
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String, Object>> list = reservationErhaltenZertifikatService.list(map);
			int count = reservationErhaltenZertifikatService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}
	
	@RequestMapping("/deleteById")
	public R deleteById(@RequestParam(value = "id") Integer id,HttpServletRequest request,HttpSession session){
		try {
			User user = (User)session.getAttribute("user");
			String strSessionid = sysSessionService.getSessionID(request);
			int list = reservationErhaltenZertifikatService.deleteById(id);	
			sysOperationLogService.CreateEntity("删除预约取证信息操作", strSessionid, 0, user.getId(),id,"");
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}
	
	@RequestMapping("/StatisticsInfo")
	public R StatisticsInfo(@RequestParam Map<String, Object> map,HttpServletRequest request,HttpSession session){
		try {
			Map<String, Object> list = reservationErhaltenZertifikatService.StatisticsInfo(map);
			list.put("total", reservationErhaltenZertifikatService.total(map));
			System.out.println(list);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}
	
}
