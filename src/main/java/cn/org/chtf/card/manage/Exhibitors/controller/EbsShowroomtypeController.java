package cn.org.chtf.card.manage.Exhibitors.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroomtype;
import cn.org.chtf.card.manage.Exhibitors.service.EbsShowroomtypeService;
import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.common.utils.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 参展商管理-展厅类型Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsShowroomtype")
public class EbsShowroomtypeController {

    @Autowired
    private EbsShowroomtypeService ebsShowroomtypeService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;

    /**
     * 查询参展商管理-展厅类型页面
     * @return 分页参展商管理-展厅类型数据
     */
    @GetMapping("/page")
    public PageInfo<EbsShowroomtype> page() {
        return ebsShowroomtypeService.page(new RequestParamsUtil());
    }    
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsShowroomtype> list = ebsShowroomtypeService.list(map);			
			int a = ebsShowroomtypeService.listcount(map);
			return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    

    /**
     * 通过id查询单个参展商管理-展厅类型
     */
    @GetMapping("/findById")
    public ResultVO findById(@RequestParam(value = "id") Integer id) {
        EbsShowroomtype ebsShowroomtype =ebsShowroomtypeService.findById(id);
        return ResultVOUtil.success(ebsShowroomtype);
    }   

    /**
     * 通过map查询单个参展商管理-展厅类型
     */
    @GetMapping("/findByMap")
    public ResultVO findByMap() {
        EbsShowroomtype ebsShowroomtype =ebsShowroomtypeService.findByMap(new RequestParamsUtil().getParameters());
        return ResultVOUtil.success(ebsShowroomtype);
    }

    /**
     * 添加参展商管理-展厅类型
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody EbsShowroomtype ebsShowroomtype, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	ebsShowroomtype.setSession(strSessionid);
        ebsShowroomtypeService.save(ebsShowroomtype);
        sysOperationLogService.CreateEntity("添加展厅类型", strSessionid, 0, user.getId(), 
        		ebsShowroomtype.getId(), JSONObject.toJSONString(ebsShowroomtype));
        return ResultVOUtil.success();
    }

    /**
     * 修改参展商管理-展厅类型
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody EbsShowroomtype ebsShowroomtype, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
        ebsShowroomtypeService.update(ebsShowroomtype);
        sysOperationLogService.CreateEntity("修改展厅类型", strSessionid, 0, user.getId(), 
        		ebsShowroomtype.getId(), JSONObject.toJSONString(ebsShowroomtype));
        return ResultVOUtil.success();
    }

    /**
     * 删除参展商管理-展厅类型
     */
    @GetMapping("/deleteById")
    public ResultVO deleteById(@RequestParam(value = "id") Integer id, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	EbsShowroomtype est = ebsShowroomtypeService.findById(id);
        ebsShowroomtypeService.deleteById(id);
        sysOperationLogService.CreateEntity("删除展厅类型", strSessionid, 0, user.getId(), 
        		est.getId(), JSONObject.toJSONString(est));
        return ResultVOUtil.success();

    }
    
    /**
     * 批量删除参展商管理-展厅类型
     */
    @GetMapping("/delAll")
    public ResultVO delAll(@RequestParam(value = "isStr") String isStr, HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				EbsShowroomtype est = ebsShowroomtypeService.findById(Integer.valueOf(id));
    		        ebsShowroomtypeService.deleteById(Integer.valueOf(id));
    		        sysOperationLogService.CreateEntity("删除展厅类型", strSessionid, 0, user.getId(), 
    		        		est.getId(), JSONObject.toJSONString(est));
    			}
    		}
    	}
    	return ResultVOUtil.success();
    }
    
    @RequestMapping("/getlist")
	public ResultVO getlist(@RequestBody Map<String,Object> map,HttpServletRequest request){
    	String strSessionid = sysSessionService.getSessionID(request);
    	map.put("session", strSessionid);
		List<Map<String,Object>> lit = ebsShowroomtypeService.getList(map);
		return ResultVOUtil.success(lit);
	} 

}