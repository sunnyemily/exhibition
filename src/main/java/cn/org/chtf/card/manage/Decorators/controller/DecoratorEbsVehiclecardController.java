package cn.org.chtf.card.manage.Decorators.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.org.chtf.card.common.constant.RequestConstant;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.manage.AuditRecord.model.LogDocumentaudit;
import cn.org.chtf.card.manage.AuditRecord.service.LogDocumentauditService;
import cn.org.chtf.card.manage.Decorators.service.DecoratorEbsVehiclecardService;
import cn.org.chtf.card.manage.Exhibitors.model.EbsVehiclecard;
import cn.org.chtf.card.manage.common.service.CommonService;
import cn.org.chtf.card.manage.system.model.SysPrintTemplate;
import cn.org.chtf.card.manage.system.model.SysSession;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysPrintTemplateService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.support.util.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 搭建商管理-车辆证件审核Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/Decorators/ebsVehiclecard")
@Slf4j
public class DecoratorEbsVehiclecardController {

    @Autowired
    private DecoratorEbsVehiclecardService decoratorEbsVehiclecardService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private LogDocumentauditService logDocumentauditService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    @Autowired
    private SysPrintTemplateService sysPrintTemplateService;
    
    @Autowired
    private CommonService commonService;

    @Resource
	private HttpUtil httpUtil;

