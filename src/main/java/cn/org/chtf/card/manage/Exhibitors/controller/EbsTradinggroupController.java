package cn.org.chtf.card.manage.Exhibitors.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.manage.Exhibitors.model.EbsBooth;
import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroup;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsTradinggroupService;
import cn.org.chtf.card.manage.common.service.CommonService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;

/**
 * 参展商管理-交易团Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsTradinggroup")
public class EbsTradinggroupController {

    @Autowired
    private EbsTradinggroupService ebsTradinggroupService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private EbsBoothService ebsBoothService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    @Autowired
    private CommonService commonService;
    

    /**
     * 查询参展商管理-交易团页面
     * @return 分页参展商管理-交易团数据
     */
    @GetMapping("/page")
    public PageInfo<EbsTradinggroup> page() {
        return ebsTradinggroupService.page(new RequestParamsUtil());
    }    
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsTradinggroup> list = ebsTradinggroupService.list(map);			
			int a = ebsTradinggroupService.listcount(map);
			return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    
    @RequestMapping("/selectByType")
    public R selectByType(@RequestParam Map<String,Object> map, HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		List<Map<String,Object>> list = ebsTradinggroupService.selectByType(map);			
    		return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！");
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", 201).put("msg", "查询失败！");
    	}
    } 
    
    //查看展位分配
    @RequestMapping("/GetBoothByTradingGroupId")
	public R GetBoothByTradingGroupId(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {			
			List<Map<String,Object>> list = ebsTradinggroupService.GetBoothByTradingGroupId(map);		
			return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", list.size());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    
    //历届交易团
    @RequestMapping("/Previouslist")
	public R Previouslist(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			//String strSessionid = sysSessionService.getSessionID(request);
			//map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsTradinggroup> list = ebsTradinggroupService.list(map);			
			int a = ebsTradinggroupService.listcount(map);
			return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    

    /**
     * 通过id查询单个参展商管理-交易团
     */
    @GetMapping("/findById")
    public ResultVO findById(@RequestParam(value = "id") Integer id) {
        EbsTradinggroup ebsTradinggroup =ebsTradinggroupService.findById(id);
        return ResultVOUtil.success(ebsTradinggroup);
    }   

    /**
     * 通过map查询单个参展商管理-交易团
     */
    @GetMapping("/findByMap")
    public ResultVO findByMap() {
        EbsTradinggroup ebsTradinggroup =ebsTradinggroupService.findByMap(new RequestParamsUtil().getParameters());
        return ResultVOUtil.success(ebsTradinggroup);
    }

    /**
     * 添加参展商管理-交易团
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody EbsTradinggroup ebsTradinggroup, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	ebsTradinggroup.setSession(strSessionid);
        ebsTradinggroupService.save(ebsTradinggroup);
        
        //处理全部交易团用户关联信息
        commonService.UpdateUserRights(strSessionid,ebsTradinggroup.getId(),1);
        
        sysOperationLogService.CreateEntity("添加交易团", strSessionid, 0, user.getId(), 
        		ebsTradinggroup.getId(), JSONObject.toJSONString(ebsTradinggroup));
        return ResultVOUtil.success();
    }

    /**
     * 修改参展商管理-交易团
     */
    @PostMapping("/updatex")
    public ResultVO updatex(@RequestBody EbsTradinggroup ebsTradinggroup, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
        ebsTradinggroupService.update(ebsTradinggroup);
        sysOperationLogService.CreateEntity("更新交易团", strSessionid, 0, user.getId(), 
        		ebsTradinggroup.getId(), JSONObject.toJSONString(ebsTradinggroup));
        return ResultVOUtil.success();
    }

    /**
     * 删除参展商管理-交易团
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	Boolean isAllocateBooth = YanZhengAllocateBooth(id.toString(), strSessionid);
    	if(!isAllocateBooth){
    		return R.error().put("code", -100).put("msg", "此交易团已分配了展位，不允许删除");
    	}
    	EbsTradinggroup etg = ebsTradinggroupService.findById(id);
        ebsTradinggroupService.deleteById(id);
        //处理全部交易团用户关联信息
        commonService.UpdateUserRights(strSessionid,Integer.valueOf(id),0);
        sysOperationLogService.CreateEntity("删除交易团", strSessionid, 0, user.getId(), 
        		etg.getId(), JSONObject.toJSONString(etg));
        return R.ok().put("code", 1).put("msg", "删除成功");

    }
    
    //验证是否分配了展位
    private Boolean YanZhengAllocateBooth(String id, String session) {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("session", session);
    	map.put("tradinggroupid", id);
    	List<EbsBooth> list = ebsBoothService.GetBoothListByTradingGrounIdAndSession(map);
    	if(list.size()>0){
    		return false;
    	}
    	return true;
	}

	/**
     * 批量删除参展商管理-交易团
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr, HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	int itotal = 0;
		int ijujue = 0;
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");    		
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				itotal++;
    				Boolean isAllocateBooth = YanZhengAllocateBooth(id.toString(), strSessionid);
    		    	if(!isAllocateBooth){
    		    		ijujue++;
    		    	}
    		    	else{    		
    		    		EbsTradinggroup etg = ebsTradinggroupService.findById(Integer.valueOf(id));
    		    		ebsTradinggroupService.deleteById(Integer.valueOf(id));
    		    		
    		    		//处理全部交易团用户关联信息
    		            commonService.UpdateUserRights(strSessionid,Integer.valueOf(id),0);
    		    		
    		    		sysOperationLogService.CreateEntity("删除交易团", strSessionid, 0, user.getId(), 
    		            		etg.getId(), JSONObject.toJSONString(etg));
    		    	}
    			}
    		}
    	}
    	String  msg = "共选中"+itotal+"项，成功删除"+(itotal-ijujue)+"项";
    	if(ijujue>0){
    		msg+="，有"+ijujue+"项已分配了展位，不允许删除";
    	}
    	return R.ok().put("code", 1).put("msg", msg);
    }
    
    @RequestMapping("/UseTradingGroup")
	public R UseTradingGroup(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
		map.put("strSessionid", strSessionid);
		int iRet = ebsTradinggroupService.UseTradingGroup(map);
		if(iRet==1){
			//处理全部交易团用户关联信息
	        commonService.UpdateUserRights(strSessionid,0,2);
			sysOperationLogService.CreateEntity("提取历届交易团", strSessionid, 0, user.getId(), 
	        		0, JSONObject.toJSONString(map));
			return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
		}
		else{
			return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
		}
	}
    
    @RequestMapping("/UpdateTradinggroupType")
	public R UpdateTradinggroupType(@RequestBody Map<String,Object> map,HttpServletRequest request) {
    	EbsTradinggroup etg = new EbsTradinggroup();
    	etg.setId(Integer.valueOf(String.valueOf(map.get("id"))));
    	etg.setType(String.valueOf(map.get("type")));
    	ebsTradinggroupService.update(etg);
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}

}