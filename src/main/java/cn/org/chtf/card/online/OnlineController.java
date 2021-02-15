package cn.org.chtf.card.online;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.service.EbsCompanyinfoService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsPersonnelcardService;
import cn.org.chtf.card.manage.basic.dao.BasicMapper;
import cn.org.chtf.card.manage.basic.pojo.Basic;
import cn.org.chtf.card.manage.friendlink.service.FriendlinkService;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.member.service.MemberService;
import cn.org.chtf.card.manage.menu.service.MenuService;
import cn.org.chtf.card.manage.online.model.ExhibitionInfoModel;
import cn.org.chtf.card.manage.online.model.ExhibitorModel;
import cn.org.chtf.card.manage.online.model.OnlineActivityDetails;
import cn.org.chtf.card.manage.online.model.OnlineApplyMeeting;
import cn.org.chtf.card.manage.online.model.OnlineInquiry;
import cn.org.chtf.card.manage.online.model.OnlineNegotiate;
import cn.org.chtf.card.manage.online.model.ProductModel;
import cn.org.chtf.card.manage.online.service.OnlineActivityDetailsService;
import cn.org.chtf.card.manage.online.service.OnlineApplyMeetingService;
import cn.org.chtf.card.manage.online.service.OnlineInquiryService;
import cn.org.chtf.card.manage.online.service.OnlineNegotiateService;
import cn.org.chtf.card.manage.online.service.OnlineNewsService;
import cn.org.chtf.card.manage.online.service.OnlineReservationDetailsService;
import cn.org.chtf.card.manage.online.service.OnlineService;
import cn.org.chtf.card.manage.product.model.WebProduct;
import cn.org.chtf.card.manage.product.service.WebProductService;
import cn.org.chtf.card.manage.system.model.SysIndustry;
import cn.org.chtf.card.manage.system.service.SysIndustryService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.wechatuser.dao.WechatUserMapper;
import cn.org.chtf.card.manage.wechatuser.pojo.WechatUser;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.PaginationUtil;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.support.util.tencentyun.TLSSigAPIv2;
import cn.org.chtf.card.support.util.tencentyun.Util;

@Controller
@RequestMapping(value = { "/online" })
public class OnlineController {
	OnlineController() {
	}

	Map<String, Object> exhibitionInfo;

	@Resource(name = "MenuServiceImpl")
	MenuService menuService;
	@Resource(name = "MemberServiceImpl")
	MemberService memberService;

	@Autowired
	SysSessionService sessionService;
	
	@Resource
	private Util tencentUtil;

	@Autowired
	private WebProductService webProductService;

	@Autowired
	private FriendlinkService firendlinkService;
	
	@Autowired
	private EbsPersonnelcardService ebsPersonnelcardService;

	@Autowired
	private OnlineService onlineService;

	@Autowired
	private SysOperationLogService sysOperationLogService;

	@Autowired
	private OnlineReservationDetailsService onlineReservationDetailsService;
	
	@Autowired
	private OnlineActivityDetailsService onlineActivityDetailsService;

	@Autowired
	private OnlineNewsService onlineNewsService;

	@Autowired
	private SysIndustryService sysIndustryService;

	@Autowired
	private EbsCompanyinfoService ebsCompanyinfoService;

	@Autowired
	private OnlineInquiryService onlineInquiryService;

	@Autowired
	private OnlineNegotiateService onlineNegotiateService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private BasicMapper basicDao;
	@Autowired
	private OnlineApplyMeetingService onlineApplyMeetingService;
	@Autowired
	private WechatUserMapper wechatUserDAO;

	/**
	 * 返回模版路径
	 * 
	 * @param string
	 * @param language
	 * @return
	 */
	private String ReturnTemplatePath(String string, String language) {
		return "/online/" + language + "/" + string;
	}
	
	/**
	 * 安全登陆验证，需登陆后访问的都加此验证
	 */
	public Boolean safeCheck(String language,HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Object obj = session.getAttribute("wechatUser");
		if(obj==null) {
			try {
				request.getRequestDispatcher("/online/" + language + "/index.html").forward(request,response);	
				return false;
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return true;
	}

	// 登录
	@RequestMapping("/{language}/login")
	@ResponseBody
	public ResultModel Login(@PathVariable String language, String phone,
			String password, String vCode, HttpSession session) {
		setCommonContent();
		ResultModel result = onlineService.login(phone, password, session);
		return result;
	}
	
	// 修改密码
		@RequestMapping("/{language}/ChangePass")
		@ResponseBody
		public ResultModel ChangePass(@PathVariable String language, String oldpass,
				String pass, String confirmpass, HttpSession session) {
			setCommonContent();
			WechatUser user = (WechatUser) session.getAttribute("wechatUser");
			if(user==null){
				return WConst.RELOGINJSON;
			}
			return onlineService.ChangePass(oldpass,pass,confirmpass,user.getUid(),user.getPhone());
		}

	// 验证注册手机号是否已存在
	@RequestMapping("/{language}/CheckReg")
	@ResponseBody
	public ResultModel CheckReg(@PathVariable String language, String phone,
			HttpSession session) {
		setCommonContent();
		ResultModel result = onlineService.CheckReg(phone);
		return result;
	}

	// 发送验证码
	@RequestMapping("/{language}/sendConsolePhoneCode")
	@ResponseBody
	public ResultModel sendConsolePhoneCode(@PathVariable String language,
			String phone, HttpSession session) {
		setCommonContent();
		String sessionId = exhibitionInfo.get("sessionId").toString();
		ResultModel result = onlineService.sendConsolePhoneCode(phone, session,
				sessionId);
		return result;
	}

	// 注册，先将手机号密码存入redis
	@RequestMapping("/{language}/reg")
	@ResponseBody
	public ResultModel reg(@PathVariable String language, String phone,
			String password, String vCode, HttpSession session) {
		setCommonContent();
		ResultModel result = onlineService.reg(phone, password, vCode, session);
		return result;
	}

	// 注册，先将手机号密码存入redis
	@RequestMapping("/{language}/updateInfo")
	@ResponseBody
	public ResultModel updateInfo(@PathVariable String language, String name,
			String cardnumber, HttpSession session) {
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		if(user==null){
			return WConst.RELOGINJSON;
		}
		
		String sessionId = this.exhibitionInfo.get("sessionId").toString();
		return ebsPersonnelcardService.updateWebPersonRole(cardnumber, name, Integer.parseInt(sessionId), session);
		/*
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("cardnumber", cardnumber);
		map.put("uid", user.getUid());
		onlineService.updateInfo(map);*/
		//return new ResultModel(WConst.SUCCESS, WConst.SETED, null);
	}
	
	/*
	 * 会议申请
	 */
	@RequestMapping("/{language}/addApplyMeeting")
	@ResponseBody
	public ResultModel addApplyMeeting(@PathVariable String language,@RequestBody OnlineApplyMeeting oam, HttpSession session) {
			setCommonContent();
			WechatUser user = (WechatUser) session.getAttribute("wechatUser");
			if(user==null){
				return WConst.RELOGINJSON;
			}			
			String sessionId = this.exhibitionInfo.get("sessionId").toString();
			oam.setCreateby(user.getUid());			
			oam.setPhones(oam.getPhones().replace("\n", ","));
			oam.setSession(sessionId);
			onlineApplyMeetingService.save(oam);			
			
			return new ResultModel(WConst.SUCCESS, WConst.SETED, null);
	}

	// 会员页
	@RequestMapping(value = { "/{language}/member.html" })
	public String exhibitionarea(@PathVariable String language, Model model,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		setCommonContent();
		
		safeCheck(language,session,request,response);
		
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		
		model.addAttribute("userinfo", user);

		model.addAttribute("pageName", "member");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("member", language);
	}
	
	// 修改密码
		@RequestMapping(value = { "/{language}/changepass.html" })
		public String changepass(@PathVariable String language, Model model,
				HttpSession session, HttpServletRequest request, HttpServletResponse response) {
			setCommonContent();
			
			safeCheck(language,session,request,response);
			
			WechatUser user = (WechatUser) session.getAttribute("wechatUser");
			
			model.addAttribute("userinfo", user);

			model.addAttribute("pageName", "changepass");
			Basic basicfoot = basicDao.getBasicByMenuId(269);
			model.addAttribute("foot", basicfoot);
			return ReturnTemplatePath("changepass", language);
		}

	// 收藏的企业
	@RequestMapping(value = { "/{language}/sccompany-{index}.html" })
	public String sccompany(@PathVariable String language,
			@PathVariable Integer index, Model model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		setCommonContent();
		safeCheck(language,session,request,response);
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);

		PageModel page = new PageModel();
		int iMember = user.getUid();
		page.setMemberid(iMember);
		page.setLimit(8);
		page.setPage(index);
		page.setAct(0);
		page.setSession(exhibitionInfo.get("sessionId").toString());
		model.addAttribute("pageIndex", index);
		List<Map<String, Object>> list = onlineService.GetMyFavorites(page);
		int iCount = onlineService.GetMyFavoritesCount(page);

		model.addAttribute("scCompanys", list);

		int pageSize = iCount / 8;
		if (iCount % 8 != 0) {
			pageSize++;
		}
		// 3.添加页码
		model.addAttribute("paging",
				(new PaginationUtil(index, pageSize).getPageList()));
		model.addAttribute("path", "");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		model.addAttribute("pageName", "sccompany");
		return ReturnTemplatePath("sccompany", language);
	}

	// 收藏的产品
	@RequestMapping(value = { "/{language}/scproduct-{index}.html" })
	public String scproduct(@PathVariable String language,
			@PathVariable Integer index, Model model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		safeCheck(language,session,request,response);
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);

		PageModel page = new PageModel();
		int iMember = user.getUid();
		page.setMemberid(iMember);
		page.setLimit(8);
		page.setPage(index);
		page.setAct(1);
		page.setSession(exhibitionInfo.get("sessionId").toString());
		model.addAttribute("pageIndex", index);
		List<Map<String, Object>> list = onlineService.GetMyFavorites(page);
		int iCount = onlineService.GetMyFavoritesCount(page);

		model.addAttribute("scProducts", list);

		int pageSize = iCount / 8;
		if (iCount % 8 != 0) {
			pageSize++;
		}
		// 3.添加页码
		model.addAttribute("paging",
				(new PaginationUtil(index, pageSize).getPageList()));
		model.addAttribute("path", "");

		model.addAttribute("pageName", "scproduct");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("scproduct", language);
	}

