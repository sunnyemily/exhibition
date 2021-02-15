package cn.org.chtf.card.manage.common.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.model.EbsPersonnelcard;
import cn.org.chtf.card.manage.Exhibitors.service.EbsCompanyinfoService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsPersonnelcardService;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.MakeEvidence.service.CmCertificateTypeService;
import cn.org.chtf.card.manage.Volunteer.model.VolVolunteer;
import cn.org.chtf.card.manage.Volunteer.service.VolVolunteerService;
import cn.org.chtf.card.manage.common.service.CommonService;
import cn.org.chtf.card.manage.function.pojo.Function;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.system.model.SysSession;
import cn.org.chtf.card.manage.system.model.SysSurvey;
import cn.org.chtf.card.manage.system.model.SystemDictionaries;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.system.service.SysSurveyResultService;
import cn.org.chtf.card.manage.system.service.SysSurveyService;
import cn.org.chtf.card.manage.system.service.SystemDictionariesService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.manage.user.service.UserService;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.support.util.QRCode.QRCodeUtil;

import com.alibaba.fastjson.JSONObject;


/**
 * 届次信息Controller
 * @author lm
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
	private EbsPersonnelcardService ebsPersonnelcardService;
    
    @Autowired
    private SystemDictionariesService systemDictionariesService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private EbsCompanyinfoService ebsCompanyinfoService;
    
    @Autowired
    private SysSurveyService sysSurveyService;
    
    @Autowired
    private SysSurveyResultService sysSurveyResultService;
    
    @Autowired
	private CmCertificateTypeService cmCertificateTypeService;
    
    @Autowired
    private VolVolunteerService volVolunteerService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    @GetMapping("/GetTicketNumber")
   	public R GetTicketNumber(@RequestParam(value = "value") String value) throws Exception {  
    	Map<String,Object> exhibitionInfo = new HashMap<String,Object>();
    	String code = commonService.GetTicketNumber(exhibitionInfo);
   		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("result", code);
   	}
    
    
    @GetMapping("/createQrCode")
	public R createQrCode(@RequestParam(value = "value") String value, @RequestParam(value = "logo") String logo) throws Exception {    	
    	String path = QRCodeUtil.encode(value,logo);//path 二维码保存的服务器的路径
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("result", path);
	}
    
    @GetMapping("/getQrCode")
	public void getQrCode(@RequestParam(value = "value") String value, @RequestParam(value = "logo") String logo,
			HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	try {
    		  response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
    		  response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
    		  response.setHeader("Cache-Control", "no-cache");
    		  response.setDateHeader("Expire", 0);
    		  QRCodeUtil qru = new QRCodeUtil();
    		  qru.getQrCode(value,"./static/images/logo.png",request,response);
    		 } catch (Exception e) {
    			 System.out.print(e);
    		 }
	}   
    
    @GetMapping("/getQrCodeNoLogo")
	public void getQrCodeNoLogo(@RequestParam(value = "value") String value,
			HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	try {
    		  response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
    		  response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
    		  response.setHeader("Cache-Control", "no-cache");
    		  response.setDateHeader("Expire", 0);
    		  QRCodeUtil qru = new QRCodeUtil();
    		  qru.getQrCode(value,"",request,response);
    		 } catch (Exception e) {
    			 System.out.print(e);
    		 }
	}  
    
    @RequestMapping("/getCommand")
	@ResponseBody
	public R getCommand(String pass,String command){
    	return commonService.ExcuteCommand(pass,command);
    }
    
    /*
     * 上传视频回调接口
     */
    @RequestMapping("/fileCallBack")
	@ResponseBody
	public String fileCallBack(@RequestBody JSONObject jsonParam){
		System.out.println("/common/fileCallBack  success被调用");	
		
		System.out.println(jsonParam);
		
		JSONObject root = jsonParam.getJSONObject("ProcedureStateChangeEvent");//.getJSONObject("SessionContext");
		if(root.getString("Message").equals("SUCCESS")){
			//记录表ID
			String zhujianid= root.getString("SessionContext");
			//JSONObject data = root.getJSONObject("MediaProcessResultSet");
			JSONArray data = root.getJSONArray("MediaProcessResultSet");
			for(int j=0;j<data.size();j++){
				JSONObject data1 = data.getJSONObject(j);
				if(data1.getString("Type").equals("Transcode")){
					JSONObject data2 = data1.getJSONObject("TranscodeTask").getJSONObject("Output");
					String videoUrl = data2.getString("Url");
					Map<String,Object> para = new HashMap<String,Object>();
					para.put("id",zhujianid);
					para.put("url", videoUrl.replace("http:", "https:"));
					commonService.ChuLiWanChengDeShiPin(para);
				}				
			}
			
			
			/*
			JSONArray data = root.getJSONArray("MediaProcessResultSet");
			for(int j=0;j<data.size();j++){
				JSONObject data1 = data.getJSONObject(j);
				if(data1.getString("Type").equals("AdaptiveDynamicStreaming")){
					JSONObject data2 = data1.getJSONObject("AdaptiveDynamicStreamingTask").getJSONObject("Output");
					String videoUrl = data2.getString("Url");
					Map<String,Object> para = new HashMap<String,Object>();
					para.put("id",zhujianid);
					para.put("url", videoUrl.replace("http:", "https:"));
					commonService.ChuLiWanChengDeShiPin(para);
				}				
			}*/
		}			
		//System.out.println("jsonParam.toJSONString() :  "+jsonParam.toJSONString());
	
		return "";
	}
    
    @RequestMapping("/isExceedLimit")
	public R isExceedLimit(@RequestBody Map<String,Object> map,	HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	String companyid = map.get("companyid").toString();
    	CmCertificateType card = new CmCertificateType();
    	EbsPersonnelcard ebsPersonnelcard = new EbsPersonnelcard();
		Member member = new Member();
		Map<String, Object> exhibitionInfo = new HashMap<String,Object>();
		SysSession ss = sysSessionService.findById(Integer.valueOf(strSessionid));
		exhibitionInfo.put("sessionId", strSessionid);
		exhibitionInfo.put("purchaserCardLimit", ss.getPurchaserCardLimit());
		Map<String,Object> par = new HashMap<String,Object>();
		par.put("companyid",companyid);
		par.put("session", strSessionid);
		member = ebsCompanyinfoService.GetMemberInfoByCompanyId(par);
		
		// 找当前届次证件类型为参展证ID
		Map<String, Object> mapx = new HashMap<String, Object>();
		mapx.put("type", 0);
		mapx.put("useable", 1);
		mapx.put("isexhibitor", 1);
		mapx.put("session", strSessionid);
		card = cmCertificateTypeService.findByMap(mapx);
		ebsPersonnelcard.setId(null);
		ebsPersonnelcard.setCardtype(card.getId());
		
		boolean istrue = ebsPersonnelcardService.isExceedLimit(card,ebsPersonnelcard,member,exhibitionInfo);
		if(istrue){
			return R.error().put("code", -100).put("msg", "新增此类型证件后，将超出允许添加的证件总数");
		}
		//return R.error().put("code", -100).put("msg", "新增此类型证件后，将超出允许添加的证件总数");
    	return R.ok();
	}
    
    /**
     * 获取参会角色
     * @param map
     * @return
     */
    @RequestMapping("/GetParticipants")
	public R GetParticipants(@RequestBody Map<String,Object> map){
		List<SystemDictionaries> list = systemDictionariesService.findByMap(map);    	
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 

    /**
     * 获取行业
     * @param map
     * @return
     */
    @RequestMapping("/GetIndustry")
	public R GetIndustry(@RequestBody Map<String,Object> map){
		List<Map<String,Object>> list = commonService.GetIndustry(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 
    
    @RequestMapping("/GetCountry")
	public R GetCountry(@RequestBody Map<String,Object> map){
		List<Map<String,Object>> list = commonService.GetCountry(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}   
    
    @RequestMapping("/GetCardType")
	public R GetCardType(@RequestBody Map<String,Object> map,HttpServletRequest request){
    	String strSessionid = sysSessionService.getSessionID(request);
		map.put("session",strSessionid);
		List<Map<String,Object>> list = commonService.GetCardType(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 
    
    @RequestMapping("/GetCompanyInfo")
	public R GetCompanyInfo(@RequestBody Map<String,Object> map,HttpServletRequest request){
    	String strSessionid = sysSessionService.getSessionID(request);
		map.put("session",strSessionid);
		List<EbsCompanyinfo> list = ebsCompanyinfoService.findByMap(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 
    
    @RequestMapping("/GetDaiBanYuanZhengJianLeiXing")
	public R GetDaiBanYuanZhengJianLeiXing(@RequestBody Map<String,Object> map,HttpServletRequest request){  
    	String oldSession = String.valueOf(map.get("oldsession"));
    	if(oldSession==null || oldSession.equals("null") || oldSession.equals("")){
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    	}
    	else{
    		map.put("session", oldSession);
    	}
		List<Map<String,Object>> list = commonService.GetDaiBanYuanZhengJianLeiXing(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 
    
    @RequestMapping("/GetCardTypesForPrint")
	public R GetCardTypesForPrint(@RequestBody Map<String,Object> map,HttpServletRequest request){      	
    	String strSessionid = sysSessionService.getSessionID(request);
    	map.put("session",strSessionid);    	
		List<Map<String,Object>> list = commonService.GetCardTypesForPrint(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 
    
    
    /**查询交易团及零散交易团list
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/GetTradingGroup")
	public R GetTradingGroup(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);

		User user = (User) session.getAttribute("user");
		map.put("userId",user.getId());
    	
    	//默认查询当前届次的交易团list
    	if(map.get("session")==null || "".equals(map.get("session"))) map.put("session",strSessionid);
    	if(map.get("type")==null || "".equals(map.get("type"))) map.put("type",0);
		
    	List<Map<String,Object>> list = commonService.GetTradingGroup(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("currentSession", strSessionid);
	} 
    
    @RequestMapping("/GetTradingGroupForGreen")
	public R GetTradingGroupForGreen(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);

		User user = (User) session.getAttribute("user");
		map.put("userId",user.getId());
    	map.put("session",strSessionid);    	
		
    	List<Map<String,Object>> list = commonService.GetTradingGroupForGreen(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("currentSession", strSessionid);
	} 
    
    
    @RequestMapping("/GetZhengJianZhiZuoTradingGroup")
	public R GetZhengJianZhiZuoTradingGroup(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);		
    	User user = (User) session.getAttribute("user");
    	map.put("userId",user.getId());
    	map.put("session",strSessionid);   	
    	List<Map<String,Object>> list = commonService.GetZhengJianZhiZuoGetTradingGroup(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}    
    
    @RequestMapping("/GetPreviousTradingGroup")
	public R GetPreviousTradingGroup(@RequestBody Map<String,Object> map,HttpServletRequest request){
    	String strSessionid = sysSessionService.getSessionID(request);
    	
    	//默认查询当前届次的交易团list
    	map.put("session",strSessionid+","+map.get("oldsession"));
    	map.put("type",0);
		//当前届次
    	List<Map<String,Object>> listnew = commonService.GetPreviousTradingGroup(map);
    	
    	
    	
		return R.ok().put("data", listnew).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("currentSession", strSessionid);
	} 
    
    
    @RequestMapping("/getHistorySession")
	public R getHistorySession(HttpServletRequest request) {
		String url= CryptographyUtil.GeCurrenttUrl(request);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("url", url);
		List<Map<String,Object>> list = sysSessionService.getHistorySession(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}
    
    @RequestMapping("/getAllSession")
    public R getAllSession(HttpServletRequest request) {
    	String url= CryptographyUtil.GeCurrenttUrl(request);
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("url", url);
    	List<Map<String,Object>> list = sysSessionService.getAllSession(map);
    	return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    }
    
    @RequestMapping("/getHistoryCountryCount")
	public R getHistoryCountryCount(HttpServletRequest request) {
		String url= CryptographyUtil.GeCurrenttUrl(request);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("url", url);
		List<Map<String,Object>> list = sysSessionService.getHistoryCountryCount(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("total", list.size());
	}
    
    @RequestMapping("/getHistoryCompanyCount")
	public R getHistoryCompanyCount(HttpServletRequest request) {
		String url= CryptographyUtil.GeCurrenttUrl(request);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("url", url);
		List<Map<String,Object>> list = sysSessionService.getHistoryCompanyCount(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("total", list.size());
	}
    
    @RequestMapping("/GetAgent")
	public R GetAgent(@RequestBody Map<String,Object> map,HttpServletRequest request){  
    	String strSessionid = sysSessionService.getSessionID(request);
		map.put("session",strSessionid);
		List<Map<String,Object>> list = commonService.GetAgent(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 
    
    @RequestMapping("/GetQuestionInfo")
	public R GetQuestionInfo(@RequestBody Map<String,Object> map,HttpServletRequest request){  
    	String strSessionid = sysSessionService.getSessionID(request);
		map.put("session",strSessionid);
		SysSurvey ss = sysSurveyService.findByOneMap(map);
		map.put("surveyid", ss.getId());
		List<Map<String,Object>> list = sysSurveyResultService.GetQuestionInfoBySurveyID(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 
    
    @RequestMapping("/GetAnswerInfo")
	public R GetAnswerInfo(@RequestBody Map<String,Object> map,HttpServletRequest request){  
    	String strSessionid = sysSessionService.getSessionID(request);
		map.put("session",strSessionid);
		List<Map<String,Object>> list = sysSurveyResultService.GetAnswerInfoByQuestionID(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 
    
    @RequestMapping("/GetZhiYuanZhe")
	public R GetZhiYuanZhe(@RequestBody Map<String,Object> map,HttpServletRequest request){  
    	String strSessionid = sysSessionService.getSessionID(request);
		map.put("session",strSessionid);
		List<VolVolunteer> list = volVolunteerService.findByMap(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 
    
    @RequestMapping("/GetAnswerTongJiInfo")
	public R GetAnswerTongJiInfo(@RequestBody Map<String,Object> map,HttpServletRequest request){  
    	String strSessionid = sysSessionService.getSessionID(request);
		map.put("session",strSessionid);
		List<Map<String,Object>> list = sysSurveyResultService.GetAnswerTongJiInfoByQuestionID(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 
    
    /**
     * @Description: 获取用户所有权限url
     * @date: 2020年8月13日 下午7:03:49
     * @author: ggwudivs
     * @param request
     * @return: R
     */
    @RequestMapping("/getUserPermissions")
    public R getUserPermissions(HttpServletRequest request){  
    	User user = (User)request.getSession().getAttribute("user");
    	List<String> list = new ArrayList<>();
    	for(Function f : user.getPermissions()) {
    		list.add(f.getFunctionUrl());
    	}
    	return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    } 
    
    @RequestMapping("/GetTradingGroupForUser")
	public R GetTradingGroupForUser(@RequestBody Map<String,Object> map,HttpServletRequest request){
    	String strSessionid = sysSessionService.getSessionID(request);  
    	map.put("session",strSessionid);		
    	List<Map<String,Object>> list = commonService.GetUserRights(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("currentSession", strSessionid);
	}
    
    @RequestMapping("/GetUser")    
	public R GetUser(@RequestBody Map<String,Object> map,HttpServletRequest request){
    	List<Map<String, Object>> list = commonService.GetUser(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    }
    
    @RequestMapping("/AddUserRightsForUser")
    @Transactional(rollbackFor = Exception.class)
	public R AddUserRightsForUser(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try{
	    	String strSessionid = sysSessionService.getSessionID(request);  
	    	map.put("session",strSessionid);
	    	int iUserId = Integer.valueOf(map.get("userid").toString());
	    	int iIsAll = Integer.valueOf(map.get("isall").toString());
	    	User user = new User();
	    	user.setId(iUserId);
	    	user.setIsall(iIsAll);
	    	userService.update(user);
	    	
	    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    	String isStr = map.get("ids").toString();
	    	if (isStr!=null &&!isStr.equals("")) {
	    		String[] ids = isStr.split(",");    		
	    		for (String id : ids) {
	    			if(id!=null &&!id.equals("")){
	    				Map<String,Object> par = new HashMap<String,Object>();
	    				par.put("userid", iUserId);
	    				par.put("session",strSessionid);
	    				par.put("tradinggroupid", id);
	    				list.add(par);
	    			}
	    		}
	    	}
	    	if(list.size()>0){
	    		commonService.delUserRights(map);
	    		commonService.addUserRights(list);
	    	}	    
	    	User userx = (User)session.getAttribute("user");	
	    	sysOperationLogService.CreateEntity("用户分配交易团", strSessionid, 0, userx.getId(), 
	        		0, JSONObject.toJSONString(map));
		
    	} catch (Exception ee) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
		}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
	}
    
    @RequestMapping("/GetGreenPersonCard")
	public R GetGreenPersonCard(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	User user = (User)session.getAttribute("user");	
    	String strSessionid = sysSessionService.getSessionID(request);
    	map.put("session",strSessionid); 
    	map.put("userId", user.getId());
    	int itotal = commonService.GetGreenPersonCardCount(map);
		return R.ok().put("total", itotal).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	} 
    
    /**
     * 获取待审核证件代办员及数量（证件审核）
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/GetAgentCertificateReview")
   	public R GetAgentCertificateReview(@RequestBody Map<String,Object> map,HttpServletRequest request){
       	String strSessionid = sysSessionService.getSessionID(request);			  	
       	map.put("session",strSessionid);   	
       	List<Map<String,Object>> list = commonService.GetAgentCertificateReview(map);
   		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
   	} 
    
    /**
     * 通过证件类型获取类型下证件所属企业信息及待审核数量（证件审核）
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/GetPendingDocuments")
   	public R GetPendingDocuments(@RequestBody Map<String,Object> map,HttpServletRequest request){
       	String strSessionid = sysSessionService.getSessionID(request);			  	
       	map.put("session",strSessionid);   	
       	List<Map<String,Object>> list = commonService.GetPendingDocuments(map);
   		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
   	} 
    
    /**
     * 通过证件类型获取类型下证件所属企业信息及待打印数量（证件制作）
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/GetDocumentsToBePrinted")
   	public R GetDocumentsToBePrinted(@RequestBody Map<String,Object> map,HttpServletRequest request){
       	String strSessionid = sysSessionService.getSessionID(request);			  	
       	map.put("session",strSessionid);   	
       	List<Map<String,Object>> list = commonService.GetDocumentsToBePrinted(map);
   		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
   	} 
    
 
    
    /**
     * 获取票号
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/GetTicketNumber")
   	public R GetTicketNumber(){       	
       	String ph = commonService.GetTicketNumber("");
   		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("ticketnum", ph);
   	} 
    
    @RequestMapping("/GetCompanyList")
   	public R GetCompanyList(@RequestBody Map<String,Object> map,HttpServletRequest request){
       	String strSessionid = sysSessionService.getSessionID(request);			  	
       	map.put("session",strSessionid);   	
       	List<Map<String,Object>> list = commonService.GetCompanyList(map);
   		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
   	} 
    
    @RequestMapping("/updatePersonStatusALL")
	public R updatePersonStatusALL(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpSession session) {
    	commonService.updatePersonStatusALL(map);
    	return R.ok();
    }
    
    @GetMapping("/zhajihao")
   	public R zhajihao(@RequestParam Map<String,Object> map,HttpServletRequest request){
   		try {
   					
   			List<Map<String,Object>> list = commonService.zhajihao(map);
   			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", "查询成功！");
   		} catch (Exception e) {
   			System.out.println(e.getMessage());
   			return R.error().put("code", 201).put("msg", "查询失败！");
   		}
   	} 
    
    @GetMapping("/zhajidata")
   	public R zhajidata(@RequestParam Map<String,Object> map,HttpServletRequest request){
   		try {   					
   			List<Map<String,Object>> list = commonService.zhajidata(map);
   			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", "查询成功！");
   		} catch (Exception e) {
   			System.out.println(e.getMessage());
   			return R.error().put("code", 201).put("msg", "查询失败！");
   		}
   	} 
    @GetMapping("/zhajishijiandata")
   	public R zhajishijiandata(@RequestParam Map<String,Object> map,HttpServletRequest request){
   		try {   					
   			List<Map<String,Object>> list = commonService.zhajishijiandata(map);
   			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", "查询成功！");
   		} catch (Exception e) {
   			System.out.println(e.getMessage());
   			return R.error().put("code", 201).put("msg", "查询失败！");
   		}
   	} 
    
    @GetMapping("/GetCardTypeData")
   	public R GetCardTypeData(@RequestParam Map<String,Object> map,HttpServletRequest request){
   		try {   					
   			List<Map<String,Object>> list = commonService.GetCardTypeData(map);
   			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", "查询成功！");
   		} catch (Exception e) {
   			System.out.println(e.getMessage());
   			return R.error().put("code", 201).put("msg", "查询失败！");
   		}
   	}
    
}