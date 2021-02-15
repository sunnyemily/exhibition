package cn.org.chtf.card.manage.system.controller;

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
import cn.org.chtf.card.manage.system.service.SysReservationFrequencyService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;

/**
 * 预约取证次数限制管理Controller
 * @author guo
 *
 */
@RestController
@RequestMapping("/manage/system/sysReservationFrequency")
public class SysReservationFrequencyController {
	
	@Autowired
    private SysSessionService sysSessionService;
	
	@Autowired
	private SysReservationFrequencyService sysReservationFrequencyService;
	
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
			List<Map<String, Object>> list = sysReservationFrequencyService.list(map);
			int count = sysReservationFrequencyService.listcount(map);
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
			int list = sysReservationFrequencyService.deleteById(id);
			sysOperationLogService.CreateEntity("删除预约取证次数操作", strSessionid, 0, user.getId(),id,"");
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
			int time = sysReservationFrequencyService.selectTime(map);
			if (time==0) {
				String strSessionid = sysSessionService.getSessionID(request);
				map.put("session",1);
				int list=sysReservationFrequencyService.saveData(map);
				sysOperationLogService.CreateEntity("添加预约取证次数操作", strSessionid, 0, user.getId(),list,"");
				return R.ok().put("data", map).put("code", WConst.SUCCESS).put("msg",WConst.SAVED);
			} else {
				return R.error().put("code", WConst.ERROR).put("msg", "添加失败，已存在该时间段");
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
    	Map<String, Object> list =sysReservationFrequencyService.findById(map);
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
    	int list =sysReservationFrequencyService.updateData(map);
        sysOperationLogService.CreateEntity("更新预约次数", strSessionid, 0, user.getId(),id, "");
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }
}
