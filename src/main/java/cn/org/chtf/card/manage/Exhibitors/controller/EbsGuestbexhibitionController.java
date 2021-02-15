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
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.model.EbsGuestbexhibition;
import cn.org.chtf.card.manage.Exhibitors.service.EbsCompanyinfoService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsGuestbexhibitionService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsManageService;
import cn.org.chtf.card.manage.member.service.MemberService;
import cn.org.chtf.card.manage.membersession.service.MemberSessionService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.WConst;

/**
 * 参展商管理-嘉宾B-布撤展企业管理Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsGuestbexhibition")
public class EbsGuestbexhibitionController {
	
	@Autowired
	private EbsScatteredExhibitorsManageService ebsScatteredExhibitorsManageService;

    @Autowired
    private EbsGuestbexhibitionService ebsGuestbexhibitionService;
    
    @Autowired
    private EbsCompanyinfoService ebsCompanyinfoService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private MemberSessionService memberSessionService;
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    

    /**
     * 查询参展商管理-嘉宾B-布撤展企业管理页面
     * @return 分页参展商管理-嘉宾B-布撤展企业管理数据
     */
    @GetMapping("/page")
    public PageInfo<EbsGuestbexhibition> page() {
        return ebsGuestbexhibitionService.page(new RequestParamsUtil());
    }    
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){  
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsGuestbexhibition> list = ebsGuestbexhibitionService.list(map);			
			int a = ebsGuestbexhibitionService.listcount(map);
			return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	} 
    

    /**
     * 通过id查询单个参展商管理-嘉宾B-布撤展企业管理
     */
    @GetMapping("/findById")
    public ResultVO findById(@RequestParam(value = "id") Integer id) {
        EbsGuestbexhibition ebsGuestbexhibition =ebsGuestbexhibitionService.findById(id);
        return ResultVOUtil.success(ebsGuestbexhibition);
    }   

    /**
     * 通过map查询单个参展商管理-嘉宾B-布撤展企业管理
     */
    @GetMapping("/findByMap")
    public ResultVO findByMap() {
        EbsGuestbexhibition ebsGuestbexhibition =ebsGuestbexhibitionService.findByMap(new RequestParamsUtil().getParameters());
        return ResultVOUtil.success(ebsGuestbexhibition);
    }

    /**
     * 添加参展商管理-嘉宾B-布撤展企业管理
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody EbsGuestbexhibition ebsGuestbexhibition,HttpServletRequest request) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	ebsGuestbexhibition.setSession(strSessionid);
        ebsGuestbexhibitionService.save(ebsGuestbexhibition);
        return ResultVOUtil.success();
    }

    /**
     * 修改参展商管理-嘉宾B-布撤展企业管理
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody EbsGuestbexhibition ebsGuestbexhibition) {
        ebsGuestbexhibitionService.update(ebsGuestbexhibition);
        return ResultVOUtil.success();
    }

    /**
     * 删除参展商管理-嘉宾B-布撤展企业管理
     */
    @GetMapping("/deleteById")
    public ResultVO deleteById(@RequestParam(value = "ids") String ids, HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	String[] Args = ids.split("\\|");
    	int memberid=Integer.valueOf(Args[0]);
    	int companyid=Integer.valueOf(Args[1]);
    	EbsCompanyinfo eci = ebsCompanyinfoService.findById(companyid);
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("agentid", companyid);
		map.put("memberid", memberid);
		map.put("session", strSessionid);
		List<Map<String,Object>> list = memberSessionService.getNonCurrentSession(map);
		if(list.size()>0){
			memberSessionService.delete(map); 
		}
		else{
			ebsGuestbexhibitionService.deleteById(companyid);
			memberSessionService.delete(map);        				
			memberService.delete(memberid);
		}   
		sysOperationLogService.CreateEntity("删除企业信息", strSessionid, 0, user.getId(), 
				eci.getId(), JSONObject.toJSONString(eci));
        return ResultVOUtil.success();

    }
    
    /**
     * 批量删除参展商管理-嘉宾B-布撤展企业管理
     */
    @GetMapping("/delAll")
    public ResultVO delAll(@RequestParam(value = "isStr") String isStr,HttpServletRequest request){
    	String strSessionid = sysSessionService.getSessionID(request);
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null && !id.equals("")){
    				String[] Args = id.split("\\|");
    				int memberid=Integer.valueOf(Args[0]);
    				int companyid=Integer.valueOf(Args[1]);
    				Map<String,Object> map = new HashMap<String,Object>();
    				map.put("agentid", companyid);
    				map.put("memberid", memberid);
    				map.put("session", strSessionid);
    				List<Map<String,Object>> list = memberSessionService.getNonCurrentSession(map);
    				if(list.size()>0){
    					memberSessionService.delete(map); 
    				}
    				else{
    					ebsGuestbexhibitionService.deleteById(companyid);
        				memberSessionService.delete(map);        				
        				memberService.delete(memberid);
    				}
    				//pimAgentService.deleteTypeByAgentId(Integer.valueOf(id));
    				//pimAgentService.deleteById(Integer.valueOf(id));
    			}
    		}
    	}
    	return ResultVOUtil.success();
    }
    
    /**
     * 重置密码
     * @param map
     * @return
     */
    @RequestMapping("/ResetPassword")
	public ResultVO ResetPassword(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	String pass = CryptographyUtil.md5(WConst.DEFAULT_PASSWORD, String.valueOf(map.get("loginname")));
    	EbsGuestbexhibition ebsGuestbexhibition = new EbsGuestbexhibition();
    	ebsGuestbexhibition.setLoginpass(pass);
    	ebsGuestbexhibition.setMemberid(Integer.valueOf(String.valueOf(map.get("memberid"))));
    	ebsGuestbexhibitionService.ResetPassword(ebsGuestbexhibition);
    	sysOperationLogService.CreateEntity("初始化登录密码", strSessionid, 0, user.getId(), 
				0, JSONObject.toJSONString(map));
        return ResultVOUtil.success();
	} 

    /**统计展位数量和产品数量
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = {"/loadCountGuestB","/loadCountBexhibition"})
    public R loadCount(@RequestParam(value = "type") int type,@RequestParam Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map.put("userId",user.getId());
    		map.put("type",type);
    		Map<String, Object> data = ebsScatteredExhibitorsManageService.loadCount(map);
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", data);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
}