    /**
     * 查询搭建商管理-车辆证件审核页面
     * @return 分页搭建商管理-车辆证件审核数据
     */
    @GetMapping("/page")
    public PageInfo<EbsVehiclecard> page() {
        return decoratorEbsVehiclecardService.page(new RequestParamsUtil());
    }    
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map.put("jyt", "yes");
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsVehiclecard> list = decoratorEbsVehiclecardService.list(map);
			int a = decoratorEbsVehiclecardService.listcount(map);
			// 获取搭建商办证截止时间，判断是否已经超出范围
			boolean auditFlag = getAuditFlag(request);
			if (CollectionUtil.isNotEmpty(list)) {
				final String auditFlagStr = auditFlag + "";
				list.stream().forEach(item -> item.setAuditFlag(auditFlagStr));
			}
			return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	}

	/**
	 * 获取搭建商办证截止时间，判断是否已经超出范围
	 * @param request
	 * @return
	 */
	private boolean getAuditFlag(HttpServletRequest request) {
		// 获取搭建商办证截止时间，判断是否已经超出范围
		boolean auditFlag = true;
		try {
			String currentUrl = CryptographyUtil.GeCurrenttUrl(request);
			String url = RequestConstant.getUrl(currentUrl, RequestConstant.APPLY_CERTIFICATES_END_DATE_TYPE);
			String response = httpUtil.doGet(url);
			log.info("获取搭建商办证截止时间，请求地址:{}，返回结果:{}", url, response);
			JSONObject jsonObject = JSON.parseObject(response);
			if (jsonObject != null) {
				Object code = jsonObject.get("code");
				Object endate = jsonObject.get("endate");
				if (code != null && StrUtil.isNotEmpty(code.toString())
						&& StrUtil.equals("200", code.toString())
						&& endate != null && StrUtil.isNotEmpty(endate.toString())) {
					// 比较当前系统时间和搭建商办证截止时间
					String nowDate = DateUtil.today();
					int result = nowDate.compareTo(endate.toString());
					if (result > 0) {
						auditFlag = false;
					}
				}
			}
		} catch (Exception ex) {
			log.info("获取搭建商办证截止时间异常", ex.getMessage());
		}
		return auditFlag;
	}
    
    @RequestMapping("/Previouslist")
	public R Previouslist(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			map.put("jyt", "yes");
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsVehiclecard> list = decoratorEbsVehiclecardService.list(map);
			int a = decoratorEbsVehiclecardService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", "查询成功！").put("count", a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	}
    
    
    @RequestMapping("/VehicleCertificateProductionlist")
	public R VehicleCertificateProductionlist(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			if(map.get("caidan")==null || map.get("caidan").toString().equals("")){
				List<EbsVehiclecard> list = decoratorEbsVehiclecardService.list(map);
				int a = decoratorEbsVehiclecardService.listcount(map);
				return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
			}
			else{
				List<Map<String,Object>> list = decoratorEbsVehiclecardService.VehicleIDMarkList(map);
				int a = decoratorEbsVehiclecardService.VehicleIDMarkListCount(map);
				return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	}
    

    /**
     * 通过id查询单个搭建商管理-车辆证件审核
     */
    @GetMapping("/findById")
    public ResultVO findById(@RequestParam(value = "id") Integer id) {
        EbsVehiclecard ebsVehiclecard = decoratorEbsVehiclecardService.findById(id);
        return ResultVOUtil.success(ebsVehiclecard);
    }  
    
    @GetMapping("/PrintfindById")
    public ResultVO PrintfindById(@RequestParam(value = "id") Integer id) {
        EbsVehiclecard ebsVehiclecard = decoratorEbsVehiclecardService.findById(id);
        return ResultVOUtil.success(ebsVehiclecard);
    }  

    /**
     * 通过map查询单个搭建商管理-车辆证件审核
     */
    @GetMapping("/findByMap")
    public ResultVO findByMap() {
        List<EbsVehiclecard> ebsVehiclecard = decoratorEbsVehiclecardService.findByMap(new RequestParamsUtil().getParameters());
        return ResultVOUtil.success(ebsVehiclecard);
    }

    /**
     * 添加搭建商管理-车辆证件审核
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody EbsVehiclecard ebsVehiclecard,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	ebsVehiclecard.setSession(strSessionid);
        decoratorEbsVehiclecardService.save(ebsVehiclecard);
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("添加车辆证件", strSessionid, 0, user.getId(), 
				ebsVehiclecard.getId(), JSONObject.toJSONString(ebsVehiclecard));
        return ResultVOUtil.success();
    }

    /**
     * 修改搭建商管理-车辆证件审核
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody EbsVehiclecard ebsVehiclecard,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
        decoratorEbsVehiclecardService.update(ebsVehiclecard);
        
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("更新车辆证件", strSessionid, 0, user.getId(), 
				ebsVehiclecard.getId(), JSONObject.toJSONString(ebsVehiclecard));
        return ResultVOUtil.success();
    }

    /**
     * 删除搭建商管理-车辆证件审核
     */
    @GetMapping("/deleteById")
    public ResultVO deleteById(@RequestParam(value = "id") Integer id,HttpServletRequest request, HttpSession session) {
    	EbsVehiclecard evc = decoratorEbsVehiclecardService.findById(id);
        decoratorEbsVehiclecardService.deleteById(id);
        
        //记录操作日志
        User user = (User)session.getAttribute("user");		
		sysOperationLogService.CreateEntity("删除车辆证件", evc.getSession(), 0, user.getId(), 
				id, JSONObject.toJSONString(evc));
        
        return ResultVOUtil.success();
    }
    
    /**
     * 批量删除搭建商管理-车辆证件审核
     */
    @GetMapping("/delAll")
    public ResultVO delAll(@RequestParam(value = "isStr") String isStr,HttpServletRequest request, HttpSession session){
    	User user = (User)session.getAttribute("user");		
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				EbsVehiclecard evc = decoratorEbsVehiclecardService.findById(Integer.valueOf(id));
    				decoratorEbsVehiclecardService.deleteById(Integer.valueOf(id));
    				
    				//记录操作日志    		        
    				sysOperationLogService.CreateEntity("批量删除车辆证件", evc.getSession(), 0, user.getId(), 
    						evc.getId(), JSONObject.toJSONString(evc));
    			}
    		}
    	}
    	return ResultVOUtil.success();
    }
    
    /**
     * 批量审核通过-车辆证件审核
     */
    @GetMapping("/PassAll")
    public ResultVO PassAll(@RequestParam(value = "isStr") String isStr, HttpSession session, HttpServletRequest request){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			EbsVehiclecard ebsVehiclecard = new EbsVehiclecard();
    			if(id!=null &&!id.equals("")){
    				ebsVehiclecard.setId(Integer.valueOf(id));
    				ebsVehiclecard.setStatus(1);
    				decoratorEbsVehiclecardService.update(ebsVehiclecard);
    				
    				LogDocumentaudit log = new LogDocumentaudit();
    		    	log.setAct(1);
    		    	log.setReviewer(user.getId());
    		    	log.setStatus(1);
    		    	log.setRemark("");
    		    	log.setDocumentid(Integer.valueOf(id));
    		    	logDocumentauditService.save(log);       		    	
    			}
    		}
    		//记录操作日志    		        
			sysOperationLogService.CreateEntity("批量审核车辆证件", strSessionid, 0, user.getId(), 
					0, isStr);
    	}
    	return ResultVOUtil.success();
    }   
    
    @RequestMapping(value={"/UpdatePrintStatus","/UpdatePrintStatusBack","/UpdatePrintStatusBack_WtoY","/UpdatePrintStatusBack_ZtoW","/UpdatePrintStatusBack_ZtoY"})
	public R UpdatePrintStatus(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");
    	EbsVehiclecard ebsVehiclecard = new EbsVehiclecard();
    	ebsVehiclecard.setId(Integer.valueOf(String.valueOf(map.get("id"))));
    	ebsVehiclecard.setPrintstatus(Integer.valueOf(String.valueOf(map.get("printstatus"))));
    	if(map.get("makecardtime")!=null && !String.valueOf(map.get("makecardtime")).equals("")){
    		ebsVehiclecard.setMakecardtime(String.valueOf(map.get("makecardtime")));
    	}
    	if(map.get("printtime")!=null && !String.valueOf(map.get("printtime")).equals("")){
    		ebsVehiclecard.setPrinttime(String.valueOf(map.get("printtime")));
    	}
    	if(map.get("printtype")!=null && !String.valueOf(map.get("printtype")).equals("")){
    		ebsVehiclecard.setPrinttype(Integer.valueOf(String.valueOf(map.get("printtype"))));
    	}
    	if(ebsVehiclecard.getPrintstatus()==2){    		
    		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
    		String time = formater1.format(new Date());
    		ebsVehiclecard.setMakecardtime(time);
    		ebsVehiclecard.setPrinttime(time);		
    		ebsVehiclecard.setPrinttype(0);    		
    	}
    	decoratorEbsVehiclecardService.update(ebsVehiclecard);
    	
    	
    	sysOperationLogService.CreateEntity("更新车辆证件打印状态", strSessionid, 0, user.getId(), 
    			ebsVehiclecard.getId(), JSONObject.toJSONString(map));
    	
		return R.ok().put("code", 200).put("msg", "成功");		
	} 
    
    @RequestMapping(value={"/VehiclecardForensicMarkUpdate","/VehiclecardForensicMarkUpdateBack"})
	public R VehiclecardForensicMarkUpdate(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");
    	EbsVehiclecard ebsVehiclecard = new EbsVehiclecard();
    	ebsVehiclecard.setId(Integer.valueOf(String.valueOf(map.get("id"))));
    	ebsVehiclecard.setIsforensics(Integer.valueOf(String.valueOf(map.get("isforensics"))));
    	ebsVehiclecard.setForensicstime(map.get("forensicstime").toString());
    	decoratorEbsVehiclecardService.update(ebsVehiclecard);
    	
    	if(map.get("phone")!=null && !"".equals(map.get("phone"))){
			Map<String,Object> para = new HashMap<String,Object>();
			para.put("primaryid", ebsVehiclecard.getId());
			para.put("phone", map.get("phone"));
			para.put("createby", user.getId());
			para.put("act", "person");
			commonService.addMarkLog(para);
		}
    	
    	sysOperationLogService.CreateEntity("更新车辆证件取证状态", strSessionid, 0, user.getId(), 
    			ebsVehiclecard.getId(), JSONObject.toJSONString(map));
    	
		return R.ok().put("code", 200).put("msg", "成功");		
	} 
    
    /**修改车辆证件审核状态
     * @param map
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/UpdateAuditStatus","/againAudit"})
    @Transactional(rollbackFor = Exception.class)
	public R UpdateAuditStatus(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
    		User user = (User)session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		EbsVehiclecard per = new EbsVehiclecard();
    		per.setId(Integer.valueOf(String.valueOf(map.get("id"))));
    		if ("-1".equals(map.get("status"))) {//审核通过时
    			per.setReviewremark((String) map.get("remark"));
    		} else {
    			per.setReviewremark("");
    		}
    		per.setStatus(Integer.valueOf(String.valueOf(map.get("status"))));
    		per.setAudittime(new java.sql.Timestamp(System.currentTimeMillis()));
    		
    		LogDocumentaudit log = new LogDocumentaudit();
    		log.setAct(Integer.valueOf(String.valueOf(map.get("act"))));
    		log.setReviewer(user.getId());
    		log.setStatus(Integer.valueOf(String.valueOf(map.get("status"))));
    		log.setRemark(String.valueOf(map.get("remark")));
    		log.setDocumentid(Integer.valueOf(String.valueOf(map.get("id")))); 
    		
    		decoratorEbsVehiclecardService.update(per);
    		
    		logDocumentauditService.save(log);
    		
    		sysOperationLogService.CreateEntity("车辆证件修改审核状态", strSessionid, 0, user.getId(), per.getId(), JSONObject.toJSONString(map));
    		
    		return R.ok().put("code", 200).put("msg", "成功");
    	} catch (Exception e) {
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
	}
    
    @RequestMapping("/updatePrintNumberByMap")
	public R updatePrintNumberByMap(@RequestParam Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	try {
    		User user = (User)session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session", strSessionid);
			SysSession sysSession = sysSessionService.findById(Integer.valueOf(strSessionid));
			map.put("evidenceDelay", sysSession.getEvidenceDelay());
    		decoratorEbsVehiclecardService.updatePrintNumberByMap(map);
    		
    		sysOperationLogService.CreateEntity("更新车辆证件打印批号", strSessionid, 0, user.getId(), 
        			0, JSONObject.toJSONString(map));
    		
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    	} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
		}
    }
    
    @GetMapping("/PrintAllcar")
	public R PrintAllcar(@RequestParam(value = "isStr") String isStr,@RequestParam(value = "leixing") String leixing, 
			HttpSession session,HttpServletRequest request) throws Exception {
		String strSessionid = sysSessionService.getSessionID(request);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (isStr != null && !isStr.equals("")) {
			String[] ids = isStr.split(",");
			int count=0;
			String title="";
			for (String strid : ids) {
				if (strid != null && !strid.equals("")) {
					//String[] strArgs = strid.split("\\|");
					String id = strid;//strArgs[0];
					//String cardTypeName = strArgs[1];
					
						EbsVehiclecard epc = decoratorEbsVehiclecardService.findById(Integer.valueOf(id));
						Map<String,Object> par = new HashMap<String,Object>();
						par.put("session", strSessionid);
						par.put("cardtypeid", epc.getCardtype());
						List<SysPrintTemplate> sysPrintTemplate = sysPrintTemplateService.findByMap(par);					
						if(sysPrintTemplate!=null){
							String temp = sysPrintTemplate.get(0).getPrintTemplate();							
							if(count==0){
								title=temp.split(";")[0]+";";
							}
							if (count > 0) {
								//temp="LODOP.NewPage();"+temp;
								temp = temp.replace(title, "");
							}
							temp = temp.replace("${CHEPAIHAO}", epc.getPlatenumber());
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("temp", temp);
							list.add(map);
							count++;							
						}						
				}
			}
			User user = (User)session.getAttribute("user");		
			sysOperationLogService.CreateEntity("批量打印车辆证件", strSessionid, 0, user.getId(), 
					0, JSONObject.toJSONString(isStr));
		}
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", list);
	}
    
    /**统计展位数量和产品数量
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/loadCount")
    public R loadCount(@RequestParam Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map.put("userId",user.getId());
    		Map<String,Object> data = decoratorEbsVehiclecardService.loadCount(map);
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", data);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    //塑封
    @RequestMapping("/VehicleCertificatePlasticPackagelist")
	public R VehicleCertificatePlasticPackagelist(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			
			List<EbsVehiclecard> list = decoratorEbsVehiclecardService.list(map);
			int a = decoratorEbsVehiclecardService.listcount(map);
			return R.ok().put("data", list).put("code", 200).put("msg", "查询成功！").put("total", a);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", 201).put("msg", "查询失败！");
		}
	}
    
    //更新塑封状态
    @RequestMapping(value = {"/UpdatePlasticPackage","/UpdatePlasticPackageBack"})
	public R UpdatePlasticPackage(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	User user = (User)session.getAttribute("user");
    	String strSessionid = sysSessionService.getSessionID(request);
    	EbsVehiclecard per = new EbsVehiclecard();
    	per.setId(Integer.valueOf(String.valueOf(map.get("id"))));    	
    	per.setIsplastic(Integer.valueOf(String.valueOf(map.get("isplastic"))));    
    	if(per.getIsplastic()==1){
    		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
			String time = formater1.format(new Date());
			per.setPlastictime(time);
    	}
    	decoratorEbsVehiclecardService.update(per);
    	if(per.getPrintstatus()==1){//打印完成给用户发短信取证
			commonService.SendSMS(per.getId(),"car",strSessionid);
		}
    	
    	sysOperationLogService.CreateEntity("车辆证件塑封", strSessionid, 0, user.getId(), 
    			per.getId(), JSONObject.toJSONString(map));
    	
		return R.ok().put("code", 200).put("msg", "成功");
		
	}
    
    @RequestMapping("/VehiclecardForensicMarkBiaoJi")
	@Transactional(rollbackFor = Exception.class)
	public R VehiclecardForensicMarkBiaoJi(
			@RequestBody Map<String, Object> map, HttpServletRequest request,
			HttpSession session) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			User user = (User) session.getAttribute("user");
			String value = String.valueOf(map.get("ids"));
			String[] ids = value.split(",");
			for (String id : ids) {
				EbsVehiclecard per = new EbsVehiclecard();
				if (id != null && !id.equals("")) {
					per.setId(Integer.valueOf(id));
					per.setIsforensics(1);
					SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
					String time = formater1.format(new Date());
					per.setForensicstime(time);
					decoratorEbsVehiclecardService.update(per);
					Map<String,Object> para = new HashMap<String,Object>();
					para.put("primaryid", id);
					para.put("phone", map.get("phone"));
					para.put("createby", user.getId());
					para.put("act", "car");
					commonService.addMarkLog(para);
					
					sysOperationLogService.CreateEntity("批量更新车辆证件取证状态",
							strSessionid, 0, user.getId(), per.getId(),
							JSONObject.toJSONString(per));
	
				}
			}
			return R.ok().put("code", 200).put("msg", "标记成功");
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
		}
	}
    
}