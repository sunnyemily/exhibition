package cn.org.chtf.card.manage.system.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysReservationCentificateService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;
/**
 * 预约取证名额管理Controller
 * @author guo
 *
 */
@RestController
@RequestMapping("/manage/system/sysReservationCentificate")
public class SysReservationCentificateController {
	
	@Autowired
    private SysSessionService sysSessionService;
	
	@Autowired
    private SysReservationCentificateService sysReservationCentificateService;
	
	@Autowired
	private SysOperationLogService sysOperationLogService;
	
	
	/**
	 * 查询所有记录
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	@Transactional(rollbackFor = Exception.class)
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String, Object>> list = sysReservationCentificateService.list(map);
			int count = sysReservationCentificateService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	/**
	 * 根据id删除记录
	 * @param id
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/deleteById")
	public R deleteRById(@RequestParam(value = "id") Integer id,HttpServletRequest request,HttpSession session){
		try {
			User user = (User)session.getAttribute("user");
			String strSessionid = sysSessionService.getSessionID(request);
			int list = sysReservationCentificateService.deleteById(id);
			sysOperationLogService.CreateEntity("删除预约取证名额", strSessionid, 0, user.getId(),id,"");
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.DELETEDERROR);
		}
	} 
	/**
	 * 添加预约取证名额
	 * @param map
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/saveData")
	@Transactional(rollbackFor = Exception.class)
	public R saveData(@RequestParam Map<String,Object> map,HttpServletRequest request,HttpSession session){
		try {
			User user = (User)session.getAttribute("user");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			long reserStartDate = simpleDateFormat.parse(map.get("reserStartDate").toString()).getTime();
			long reserEndDate = simpleDateFormat.parse(map.get("reserEndDate").toString()).getTime();
			int date = sysReservationCentificateService.selectDate(map);
			if (reserStartDate>reserEndDate) {
				return R.error().put("code", WConst.ERROR).put("msg","开始时间早于截止时间");
			} else if (date==0) {
				String strSessionid = sysSessionService.getSessionID(request);
				map.put("session",1);
				int list=sysReservationCentificateService.saveData(map);
				sysOperationLogService.CreateEntity("添加预约取证名额", strSessionid, 0, user.getId(),list,"");
				return R.ok().put("data", map).put("code", WConst.SUCCESS).put("msg",WConst.SAVED);
			}else {
				return R.error().put("code", WConst.ERROR).put("msg","时间段重复，请重新选择");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.error().put("code", WConst.ERROR).put("msg", WConst.SAVEDERROR);
		}
	} 
	
	 /**
     * 通过id查询单个线上预约详细
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
    	Map<String, Object> map = new HashMap();
    	map.put("id", id);
    	Map<String, Object> list =sysReservationCentificateService.findById(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", list);
    }   
    
    /**
     * 修改预约取证详细
     */
    @PostMapping("/updateData")
    public R updateData(@RequestParam(value = "id") Integer id,@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");
    	findById(id);
    	int list =sysReservationCentificateService.updateData(map);
        sysOperationLogService.CreateEntity("更新预约名额", strSessionid, 0, user.getId(),id, "");
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

}
