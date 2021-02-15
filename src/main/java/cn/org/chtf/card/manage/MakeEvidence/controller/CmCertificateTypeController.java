package cn.org.chtf.card.manage.MakeEvidence.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.MakeEvidence.service.CmCertificateTypeService;
import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.common.utils.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

/**
 * 证件类型管理Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/MakeEvidence/cmCertificateType")
public class CmCertificateTypeController {

    @Autowired
    private CmCertificateTypeService cmCertificateTypeService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    

    /**
     * 查询证件类型管理页面
     * @return 分页证件类型管理数据
     */
    @GetMapping("/page")
    public PageInfo<CmCertificateType> page() {
        return cmCertificateTypeService.page(new RequestParamsUtil());
    }    
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<CmCertificateType> list = cmCertificateTypeService.list(map);			
			int a = cmCertificateTypeService.listcount(map);
			return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    
    //历届证件类型
    @RequestMapping("/Previouslist")
	public R Previouslist(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			//String strSessionid = sysSessionService.getSessionID(request);
			//map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<CmCertificateType> list = cmCertificateTypeService.list(map);			
			int a = cmCertificateTypeService.listcount(map);
			return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    

    /**
     * 通过id查询单个证件类型管理
     */
    @GetMapping("/findById")
    public ResultVO findById(@RequestParam(value = "id") Integer id) {
        CmCertificateType cmCertificateType =cmCertificateTypeService.findById(id);
        return ResultVOUtil.success(cmCertificateType);
    }   

    /**
     * 通过map查询单个证件类型管理
     */
    @GetMapping("/findByMap")
    public ResultVO findByMap() {
        CmCertificateType cmCertificateType =cmCertificateTypeService.findByMap(new RequestParamsUtil().getParameters());
        return ResultVOUtil.success(cmCertificateType);
    }

    /**
     * 添加证件类型管理
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody CmCertificateType cmCertificateType,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	cmCertificateType.setSession(strSessionid);
        cmCertificateTypeService.save(cmCertificateType);
        	
		sysOperationLogService.CreateEntity("添加证件类型", strSessionid, 0, user.getId(), 
				cmCertificateType.getId(), JSONObject.toJSONString(cmCertificateType));
        return ResultVOUtil.success();
    }

    /**
     * 修改证件类型管理
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody CmCertificateType cmCertificateType,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
        cmCertificateTypeService.update(cmCertificateType);
        sysOperationLogService.CreateEntity("更新证件类型", strSessionid, 0, user.getId(), 
				cmCertificateType.getId(), JSONObject.toJSONString(cmCertificateType));
        return ResultVOUtil.success();
    }

    /**
     * 删除证件类型管理
     */
    @GetMapping("/deleteById")
    public ResultVO deleteById(@RequestParam(value = "id") Integer id,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	CmCertificateType cct = cmCertificateTypeService.findById(id);
        cmCertificateTypeService.deleteById(id);
        sysOperationLogService.CreateEntity("删除证件类型", strSessionid, 0, user.getId(), 
				cct.getId(), JSONObject.toJSONString(cct));
        return ResultVOUtil.success();
    }
    
    /**
     * 批量删除证件类型管理
     */
    @GetMapping("/delAll")
    public ResultVO delAll(@RequestParam(value = "isStr") String isStr,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				CmCertificateType cct = cmCertificateTypeService.findById(Integer.valueOf(id));
    				cmCertificateTypeService.deleteById(Integer.valueOf(id));
    				sysOperationLogService.CreateEntity("删除证件类型", strSessionid, 0, user.getId(), 
    						cct.getId(), JSONObject.toJSONString(cct));
    			}
    		}
    	}
    	return ResultVOUtil.success();
    }
    
    @RequestMapping("/getlist")
  	public ResultVO getlist(@RequestBody Map<String,Object> map,HttpServletRequest request){
    	String strSessionid = sysSessionService.getSessionID(request);
    	map.put("session", strSessionid);
  		List<Map<String,Object>> lit = cmCertificateTypeService.getList(map);
  		return ResultVOUtil.success(lit);
  	} 
    
    @RequestMapping("/UseCardType")
	public R UseCardType(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
		map.put("strSessionid", strSessionid);
		cmCertificateTypeService.UseCardType(map);
		sysOperationLogService.CreateEntity("提取历届证件类型", strSessionid, 0, user.getId(), 
				0, JSONObject.toJSONString(map));
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}
    
    @GetMapping("/DailyIDSummaryTableList")
	public R DailyIDSummaryTableList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);			
			List<Map<String,Object>> list = cmCertificateTypeService.DailyIDSummaryTableList(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", "查询成功！");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    
    @GetMapping("/DailyIDSummaryTableListRiQi")
	public R DailyIDSummaryTableListRiQi(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);			
			List<Map<String,Object>> list = cmCertificateTypeService.DailyIDSummaryTableListRiQi(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", "查询成功！");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    
    @GetMapping("/DailyIDSummaryTableListDataByRiQi")
	public R DailyIDSummaryTableListDataByRiQi(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);			
			List<Map<String,Object>> list = cmCertificateTypeService.DailyIDSummaryTableListDataByRiQi(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", "查询成功！");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    
    @GetMapping("/DailyIDSummaryTableListHeJi")
	public R DailyIDSummaryTableListHeJi(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);			
			List<Map<String,Object>> list = cmCertificateTypeService.DailyIDSummaryTableListHeJi(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", "查询成功！");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    
    @GetMapping("/CertificateSummaryFormList")
	public R CertificateSummaryFormList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);			
			List<Map<String,Object>> list = cmCertificateTypeService.CertificateSummaryFormList(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", "查询成功！");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    
    @GetMapping("/CertificateSummaryFormDaiBanYuanZhengJianList")
	public R CertificateSummaryFormDaiBanYuanZhengJianList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);			
			List<Map<String,Object>> list = cmCertificateTypeService.CertificateSummaryFormDaiBanYuanZhengJianList(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", "查询成功！");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	}

}