package cn.org.chtf.card.manage.Exhibitors.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.Exhibitors.model.EbsBooth;
import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroupboothallocation;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsTradinggroupboothallocationService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;

/**
 * 参展商管理-交易团展位分配Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsTradinggroupboothallocation")
public class EbsTradinggroupboothallocationController {

    @Autowired
    private EbsTradinggroupboothallocationService ebsTradinggroupboothallocationService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private EbsBoothService ebsBoothService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    /**查询展厅已分配展位信息
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/getBoothAllocationInfoList")
    public R getBoothAllocationInfoList(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);			
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = ebsTradinggroupboothallocationService.getBoothAllocationInfoList(map);	
			if(String.valueOf(map.get("zwpj")).equals("yes")){
				String result = StringUtil.GetZhanWeiPinJie(list);
				return R.ok().put("code", WConst.SUCCESS).put("msg", result);
			}
			
			int count = ebsTradinggroupboothallocationService.getBoothAllocationInfoListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
    }   

	/**查询剩余未分配的展位信息
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/getRemainingBoothInfoList")
    public R getRemainingBoothInfoList(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map = ResultVOUtil.TiaoZhengFenYe(map);
    		List<Map<String,Object>> list = ebsTradinggroupboothallocationService.getRemainingBoothInfoList(map);			
    		int count = ebsTradinggroupboothallocationService.getRemainingBoothInfoListCount(map);
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsTradinggroupboothallocation> list = ebsTradinggroupboothallocationService.list(map);			
			int count = ebsTradinggroupboothallocationService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个参展商管理-交易团展位分配
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<EbsTradinggroupboothallocation> ebsTradinggroupboothallocation =ebsTradinggroupboothallocationService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", ebsTradinggroupboothallocation);
    } 
    

    /**
     * 通过id查询单个参展商管理-交易团展位分配
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        EbsTradinggroupboothallocation ebsTradinggroupboothallocation =ebsTradinggroupboothallocationService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", ebsTradinggroupboothallocation);
    }   

    /**
     * 添加参展商管理-交易团展位分配
     */
    @PostMapping("/save")
    public R save(@RequestBody EbsTradinggroupboothallocation ebsTradinggroupboothallocation,HttpServletRequest request) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	ebsTradinggroupboothallocation.setSession(strSessionid);
        ebsTradinggroupboothallocationService.save(ebsTradinggroupboothallocation);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改参展商管理-交易团展位分配
     */
    @PostMapping("/update")
    public R update(@RequestBody EbsTradinggroupboothallocation ebsTradinggroupboothallocation) {
        ebsTradinggroupboothallocationService.update(ebsTradinggroupboothallocation);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除参展商管理-交易团展位分配
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id) {
        ebsTradinggroupboothallocationService.deleteById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除参展商管理-交易团展位分配
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr){
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				ebsTradinggroupboothallocationService.deleteById(Integer.valueOf(id));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

    /**获取该交易团已分配的展位所属的展厅
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/getShowRoomList1")
	public R getShowRoomList1(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			List<Map<String,Object>> list = ebsTradinggroupboothallocationService.getShowRoomList1(map);			
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}
    
    /**获取所有剩余未分配展位所属的展厅
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/getShowRoomList2")
    public R getShowRoomList2(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		List<Map<String,Object>> list = ebsTradinggroupboothallocationService.getShowRoomList2(map);			
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    /**提交分配
     * 保存在ebs_tradinggroupboothallocation表，同时更新ebs_booth表
     * @param ebsTradinggroupboothallocation
     * @return
     */
    @RequestMapping("/submitAllocation")
    @Transactional(rollbackFor = Exception.class)
    public R submitAllocation(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
		map.put("session",strSessionid);
    	String isStr = map.get("isStr").toString();
    	try {
    		if (isStr!=null && !isStr.equals("")) {
        		String[] ids = isStr.split(",");
        		for (String id : ids) {
        			if(id!=null &&!id.equals("")){
        				map.put("boothId", id.substring(0, id.indexOf("|")));
        				map.put("showRoomId", id.substring(id.indexOf("|")+1));
        				ebsTradinggroupboothallocationService.submitAllocation(map);
        				EbsBooth ebsBooth = new EbsBooth();
        				ebsBooth.setId(Integer.valueOf(map.get("boothId").toString()));
        				ebsBooth.setTradinggroupid(Integer.valueOf(map.get("tradingGroupId").toString()));
        				ebsBoothService.update(ebsBooth);
        			}
        		}
        	}
        	sysOperationLogService.CreateEntity("交易团展位分配", strSessionid, 0, user.getId(), 
            		0, JSONObject.toJSONString(map));
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
		}
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }
}