	// 站内信
	@RequestMapping(value = { "/{language}/message-{index}.html" })
	public String message(@PathVariable String language,
			@PathVariable Integer index, Model model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		safeCheck(language,session,request,response);
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);	
		
		PageModel page = new PageModel();
		int iMember = user.getUid();
		page.setMemberid(iMember);
		page.setCompanyid(user.getCompanyid().toString());
		page.setLimit(10);
		page.setPage(index);
		page.setSession(exhibitionInfo.get("sessionId").toString());
		model.addAttribute("pageIndex", index);
		List<Map<String, Object>> list = onlineService.MyReceived(page);
		int iCount = onlineService.MyReceivedCount(page);

		model.addAttribute("reciveInfos", list);

		int pageSize = iCount / 10;
		if (iCount % 10 != 0) {
			pageSize++;
		}
		// 3.添加页码
		model.addAttribute("paging",
				(new PaginationUtil(index, pageSize).getPageList()));
		model.addAttribute("path", "");
		
		
		model.addAttribute("pageName", "message");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("message", language);
	}
	
	// 我的询盘
		@RequestMapping(value = { "/{language}/inquiry-{index}.html" })
		public String inquiry(@PathVariable String language,
				@PathVariable Integer index, Model model, HttpSession session,
				HttpServletRequest request, HttpServletResponse response) {
			safeCheck(language,session,request,response);
			setCommonContent();
			WechatUser user = (WechatUser) session.getAttribute("wechatUser");
			
			model.addAttribute("userinfo", user);	
			
			PageModel page = new PageModel();
			int iMember = user.getUid();
			page.setMemberid(iMember);
			page.setCompanyid(user.getCompanyid().toString());
			page.setLimit(10);
			page.setPage(index);
			page.setSession(exhibitionInfo.get("sessionId").toString());
			model.addAttribute("pageIndex", index);
			List<Map<String, Object>> list = onlineService.MyPostInfo(page);
			int iCount = onlineService.MyPostInfoCount(page);

			model.addAttribute("xpinfos", list);

			int pageSize = iCount / 10;
			if (iCount % 10 != 0) {
				pageSize++;
			}
			// 3.添加页码
			model.addAttribute("paging",
					(new PaginationUtil(index, pageSize).getPageList()));
			model.addAttribute("path", "");
			
			
			model.addAttribute("pageName", "inquiry");
			Basic basicfoot = basicDao.getBasicByMenuId(269);
			model.addAttribute("foot", basicfoot);
			return ReturnTemplatePath("inquiry", language);
		}

	// 申请会议
	@RequestMapping(value = { "/{language}/applymeeting-{index}.html" })
	public String applymeeting(@PathVariable String language,
			@PathVariable Integer index, Model model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		safeCheck(language,session,request,response);
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("pageName", "applymeeting");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("applymeeting", language);
	}

	// 参加会议
	@RequestMapping(value = { "/{language}/attendmeeting-{index}.html" })
	public String attendmeeting(@PathVariable String language,
			@PathVariable Integer index, Model model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		safeCheck(language,session,request,response);
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("pageName", "attendmeeting");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		
		PageModel page = new PageModel();
		
		page.setPhone(user.getPhone());
		page.setLimit(8);
		page.setPage(index);
		page.setSession(exhibitionInfo.get("sessionId").toString());
		model.addAttribute("pageIndex", index);
		List<Map<String, Object>> list = onlineService.MeetingList(page);
		for(int j=0;j<list.size();j++){
			SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date starttime = formater1.parse(list.get(j).get("starttime").toString());
			Date endtime = formater1.parse(list.get(j).get("endtime").toString());
			Date nowtime = formater1.parse(formater1.format(new Date()));
			if (nowtime.compareTo(starttime) < 0) {// 未开始				
				list.get(j).put("status", 0);
			}
			if (nowtime.compareTo(starttime) > 0 && nowtime.compareTo(endtime) < 0) {// 时间区间内
				list.get(j).put("status", 1);
			}
			if (nowtime.compareTo(endtime) > 0) {// 已结束
				list.get(j).put("status", 2);
			}
		}
		
		
		int iCount = onlineService.MeetingListCount(page);

		model.addAttribute("meetings", list);

		int pageSize = iCount / 8;
		if (iCount % 8 != 0) {
			pageSize++;
		}
		// 3.添加页码
		model.addAttribute("paging",
				(new PaginationUtil(index, pageSize).getPageList()));
		model.addAttribute("path", "");
		
		return ReturnTemplatePath("attendmeeting", language);
	}
	
	@RequestMapping("/{language}/MeetingShip")
	@ResponseBody
	public ResultModel MeetingShip(@PathVariable String language,
			String pass, String userName, String meetingnum, HttpSession session, HttpServletRequest request) {
		setCommonContent();	
		if("".equals(meetingnum))return new ResultModel(WConst.ERROR,"会议号不能为空",null);
		if("".equals(pass))return new ResultModel(WConst.ERROR,"请输入密码",null);
		
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		//String strOpenID = "oyozk5BbqH2iwror_BOgN4j3Uf8g";
		//user = wechatUserDAO.selectByRoutineOpenid(strOpenID);
		if(user==null){
			return new ResultModel(WConst.LOGINOVERTIME,WConst.LOGINOVERTIMEMSG,null);
		}
		Map<String,Object> resMap = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("phone", user.getPhone());
		map.put("meetingnumber", meetingnum);
		map.put("pass", pass);
		Boolean isok = onlineService.VerifyPassword(map);
		if(!isok){
			return new ResultModel(WConst.ERROR,"密码验证失败",null);
		}
		//获取主持人uid
		Integer hostid = onlineService.GetHostUid(map);
		resMap.put("hostid",hostid);
		//用户uid
		resMap.put("uid",user.getUid());
		//生成签名
		Integer appid = Util.IM_APP_ID;
		String sign = Util.IM_APP_KEY;
		TLSSigAPIv2 tlss = new TLSSigAPIv2(appid,sign);
		Integer EXPIRETIME = 7 * 24 * 60 * 60;
		String sig = tlss.genUserSig(String.valueOf(user.getUid()), EXPIRETIME);
		resMap.put("sign",sig);
		
		if(!user.getNickname().equals(userName)){
			WechatUser existUser = new WechatUser();
			existUser.setUid(user.getUid());
			existUser.setHeadimgurl(user.getHeadimgurl());
			existUser.setNickname(userName);
			existUser.setSex(user.getSex());
			existUser.setUserTypeId(user.getUserTypeId());
			existUser.setRoutineOpenid(user.getRoutineOpenid());
			existUser.setSessionKey(user.getSessionKey());	
			wechatUserDAO.updateByRoutineOpenidSelective(existUser);
			// 更新到腾讯云
			tencentUtil.updateIMUserProfile(existUser);
		}
		
		return new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,resMap);		
	}

	// 取消收藏
	@RequestMapping(value = { "/{language}/quxiao" })
	@ResponseBody
	public ResultModel quxiao(@PathVariable String language, Integer fid,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		onlineService.UnFavorite(fid);
		return new ResultModel(WConst.SUCCESS, WConst.SETED, null);
	}

	// 会员退出
	@RequestMapping(value = { "/{language}/out" })
	@ResponseBody
	public ResultModel out(@PathVariable String language, Model model,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		session.setAttribute("wechatUser", null);
		return new ResultModel(WConst.SUCCESS, WConst.SETED, null);
	}
	
	@RequestMapping(value = { "/{language}/activity.html" })
	public String activity(@PathVariable String language, Model model,
			HttpSession session, HttpServletRequest request) throws ParseException {
		setCommonContent();	
		String firstDate="";
		List<Map<String,Object>> list = onlineService.getActivityList(exhibitionInfo);
		for(int j=0;j<list.size();j++){
			if(j==0){
				firstDate = list.get(j).get("date").toString();
			}
			String riqi = list.get(j).get("date").toString();
			list.get(j).put("shortdate", riqi.substring(riqi.length()-2));
		}
		model.addAttribute("topzxs", list);
		//赋值为当前日期
		SimpleDateFormat formaterx = new SimpleDateFormat("yyyy-MM-dd");		
		firstDate = formaterx.format(new Date());
		model.addAttribute("firstDate", firstDate);
		
		Basic basickms = basicDao.getBasicByMenuId(268);
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date starttime = formater1.parse(basickms.getStarttime());
		Date endtime = formater1.parse(basickms.getEndtime());
		Date nowtime = formater1.parse(formater1.format(new Date()));		
		Integer status = -1;
		if(nowtime.compareTo(starttime)<0){//未开始
			status=0;
		}
		if(nowtime.compareTo(starttime)>0 && nowtime.compareTo(endtime)<0){//时间区间内
			status=1;
		}
		if(nowtime.compareTo(endtime)>0){//已结束
			status=2;
		}
		basickms.setStatus(status);
		model.addAttribute("kms", basickms);	
		
		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "index");
		
		return ReturnTemplatePath("activity", language);
	}
	
	@RequestMapping(value = { "/{language}/productandcompany.html" })
	public String productandcompany(@PathVariable String language, Model model,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();	
		String firstDate="";
		List<Map<String,Object>> list = onlineService.getActivityList(exhibitionInfo);
		for(int j=0;j<list.size();j++){
			if(j==0){
				firstDate = list.get(j).get("date").toString();
			}
			String riqi = list.get(j).get("date").toString();
			list.get(j).put("shortdate", riqi.substring(riqi.length()-2));
		}
		model.addAttribute("topzxs", list);
		model.addAttribute("firstDate", firstDate);
		
		Basic basickms = basicDao.getBasicByMenuId(268);
		model.addAttribute("kms", basickms);		
		
		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "index");
		
		return ReturnTemplatePath("productandcompany", language);
	}
	

	/**
	 * 首页
	 * @throws ParseException 
	 */
	@RequestMapping(value = { "/{language}/index.html" })
	public String index(@PathVariable String language, Model model,
			HttpSession session, HttpServletRequest request) throws ParseException {
		setCommonContent();
		
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("dl", 0);
		if(user!=null){
			model.addAttribute("dl", 1);
		}
		
		String firstDate="";
		List<Map<String,Object>> list = onlineService.getActivityList(exhibitionInfo);
		for(int j=0;j<list.size();j++){
			if(j==0){
				firstDate = list.get(j).get("date").toString();
			}
			String riqi = list.get(j).get("date").toString();
			list.get(j).put("shortdate", riqi.substring(riqi.length()-2));
		}
		model.addAttribute("topzxs", list);
		//赋值为当前日期
		SimpleDateFormat formaterx = new SimpleDateFormat("yyyy-MM-dd");		
		firstDate = formaterx.format(new Date());
		if(firstDate.compareTo("2020-10-16")<0){
			firstDate="2020-10-16";
		}
			
		model.addAttribute("firstDate", firstDate);
		// 在线巡馆
		List<Map<String, Object>> onlinepavilion = onlineService
				.GetFriendLink(264);
		List<Map<String, Object>> onlinepavilions = onlinepavilion.stream()
				.limit(12).collect(Collectors.toList());
		model.addAttribute("onlinepavilions", onlinepavilions);

		// 获取顶部分类
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", exhibitionInfo.get("sessionId"));

		List<Map<String, Object>> pictureList = onlineService.GetFriendLink(262);
		// 处理超长文字
		for (int j = 0; j < pictureList.size(); j++) {
			String name = pictureList.get(j).get("title").toString();
			if (name.length() > 15) {
				pictureList.get(j).put("shorttitle",
						name.substring(0, 15) + "...");
			} else {
				pictureList.get(j).put("shorttitle", name);
			}
		}
		List<Map<String, Object>> toppictureList = pictureList.stream()
				.limit(12).collect(Collectors.toList());
		for (int j = 0; j < toppictureList.size(); j++) {
			String name = toppictureList.get(j).get("title").toString();
			if (name.length() > 11) {
				toppictureList.get(j).put("shorttitle",
						name.substring(0, 11) + "...");
			} else {
				toppictureList.get(j).put("shorttitle", name);
			}
		}

		// 获取360全景地址
		Basic basic = basicDao.getBasicByMenuId(267);
		model.addAttribute("url360", basic.getBasicIntro());
		
		Basic basickms = basicDao.getBasicByMenuId(268);
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date starttime = formater1.parse(basickms.getStarttime());
		Date endtime = formater1.parse(basickms.getEndtime());
		Date nowtime = formater1.parse(formater1.format(new Date()));		
		Integer status = -1;
		if(nowtime.compareTo(starttime)<0){//未开始
			status=0;
		}
		if(nowtime.compareTo(starttime)>0 && nowtime.compareTo(endtime)<0){//时间区间内
			status=1;
		}
		if(nowtime.compareTo(endtime)>0){//已结束
			status=2;
		}
		basickms.setStatus(status);
		model.addAttribute("kms", basickms);
		
		List<Map<String, Object>> bannerLists = onlineService.GetFriendLink(263);
		model.addAttribute("bannerLists", bannerLists);

		model.addAttribute("pictureLists", pictureList);
		
		//中部线上展厅
		List<Map<String, Object>> pictureListM = onlineService.GetFriendLink(270);
		// 处理超长文字
		for (int j = 0; j < pictureListM.size(); j++) {
			String name = pictureListM.get(j).get("title").toString();
			if (name.length() > 15) {
				pictureListM.get(j).put("shorttitle",name.substring(0, 15) + "...");
			} else {
				pictureListM.get(j).put("shorttitle", name);
			}
		}
		model.addAttribute("pictureListMs", pictureListM);
		
		//*********************************************
		//Banner下方模块-在线活动
		List<Map<String, Object>> zxhdList = onlineService.GetFriendLink(272);
				// 处理超长文字
		for (int j = 0; j < zxhdList.size(); j++) {
			String name = zxhdList.get(j).get("title").toString();
			if (name.length() > 7) {
				zxhdList.get(j).put("shorttitle",name.substring(0, 7) + "...");
			} else {
				zxhdList.get(j).put("shorttitle", name);
			}
		}
		model.addAttribute("zxhdLists", zxhdList);
		
		//Banner下方模块-网上展馆
		List<Map<String, Object>> wszgList = onlineService.GetFriendLink(273);
						// 处理超长文字
		for (int j = 0; j < wszgList.size(); j++) {
			String name = wszgList.get(j).get("title").toString();
			if (name.length() > 7) {
				wszgList.get(j).put("shorttitle",name.substring(0, 7) + "...");
			} else {
				wszgList.get(j).put("shorttitle", name);
			}
		}
		model.addAttribute("wszgLists", wszgList);
		
		//Banner下方模块-供采对接
		List<Map<String, Object>> gcdjList = onlineService.GetFriendLink(274);
		// 处理超长文字
		for (int j = 0; j < gcdjList.size(); j++) {
			String name = gcdjList.get(j).get("title").toString();
			if (name.length() > 7) {
				gcdjList.get(j).put("shorttitle",name.substring(0, 7) + "...");
			} else {
				gcdjList.get(j).put("shorttitle", name);
			}
		}
		model.addAttribute("gcdjLists", gcdjList);
		//*********************************************
		
		model.addAttribute("toppictureLists", toppictureList);
		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "index");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("index", language);
	}

	/**
	 * 展区页
	 */
	@RequestMapping(value = { "/{language}/exhibitionarea-{exhibitionid}.html" })
	public String exhibitionarea(@PathVariable String language,
			@PathVariable String exhibitionid, Model model,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("dl", 0);
		if(user!=null){
			model.addAttribute("dl", 1);
		}

		model.addAttribute("exhibitionid", exhibitionid);
		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("exhibitionarea", language);
	}

	/**
	 * 供采对接
	 */
	@RequestMapping(value = { "/{language}/spdocking-{menuId}-{keywords}-{page}.html" })
	public String spdocking(@PathVariable String language,@PathVariable Integer menuId,@PathVariable String keywords,@PathVariable Integer page, Model model,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("dl", 0);
		if(user!=null){
			model.addAttribute("dl", 1);
		}
		String sessionId = exhibitionInfo.get("sessionId").toString();
		// String memberTypeToken = getMemberTypeToken(member.getMemberType());
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("menuid", menuId);
		params.put("keywords", keywords);
		model.addAttribute("params", params);
		int limit  = 10;
		model.addAttribute("pageIndex", page);
		PageModel pageModel = new PageModel();
		pageModel.setLimit(limit);
		pageModel.setPage(page);
		if(menuId>0){
			pageModel.setMenuId(menuId);
		}
		pageModel.setKeywords(keywords);
		pageModel.setSession(exhibitionInfo.get("sessionId").toString());

		// model.addAttribute("pageIndex", page);
		List<Map<String,Object>> gclist = onlineService.GetGongCaiList(pageModel);
		for(int j=0;j<gclist.size();j++){
			String addtime = gclist.get(j).get("addtime").toString();
			gclist.get(j).put("addtime", StringUtil.GetTimeDifference(addtime));
		}
		int iCount = onlineService.GetGongCaiListCount(pageModel);

		// 2.设置列表信息
		model.addAttribute("gclists", gclist);
		model.addAttribute("gclistsCount", gclist.size());

		int pageSize = iCount / limit;
		if (iCount % limit != 0) {
			pageSize++;
		}
		// 3.添加页码
		model.addAttribute("paging", (new PaginationUtil(page,
				pageSize).getPageList()));

		model.addAttribute("path", "");
		
		
		PageModel pageModel1 = new PageModel();
		pageModel1.setLimit(5);
		pageModel1.setPage(1);
		pageModel1.setSession(sessionId);
		List<ProductModel> ProductList = onlineService
				.getProductList(pageModel1);
		model.addAttribute("ProductLists", ProductList);

		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "spdocking");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("spdocking", language);
	}

	@RequestMapping(value = { "/{language}/GetOnlineInquiry-{keywords}-{menuid}" })
	@ResponseBody
	public R GetOnlineInquiry(@PathVariable String language,
			@PathVariable String keywords, @PathVariable Integer menuid) {
		setCommonContent();
		String sessionId = exhibitionInfo.get("sessionId").toString();
		// String memberTypeToken = getMemberTypeToken(member.getMemberType());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", sessionId);
		if (menuid != 0) {
			map.put("menuid", menuid);
		}
		map.put("keywords", keywords);
		List<Map<String, Object>> CompanyList = onlineService
				.GetOnlineInquiry(map);

		return R.ok().put("data", CompanyList).put("code", WConst.SUCCESS)
				.put("msg", WConst.QUERYSUCCESS).put("count", CompanyList.size());
	}

	/**
	 * 在线活动
	 * @throws ParseException 
	 */
	@RequestMapping(value = { "/{language}/onlineactivity.html" })
	public String onlineactivity(@PathVariable String language, Model model,
			HttpSession session, HttpServletRequest request) throws ParseException {
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("dl", 0);
		if(user!=null){
			model.addAttribute("dl", 1);
		}
		
		List<Map<String, Object>> huifanglists = onlineService.GetFriendLink(265);
		List<Map<String, Object>> hfs = huifanglists.stream().limit(1000).collect(Collectors.toList());
		for(int j=0;j<hfs.size();j++){
			String title=hfs.get(j).get("title").toString();
			String shortTitle = title;
			if(title.length()>13){
				shortTitle = shortTitle.substring(0,13)+"...";
			}
			hfs.get(j).put("shorttitle", shortTitle);
		}
		
		model.addAttribute("hfs", hfs);
		
		List<Map<String, Object>> zhibolists = onlineService.GetCompanyLink(266);
		List<Map<String, Object>> zbs = zhibolists.stream().limit(14).collect(Collectors.toList());
		for(int j=0;j<zbs.size();j++){
			SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date starttime = formater1.parse(zbs.get(j).get("starttime").toString());
			Date endtime = formater1.parse(zbs.get(j).get("endtime").toString());
			Date nowtime = formater1.parse(zbs.get(j).get("nowtime").toString());
			
			Integer status = -1;
			if(nowtime.compareTo(starttime)<0){//未开始
				status=0;
			}
			if(nowtime.compareTo(starttime)>0 && nowtime.compareTo(endtime)<0){//时间区间内
				status=1;
			}
			if(nowtime.compareTo(endtime)>0){//已结束
				status=2;
			}
			zbs.get(j).put("status", status);
		}
		model.addAttribute("zbs", zbs);
		model.addAttribute("zbsCount",zbs.size());
		
		List<Map<String,Object>> list = onlineService.getActivityList(exhibitionInfo);
		String firstDate="";
		for(int j=0;j<list.size();j++){
			if(j==0){
				firstDate = list.get(j).get("date").toString();
			}
			String riqi = list.get(j).get("date").toString();
			list.get(j).put("shortdate", riqi.substring(riqi.length()-2));
		}
		model.addAttribute("topzxs", list);
		//赋值为当前日期
		SimpleDateFormat formaterx = new SimpleDateFormat("yyyy-MM-dd");		
		firstDate = formaterx.format(new Date());
		if(firstDate.compareTo("2020-10-16")<0){
			firstDate="2020-10-16";
		}
		model.addAttribute("firstDate", firstDate);
		//开幕式
		Basic basic = basicDao.getBasicByMenuId(268);
		
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date starttime = formater1.parse(basic.getStarttime());
		Date endtime = formater1.parse(basic.getEndtime());
		Date nowtime = formater1.parse(formater1.format(new Date()));		
		Integer status = -1;
		if(nowtime.compareTo(starttime)<0){//未开始
			status=0;
		}
		if(nowtime.compareTo(starttime)>0 && nowtime.compareTo(endtime)<0){//时间区间内
			status=1;
		}
		if(nowtime.compareTo(endtime)>0){//已结束
			status=2;
		}
		basic.setStatus(status);
		model.addAttribute("kms", basic);

		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "onlineactivity");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("onlineactivity", language);
	}
	
	@RequestMapping(value = { "/{language}/GetActivity/{strDate}" })
	@ResponseBody
	public R GetActivity(@PathVariable String language,	@PathVariable String strDate) {
		setCommonContent();
		//String sessionId = exhibitionInfo.get("sessionId").toString();
		List<Map<String,Object>> resMap = new ArrayList<Map<String,Object>>();
		exhibitionInfo.put("riqi", strDate);
		List<Map<String,Object>> lit = onlineService.GetActivityTime(exhibitionInfo);
		for(int j=0;j<lit.size();j++){
			Map<String,Object> item = new HashMap<String,Object>();
			item.put("startTime", lit.get(j).get("starttime"));
			item.put("endTime", lit.get(j).get("endtime"));	
			List<Map<String,Object>> list = onlineService.GetActivityDetails(lit.get(j));
			item.put("activities", list);
			resMap.add(item);
		}

		return R.ok().put("data", resMap).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}
	
	@RequestMapping(value = { "/{language}/GetTopActivity/{strDate}" })
	@ResponseBody
	public R GetTopActivity(@PathVariable String language, @PathVariable String strDate) {
		setCommonContent();
		//String sessionId = exhibitionInfo.get("sessionId").toString();		
		exhibitionInfo.put("riqi", strDate);
		List<Map<String,Object>> lit = onlineService.GetTopActivity(exhibitionInfo);	
		return R.ok().put("data", lit).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}
	

	/**
	 * 网上展馆
	 */
	@RequestMapping(value = { "/{language}/onlinepavilion.html" })
	public String onlinepavilion(@PathVariable String language, Model model,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("dl", 0);
		if(user!=null){
			model.addAttribute("dl", 1);
		}

		List<Map<String, Object>> pictureList = onlineService
				.GetFriendLink(270);
		// 处理超长文字
		for (int j = 0; j < pictureList.size(); j++) {
			String name = pictureList.get(j).get("title").toString();
			if (name.length() > 15) {
				pictureList.get(j).put("shorttitle",
						name.substring(0, 15) + "...");
			} else {
				pictureList.get(j).put("shorttitle", name);
			}
		}
		model.addAttribute("pictureListMs", pictureList);

		// 获取360全景地址
		Basic basic = basicDao.getBasicByMenuId(267);
		model.addAttribute("url360", basic.getBasicIntro());

		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "onlinepavilion");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("onlinepavilion", language);
	}

	/**
	 * 联系我们
	 */
	@RequestMapping(value = { "/{language}/contact.html" })
	public String concat(@PathVariable String language, Model model,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("dl", 0);
		if(user!=null){
			model.addAttribute("dl", 1);
		}
		Map<String, Object> basic = onlineService.GetBacicInfo(261);
		model.addAttribute("basic", basic);
		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "contact");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("contact", language);
	}

	/**
	 * 获取展区信息
	 * 
	 * @param language
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/exhibitioninfo" })
	@ResponseBody
	public ResultModel exhibitioninfo(@PathVariable String language) {
		setCommonContent();
		String sessionId = exhibitionInfo.get("sessionId").toString();
		// String memberTypeToken = getMemberTypeToken(member.getMemberType());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", sessionId);
		map.put("type", "");
		List<ExhibitionInfoModel> ExhibitionInfoList = onlineService
				.GetExhibitionInfo(map);

		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS,
				ExhibitionInfoList, ExhibitionInfoList.size());
	}

	/**
	 * 随机优质展商
	 * 
	 * @param language
	 * @param shuliang
	 * @return
	 */
	@RequestMapping(value = { "/{language}/GetQualityExhibitors/{itop}/{exhibitionid}" })
	@ResponseBody
	public R GetQualityExhibitors(@PathVariable String language,
			@PathVariable String itop, @PathVariable Integer exhibitionid) {
		setCommonContent();
		String sessionId = exhibitionInfo.get("sessionId").toString();
		// String memberTypeToken = getMemberTypeToken(member.getMemberType());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", sessionId);
		if (exhibitionid != 0) {
			map.put("exhibitionid", exhibitionid);
		}
		map.put("itop", itop);
		List<Map<String, Object>> CompanyList = onlineService
				.GetCompanyList(map);

		return R.ok().put("data", CompanyList).put("code", WConst.SUCCESS)
				.put("msg", WConst.QUERYSUCCESS);
	}

	/**
	 * 收藏展品、展商
	 * 
	 * @param language
	 * @return
	 */
	@RequestMapping(value = { "/{language}/Favorites/{companyid}/{type}/{act}" })
	@ResponseBody
	public R Favorites(@PathVariable String language,
			@PathVariable Integer companyid, @PathVariable Integer type,
			@PathVariable Integer act,	HttpSession session, HttpServletRequest request) {
		setCommonContent();
		
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		if(user==null){
			return R.error().put("code", WConst.ERROR).put("msg", "请先登录");
		}
		
		String sessionId = exhibitionInfo.get("sessionId").toString();
		// String memberTypeToken = getMemberTypeToken(member.getMemberType());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", sessionId);
		map.put("primaryid", companyid);
		map.put("act", act);
		map.put("memberid", user.getUid());
		if (type == 0) {// 收藏
			onlineService.addFavorites(map);
		} else {// 取消收藏
			onlineService.delFavorites(map);
		}

		return R.ok().put("code", WConst.SUCCESS)
				.put("msg", WConst.QUERYSUCCESS);
	}

	/**
	 * 点赞
	 * 
	 * @param language
	 * @return
	 */
	@RequestMapping(value = { "/{language}/Awesome/{companyid}/{type}/{act}" })
	@ResponseBody
	public R Awesome(@PathVariable String language,
			@PathVariable Integer companyid, @PathVariable Integer type,
			@PathVariable Integer act, HttpSession session, HttpServletRequest request) {
		setCommonContent();
		
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		if(user==null){
			return R.error().put("code", WConst.ERROR).put("msg", "请先登录");
		}
		
		String sessionId = exhibitionInfo.get("sessionId").toString();
		// String memberTypeToken = getMemberTypeToken(member.getMemberType());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", sessionId);
		map.put("primaryid", companyid);
		map.put("act", act);
		map.put("memberid", user.getUid());
		if (type == 0) {// 点赞
			onlineService.addAwesome(map);
			// 点击率加1
			if (act == 0) {
				onlineService.updateCompany(map);
			} else {
				onlineService.updateProduct(map);
			}
		} else {// 取消点赞
			onlineService.delAwesome(map);
		}

		return R.ok().put("code", WConst.SUCCESS)
				.put("msg", WConst.QUERYSUCCESS);
	}

	/**
	 * 随机优质展品
	 * 
	 * @param language
	 * @param shuliang
	 * @return
	 */
	@RequestMapping(value = { "/{language}/GetQualityProducts/{itop}/{exhibitionid}/{companyid}" })
	@ResponseBody
	public R GetQualityProducts(@PathVariable String language,
			@PathVariable String itop, @PathVariable Integer exhibitionid,
			@PathVariable Integer companyid) {
		setCommonContent();
		String sessionId = exhibitionInfo.get("sessionId").toString();
		// String memberTypeToken = getMemberTypeToken(member.getMemberType());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", sessionId);
		if (exhibitionid != 0) {
			map.put("exhibitionid", exhibitionid);
		}
		if (companyid != 0) {
			map.put("companyid", companyid);
		}
		map.put("itop", itop);
		List<Map<String, Object>> ProductList = onlineService
				.GetProductList(map);

		return R.ok().put("data", ProductList).put("code", WConst.SUCCESS)
				.put("msg", WConst.QUERYSUCCESS).put("count", ProductList.size());
	}

	/**
	 * 展商列表
	 */
	@RequestMapping(value = { "/{language}/exhibitorlist-{keywords}-{country}-{province}-{industry}-{exhibition}-{page}-{limit}-{companyid}.html" })
	public String exhibitorlist(@PathVariable String language,
			@PathVariable String keywords, @PathVariable String country,
			@PathVariable String province, @PathVariable String industry,
			@PathVariable String exhibition, @PathVariable Integer page,
			@PathVariable String limit, @PathVariable String companyid,
			Model model, HttpSession session, HttpServletRequest request) {
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("dl", 0);
		if(user!=null){
			model.addAttribute("dl", 1);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keywords", keywords);
		params.put("country", country);
		params.put("province", province);
		params.put("industry", !industry.equals("") ? Integer.valueOf(industry)
				: 0);
		params.put("exhibition", exhibition);
		params.put("companyid", companyid);
		params.put("limit", limit);
		model.addAttribute("params", params);

		model.addAttribute("pageIndex", page);
		PageModel pageModel = new PageModel();
		pageModel.setLimit(Integer.valueOf(limit));
		pageModel.setPage(Integer.valueOf(page));
		pageModel.setCountry(country);
		pageModel.setProvince(province);
		pageModel.setIndustry(industry);
		pageModel.setExhibition(exhibition);
		if (!companyid.equals("")) {
			pageModel.setCompanyid(companyid);
		}
		pageModel.setKeywords(keywords);

		pageModel.setSession(exhibitionInfo.get("sessionId").toString());

		// model.addAttribute("pageIndex", page);
		List<ExhibitorModel> exhibitionList = onlineService
				.getExhibitiorList(pageModel);
		int iCount = onlineService.getExhibitiorListCount(pageModel);
		String ids = "";

		for (int k = 0; k < exhibitionList.size(); k++) {
			if (k == 0) {
				ids = exhibitionList.get(k).getCompanyid();
			} else {
				ids = ids + "," + exhibitionList.get(k).getCompanyid();
			}
		}
		model.addAttribute("ids", ids);
		// 2.设置列表信息
		model.addAttribute("ExhibitionList", exhibitionList);

		int pageSize = iCount / Integer.valueOf(limit);
		if (iCount % Integer.valueOf(limit) != 0) {
			pageSize++;
		}
		// 3.添加页码
		model.addAttribute("paging", (new PaginationUtil(Integer.valueOf(page),
				pageSize).getPageList()));
		model.addAttribute("path", "");

		// 行业、展区
		Map<String, Object> map = new HashMap<>();
		map.put("index", 0);
		map.put("size", 10000);
		List<SysIndustry> industries = sysIndustryService.list(map);
		model.addAttribute("industries", industries);
		map.put("session", exhibitionInfo.get("sessionId"));
		List<ExhibitionInfoModel> exhibitionLists = onlineService
				.GetExhibitionInfo(map);
		model.addAttribute("exhibitionLists", exhibitionLists);

		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("exhibitorlist", language);
	}

	/**
	 * 展商详细页
	 */
	@RequestMapping(value = { "/{language}/exhibitorpage-{companyid}.html" })
	public String exhibitorpage(@PathVariable String language,
			@PathVariable Integer companyid, Model model, HttpSession session,
			HttpServletRequest request) {
		setCommonContent();
		
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("dl", 0);
		int iMemberid=0;
		if(user!=null){
			model.addAttribute("dl", 1);
			iMemberid=user.getUid();
		}
		
		EbsCompanyinfo companyinfos = ebsCompanyinfoService.findById(companyid);
		model.addAttribute("companyinfo", companyinfos);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String comnpanyPictures = companyinfos.getCompanypictures();
		comnpanyPictures = comnpanyPictures.replace("[", "").replace("]", "")
				.replace("\"", "");
		if (!comnpanyPictures.equals("")) {
			String[] strArgs = comnpanyPictures.split(",");
			for (int j = 0; j < strArgs.length; j++) {
				Map<String, Object> map = new HashMap<>();
				map.put("picture", strArgs[j]);
				list.add(map);
			}
		}
		model.addAttribute("pictures", list);
		model.addAttribute("pictureCount", list.size());

		// 收藏
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", exhibitionInfo.get("sessionId"));
		map.put("primaryid", companyid);
		map.put("act", 0);
		map.put("memberid", iMemberid);
		int isSC = onlineService.GetSCCount(map);
		model.addAttribute("shoucang", isSC);

		// 点赞
		Map<String, Object> mapx = new HashMap<String, Object>();
		mapx.put("session", exhibitionInfo.get("sessionId"));
		mapx.put("primaryid", companyid);
		mapx.put("act", 0);
		mapx.put("memberid", iMemberid);
		int isDZ = onlineService.GetDZCount(mapx);
		model.addAttribute("dianzan", isDZ);
		
		//直播情况
		Map<String,Object> zhibo = onlineService.GetZhiBoQingKuang(companyid);
		if(zhibo==null){
			zhibo = new HashMap<String,Object>();
			zhibo.put("zhuangtai", 0);
			zhibo.put("faddress", "javascript:void(0)");
		}
		model.addAttribute("zhibo", zhibo);
		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("exhibitorpage", language);
	}

	/**
	 * 展品列表
	 */
	@RequestMapping(value = { "/{language}/products-{keywords}-{country}-{province}-{industry}-{exhibition}-{page}-{limit}-{companyid}.html" })
	public String products(@PathVariable String language,
			@PathVariable String keywords, @PathVariable String country,
			@PathVariable String province, @PathVariable String industry,
			@PathVariable String exhibition, @PathVariable Integer page,
			@PathVariable String limit, @PathVariable String companyid,
			Model model, HttpSession session, HttpServletRequest request) {
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("dl", 0);
		if(user!=null){
			model.addAttribute("dl", 1);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keywords", keywords);
		params.put("country", country);
		params.put("province", province);
		params.put("industry", !industry.equals("") ? Integer.valueOf(industry)
				: 0);
		params.put("exhibition", exhibition);
		params.put("companyid", companyid);
		params.put("limit", limit);
		model.addAttribute("params", params);

		model.addAttribute("pageIndex", page);
		PageModel pageModel = new PageModel();
		pageModel.setLimit(Integer.valueOf(limit));
		pageModel.setPage(Integer.valueOf(page));
		pageModel.setCountry(country);
		pageModel.setProvince(province);
		pageModel.setIndustry(industry);
		pageModel.setExhibition(exhibition);
		pageModel.setCompanyid(companyid);
		pageModel.setKeywords(keywords);
		pageModel.setSession(exhibitionInfo.get("sessionId").toString());

		// model.addAttribute("pageIndex", page);
		List<ProductModel> ProductList = onlineService
				.getProductList(pageModel);
		int iCount = onlineService.getProductListCount(pageModel);

		// 2.设置列表信息
		model.addAttribute("ProductLists", ProductList);

		int pageSize = iCount / Integer.valueOf(limit);
		if (iCount % Integer.valueOf(limit) != 0) {
			pageSize++;
		}
		// 3.添加页码
		model.addAttribute("paging", (new PaginationUtil(Integer.valueOf(page),
				pageSize).getPageList()));

		model.addAttribute("path", "");

		Map<String, Object> map = new HashMap<>();
		map.put("index", 0);
		map.put("size", 10000);
		List<SysIndustry> industries = sysIndustryService.list(map);
		model.addAttribute("industries", industries);
		map.put("session", exhibitionInfo.get("sessionId"));
		List<ExhibitionInfoModel> exhibitionLists = onlineService
				.GetExhibitionInfo(map);
		model.addAttribute("exhibitionLists", exhibitionLists);

		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("products", language);
	}

	/**
	 * 展商主页
	 */
	@RequestMapping(value = { "/{language}/exhibitorproducts-{keywords}-{companyid}-{menuid}-{page}-{limit}.html" })
	public String exhibitorproducts(@PathVariable String language,
			@PathVariable String keywords, @PathVariable Integer companyid,
			@PathVariable String menuid, @PathVariable Integer page,
			@PathVariable String limit, Model model, HttpSession session,
			HttpServletRequest request) {
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("dl", 0);
		if(user!=null){
			model.addAttribute("dl", 1);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keywords", keywords);
		params.put("menuid", !menuid.equals("") ? Integer.valueOf(menuid) : 0);
		params.put("companyid", companyid);
		params.put("limit", limit);
		model.addAttribute("params", params);

		model.addAttribute("pageIndex", page);
		PageModel pageModel = new PageModel();
		pageModel.setLimit(Integer.valueOf(limit));
		pageModel.setPage(Integer.valueOf(page));
		pageModel.setCompanyid(companyid.toString());
		if (!keywords.equals("")) {
			pageModel.setKeywords(keywords);
		}
		if (!menuid.equals("")) {
			pageModel.setMenuId(Integer.valueOf(menuid));
		}
		pageModel.setSession(exhibitionInfo.get("sessionId").toString());

		// model.addAttribute("pageIndex", page);
		List<ProductModel> ProductList = onlineService
				.getProductList(pageModel);
		int iCount = onlineService.getProductListCount(pageModel);

		// 2.设置列表信息
		model.addAttribute("ProductLists", ProductList);
		model.addAttribute("ProductListsCount", ProductList.size());
		int pageSize = iCount / Integer.valueOf(limit);
		if (iCount % Integer.valueOf(limit) != 0) {
			pageSize++;
		}
		// 3.添加页码
		model.addAttribute("paging", (new PaginationUtil(Integer.valueOf(page),
				pageSize).getPageList()));
		model.addAttribute("path", "");

		// 展商信息
		EbsCompanyinfo companyinfos = ebsCompanyinfoService.findById(companyid);
		model.addAttribute("companyinfo", companyinfos);
		// 产品分类
		exhibitionInfo.put("companyid", companyid);
		List<Map<String, Object>> productmenus = onlineService
				.GetProductMenus(exhibitionInfo);
		model.addAttribute("productmenus", productmenus);
		
		//直播情况
		Map<String,Object> zhibo = onlineService.GetZhiBoQingKuang(companyid);
		if(zhibo==null){
			zhibo = new HashMap<String,Object>();
			zhibo.put("zhuangtai", 0);
			zhibo.put("faddress", "javascript:void(0)");
		}
		model.addAttribute("zhibo", zhibo);

		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("exhibitorproducts", language);
	}

	/**
	 * 展品详细页
	 */
	@RequestMapping(value = { "/{language}/productinfo-{productid}.html" })
	public String productinfo(@PathVariable String language,
			@PathVariable Integer productid, Model model, HttpSession session,
			HttpServletRequest request) {
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("dl", 0);
		int iMemberid=0;
		if(user!=null){
			model.addAttribute("dl", 1);
			iMemberid=user.getUid();
		}
		// 产品信息
		WebProduct webProduct = webProductService.findById(productid);
		model.addAttribute("productinfo", webProduct);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String productPictures = webProduct.getProductPictures().replace("null,null,null,null,null,null", "");
		productPictures = productPictures.replace("[", "").replace("]", "")
				.replace("\"", "");
		if (!productPictures.equals("")) {
			String[] strArgs = productPictures.split(",");
			for (int j = 0; j < strArgs.length; j++) {
				Map<String, Object> map = new HashMap<>();
				map.put("picture", strArgs[j]);
				list.add(map);
			}
		}
		else{
			Map<String, Object> map = new HashMap<>();
			map.put("picture", webProduct.getProductPicture());
			list.add(map);
		}
		model.addAttribute("pictures", list);
		Integer companyid = webProduct.getCompanyId();
		// 展商信息
		EbsCompanyinfo companyinfos = ebsCompanyinfoService.findById(companyid);
		model.addAttribute("companyinfo", companyinfos);

		// 收藏
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", exhibitionInfo.get("sessionId"));
		map.put("primaryid", productid);
		map.put("act", 1);
		map.put("memberid", iMemberid);
		int isSC = onlineService.GetSCCount(map);
		model.addAttribute("shoucang", isSC);

		// 点赞
		Map<String, Object> mapx = new HashMap<String, Object>();
		mapx.put("session", exhibitionInfo.get("sessionId"));
		mapx.put("primaryid", productid);
		mapx.put("act", 1);
		mapx.put("memberid", iMemberid);
		int isDZ = onlineService.GetDZCount(mapx);
		model.addAttribute("dianzan", isDZ);
		mapx.put("memberid", 0);
		
		//直播情况
				Map<String,Object> zhibo = onlineService.GetZhiBoQingKuang(companyid);
				if(zhibo==null){
					zhibo = new HashMap<String,Object>();
					zhibo.put("zhuangtai", 0);
					zhibo.put("faddress", "javascript:void(0)");
				}
				model.addAttribute("zhibo", zhibo);

		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("productinfo", language);
	}

	/**
	 * 在线巡馆详情
	 */
	@RequestMapping(value = { "/{language}/pavilioninfo-{fid}.html" })
	public String pavilioninfo(@PathVariable String language,
			@PathVariable Integer fid, Model model, HttpSession session,
			HttpServletRequest request) {
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("dl", 0);
		if(user!=null){
			model.addAttribute("dl", 1);
		}
		Map<String, Object> flink = firendlinkService.GetFirendLink(fid);
		model.addAttribute("flink", flink);
		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("pavilioninfo", language);
	}
	
	/*
	 * 在线活动详细
	 */
	@RequestMapping(value = { "/{language}/activityinfo-{id}.html" })
	public String activityinfo(@PathVariable String language,
			@PathVariable Integer id, Model model, HttpSession session,
			HttpServletRequest request) {
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		model.addAttribute("userinfo", user);
		model.addAttribute("dl", 0);
		if(user!=null){
			model.addAttribute("dl", 1);
		}
		OnlineActivityDetails oad = onlineActivityDetailsService.findById(id);
		model.addAttribute("info", oad);
		model.addAttribute("exhibitiontime", exhibitionInfo.get("startenddate"));
		model.addAttribute("pageName", "");
		Basic basicfoot = basicDao.getBasicByMenuId(269);
		model.addAttribute("foot", basicfoot);
		return ReturnTemplatePath("activityinfo", language);
	}

	/**
	 * 在线询盘提交
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/{language}/addSpdocking")
	@ResponseBody
	public R addSpdocking(@PathVariable String language,
			@RequestBody Map<String, Object> map, HttpSession session, HttpServletRequest request) {
		setCommonContent();
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		if(user==null){
			return R.error().put("code", WConst.ERROR).put("msg", "请先登录");
		}
		OnlineInquiry oi = new OnlineInquiry();
		int iMemberid = user.getUid();
		oi.setMemberid(iMemberid);
		oi.setTitle(map.get("title").toString());
		oi.setProductmenuid(Integer.valueOf(map.get("productmenuid").toString()));
		oi.setQuantity(map.get("quantity").toString());
		oi.setQuantityunit(Integer.valueOf(map.get("quantityunit").toString()));
		oi.setTel(map.get("tel").toString());
		oi.setContent(map.get("content").toString());
		oi.setSession(Integer.valueOf(exhibitionInfo.get("sessionId").toString()));
		onlineInquiryService.save(oi);
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
	}

	/**
	 * 意向订单及预约洽谈提交
	 * 
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/{language}/addIntentionOrder")
	@ResponseBody
	public R addIntentionOrder(@PathVariable String language,
			@RequestBody Map<String, Object> map, HttpSession session, HttpServletRequest request) throws Exception {
		setCommonContent();
		
		WechatUser user = (WechatUser) session.getAttribute("wechatUser");
		if(user==null){
			return R.error().put("code", WConst.ERROR).put("msg", "请先登录");
		}
		
		OnlineNegotiate oi = new OnlineNegotiate();
		oi.setMemberid(user.getUid());
		oi.setName(map.get("name").toString());
		oi.setTel(map.get("tel").toString());
		oi.setContent(map.get("content").toString());
		oi.setSession(Integer.valueOf(exhibitionInfo.get("sessionId")
				.toString()));
		oi.setAct(Integer.valueOf(map.get("act").toString()));
		oi.setCompanyid(Integer.valueOf(map.get("companyid").toString()));
		oi.setProductid(Integer.valueOf(map.get("productid").toString()));

		List<OnlineNegotiate> list = onlineNegotiateService.findByMap(map);
		if (list.size() > 0) {
			return R.ok().put("code", WConst.ERROR).put("msg", "相同的内容请不要重复提交。");
		}
		onlineNegotiateService.save(oi);
		//保存成功后发送短信及微信服务消息
		int iCompanyid=oi.getCompanyid();
		
		Map<String,Object> mapx = new HashMap<String,Object>();
        mapx.put("session", exhibitionInfo.get("sessionId"));
        mapx.put("companyid", iCompanyid);
        //验证企业是否认证
        WechatUser userx = wechatUserDAO.getUserInfoByCompanyid(iCompanyid);		
		
		onlineService.SendSMSAndMessage(oi,userx);
		
		
		return R.ok().put("code", WConst.SUCCESS).put("msg", "提交成功");
	}

	/*** 设置公共部分 **/

	/**
	 * 设置公共参数
	 * 
	 * @param model
	 */
	public void setCommonContent(Model model) {
		// 1.设置当前届次id
		setCommonContent();
		model.addAttribute("exhibitionSessionId",
				exhibitionInfo.get("sessionId"));
		model.addAttribute("exhibitionInfo", exhibitionInfo);
	}

	public void setCommonContent() {
		exhibitionInfo = sessionService.getExhibitionInfo(request);
	}

	

	private Integer getMemberTypeId(String type) {
		Integer typeId = 0;
		switch (type) {// 会员类型，
		case "exhibitor":// 2：零散展商，
			typeId = Member.MEMBER_TYPE_EXHIBITOR_CODE;
			break;
		case "delegation":// 0：交易团，
			typeId = Member.MEMBER_TYPE_TRADE_CODE;
			break;
		case "decorator":// 3：布撤展商，
			typeId = Member.MEMBER_TYPE_DECORATOR_CODE;
			break;
		case "foreign":// 4：外宾，
			typeId = Member.MEMBER_TYPE_FOREIGN_CODE;
			break;
		case "reporter":// 1：记者，
			typeId = Member.MEMBER_TYPE_REPORT_CODE;
			break;
		case "purchaser":// 5：采购商，
			typeId = Member.MEMBER_TYPE_PURCHASER_CODE;
			break;

		}
		return typeId;
	}

	private String getMemberTypeToken(Integer type) {
		String typeToken = "";
		switch (type) {// 会员类型，
		case Member.MEMBER_TYPE_EXHIBITOR_CODE:// 2：零散展商，
			typeToken = "exhibitor";
			break;
		case Member.MEMBER_TYPE_TRADE_CODE:// 0：交易团，
			typeToken = "delegation";
			break;
		case Member.MEMBER_TYPE_DECORATOR_CODE:// 3：布撤展商，
			typeToken = "decorator";
			break;
		case Member.MEMBER_TYPE_FOREIGN_CODE:// 4：外宾，
			typeToken = "foreign";
			break;
		case Member.MEMBER_TYPE_REPORT_CODE:// 1：记者，
			typeToken = "reporter";
			break;
		case Member.MEMBER_TYPE_PURCHASER_CODE:// 5：采购商，
			typeToken = "purchaser";
			break;
		case Member.MEMBER_TYPE_AUDIENCE_CODE:// 6：观众，
			typeToken = "audience";
			break;
		case Member.MEMBER_TYPE_EXHIBITOR_ONLINE_CODE:// 7：线上，
			typeToken = "online";
			break;

		}
		return typeToken;
	}

	private String getMemberTypeName(String type, String language) {
		String typeName = "";
		switch (type) {
		case "exhibitor":
			switch (language) {
			case "cn":
				typeName = "零散参展商";
				break;
			case "en":
				typeName = "Scattered exhibitors";
				break;
			case "ru":
				typeName = "расходящийся участник";
				break;
			case "jp":
				typeName = "零細出展者";
				break;
			case "kr":
				typeName = "영세 상인";
				break;
			}
			break;
		case "delegation":
			switch (language) {
			case "cn":
				typeName = "交易团/省直厅局";
				break;
			case "en":
				typeName = "business delegation";
				break;
			case "ru":
				typeName = "торговая группа";
				break;
			case "jp":
				typeName = "交易団";
				break;
			case "kr":
				typeName = "교역 단";
				break;
			}
			break;
		case "decorator":
			switch (language) {
			case "cn":
				typeName = "搭建商";
				break;
			case "en":
				typeName = "Builder";
				break;
			case "ru":
				typeName = "строитель";
				break;
			case "jp":
				typeName = "建て商";
				break;
			case "kr":
				typeName = "교역 단";
				break;
			}
			break;
		case "foreign":
			switch (language) {
			case "cn":
				typeName = "外宾";
				break;
			case "en":
				typeName = "Foreign guests";
				break;
			case "ru":
				typeName = "иностранный гость ";
				break;
			case "jp":
				typeName = "外国のお客さん";
				break;
			case "kr":
				typeName = "외빈";
				break;
			}
			break;
		case "reporter":
			switch (language) {
			case "cn":
				typeName = "记者";
				break;
			case "en":
				typeName = "reporter";
				break;
			case "ru":
				typeName = "журналист";
				break;
			case "jp":
				typeName = "記者";
				break;
			case "kr":
				typeName = "기자";
				break;
			}
			break;
		case "purchaser":
			switch (language) {
			case "cn":
				typeName = "采购商";
				break;
			case "en":
				typeName = "Purchasers";
				break;
			case "ru":
				typeName = "заготовитель";
				break;
			case "jp":
				typeName = "買い付け商";
				break;
			case "kr":
				typeName = "구입 상";
				break;
			}
			break;

		}
		return typeName;
	}

}
