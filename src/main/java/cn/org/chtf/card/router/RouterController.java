package cn.org.chtf.card.router;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hutool.core.date.DateTime;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsCompanyinfoMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsPersonnelcardMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsVehiclecardMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.model.EbsPersonnelcard;
import cn.org.chtf.card.manage.Exhibitors.model.EbsVehiclecard;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothApplyService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsCompanyinfoService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsPersonnelcardService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsShowroomService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsTradinggroupboothallocationService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsVehiclecardService;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.MakeEvidence.service.CmCertificateTypeService;
import cn.org.chtf.card.manage.PreviousInformation.dao.PimAgentMapper;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgent;
import cn.org.chtf.card.manage.PreviousInformation.service.PimAgentService;
import cn.org.chtf.card.manage.article.service.ArticleService;
import cn.org.chtf.card.manage.basic.dao.BasicMapper;
import cn.org.chtf.card.manage.basic.pojo.Basic;
import cn.org.chtf.card.manage.basic.service.BasicService;
import cn.org.chtf.card.manage.basicsetting.service.BasicSettingService;
import cn.org.chtf.card.manage.common.dao.CommonMapper;
import cn.org.chtf.card.manage.common.service.CommonService;
import cn.org.chtf.card.manage.friendlink.dao.FriendlinkMapper;
import cn.org.chtf.card.manage.friendlink.pojo.Friendlink;
import cn.org.chtf.card.manage.friendlink.service.FriendlinkService;
import cn.org.chtf.card.manage.member.dao.MemberDAO;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.member.service.MemberService;
import cn.org.chtf.card.manage.membersession.pojo.MemberSession;
import cn.org.chtf.card.manage.membersession.service.MemberSessionService;
import cn.org.chtf.card.manage.menu.service.MenuService;
import cn.org.chtf.card.manage.online.model.ExhibitionInfoModel;
import cn.org.chtf.card.manage.online.model.ExhibitorModel;
import cn.org.chtf.card.manage.online.model.NewsParams;
import cn.org.chtf.card.manage.online.model.OnlineActivityDetails;
import cn.org.chtf.card.manage.online.model.OnlineApplyMeeting;
import cn.org.chtf.card.manage.online.model.OnlineInquiry;
import cn.org.chtf.card.manage.online.model.OnlineNegotiate;
import cn.org.chtf.card.manage.online.model.OnlineReservationDetails;
import cn.org.chtf.card.manage.online.model.ProductModel;
import cn.org.chtf.card.manage.online.model.ReservationModel;
import cn.org.chtf.card.manage.online.service.OnlineActivityDetailsService;
import cn.org.chtf.card.manage.online.service.OnlineApplyMeetingService;
import cn.org.chtf.card.manage.online.service.OnlineInquiryService;
import cn.org.chtf.card.manage.online.service.OnlineNegotiateService;
import cn.org.chtf.card.manage.online.service.OnlineNewsService;
import cn.org.chtf.card.manage.online.service.OnlineReservationDetailsService;
import cn.org.chtf.card.manage.online.service.OnlineService;
import cn.org.chtf.card.manage.product.model.WebProduct;
import cn.org.chtf.card.manage.product.service.WebProductService;
import cn.org.chtf.card.manage.system.model.SysCompanyType;
import cn.org.chtf.card.manage.system.model.SysCountryArea;
import cn.org.chtf.card.manage.system.model.SysIndustry;
import cn.org.chtf.card.manage.system.model.SystemDictionaries;
import cn.org.chtf.card.manage.system.service.SysCompanyTypeService;
import cn.org.chtf.card.manage.system.service.SysCountryAreaService;
import cn.org.chtf.card.manage.system.service.SysIndustryService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysReservationSettingService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.system.service.SysSmsTemplateService;
import cn.org.chtf.card.manage.system.service.SystemDictionariesService;
import cn.org.chtf.card.manage.wechatuser.dao.WechatUserMapper;
import cn.org.chtf.card.manage.wechatuser.pojo.WechatUser;
import cn.org.chtf.card.support.util.BarCodeUtil;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.DateUtil;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.RandomValidateCodeUtil;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.SMSUtil;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.support.util.WebFileUtil;
import cn.org.chtf.card.support.util.QRCode.QRCodeUtil;
import cn.org.chtf.card.support.util.http.HttpUtil;
import cn.org.chtf.card.support.util.tencentyun.TLSSigAPIv2;
import cn.org.chtf.card.support.util.tencentyun.Util;
import cn.org.chtf.card.support.util.wechat.AES;
import cn.org.chtf.card.support.util.wechat.Account;
import cn.org.chtf.card.support.util.wechat.WxPKCS7Encoder;
import cn.org.chtf.card.support.util.wechat.wechatApi;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = { "/router" })
public class RouterController {
	Map<String, Object> exhibitionInfo;

	RouterController() {
	}

	private static boolean isTest = false;

	@Resource(name = "MenuServiceImpl")
	MenuService menuService;
	@Resource(name = "MemberServiceImpl")
	MemberService memberService;
	
	@Resource(name = "MemberSessionServiceImpl")
	MemberSessionService memberSessionService;

	@Autowired
	private WechatUserMapper wechatUserDAO;
	@Autowired
	private wechatApi wechat;
	@Autowired
	private FriendlinkMapper friendlinkDAO;
	@Autowired
	private PimAgentMapper agentDAO;
	
	@Autowired
    private CommonMapper commonDao;
	
	@Autowired
    private OnlineApplyMeetingService onlineApplyMeetingService;

	@Autowired
	private OnlineActivityDetailsService onlineActivityDetailsService;

	@Autowired
	SysSessionService sessionService;

	@Autowired
	private WebProductService webProductService;

	@Autowired
	private SystemDictionariesService systemDictionariesService;

	@Resource(name = "ArticleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "BasicServiceImpl")
	private BasicService basicService;
	@Resource(name = "BasicSettingServiceImpl")
	private BasicSettingService basicSettingService;
	@Autowired
	private EbsPersonnelcardService ebsPersonnelcardService;
	@Autowired
	private EbsVehiclecardService vehiclecardService;
	@Resource
	private CmCertificateTypeService certificateType;
	@Autowired
	private EbsBoothService ebsBoothService;
	@Autowired
	private EbsShowroomService roomService;
	@Autowired
	private PimAgentService agentSerivce;

	@Autowired
	private EbsCompanyinfoService ebsCompanyinfoService;

	@Autowired
	private EbsCompanyinfoMapper ebsCompanyinfoDao;

	@Autowired
	private OnlineNegotiateService onlineNegotiateService;

	@Autowired
	private CommonService commonService;

	@Resource
	private SMSUtil sMSUtil;

	@Resource
	private Util tencentUtil;

	@Resource
	private Account account;

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private SysCountryAreaService sysCountryAreaService;
	@Autowired
	private SysCompanyTypeService sysCompanyTypeService;
	@Autowired
	private EbsCompanyinfoService companyService;
	@Autowired
	private EbsTradinggroupboothallocationService tradinggroupboothallocationService;
	@Autowired
	private SysIndustryService sysIndustryService;
	@Autowired
	private EbsBoothApplyService applyService;

	@Autowired
	private OnlineService onlineService;

	@Autowired
	private OnlineNewsService onlineNewsService;

	@Autowired
	private SysOperationLogService sysOperationLogService;

	@Autowired
	private OnlineReservationDetailsService onlineReservationDetailsService;

	@Autowired
	private FriendlinkService firendlinkService;

	@Autowired
	private SysReservationSettingService sysReservationSettingService;

	@Autowired
	private BasicMapper basicDao;

	@Autowired
	private OnlineInquiryService onlineInquiryService;


    @Autowired
    private EbsVehiclecardMapper ebsVehiclecardDao;
    

    @Autowired
    private EbsPersonnelcardMapper ebsPersonnelcardDao;

	@Autowired
	private SysSmsTemplateService smsService;
	/**
	 * 首页接口
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/{language}/index" })
	@ResponseBody
	public ResultModel index(@PathVariable String language,
			HttpSession session, HttpServletRequest request) throws IOException {
		setCommonContent();
		System.out.println(session.getId());
		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		Map<String, Object> resMap = new HashMap<String, Object>();
		// 1.展会开始时间
		String startDate = exhibitionInfo.get("startDate").toString();
		int days = DateUtil.getDifferenceTotalDays(null, startDate);
		if (days < 0) {
			days = 0;
		}
		exhibitionInfo.put("session", exhibitionInfo.get("sessionId"));
		resMap.put("session", session.getId());
		resMap.put("cookie", request.getCookies());
		resMap.put("days", days);

		// 首页banner
		Map<String, Object> mapBasic = onlineService.GetBacicInfo(247);
		resMap.put("banner", mapBasic.get("picture"));

		// 2.天气
		exhibitionInfo.put("cityid", "101050101");
		Map<String, Object> mapWeather = onlineService
				.getWeatherInfo(exhibitionInfo);
		Map<String, Object> weather = new HashMap<String, Object>();
		weather.put("date", mapWeather.get("monthday"));
		weather.put("city", mapWeather.get("citynm"));
		weather.put("temperature", mapWeather.get("temperature_curr"));
		weather.put("type", mapWeather.get("weather_curr"));
		weather.put("picture", mapWeather.get("weather_icon"));
		resMap.put("weather", weather);

		// 3.新闻
		List<Map> listNews = onlineNewsService.GetTopNews(4);
		resMap.put("news", listNews);

		// 3.展商
		exhibitionInfo.put("count", 3);
		List<Map<String, Object>> companys = new ArrayList<Map<String, Object>>();
		if (isTest) {
			for (int i = 0; i < 4; i++) {
				Map<String, Object> product = new HashMap<String, Object>();
				product.put("companyId", i + 1);
				product.put("picture", "/upload/router/company" + (i + 1)
						+ ".png");
				product.put("area", "绿色食品展区");
				product.put("num", "B405" + i + 1);
				if (i == 0) {
					product.put("companyName", "玉泉酒业");
					product.put("companyIntro", "玉泉酒业");
				}
				if (i == 1) {
					product.put("companyName", "神顶峰");
					product.put("companyIntro", "神顶峰");
				}
				if (i == 2) {
					product.put("companyName", "和粮农业");
					product.put("companyIntro", "和粮农业");
				}
				if (i == 3) {
					product.put("companyName", "中粮");
					product.put("companyIntro", "中粮");
				}
				companys.add(product);
			}
		} else {
			companys = companyService.GetTopComapnyInfo(exhibitionInfo);
		}

		resMap.put("companys", companys);
		// 4.展品
		List<Map<String, Object>> products = new ArrayList<Map<String, Object>>();
		exhibitionInfo.put("count", 10);
		List<WebProduct> productlist = new ArrayList<WebProduct>();
		if (isTest) {
			for (int i = 0; i < 4; i++) {
				Map<String, Object> product = new HashMap<String, Object>();
				product.put("picture", "/upload/router/product" + (i + 1)
						+ ".png");
				product.put("productId", i + 1);
				if (i == 0)
					product.put("productName", "椴树雪蜜");
				if (i == 1)
					product.put("productName", "野生蓝莓果汁");
				if (i == 2)
					product.put("productName", "麦芯小麦粉饺子专用1kg");
				if (i == 3)
					product.put("productName", "有机赤小豆");
				products.add(product);
			}
		} else {
			productlist = webProductService.getTopProduct(exhibitionInfo);
			for (int j = 0; j < productlist.size(); j++) {
				Map<String, Object> product = new HashMap<String, Object>();
				product.put("picture", productlist.get(j).getProductPicture());
				product.put("productId", productlist.get(j).getProductId());
				product.put("productName", productlist.get(j).getProductName());
				products.add(product);
			}
		}

		resMap.put("products", products);

		result.setResult(resMap);
		return result;
	}

	// 通过手机号激活用户
	@RequestMapping(value = { "/{language}/UserActivation/{phone}" })
	@ResponseBody
	public ResultModel UserActivation(@PathVariable String language,
			HttpSession session, HttpServletRequest request,
			@PathVariable String phone) throws IOException {
		setCommonContent();

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		String strOpenID = openId.toString();
		WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(strOpenID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("uid", existUser.getUid());

		// 验证手机号是否已存在
		int isExists = onlineService.getWechatUser(map);
		if (isExists > 0) {
			return new ResultModel(WConst.ERROR, "手机号已被其他用户注册", null);
		}

		boolean b = onlineService.UserActivation(map);
		if (b) {
			return new ResultModel(WConst.SUCCESS, "激活成功", null);
		} else {
			return new ResultModel(WConst.ERROR, "激活失败", null);
		}
	}

	/**
	 * 友情连接web页
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/{language}/link-{id}.html" })
	public String linkInfo(@PathVariable String language,
			@PathVariable Integer id, Model model, HttpSession session) {
		setCommonContent(model);
		Friendlink link = friendlinkDAO.selectByPrimaryKey(id);
		model.addAttribute("link", link);
		return "router/" + language + "/link";
	}

	/**
	 * 走进绿博会
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/{language}/in" })
	@ResponseBody
	public ResultModel in(@PathVariable String language, HttpSession session,
			HttpServletRequest request) throws IOException {
		setCommonContent();
		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		Map<String, Object> resMap = new HashMap<String, Object>();
		// 1.关于绿博会
		Map<String, Object> basic = onlineService.GetBacicInfo(255);
		resMap.put("about", basic.get("intro"));
		basic = onlineService.GetBacicInfo(256);
		resMap.put("hostUnits", basic.get("intro"));
		basic = onlineService.GetBacicInfo(257);
		resMap.put("sponsoringUnits", basic.get("intro"));
		List<Map<String, Object>> pictureList = onlineService
				.GetFriendLink(258);
		List<Map<String, Object>> pictures = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> pic : pictureList) {
			Map<String, Object> picture = new HashMap<String, Object>();
			picture.put("url", pic.get("picture"));
			picture.put("link", pic.get("link"));
			pictures.add(picture);
		}
		resMap.put("pictures", pictures);
		basic = onlineService.GetBacicInfo(259);
		resMap.put("contact", basic.get("intro"));

		result.setResult(resMap);
		return result;
	}

	/**
	 * 参展报名
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/{language}/join" })
	@ResponseBody
	public ResultModel join(@PathVariable String language, HttpSession session,
			HttpServletRequest request) {
		setCommonContent();
		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		Map<String, Object> resMap = new HashMap<String, Object>();
		String title = "我要参展";
		// 我要参展
		Map<String, Object> mapBasic = onlineService.GetBacicInfo(249);
		resMap.put("content", mapBasic.get("content"));
		resMap.put("intro", mapBasic.get("intro"));
		resMap.put("title", title);
		// 3.图标
		List<Map<String, Object>> pictures = onlineService.GetFriendLink(251);
		resMap.put("pictures", pictures);
		result.setResult(resMap);
		return result;
	}

	/**
	 * 我要参会
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/{language}/joinmeet" })
	@ResponseBody
	public ResultModel joinmeet(@PathVariable String language,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		Map<String, Object> resMap = new HashMap<String, Object>();
		String title = "我要参会";
		// 我要参展
		Map<String, Object> mapBasic = onlineService.GetBacicInfo(250);
		resMap.put("banner", mapBasic.get("picture"));
		resMap.put("title", title);
		// 3.图标
		List<Map<String, Object>> pictures = onlineService.GetFriendLink(252);
		resMap.put("pictures", pictures);
		result.setResult(resMap);
		return result;
	}

	/**
	 * 在线活动
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/{language}/activity/datelist" })
	@ResponseBody
	public ResultModel activityDateList(@PathVariable String language,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		List<Map<String, Object>> resMap = new ArrayList<Map<String, Object>>();
		if (isTest) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("date", "2020-10-18");
			item.put("week", "周五");
			resMap.add(item);
			Map<String, Object> item1 = new HashMap<String, Object>();
			item1.put("date", "2020-10-19");
			item1.put("week", "周六");
			resMap.add(item1);
			Map<String, Object> item2 = new HashMap<String, Object>();
			item2.put("date", "2020-10-20");
			item2.put("week", "周日");
			resMap.add(item2);
			Map<String, Object> item3 = new HashMap<String, Object>();
			item3.put("date", "2020-10-21");
			item3.put("week", "周一");
			resMap.add(item3);
			Map<String, Object> item4 = new HashMap<String, Object>();
			item4.put("date", "2020-10-22");
			item4.put("week", "周二");
			resMap.add(item4);
			result.setResult(resMap);
		} else {
			List<Map<String, Object>> list = onlineService
					.getActivityList(exhibitionInfo);
			result.setResult(list);
		}

		return result;
	}
	
	@RequestMapping(value = { "/{language}/GetRecommend" })
	@ResponseBody
	public ResultModel GetRecommend(@PathVariable String language,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (isTest) {
			resMap.put("title","开幕式");
			resMap.put("intro","介绍");
			resMap.put("picture","/upload/picture/2020-09-29/8da4fb6e-3a1c-4465-afdb-c484114ef833.jpg");
			resMap.put("link","");
			
		} else {
			Basic basickms = basicDao.getBasicByMenuId(268);
			resMap.put("title",basickms.getBasicContent());
			resMap.put("intro",basickms.getBasicOperator());
			resMap.put("picture",basickms.getBasicPicture());
			resMap.put("link",basickms.getBasicIntro());
			resMap.put("liveaddress",basickms.getLiveaddress());
			resMap.put("videortmp",basickms.getVideortmp());
			resMap.put("videoflv",basickms.getVideoflv());
			resMap.put("videohls",basickms.getVideohls());
			resMap.put("videoudp",basickms.getVideoudp());
		}
		result.setResult(resMap);
		return result;
	}

	/**
	 * 在线活动列表
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = { "/{language}/activity/list" })
	@ResponseBody
	public ResultModel activityList(@PathVariable String language,
			String strDate, HttpSession session, HttpServletRequest request)
			throws ParseException {
		setCommonContent();
		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		List<Map<String, Object>> resMap = new ArrayList<Map<String, Object>>();
		if (isTest) {
			// 1.开馆
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("startTime", "08:30");
			item.put("endTime", null);
			List<Map<String, Object>> activities = new ArrayList<Map<String, Object>>();
			item.put("activities", activities);
			resMap.add(item);

			for (int i = 0; i < 3; i++) {
				Map<String, Object> item1 = new HashMap<String, Object>();
				item1.put("startTime", "10:30");
				item1.put("endTime", "11:00");
				List<Map<String, Object>> activities1 = new ArrayList<Map<String, Object>>();

				Map<String, Object> activity = new HashMap<String, Object>();
				activity.put("activityId", 0);
				if (i == 0)
					activity.put("activityName", "黑龙江好食材暨龙菜品鉴会");
				if (i == 1)
					activity.put("activityName", "大米品评品鉴及十大好吃米饭评选活动");
				if (i == 2)
					activity.put("activityName", "黑龙江国际大米节”直播带货活动");
				activity.put("place", "待定");
				activity.put("intro", "待定");
				activity.put("roomId", "20209087");
				activity.put("link",
						"http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey");
				activity.put("picture", "/upload/router/1.jpg");
				// activities1.add(activity);

				// item1.put("activities", activities1);

				// resMap.add(item1);
			}
			// 5.闭馆
			Map<String, Object> item5 = new HashMap<String, Object>();
			item5.put("startTime", null);
			item5.put("endTime", "22:30");
			item.put("activities", activities);
			resMap.add(item5);
			result.setResult(resMap);
		} else {
			exhibitionInfo.put("riqi", strDate);
			List<Map<String, Object>> lit = onlineService
					.GetActivityTime(exhibitionInfo);
			for (int j = 0; j < lit.size(); j++) {
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("startTime", lit.get(j).get("starttime"));
				item.put("endTime", lit.get(j).get("endtime"));
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				if (!lit.get(j).get("endtime").toString().equals("")
						&& !lit.get(j).get("starttime").toString().equals("")) {
					list = onlineService.GetActivityDetails(lit.get(j));
					for (int k = 0; k < list.size(); k++) {
						int izhuangtai = Integer.valueOf(list.get(k)
								.get("zhuangtai").toString());
						if (izhuangtai == 1) {// 线下
							list.get(k).put("icon",
									"/online/cn/images/ico1.png");
						} else {// 线上
							SimpleDateFormat formater1 = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm");
							Date starttime = formater1.parse(strDate + " "
									+ lit.get(j).get("starttime").toString());
							Date endtime = formater1.parse(strDate + " "
									+ lit.get(j).get("endtime").toString());
							Date nowtime = formater1.parse(formater1
									.format(new Date()));
							if (nowtime.compareTo(starttime) < 0) {// 未开始
								list.get(k).put("icon",	"/online/cn/images/ico6.png");
								list.get(k).put("msg",	"未开始");
							}
							if (nowtime.compareTo(starttime) > 0
									&& nowtime.compareTo(endtime) < 0) {// 时间区间内
								list.get(k).put("icon",	"/online/cn/images/ico5.png");
								list.get(k).put("msg",	"进行中");
							}
							if (nowtime.compareTo(endtime) > 0) {// 已结束
								list.get(k).put("icon",	"/online/cn/images/ico6.png");
								list.get(k).put("msg",	"已结束");
							}
						}
					}

				}
				item.put("activities", list);
				resMap.add(item);
			}
		}
		result.setResult(resMap);
		return result;
	}

	/**
	 * 在线活动详细
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/{language}/activity/info" })
	@ResponseBody
	public ResultModel info(@PathVariable String language, Integer id,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		Map<String, Object> resMap = new HashMap<String, Object>();
		OnlineActivityDetails oad = onlineActivityDetailsService.findById(id);
		resMap.put("activityName", oad.getTitle());
		resMap.put("startTime", oad.getStartdatetime());
		resMap.put("endTime", oad.getEnddatetime());
		resMap.put("palce", oad.getAddress());
		resMap.put("content", oad.getContent());
		resMap.put("intro", oad.getIntro());
		resMap.put("roomId", oad.getRoomid());
		resMap.put("link", oad.getLink());
		resMap.put("picture", oad.getAcpicture());
		result.setResult(resMap);
		return result;
	}

	/**
	 * 网上展馆
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/{language}/hall" })
	@ResponseBody
	public ResultModel hall(@PathVariable String language, HttpSession session,
			HttpServletRequest request) throws IOException {
		setCommonContent();
		exhibitionInfo.put("session", exhibitionInfo.get("sessionId"));
		System.out.println(session.getId());
		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		Map<String, Object> resMap = new HashMap<String, Object>();
		// 1.360全景展示
		Map<String, Object> panorama = new HashMap<String, Object>();
		panorama.put("title", "360全景展示");

		if (isTest) {
			panorama.put("link", "");
			panorama.put("picture", "/upload/router/panorama.png");
		} else {
			Basic basic = basicDao.getBasicByMenuId(267);
			panorama.put("link", basic.getBasicIntro());
			panorama.put("picture", basic.getBasicPicture());
		}
		resMap.put("panorama", panorama);

		// 2.线上展览
		Map<String, Object> hall = new HashMap<String, Object>();
		hall.put("title", "线上展览");
		Basic basic = basicDao.getBasicByMenuId(275);
		hall.put("link", basic.getBasicIntro());
		hall.put("picture", basic.getBasicPicture());
		resMap.put("hall", hall);

		// 3.展商
		exhibitionInfo.put("count", 4);
		List<Map<String, Object>> companys = new ArrayList<Map<String, Object>>();
		// 4.展品
		List<Map<String, Object>> products = new ArrayList<Map<String, Object>>();
		exhibitionInfo.put("count", 4);
		List<WebProduct> productlist = new ArrayList<WebProduct>();

		if (isTest) {
			for (int i = 0; i < 4; i++) {
				Map<String, Object> product = new HashMap<String, Object>();
				product.put("picture", "/upload/router/product" + (i + 1)
						+ ".png");
				product.put("productId", i + 1);
				if (i == 0)
					product.put("productName", "椴树雪蜜");
				if (i == 1)
					product.put("productName", "野生蓝莓果汁");
				if (i == 2)
					product.put("productName", "麦芯小麦粉饺子专用1kg");
				if (i == 3)
					product.put("productName", "有机赤小豆");
				products.add(product);
			}
			for (int i = 0; i < 4; i++) {
				Map<String, Object> product = new HashMap<String, Object>();
				product.put("picture", "/upload/router/company" + (i + 1)
						+ ".png");
				product.put("area", "绿色食品展区");
				product.put("num", "B405" + i + 1);
				if (i == 0)
					product.put("companyName", "玉泉酒业");
				if (i == 1)
					product.put("companyName", "神顶峰");
				if (i == 2)
					product.put("companyName", "和粮农业");
				if (i == 3)
					product.put("companyName", "中粮");
				companys.add(product);
			}
		} else {
			companys = companyService.GetTopComapnyInfo(exhibitionInfo);
			productlist = webProductService.getTopProduct(exhibitionInfo);
			for (int j = 0; j < productlist.size(); j++) {
				Map<String, Object> product = new HashMap<String, Object>();
				product.put("picture", productlist.get(j).getProductPicture());
				product.put("productId", productlist.get(j).getProductId());
				product.put("productName", productlist.get(j).getProductName());
				products.add(product);
			}

		}

		resMap.put("companys", companys);
		resMap.put("products", products);

		result.setResult(resMap);
		return result;
	}

	/**
	 * 供采对接，基础参数
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/{language}/deal/hall" })
	@ResponseBody
	public ResultModel dealHall(@PathVariable String language,
			HttpSession session, HttpServletRequest request) throws IOException {
		setCommonContent();

		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		Map<String, Object> resMap = new HashMap<String, Object>();

		// 1.产品种类
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("dicParentid", 90);
		filter.put("useable", 1);
		List<SystemDictionaries> categories = systemDictionariesService
				.findByMap(filter);
		resMap.put("categories", categories);
		// 2.产品单位
		Map<String, Object> filter1 = new HashMap<String, Object>();
		filter.put("dicParentid", 105);
		filter.put("useable", 1);
		List<SystemDictionaries> units = systemDictionariesService
				.findByMap(filter);
		resMap.put("units", units);

		result.setResult(resMap);
		return result;
	}

	/**
	 * 在线询盘提交
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/{language}/deal/addSpdocking")
	@ResponseBody
	public ResultModel addSpdocking(@PathVariable String language,
			OnlineInquiry oi, HttpSession session) {
		setCommonContent();

		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg("发布成功");

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		// 取OpenId
		String strOpenID = openId.toString();
		WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(strOpenID);

		int iMember = existUser.getUid();

		oi.setMemberid(iMember);
		oi.setSession(Integer.valueOf(exhibitionInfo.get("sessionId")
				.toString()));
		onlineInquiryService.save(oi);
		return result;
	}

	/**
	 * 在线询盘列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/{language}/deal/list")
	@ResponseBody
	public ResultModel dealList(@PathVariable String language, PageModel page,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();

		page.setSession(exhibitionInfo.get("sessionId").toString());
		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		List<OnlineInquiry> list = new ArrayList<OnlineInquiry>();
		if (isTest) {
			// 加点儿临时数据
			OnlineInquiry o1 = new OnlineInquiry();
			o1.setTitle("生产加工农副产品原材料");
			o1.setContent("用于农副产品深加工");
			o1.setQuantity("50");
			o1.setQuantityunitname("吨");
			o1.setMenuname("绿色有机食品展区");
			o1.setAddtime("2020-09-22 10:00:00");

			OnlineInquiry o2 = new OnlineInquiry();
			o2.setTitle("无纺布、酒精、脱脂棉");
			o2.setContent("铝箔内包装、休闲食品拉伸膜内包装、产品外包装");
			o2.setQuantity("30");
			o2.setQuantityunitname("包");
			o2.setMenuname("品牌推广板块");
			o2.setAddtime("2020-09-19 11:25:01");

			OnlineInquiry o3 = new OnlineInquiry();
			o3.setTitle("采购非转基因大豆、黑豆、有机大豆");
			o3.setContent("用于生产非转基因豆粉、豆奶粉、豆浆粉及有机豆浆");
			o3.setQuantity("50");
			o3.setQuantityunitname("吨");
			o3.setMenuname("绿色有机食品展区");
			o3.setAddtime("2020-09-18 12:00:04");
			list.add(o1);
			list.add(o2);
			list.add(o3);

		} else {
			list = onlineInquiryService.GetInquiryList(page);
			int iCount = onlineInquiryService.GetInquiryListCount(page);
			result.setCount(iCount);
		}
		result.setResult(list);
		return result;
	}

	/**
	 * 我的收藏
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/{language}/MyFavorites")
	@ResponseBody
	public ResultModel MyFavorites(@PathVariable String language,
			PageModel page, HttpSession session, HttpServletRequest request) {
		setCommonContent();

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		// 取OpenId
		String strOpenID = openId.toString();
		WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(strOpenID);

		int iMember = existUser.getUid();
		page.setMemberid(iMember);
		page.setSession(exhibitionInfo.get("sessionId").toString());

		List<Map<String, Object>> list = onlineService.GetMyFavorites(page);
		int iCount = onlineService.GetMyFavoritesCount(page);

		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, list,
				iCount);
	}

	/**
	 * 意向订单、预约洽谈
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/{language}/MyReceived")
	@ResponseBody
	public ResultModel MyReceived(@PathVariable String language,
			PageModel page, HttpSession session, HttpServletRequest request) {
		setCommonContent();

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			//return WConst.RELOGINJSON;
		}
		// 取OpenId
		String strOpenID = "oyozk5BbqH2iwror_BOgN4j3Uf8g";//openId.toString();
		WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(strOpenID);

		int iMember = existUser.getUid();
		page.setMemberid(iMember);
		page.setSession(exhibitionInfo.get("sessionId").toString());
		page.setCompanyid(existUser.getCompanyid().toString());

		List<Map<String, Object>> list = onlineService.MyReceived(page);
		int iCount = onlineService.MyReceivedCount(page);

		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, list,
				iCount);
	}
	
	@RequestMapping("/{language}/MeetingShip")
	@ResponseBody
	public ResultModel MeetingShip(@PathVariable String language,
			String pass, String userName, String meetingnum, HttpSession session, HttpServletRequest request) {
		setCommonContent();
		if("".equals(meetingnum))return new ResultModel(WConst.ERROR,"会议号不能为空",null);
		if("".equals(pass))return new ResultModel(WConst.ERROR,"请输入密码",null);
		
		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		// 取OpenId
		String strOpenID = openId.toString();
		WechatUser user = wechatUserDAO.selectByRoutineOpenid(strOpenID);
		
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
	
	@RequestMapping("/{language}/MeetingList")
	@ResponseBody
	public ResultModel MeetingList(@PathVariable String language,
			PageModel page, HttpSession session, HttpServletRequest request) {
		setCommonContent();
		
		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		// 取OpenId
		String strOpenID = openId.toString();
		WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(strOpenID);
		String phone = existUser.getPhone();
		//int iMember = existUser.getUid();
		//page.setMemberid(iMember);
		page.setSession(exhibitionInfo.get("sessionId").toString());
		page.setPhone(phone);
		int iCount = 0;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(isTest){
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("title", "黑龙江国际大米节”直播带货活动 ");
			map.put("ishost", 0);
			map.put("pass", "123456");
			map.put("meetingnumber", "000001");
			map.put("phone", "13001098117");
			map.put("starttime", "2020-09-30 12:00");
			map.put("endtime", "2020-10-30 14:30");
			list.add(map);
		}
		else{
			list = onlineService.MeetingList(page);
			iCount = onlineService.MeetingListCount(page);
		}
		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, list, iCount);
	}

	/**
	 * 我发布的询盘信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/{language}/MyPostInfo")
	@ResponseBody
	public ResultModel MyPostInfo(@PathVariable String language,
			PageModel page, HttpSession session, HttpServletRequest request) {
		setCommonContent();

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		// 取OpenId
		String strOpenID = openId.toString();
		WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(strOpenID);

		int iMember = existUser.getUid();
		page.setMemberid(iMember);
		page.setSession(exhibitionInfo.get("sessionId").toString());

		List<Map<String, Object>> list = onlineService.MyPostInfo(page);
		int iCount = onlineService.MyPostInfoCount(page);

		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, list,
				iCount);
	}

	/**
	 * 取消收藏
	 * 
	 * @param fid
	 * @return
	 */
	@RequestMapping("/{language}/UnFavorite/{fid}")
	@ResponseBody
	public ResultModel UnFavorite(@PathVariable String language,
			@PathVariable Integer fid, HttpSession session,
			HttpServletRequest request) {
		setCommonContent();

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		// 取OpenId
		String strOpenID = openId.toString();
		WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(strOpenID);

		onlineService.UnFavorite(fid);

		return new ResultModel(WConst.SUCCESS, WConst.SETED, null);
	}

	/**
	 * 获取邀请函信息
	 * 
	 * @param companyid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{language}/getCardInfo/{companyid}")
	@ResponseBody
	public ResultModel getCardInfo(@PathVariable String language,
			@PathVariable Integer companyid, HttpSession session,
			HttpServletRequest request) throws Exception {
		setCommonContent();

		EbsCompanyinfo eci = ebsCompanyinfoService.findById(companyid);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("logo", "/images/logo.png");
		resMap.put("title", "第八届黑龙江绿色食品产业博览会\n第三届中国·黑龙江国际大米节");
		resMap.put("companyname", eci.getChinesename());
		resMap.put("time", "2020/10/18-10/22");
		resMap.put("address", "哈尔滨国际会展体育中心");
		resMap.put("exhibitionname", eci.getTradinggroupname());
		resMap.put("num", eci.getZhanweihao());

		String token = account.getWechatToken();

		Map<String, Object> para = new HashMap<String, Object>();
		para.put("scene", "cid=" + companyid);
		para.put("page", "pages/merchants/detail/detail");
		para.put("width", "280");
		para.put("is_hyaline", true);
		String imgpath = WebFileUtil.downloadMiniCode(para, token);

		resMap.put("qrcode", imgpath);
		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, resMap);
	}

	/**
	 * “我的”首页
	 * 
	 * @param fid
	 * @return
	 */
	/*
	 * @RequestMapping("/{language}/MyIndex")
	 * 
	 * @ResponseBody public ResultModel MyIndex(@PathVariable String language,
	 * HttpSession session,HttpServletRequest request){ setCommonContent();
	 * 
	 * Object openId = session.getAttribute("openId");
	 * if(openId==null||openId.equals("")) { return WConst.RELOGINJSON; }
	 * //取OpenId String strOpenID = openId.toString(); WechatUser existUser =
	 * wechatUserDAO.selectByRoutineOpenid(strOpenID); int iMemberid =
	 * existUser.getUid();
	 * 
	 * Map<String,Object> resMap = new HashMap<String,Object>(); //用户头像
	 * resMap.put("headimgurl",existUser.getHeadimgurl()); //用户类型
	 * resMap.put("userType",existUser.getUserType()); //用户ID
	 * resMap.put("userId",iMemberid);
	 * 
	 * PageModel page= new PageModel(); page.setMemberid(iMemberid);
	 * page.setSession(exhibitionInfo.get("sessionId").toString()); //收藏企业数
	 * page.setAct(0); int iCompanyCount =
	 * onlineService.GetMyFavoritesCount(page); //收藏产品数 page.setAct(1); int
	 * iProductCount = onlineService.GetMyFavoritesCount(page); //消息 int
	 * iMessageCount=8; resMap.put("companyCount", iCompanyCount);
	 * resMap.put("productCount", iProductCount); resMap.put("messageCount",
	 * iMessageCount); return new
	 * ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,resMap); }
	 */

	/**
	 * 产品和展商的参数
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/{language}/search/parameter" })
	@ResponseBody
	public ResultModel searchParameter(@PathVariable String language,
			HttpSession session, HttpServletRequest request) throws IOException {
		setCommonContent();

		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		Map<String, Object> resMap = new HashMap<String, Object>();

		// 1行业
		Map<String, Object> map = new HashMap<>();
		map.put("index", 0);
		map.put("size", 10000);
		List<SysIndustry> industries = sysIndustryService.list(map);
		// 2展区
		map.put("session", exhibitionInfo.get("sessionId"));
		List<ExhibitionInfoModel> exhibitionLists = onlineService
				.GetExhibitionInfo(map);
		// 3国别/地区
		List<SysCountryArea> countryArea = sysCountryAreaService.list(map);

		resMap.put("industries", industries);
		resMap.put("exhibitionArea", exhibitionLists);
		resMap.put("countryArea", countryArea);

		result.setResult(resMap);
		return result;
	}

	/**
	 * 产品和展商的参数
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/{language}/company/list" })
	@ResponseBody
	public ResultModel companyList(@PathVariable String language,
			HttpSession session, HttpServletRequest request, PageModel page,
			Integer industryId, Integer exhibitionAreaId, Integer countryId,
			Integer areaId) throws IOException {
		setCommonContent();
		if (isTest) {
			ResultModel result = new ResultModel();
			result.setStatus(WConst.SUCCESS);
			result.setMsg(WConst.QUERYSUCCESS);
			exhibitionInfo.put("count", 4);
			List<Map<String, Object>> companys = companyService
					.GetTopComapnyInfo(exhibitionInfo);
			if (true)// companys.size()==0)
				for (int i = 0; i < 4; i++) {
					Map<String, Object> product = new HashMap<String, Object>();
					product.put("companyId", i + 1);
					product.put("picture", "/upload/router/company" + (i + 1)
							+ ".png");
					product.put("area", "绿色食品展区");
					product.put("num", "B405" + i + 1);
					if (i == 0) {
						product.put("companyName", "玉泉酒业");
						product.put("companyIntro", "玉泉酒业");
					}
					if (i == 1) {
						product.put("companyName", "神顶峰");
						product.put("companyIntro", "神顶峰");
					}
					if (i == 2) {
						product.put("companyName", "和粮农业");
						product.put("companyIntro", "和粮农业");
					}
					if (i == 3) {
						product.put("companyName", "中粮");
						product.put("companyIntro", "中粮");
					}
					companys.add(product);
				}
			result.setResult(companys);
			return result;
		} else {
			page.setSession(exhibitionInfo.get("sessionId").toString());
			if (countryId > 0) {
				page.setCountry(countryId.toString());
			}
			if (areaId > 0) {
				page.setProvince(areaId.toString());
			}
			if (industryId > 0) {
				page.setIndustry(industryId.toString());
			}
			if (exhibitionAreaId > 0) {
				page.setExhibition(exhibitionAreaId.toString());
			}
			List<ExhibitorModel> exhibitionList = onlineService
					.getExhibitiorList(page);
			List<Map<String, Object>> exhibitorList = new ArrayList<Map<String, Object>>();
			for (int j = 0; j < exhibitionList.size(); j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("companyType", exhibitionList.get(j).getCompanytypeid());
				map.put("area", exhibitionList.get(j).getExhibitionname());
				map.put("companyId", exhibitionList.get(j).getCompanyid());
				map.put("num", exhibitionList.get(j).getZhanweihao());
				map.put("companyName", exhibitionList.get(j).getChinesename());
				map.put("picture", exhibitionList.get(j).getCompanylogo());
				exhibitorList.add(map);
			}
			int iCount = onlineService.getExhibitiorListCount(page);
			return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS,
					exhibitorList, iCount);
		}
	}

	/**
	 * 展商详细
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @param companyid
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/{language}/company/info/{companyid}" })
	@ResponseBody
	public ResultModel companyinfo(@PathVariable String language,
			HttpSession session, HttpServletRequest request,
			@PathVariable Integer companyid) throws IOException {
		setCommonContent();

		int iMember = 0;
		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			// return WConst.RELOGINJSON;
		} else {
			// 取OpenId
			String strOpenID = openId.toString();
			WechatUser existUser = wechatUserDAO
					.selectByRoutineOpenid(strOpenID);
			iMember = existUser.getUid();
		}

		Map<String, Object> result = new HashMap<String, Object>();
		EbsCompanyinfo ci = ebsCompanyinfoService.findById(companyid);
		Map<String, Object> company = new HashMap<String, Object>();
		company.put("companyName", ci.getChinesename());
		company.put("companyType", ci.getComanyTypeId());
		company.put("area", ci.getTradinggroupname());
		company.put("num", ci.getZhanweihao());
		company.put("picture", ci.getCompanylogo());
		company.put("companyId", ci.getId());
		company.put("url", ci.getUrl());
		company.put("companyprofile", ci.getCompanyprofile());
		company.put("chineseaddress", ci.getChineseaddress());
		company.put("postcode", ci.getPostcode());
		company.put("tel", ci.getTel());
		company.put("fax", ci.getFax());
		company.put("contactperson", ci.getContactperson());
		company.put("jobtitle", ci.getJobtitle());
		company.put("phone", ci.getPhone());
		company.put("email", ci.getEmail());
		company.put("companyvideo", ci.getVideourl());
		company.put("uid",ci.getUid());
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String comnpanyPictures = ci.getCompanypictures();
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
		company.put("pictureslist", list);
		

		// 是否收藏
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", exhibitionInfo.get("sessionId"));
		map.put("primaryid", companyid);
		map.put("act", 0);
		map.put("memberid", iMember);
		int isSC = onlineService.GetSCCount(map);
		company.put("isfavorites", isSC);
		// 是否点赞
		Map<String, Object> mapx = new HashMap<String, Object>();
		mapx.put("session", exhibitionInfo.get("sessionId"));
		mapx.put("primaryid", companyid);
		mapx.put("act", 0);
		mapx.put("memberid", iMember);
		int isDZ = onlineService.GetDZCount(mapx);
		company.put("isawesome", isDZ);
		mapx.put("memberid", 0);
		company.put("awesomeCount", onlineService.GetDZCount(mapx));
		result.put("companyinfo", company);
		mapx.put("itop", 6);
		List<Map<String, Object>> topLikes = onlineService.GettopLikes(mapx);
		result.put("awesomelist", topLikes);

		PageModel pageModel = new PageModel();
		pageModel.setLimit(8);
		pageModel.setPage(1);
		pageModel.setCompanyid(companyid.toString());
		pageModel.setSession(exhibitionInfo.get("sessionId").toString());
		List<ProductModel> ProductList = onlineService
				.getProductList(pageModel);
		result.put("productlist", ProductList);
		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, result);
	}

	/**
	 * 展品详细
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @param companyid
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/{language}/product/info/{productid}" })
	@ResponseBody
	public ResultModel info(@PathVariable String language, HttpSession session,
			HttpServletRequest request, @PathVariable Integer productid)
			throws IOException {
		setCommonContent();
		int iMember = 0;
		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			// return WConst.RELOGINJSON;
		} else {
			// 取OpenId
			String strOpenID = openId.toString();
			WechatUser existUser = wechatUserDAO
					.selectByRoutineOpenid(strOpenID);
			iMember = existUser.getUid();
		}

		Map<String, Object> result = new HashMap<String, Object>();
		// 产品信息
		WebProduct webProduct = webProductService.findById(productid);
		Map<String, Object> product = new HashMap<String, Object>();
		product.put("productName", webProduct.getProductName());
		product.put("productPrice", webProduct.getProductPrice());
		product.put("productUnit", webProduct.getUnitname());
		product.put("productBrand", webProduct.getProductBrand());
		product.put(
				"productOrigin",
				webProduct.getCityname().equals("") ? webProduct
						.getProvincename().equals("") ? webProduct
						.getCountryname() : webProduct.getProvincename()
						: webProduct.getCityname());
		product.put("productDescription", webProduct.getProductDescription());
		product.put("companyId", webProduct.getCompanyId());
		product.put("productId", webProduct.getProductId());

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
		product.put("pictures", list);

		// 是否收藏
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", exhibitionInfo.get("sessionId"));
		map.put("primaryid", productid);
		map.put("act", 1);
		map.put("memberid", iMember);
		int isSC = onlineService.GetSCCount(map);
		product.put("isfavorites", isSC);
		// 是否点赞
		Map<String, Object> mapx = new HashMap<String, Object>();
		mapx.put("session", exhibitionInfo.get("sessionId"));
		mapx.put("primaryid", productid);
		mapx.put("act", 1);
		mapx.put("memberid", iMember);
		int isDZ = onlineService.GetDZCount(mapx);
		product.put("isawesome", isDZ);
		mapx.put("memberid", 0);
		product.put("awesomeCount", onlineService.GetDZCount(mapx));

		mapx.put("itop", 6);
		List<Map<String, Object>> topLikes = onlineService.GettopLikes(mapx);
		product.put("awesomelist", topLikes);
		result.put("productinfo", product);

		int companyid = webProduct.getCompanyId();
		EbsCompanyinfo ci = ebsCompanyinfoService.findById(companyid);
		Map<String, Object> company = new HashMap<String, Object>();
		company.put("companyName", ci.getChinesename());
		company.put("companyType", ci.getComanyTypeId());
		company.put("area", ci.getTradinggroupname());
		company.put("num", ci.getZhanweihao());
		company.put("picture", ci.getCompanylogo());
		company.put("companyId", ci.getId());
		company.put("attendcount", ci.getAttendcount());
		company.put("uid", ci.getUid());
		result.put("companyinfo", company);

		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, result);
	}

	/**
	 * 意向订单及预约洽谈提交
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{language}/addIntentionOrderAndNegotiate")
	@ResponseBody
	public ResultModel addIntentionOrder(@PathVariable String language,
			OnlineNegotiate oin, HttpSession session, HttpServletRequest request)
			throws Exception {
		setCommonContent();

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		// 取OpenId
		String strOpenID = openId.toString();
		WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(strOpenID);

		int iMember = existUser.getUid();

		oin.setMemberid(iMember);
		oin.setSession(Integer.valueOf(exhibitionInfo.get("sessionId")
				.toString()));
		// 验证重复提交
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("act", oin.getAct());
		map.put("memberid", iMember);
		map.put("session", exhibitionInfo.get("sessionId"));
		map.put("tel", oin.getTel());
		map.put("name", oin.getName());
		map.put("content", oin.getContent());
		map.put("companyid", oin.getCompanyid());
		map.put("productid", oin.getProductid());
		List<OnlineNegotiate> list = onlineNegotiateService.findByMap(map);
		if (list.size() > 0) {
			return new ResultModel(WConst.ERROR, "相同的内容请不要重复提交。", null);
		}
		String uid = strOpenID;
		onlineNegotiateService.save(oin);

		// 保存成功后发送短信及微信服务消息
		int iCompanyid = oin.getCompanyid();

		Map<String, Object> mapx = new HashMap<String, Object>();
		mapx.put("session", exhibitionInfo.get("sessionId"));
		mapx.put("companyid", iCompanyid);
		// 验证企业是否认证
		WechatUser userx = wechatUserDAO.getUserInfoByCompanyid(iCompanyid);

		onlineService.SendSMSAndMessage(oin, userx);

		return new ResultModel(WConst.SUCCESS, "提交成功", null);
	}
	
	/**
	 * 申请会议
	 * @param language
	 * @param oam
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{language}/addApplyMeeting")
	@ResponseBody
	public ResultModel addApplyMeeting(@PathVariable String language,
			OnlineApplyMeeting oam, HttpSession session, HttpServletRequest request)
			throws Exception {
		setCommonContent();

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		// 取OpenId
		String strOpenID = openId.toString();
		WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(strOpenID);
		
		int iMember = existUser.getUid();
		oam.setCreateby(iMember);
		oam.setSession(exhibitionInfo.get("sessionId").toString());
		oam.setPhones(oam.getPhones().replace("，", ","));
		int iRet = onlineApplyMeetingService.save(oam);
		if(iRet==0){		
			return new ResultModel(WConst.ERROR, "申请失败", null);
		}
		return new ResultModel(WConst.SUCCESS, "申请成功", null);
	}

	/**
	 * 收藏展品、展商
	 * 
	 * @param language
	 * @return
	 */
	@RequestMapping(value = { "/{language}/testSendScriptMessage" })
	@ResponseBody
	public ResultModel testSendScriptMessage(@PathVariable String language,
			Integer companyid, Integer type, Integer act, HttpSession session,
			HttpServletRequest request) {

		WebFileUtil.ImageToBase64("./static/test.jpg");
		// setCommonContent();
//		Map<String, Object> postData = new HashMap<String, Object>();
//
//		// 20个以内字符，多了会发不出去
//		Map<String, Object> thing1Value = new HashMap<String, Object>();
//		thing1Value.put("value", "X.");
//		postData.put("thing1", thing1Value);
//
//		Map<String, Object> time2Value = new HashMap<String, Object>();
//		time2Value.put("value", "2020-09-29");
//		postData.put("time2", time2Value);
//
//		Map<String, Object> thing3Value = new HashMap<String, Object>();
//		// 20个以内字符，多了会发不出去
//		thing3Value.put("value", "请问这个产品可以便宜一点儿卖么？");
//		postData.put("thing3", thing3Value);
//		wechat.subscribeMessageSend("oyozk5HyiSjNoJj8koXwk2XCoDeE",
//				wechatApi.NEW_MESSAGE_TEMPLATE_ID,
//				"pages/user/message/message", postData);
		return new ResultModel(WConst.SUCCESS, WConst.SETED, null);
	}

	/**
	 * 收藏展品、展商
	 * 
	 * @param language
	 * @return
	 */
	@RequestMapping(value = { "/{language}/Favorites" })
	@ResponseBody
	public ResultModel Favorites(@PathVariable String language,
			Integer companyid, Integer type, Integer act, HttpSession session,
			HttpServletRequest request) {
		setCommonContent();

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		// 取OpenId
		String strOpenID = openId.toString();
		WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(strOpenID);

		int iMember = existUser.getUid();

		String sessionId = exhibitionInfo.get("sessionId").toString();
		// String memberTypeToken = getMemberTypeToken(member.getMemberType());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", sessionId);
		map.put("primaryid", companyid);
		map.put("act", act);
		map.put("memberid", iMember);
		if (type == 0) {// 收藏
			onlineService.addFavorites(map);
		} else {// 取消收藏
			onlineService.delFavorites(map);
		}

		return new ResultModel(WConst.SUCCESS, WConst.SETED, null);
	}

	/**
	 * 点赞展品、展商
	 * 
	 * @param language
	 * @return
	 */
	@RequestMapping(value = { "/{language}/Awesome" })
	@ResponseBody
	public ResultModel Awesome(@PathVariable String language,
			Integer companyid, Integer type, Integer act, HttpSession session,
			HttpServletRequest request) {
		setCommonContent();

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		// 取OpenId
		String strOpenID = openId.toString();
		WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(strOpenID);

		int iMember = existUser.getUid();

		String sessionId = exhibitionInfo.get("sessionId").toString();
		// String memberTypeToken = getMemberTypeToken(member.getMemberType());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", sessionId);
		map.put("primaryid", companyid);
		map.put("act", act);
		map.put("memberid", iMember);
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

		return new ResultModel(WConst.SUCCESS, WConst.SETED, null);
	}

	/**
	 * 产品和展商的参数
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/{language}/product/list" })
	@ResponseBody
	public ResultModel productList(@PathVariable String language,
			HttpSession session, HttpServletRequest request, PageModel page,
			Integer industryId, Integer exhibitionAreaId, Integer countryId,
			Integer areaId) throws IOException {
		setCommonContent();

		if (isTest) {
			ResultModel result = new ResultModel();
			result.setStatus(WConst.SUCCESS);
			result.setMsg(WConst.QUERYSUCCESS);
			exhibitionInfo.put("count", 4);
			List<Map<String, Object>> products = new ArrayList<Map<String, Object>>();
			if (products.size() == 0)
				for (int i = 0; i < 4; i++) {
					Map<String, Object> product = new HashMap<String, Object>();
					product.put("picture", "/upload/router/product" + (i + 1)
							+ ".png");
					product.put("productId", i + 1);
					if (i == 0)
						product.put("productName", "椴树雪蜜");
					if (i == 1)
						product.put("productName", "野生蓝莓果汁");
					if (i == 2)
						product.put("productName", "麦芯小麦粉饺子专用1kg");
					if (i == 3)
						product.put("productName", "有机赤小豆");
					products.add(product);
				}
			result.setResult(products);
			return result;
		} else {
			page.setSession(exhibitionInfo.get("sessionId").toString());
			if (countryId > 0) {
				page.setCountry(countryId.toString());
			}
			if (areaId > 0) {
				page.setProvince(areaId.toString());
			}
			if (industryId > 0) {
				page.setIndustry(industryId.toString());
			}
			if (exhibitionAreaId > 0) {
				page.setExhibition(exhibitionAreaId.toString());
			}

			List<ProductModel> ProductList = onlineService.getProductList(page);
			List<Map<String, Object>> products = new ArrayList<Map<String, Object>>();
			for (int j = 0; j < ProductList.size(); j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("productId", ProductList.get(j).getProductid());
				map.put("picture", ProductList.get(j).getProductpicture());
				map.put("productName", ProductList.get(j).getProductname());
				products.add(map);
			}
			int iCount = onlineService.getProductListCount(page);

			return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS,
					products, iCount);
		}
	}

	/**
	 * 获取预约信息二维码
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@RequestMapping(value = { "/{language}/GetJoinContent/{fid}" })
	@ResponseBody
	public ResultModel GetJoinContent(@PathVariable String language,
			@PathVariable String fid, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ResultModel r = firendlinkService.getFriendlink(Integer.valueOf(fid));
		return r;
	}

	/**
	 * 获取网上预约控制数据及展区信息
	 * 
	 * @param language
	 * @param session
	 * @return
	 * @throws  
	 */
	@RequestMapping(value = { "/{language}/appointment" })
	@ResponseBody
	public ResultModel appointment(@PathVariable String language,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		String sessionId = exhibitionInfo.get("sessionId").toString();
		// String memberTypeToken = getMemberTypeToken(member.getMemberType());
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", sessionId);
		map.put("status", 1);
		List<ReservationModel> ApplyDateList = onlineService
				.GetAvailableDates(map);
		resMap.put("appointmentinfo", ApplyDateList);

		// 展区信息
		map.put("session", sessionId);
		map.put("type", "2");
		List<ExhibitionInfoModel> ExhibitionInfoList = onlineService.GetDicForExhibition();//onlineService.GetExhibitionInfo(map);
		resMap.put("exhibitioninfo", ExhibitionInfoList);

		// 预约规则
		Map<String, Object> mapBasic = onlineService.GetBacicInfo(248);
		resMap.put("content", mapBasic.get("content"));
		resMap.put("intro", mapBasic.get("intro"));

		return new ResultModel(WConst.SUCCESS,
				"您的电子邀请函已预约成功，是否继续预约其他日期的邀请函，也可跳转到“我的-我的预约”查看已邀请函。", resMap);
	}

	/**
	 * 获取VIP网上预约控制数据及展区信息
	 * 
	 * @param language
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/appointmentVip" })
	@ResponseBody
	public ResultModel appointmentVip(@PathVariable String language,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		String sessionId = exhibitionInfo.get("sessionId").toString();
		// String memberTypeToken = getMemberTypeToken(member.getMemberType());
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session", sessionId);
		// 展区信息
		map.put("session", sessionId);
		map.put("type", "2");
		List<ExhibitionInfoModel> ExhibitionInfoList = onlineService.GetDicForExhibition();//onlineService.GetExhibitionInfo(map);
		resMap.put("exhibitioninfo", ExhibitionInfoList);

		// 预约规则
		Map<String, Object> mapBasic = onlineService.GetBacicInfo(253);
		resMap.put("content", mapBasic.get("content"));
		resMap.put("intro", mapBasic.get("intro"));

		return new ResultModel(WConst.SUCCESS,
				"您的VIP邀请函已激活，为保证安全观展，入场请携带本VIP邀请函，一函一码、专码专用、实名激活后，从贵宾通道入场。 ",
				resMap);

	}

	/**
	 * 获取用户预约信息
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/{language}/GetAppointmentInformation" })
	@ResponseBody
	public ResultModel GetAppointmentInformation(@PathVariable String language,
			NewsParams news, HttpSession session, HttpServletRequest request) {
		setCommonContent();
		// 判断用户是否登录
		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		// 取OpenId
		String strOpenID = openId.toString();
		// 通过openid获取用户ID
		WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(strOpenID);
		news.setSession(exhibitionInfo.get("sessionId").toString());
		news.setUid(existUser.getUid());
		return onlineService.GetAppointmentInformation(news);
	}

	/**
	 * 获取展商进度
	 * 
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/{language}/apply/process" })
	@ResponseBody
	public ResultModel GetExhibitorProcess(@PathVariable String language,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		// 判断用户是否登录
		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		// 取OpenId
		String strOpenID = openId.toString();
		// strOpenID = "oyozk5HyiSjNoJj8koXwk2XCoDeE";
		// 通过openid获取用户ID
		WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(strOpenID);
		if (existUser.getMemberId().equals(0)) {// 没有绑定用户
			return new ResultModel(WConst.CONFIRM,
					"您尚不是企业管理员，请前往“我的”栏目认证，或者立即注册。", existUser);
		} else if (existUser.getMemberId() == null) {
			System.out.println("小程序会员ID错误，请稍后重试。用户：" + strOpenID);
			return new ResultModel(WConst.ERROR, "小程序会员ID错误，请稍后重试。", existUser);
		}
		// 获取参展进度
		Map<String, Object> boothProcess = memberService.getBoothProcess(
				existUser.getMemberId(),
				Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
		EbsCompanyinfo company = companyService.findById(Integer
				.parseInt(boothProcess.get("companyId").toString()));
		boothProcess.put("chineseName", company.getChinesename());
		Object exhibitionAuditStatus = boothProcess
				.get("exhibitionAuditStatus");
		if (exhibitionAuditStatus == null) {
			boothProcess.put("exhibitionAuditStatus", "0");
		}
		Object financeAuditStatus = boothProcess.get("financeAuditStatus");
		if (financeAuditStatus == null) {
			boothProcess.put("financeAuditStatus", "0");
		}

		// String exhibitionAuditTime =
		// boothProcess.get("exhibitionAuditTime").toString();
		// String registerTime = boothProcess.get("registerTime").toString();
		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS,
				boothProcess);
	}

	/**
	 * 获取预约信息二维码
	 */
	@RequestMapping(value = { "/{language}/GetQrCode/{pid}" })
	@ResponseBody
	public void GetQrCode(@PathVariable String language,
			@PathVariable String pid, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		String number = onlineService.GetTicketNumber(pid);
		String secretkey = "https://card.hljlbh.org.cn/acs?t="
				+ CryptographyUtil.encrypt(number, Charset.forName("utf8"),
						"lbh@MaoC");// +"&ss="+UUID.randomUUID().toString();
		try {
			response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
			response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expire", 0);
			QRCodeUtil qru = new QRCodeUtil();

			qru.getQrCode(secretkey, "", request, response);
			qru.getQrCode(secretkey, "./static/images/logo.png", request,
					response);
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	/**
	 * 网上申请预约填报数据
	 * 
	 * @param language
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/applyexihbition" })
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public ResultModel applyexihbition(@PathVariable String language,
			OnlineReservationDetails ord, HttpSession session,
			HttpServletRequest request) {
		try {
			setCommonContent();
			String sessionId = exhibitionInfo.get("sessionId").toString();
			ord.setSession(Integer.valueOf(sessionId));
			// 1.首先判断用户是否登录
			Object openId = session.getAttribute("openId");
			if (openId == null || openId.equals("")) {
				return WConst.RELOGINJSON;
			}
			// 2.非空验证
			//oyozk5BbqH2iwror_BOgN4j3Uf8g
			String strOpenID = openId.toString();
			if (ord.getType() == null) {
				return new ResultModel(WConst.ERROR, "预约类型不能为空", null);
			}
			if (ord.getCardnumber().equals("")) {
				return new ResultModel(WConst.ERROR, "身份证号码不能为空", null);
			}
			if (ord.getExhibitionarea() == null
					|| ord.getExhibitionarea().equals("")
					|| ord.getExhibitionarea().equals("0")) {
				return new ResultModel(WConst.ERROR, "请选择展区", null);
			}
			if (ord.getType() == 0) {// 普通预约验证手机号
				if (ord.getPhone().equals("")) {
					return new ResultModel(WConst.ERROR, "手机号码不能为空", null);
				}
				// 格式暂时不验证，有可能会有外国手机号
				// if(ord.getPhone().length()!=11){
				// return new ResultModel(WConst.ERROR, "手机号格式不正确", null);
				// }
				
				exhibitionInfo.put("exhibitiondate", ord.getExhibitiondate());
				exhibitionInfo.put("status", 1);

				// 3.验证当前时间是否可以预约
				int reserveRulesCount = sysReservationSettingService
						.canReserve(Integer.parseInt(sessionId),
								ord.getExhibitiondate());
				if (reserveRulesCount == 0) {
					return new ResultModel(WConst.ERROR, "所选日期不可预约", null);
				}

				// 4.验证指定预约日期是否预约过
				int iTodayCount = onlineService.getTodayCountByCardNumber(ord);
				if (iTodayCount > 0) {
					return new ResultModel(WConst.ERROR, "填写的证件号码已存在预约记录", null);
				}

			}
			// 验证是否可预约
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("session", sessionId);
			map.put("status", 1);
			map.put("exhibitiondate", ord.getExhibitiondate());
			map.put("verification", "yes");
			Map<String, Object> param = new HashMap<String, Object>();
			if (ord.getType() == 0) {// 普通预约验证可否预约
				// 这里应该也可以去掉一部分功能的
				List<ReservationModel> ApplyDateList = onlineService.GetAvailableDates(map);
				if (ApplyDateList.size() == 0) {
					return new ResultModel(WConst.ERROR, "今天不可预约参观"
							+ ord.getExhibitiondate() + "的展会", null);
				}
				if (ApplyDateList.get(0).getUseable() == 0) {
					return new ResultModel(WConst.ERROR,
							ord.getExhibitiondate() + "已约满", null);
				}
			} else {// VIP预约验证预约码是否有效
				String secretkey = ord.getSecretkey();
				if (ord.getSecretkey().equals("")) {
					return new ResultModel(WConst.ERROR, "预约码不可为空", null);
				}
				// 。。。。解密预留
				try {
					secretkey = CryptographyUtil.decrypt(ord.getSecretkey(),
							Charset.forName("utf8"), "lbh@MaoC");
				} catch (Exception ee) {
					return new ResultModel(WConst.ERROR, "预约码异常", null);
				}
				param.put("secretkey", secretkey);
				param.put("type", 1);
				List<Map<String, Object>> res = onlineService.GetReceipt(param);
				if (res.size() > 0) {
					int useable = Integer.valueOf(res.get(0).get("useable")
							.toString());
					if (useable == 0) {
						return new ResultModel(WConst.ERROR, "此预约码已使用过", null);
					}
				} else {
					return new ResultModel(WConst.ERROR, "预约码无效", null);
				}
			}

			// 验证证件号预约情况
			map.put("cardnumber", ord.getCardnumber());
			// 此处没用了，可以删除
			// int iCount = onlineService.isExistsByCardNumber(map);
			// if(iCount>0){
			// return new ResultModel(WConst.ERROR, "该证件号已预约过本届展会", null);
			// }

			String strPiaoHao = "";
			if (ord.getType() == 1) {
				onlineReservationDetailsService.updateSecretKey(param);
				strPiaoHao = param.get("secretkey").toString();
			} else {
				strPiaoHao = commonService.GetTicketNumber(exhibitionInfo);
				if (strPiaoHao == null || strPiaoHao.equals("")) {
					return new ResultModel(WConst.ERROR, "本届展会已预约满", null);
				}
			}
			ord.setSecretkey(strPiaoHao);
			// 获取用户ID
			WechatUser existUser = wechatUserDAO
					.selectByRoutineOpenid(strOpenID);
			ord.setCreateby(existUser.getUid());
			onlineReservationDetailsService.save(ord);
			// 生成票据信息
			Map<String, Object> result = onlineService.GetTicketManage(ord
					.getType().toString());
			if (result == null) {
				return new ResultModel(WConst.ERROR, "线上预约功能暂未开通", null);
			}
			result.put("type", ord.getType());
			result.put("ticketnum", strPiaoHao);
			result.put("session", sessionId);
			// 普通门票用预约日作为可进场日
			if (ord.getType() == 0) {
				result.put("startdate", ord.getExhibitiondate());
				result.put("enddate", ord.getExhibitiondate());
			}

			result.put("reservationid", ord.getId());
			onlineService.insertTicketInfo(result);

			// 预约成功更新用户身份为观众
			Map<String, Object> par = new HashMap<String, Object>();
			par.put("uid", existUser.getUid());
			par.put("usertypeid", "1");
			par.put("usertype", "观众");
			onlineService.updateWechatUser(par);
			
			
			//加入门禁推送队列
			Map<String,Object> obj = new HashMap<String,Object>();
			obj.put("session", sessionId);
			obj.put("ticketnum",strPiaoHao);
			obj.put("name",ord.getName());
			obj.put("islimit",1);
			obj.put("limits",1);
			obj.put("isvip",ord.getType());
			obj.put("idno",ord.getCardnumber());
			obj.put("picture","");
			if(ord.getType()==0){
				obj.put("startdate",ord.getExhibitiondate().replace("-", ""));
				obj.put("enddate",ord.getExhibitiondate().replace("-", ""));
			}
			else{
				obj.put("startdate","20201018");
				obj.put("enddate","20201022");
			}
			commonDao.AddCardPushInfo(obj);
			
			sysOperationLogService.CreateEntity("添加网上预约信息", sessionId, 1, 0,
					ord.getId(), JSONObject.toJSONString(ord));

			return new ResultModel(WConst.SUCCESS, "预约成功", null);
		} catch (Exception ee) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			ee.printStackTrace();
			return new ResultModel(WConst.ERROR, "系统异常", null);
		}
	}

	/**
	 * 通过code从腾讯取OpenId,同时更新用户信息到数据库
	 */
	@RequestMapping(value = { "/{language}/getOpenId" })
	@ResponseBody
	public ResultModel getOpenId(@PathVariable String language, String code,
			WechatUser wechatUser, HttpSession session) {
		try {
			WechatUser user = account.getWechatOpenIDByCode(code);
			wechatUser.setRoutineOpenid(user.getRoutineOpenid());
			wechatUser.setSessionKey(user.getSessionKey());
			WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(user
					.getRoutineOpenid());
			Integer uid = 0;
			if (existUser == null) {
				wechatUser.setAddTime(DateTime.now().getMinutes());
				wechatUser.setMemberId(0);
				wechatUser.setUserType("游客");
				wechatUser.setUserTypeId(0);
				wechatUserDAO.insert(wechatUser);
				uid = wechatUser.getUid();
				// 更新到腾讯云
				tencentUtil.updateIMUserProfile(wechatUser);
			} else {
				existUser.setHeadimgurl(wechatUser.getHeadimgurl());
				existUser.setNickname(wechatUser.getNickname());
				existUser.setRoutineOpenid(user.getRoutineOpenid());
				existUser.setSessionKey(user.getSessionKey());
				wechatUserDAO.updateByRoutineOpenidSelective(existUser);
				uid = existUser.getUid();
				// 更新到腾讯云
				tencentUtil.updateIMUserProfile(existUser);
			}

			session.setAttribute("openId", user.getRoutineOpenid());

			session.setAttribute("wechatUser", user);
			
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("token", session.getId());
			Long time = System.currentTimeMillis()
					+ session.getMaxInactiveInterval() * 1000;
			res.put("expiresTime", time);
			res.put("uid", uid);
			return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, res);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultModel(WConst.ERROR, "网络错误，请稍后重试", e);
		}
	}

	@RequestMapping("/{language}/decryptPhoneNumber")
	@ResponseBody
	public ResultModel decryptPhoneNumber(String encryptedData, String iv,
			HttpSession session) { // 这三个参数是前端传过来的数据
		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		String phoneNumber = "";
		try {
			// 1.拿session_key
			WechatUser existUser = wechatUserDAO.selectByRoutineOpenid(openId
					.toString());
			String session_key = existUser.getSessionKey();
			String result = "";

			AES aes = new AES();
			byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData),
					Base64.decodeBase64(session_key), Base64.decodeBase64(iv));
			if (null != resultByte && resultByte.length > 0) {
				result = new String(WxPKCS7Encoder.decode(resultByte));
				JSONObject jsonObject = JSONObject.parseObject(result);
				phoneNumber = jsonObject.get("phoneNumber").toString()
						.replaceAll("\"", "");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResultModel(WConst.ERROR, "手机号获取失败", openId);

		}
		return new ResultModel(WConst.SUCCESS, "手机号获取成功", phoneNumber);
	}

	/**
	 * 身份认证接口
	 */
	@RequestMapping(value = { "/{language}/identification" })
	@ResponseBody
	public ResultModel identification(@PathVariable String language,
			WechatUser wechatUser, HttpSession session) {
		setCommonContent();

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		String sessionId = this.exhibitionInfo.get("sessionId").toString();
		return ebsPersonnelcardService.updateRouterPersonRole(
				wechatUser.getCardnumber(), wechatUser.getName(),
				Integer.parseInt(sessionId), session);
	}

	/**
	 * 管理员认证接口
	 */
	@RequestMapping(value = { "/{language}/identification/member" })
	@ResponseBody
	public ResultModel identificationMember(String username, String password,
			Integer memberType, HttpServletRequest request, HttpSession session) {
		setCommonContent();

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		String sessionId = this.exhibitionInfo.get("sessionId").toString();
		return memberService.identificationMember(username, password,
				sessionId, memberType, request, session);
	}

	/**
	 * 获取用户身份接口
	 */
	@RequestMapping(value = { "/{language}/identification/get" })
	@ResponseBody
	public ResultModel getIdentification(HttpServletRequest request,
			HttpSession session) {
		setCommonContent();

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		String sessionId = this.exhibitionInfo.get("sessionId").toString();
		return ebsPersonnelcardService.getRouterPersonRole(
				Integer.parseInt(sessionId), session);
	}

	/**
	 * 更新微信提上来的用户信息
	 * 
	 * @param language
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/wechatuser/update" })
	@ResponseBody
	public ResultModel updateWechatUser(@PathVariable String language,
			WechatUser user, HttpSession session) {
		setCommonContent();
		ResultModel result = null;

		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			result = WConst.RELOGINJSON;
			return result;
		}
		user.setRoutineOpenid(openId.toString());

		wechatUserDAO.updateByRoutineOpenidSelective(user);

		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, null);
	}

	/**
	 * 更新微信提上来的用户信息
	 * 
	 * @param language
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/wechatuser/get" })
	@ResponseBody
	public ResultModel getWechatUser(@PathVariable String language,
			HttpSession session) {
		try {
			setCommonContent();
			ResultModel result = null;
			Object openId = session.getAttribute("openId");
			if (openId == null || openId.equals("")) {// 未登录活登录超时，直接返回要求重新弄登录
				result = WConst.RELOGINJSON;
				return result;
			}
			WechatUser userinfo = wechatUserDAO.selectByRoutineOpenid(openId
					.toString());

			PageModel page = new PageModel();
			page.setMemberid(userinfo.getUid());
			page.setSession(exhibitionInfo.get("sessionId").toString());
			// 收藏企业数
			page.setAct(0);
			int iCompanyCount = onlineService.GetMyFavoritesCount(page);
			// 收藏产品数
			page.setAct(1);
			int iProductCount = onlineService.GetMyFavoritesCount(page);
			// 消息
			int iMessageCount = 8;
			userinfo.setCompanyCount(iCompanyCount);
			userinfo.setProductCount(iProductCount);
			userinfo.setMessageCount(iMessageCount);

			return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS,
					userinfo);
		} catch (Exception e) {
			return new ResultModel(WConst.ERROR, WConst.QUERYFAILD, e);
		}
	}

	/**
	 * 获取新闻列表
	 * 
	 * @param language
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/{language}/newslist" })
	@ResponseBody
	public ResultModel newslist(@PathVariable String language, NewsParams news)
			throws Exception {
		setCommonContent();
		ResultModel NewsList = onlineNewsService.GetNewsList(news);
		return NewsList;
	}

	/**
	 * 获取新闻列表顶部新闻及类别
	 * 
	 * @param language
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/GetTopNewsForList" })
	@ResponseBody
	public ResultModel GetTopNewsForList(@PathVariable String language,
			HttpSession session, HttpServletRequest request) {
		setCommonContent();
		Map<String, Object> resMap = onlineNewsService.GetTopNewsForList();
		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, resMap);
	}

	/**
	 * 获取新闻详细
	 * 
	 * @param language
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/newsinfo/{newsid}" })
	@ResponseBody
	public ResultModel newsinfo(@PathVariable String language,
			@PathVariable String newsid, HttpSession session,
			HttpServletRequest request) {
		setCommonContent();

		Map<String, Object> map = onlineNewsService.GetNewsInfo(newsid);

		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, map);
	}

	/**
	 * 获取新闻详细
	 * 
	 * @param language
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/newsinfo-{newsid}.html" })
	public String webNewInfo(@PathVariable String language,
			@PathVariable String newsid, Model model, HttpSession session,
			HttpServletRequest request) {
		setCommonContent();
		Map<String, Object> map = onlineNewsService.GetNewsInfo(newsid);
		model.addAttribute("new", map);
		return "router/" + language + "/new";
	}

	/**
	 * 会员登陆
	 * 
	 * @param Member
	 * @return
	 */
	@RequestMapping(value = "/{language}/login")
	@ResponseBody
	public ResultModel login(String username, String password,
			Integer memberType, HttpServletRequest request, HttpSession session) {
		setCommonContent();
		String sessionId = this.exhibitionInfo.get("sessionId").toString();
		// 小程序不需要验证码
		return memberService.login(username, password, "Maocuh@abcd",
				sessionId, memberType, request, session);
	}

	@RequestMapping(value = { "/{language}/member" })
	@ResponseBody
	public ResultModel member(@PathVariable String language, HttpSession session) {
		// 小程序相关页面配置
		String productPage = "/pages/list/products/products";
		String carPage = "/pages/list/cars/cars";
		String personPage = "/pages/list/applycard/applycard";
		String historyEnterprisePage = "/pages/listhistory/enterprises/enterprises";
		String historyPersonPage = "/pages/listhistory/members/members";
		String historyCarPage = "/pages/listhistory/cars/cars";
		String enterprisePage = "/pages/list/enterprises/enterprises";

		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		String sessionId = exhibitionInfo.get("sessionId").toString();
		String memberTypeToken = getMemberTypeToken(member.getMemberType());
		// 1.获取可办证件类型
		List<CmCertificateType> certificateTypes = memberService
				.getMemberCertificateType(member, sessionId);

		// 获取是否有权限办理室内外参展证
		if (member.getMemberType().equals(Member.MEMBER_TYPE_EXHIBITOR_CODE)) {
			// 获取当前信息填报和展位分配情况
			Map<String, Object> applyInfo = memberService
					.getExhibitorApplyInfo(session, Integer.parseInt(sessionId));

		}
		// 2.开始添加菜单
		List<Map<String, Object>> menus = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> child = new ArrayList<Map<String, Object>>();
		// 2.1申请展位
		if (member.getMemberType().equals(Member.MEMBER_TYPE_EXHIBITOR_CODE)) {
			Map<String, Object> apply = new HashMap<String, Object>();
			apply.put("title", "申请展位");
			apply.put("type", 1);
			apply.put("url", memberTypeToken + "-apply.html");
			apply.put("child", child);
			menus.add(apply);
		}
		// 2.2交易团参展商管理
		if (member.getMemberType().equals(Member.MEMBER_TYPE_TRADE_CODE)) {
			for (CmCertificateType cardModel : certificateTypes) {
				if (cardModel.getType().equals(0)
						&& cardModel.getIsexhibitor().equals(1)) {// 有参展证的办理权限，才加此菜单
					Map<String, Object> enterprises = new HashMap<String, Object>();
					enterprises.put("title", "参展商管理");
					enterprises.put("type", -1);
					enterprises.put("url", "");
					List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();
					Map<String, Object> enterprise = new HashMap<String, Object>();
					enterprise.put("title", "添加参展企业");
					enterprise.put("type", 0);
					enterprise.put("url", enterprisePage);
					enterprise.put("modifyUrl", memberTypeToken
							+ "-enterprise.html");
					childs.add(enterprise);
					enterprises.put("child", childs);
					menus.add(enterprises);
				}
			}
		}
		// 2.3添加产品
		if (member.getMemberType().equals(Member.MEMBER_TYPE_TRADE_CODE)
				|| member.getMemberType().equals(
						Member.MEMBER_TYPE_EXHIBITOR_CODE)
				|| member.getMemberType().equals(
						Member.MEMBER_TYPE_EXHIBITOR_ONLINE_CODE)) {
			Map<String, Object> product = new HashMap<String, Object>();
			product.put("title", "产品管理");
			product.put("type", 0);
			product.put("url", productPage);
			product.put("modifyUrl", memberTypeToken + "-product.html");
			product.put("child", child);
			menus.add(product);
		}
		// 2.4零散参展证办理
		if (member.getMemberType().equals(Member.MEMBER_TYPE_EXHIBITOR_CODE)) {

			Map<String, Object> permission = memberService
					.getExihibitorTypePermission(member.getMemberId(),
							Integer.parseInt(sessionId));
			Object canOutCertification = permission.get("canOutCertification");
			Object canInCertification = permission.get("canInCertification");

			Map<String, Object> joinCard = new HashMap<String, Object>();
			joinCard.put("title", "参展证办理");
			joinCard.put("type", -1);
			joinCard.put("url", "");

			List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();
			for (CmCertificateType cardModel : certificateTypes) {
				if (cardModel.getType().equals(0)
						&& cardModel.getIsexhibitor().equals(1)) {
					if ((boolean) canInCertification) {
						Map<String, Object> card = new HashMap<String, Object>();
						String title = "申办参展证（室内展场）";

						card.put("title", title);
						card.put("type", 0);
						card.put("url",
								personPage + "?cardtype=" + cardModel.getId()
										+ "&isout=0");
						card.put("modifyUrl", memberTypeToken
								+ "-personpapers-" + cardModel.getId()
								+ "i.html");
						childs.add(card);
					}
					if ((boolean) canOutCertification) {
						Map<String, Object> card = new HashMap<String, Object>();
						String title = "申办参展证（室外展场）";

						card.put("title", title);
						card.put("type", 0);
						card.put("url",
								personPage + "?cardtype=" + cardModel.getId()
										+ "&isout=1");
						card.put("modifyUrl", memberTypeToken
								+ "-personpapers-" + cardModel.getId()
								+ "o.html");
						childs.add(card);
					}
				}
			}
			joinCard.put("child", childs);

			menus.add(joinCard);
		}
		// 2.5添加人员证件

		Map<String, Object> Persons = new HashMap<String, Object>();
		Persons.put("title", "人员证件");
		Persons.put("url", "");
		List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();
		for (CmCertificateType cardModel : certificateTypes) {
			if (cardModel.getType().equals(0)) {// 人员证件
				Map<String, Object> person = new HashMap<String, Object>();
				person.put("title", cardModel.getChinesename());
				person.put("type", 0);
				person.put("url", personPage + "?cardtype=" + cardModel.getId()
						+ "&isout=1");
				person.put("modifyUrl", memberTypeToken + "-personpapers-"
						+ cardModel.getId() + ".html");
				childs.add(person);
			}
		}
		Persons.put("child", childs);

		menus.add(Persons);
		// 2.6添加车辆证件
		if (member.getMemberType().equals(Member.MEMBER_TYPE_TRADE_CODE)
				|| member.getMemberType()
						.equals(Member.MEMBER_TYPE_REPORT_CODE)
				|| member.getMemberType().equals(
						Member.MEMBER_TYPE_EXHIBITOR_CODE)
				|| member.getMemberType().equals(
						Member.MEMBER_TYPE_DECORATOR_CODE)) {

			Map<String, Object> Cars = new HashMap<String, Object>();
			Cars.put("title", "车辆证件");
			Cars.put("type", -1);
			Cars.put("url", "");
			List<Map<String, Object>> carChild = new ArrayList<Map<String, Object>>();
			for (CmCertificateType cardModel : certificateTypes) {
				if (cardModel.getType().equals(1)) {// 人员证件
					Map<String, Object> car = new HashMap<String, Object>();
					car.put("title", cardModel.getChinesename());
					car.put("type", 0);
					car.put("url", carPage + "?cardtype=" + cardModel.getId()
							+ "&isout=1");
					car.put("modifyUrl", memberTypeToken + "-vehiclecard-"
							+ cardModel.getId() + ".html");
					carChild.add(car);
				}
			}
			Cars.put("child", carChild);

			menus.add(Cars);
		}
		// 2.7历届信息提取
		if (member.getMemberType().equals(Member.MEMBER_TYPE_TRADE_CODE)
				|| member.getMemberType()
						.equals(Member.MEMBER_TYPE_REPORT_CODE)
				|| member.getMemberType().equals(
						Member.MEMBER_TYPE_EXHIBITOR_CODE)
				|| member.getMemberType().equals(
						Member.MEMBER_TYPE_DECORATOR_CODE)) {
			Map<String, Object> history = new HashMap<String, Object>();
			history.put("title", "历届信息提取");
			history.put("type", -1);
			history.put("url", "");
			List<Map<String, Object>> historyChild = new ArrayList<Map<String, Object>>();
			if (member.getMemberType().equals(Member.MEMBER_TYPE_TRADE_CODE)) {
				Map<String, Object> companyHistory = new HashMap<String, Object>();
				companyHistory.put("title", "企业信息提取");
				companyHistory.put("type", 0);
				companyHistory.put("url", historyEnterprisePage);
				companyHistory.put("modifyUrl", memberTypeToken
						+ "-historyenterprise" + ".html");
				historyChild.add(companyHistory);
			}
			Map<String, Object> historypersoncard = new HashMap<String, Object>();
			historypersoncard.put("title", "人员证件提取");
			historypersoncard.put("type", 0);
			historypersoncard.put("url", historyPersonPage);
			historypersoncard.put("modifyUrl", memberTypeToken
					+ "-historypersoncard" + ".html");
			historyChild.add(historypersoncard);

			Map<String, Object> historycarcard = new HashMap<String, Object>();
			historycarcard.put("title", "车辆证件提取");
			historycarcard.put("type", 0);
			historycarcard.put("url", historyCarPage);
			historycarcard.put("modifyUrl", memberTypeToken + "-historycarcard"
					+ ".html");
			historyChild.add(historycarcard);

			history.put("child", historyChild);

			menus.add(history);
		}
		// 2.8取证报表
		Map<String, Object> report = new HashMap<String, Object>();
		report.put("title", "取证报表");
		report.put("type", 1);
		report.put("url", memberTypeToken + "-report.html");
		menus.add(report);
		// 2.10汇总信息
		Map<String, Object> sum = new HashMap<String, Object>();
		sum.put("title", "汇总信息");
		report.put("type", 1);
		sum.put("url", memberTypeToken + "-info.html");
		menus.add(sum);
		// 2.11修改信息

		if (member.getMemberType().equals(Member.MEMBER_TYPE_TRADE_CODE)
				|| member.getMemberType()
						.equals(Member.MEMBER_TYPE_REPORT_CODE)) {
			Map<String, Object> agent = new HashMap<String, Object>();
			agent.put("title", "修改信息");
			agent.put("type", 1);
			agent.put("url", memberTypeToken + "-agent.html");
			menus.add(agent);
		}
		// 2.12修改企业信息
		if (member.getMemberType().equals(
				Member.MEMBER_TYPE_EXHIBITOR_ONLINE_CODE)
				|| member.getMemberType().equals(
						Member.MEMBER_TYPE_EXHIBITOR_CODE)) {
			Map<String, Object> enterprise = new HashMap<String, Object>();
			enterprise.put("title", "修改企业信息");
			enterprise.put("type", 1);
			enterprise.put("url", memberTypeToken + "-company.html");
			menus.add(enterprise);
		}
		// 2.13修改密码
		if (member.getMemberType().equals(
				Member.MEMBER_TYPE_EXHIBITOR_ONLINE_CODE)
				|| member.getMemberType().equals(
						Member.MEMBER_TYPE_EXHIBITOR_CODE)) {
			Map<String, Object> company = new HashMap<String, Object>();
			company.put("title", "修改密码");
			company.put("type", 1);
			company.put("url", memberTypeToken + "-password.html");
			menus.add(company);
		}
		result = new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, menus);
		return result;

	}

	/**
	 * 获取本届的车辆证件类型
	 * 
	 * @param language
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/cardtype/car" })
	@ResponseBody
	public ResultModel cardtypeCar(@PathVariable String language,
			HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		String sessionId = exhibitionInfo.get("sessionId").toString();
		String memberTypeToken = getMemberTypeToken(member.getMemberType());

		// 1.获取可办证件类型
		List<CmCertificateType> certificateTypes = memberService
				.getMemberCertificateType(member, sessionId);

		List<CmCertificateType> returnTypes = new ArrayList<CmCertificateType>();
		for (CmCertificateType card : certificateTypes) {
			if (card.getType().equals(1)) {
				returnTypes.add(card);
			}
		}

		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, returnTypes);
	}

	/**
	 * 获取本届的人员证件类型
	 * 
	 * @param language
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/cardtype/person" })
	@ResponseBody
	public ResultModel cardtypePerson(@PathVariable String language,
			HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		String sessionId = exhibitionInfo.get("sessionId").toString();
		String memberTypeToken = getMemberTypeToken(member.getMemberType());

		// 1.获取可办证件类型
		List<CmCertificateType> certificateTypes = memberService
				.getMemberCertificateType(member, sessionId);

		List<CmCertificateType> returnTypes = new ArrayList<CmCertificateType>();
		for (CmCertificateType card : certificateTypes) {
			if (card.getType().equals(0)) {
				returnTypes.add(card);
			}
		}

		return new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, returnTypes);
	}
	
	@RequestMapping(value= {"/{language}/weui/{type}-log.html"})
	public String reportLogin(@PathVariable String language,@PathVariable String type,Model model,HttpSession session) {

		setCommonContent(model);
		model.addAttribute("language", language);
		model.addAttribute("type", type);
		model.addAttribute("pageName", "login");
		model.addAttribute("typeId", getMemberTypeId(type));
		model.addAttribute("typeName", getMemberTypeName(type, language));
		Integer menuId = 20;
		switch (language) {
		case "en":
			menuId = 234;
			break;
		case "jp":
			menuId = 234;
			break;
		case "kr":
			menuId = 234;
			break;
		case "ru":
			menuId = 234;
			break;
		}
		model.addAttribute("menuId", menuId);
		String templateName = "weui/log";
		templateName = "router/"+language+"/"+templateName;
		return templateName;
	}
	
	@RequestMapping(value= {"/{language}/weui/{type}-validate.html"})
	public String validate(@PathVariable String language,@PathVariable String type,
			Model model,HttpServletResponse response,HttpServletRequest request,HttpSession session) {

		if(session.getAttribute("member")==null) {
			try {
				request.getRequestDispatcher(type+"-log.html").forward(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		setCommonContent(model);
		model.addAttribute("language", language);
		model.addAttribute("type", type);
		model.addAttribute("pageName", "login");
		model.addAttribute("typeId", getMemberTypeId(type));
		model.addAttribute("typeName", getMemberTypeName(type, language));
		String templateName = "weui/validate";
		templateName = "router/"+language+"/"+templateName;
		return templateName;
	}
	
	@RequestMapping(value= {"/{language}/weui/{type}-list-{phone}.html"})
	public String cardList(@PathVariable String language,@PathVariable String type,@PathVariable String phone,
			Model model,HttpServletResponse response,HttpServletRequest request,HttpSession session) {

		Object storageCode = session.getAttribute(SMSUtil.REGIST_CODEKEY);
		if(session.getAttribute("member")==null||storageCode==null) {
			try {
				request.getRequestDispatcher(type+"-log.html").forward(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		//1.获取取证码
		Member member = (Member) session.getAttribute("member");
		if(member.getMemberType().equals(0)||member.getMemberType().equals(1)) {
			PimAgent agent = agentDAO.getAgentByMemberIdAndSessionId(member.getMemberId(), member.getMemberSessionId());
			model.addAttribute("title", agent.getName()+agent.getCompanyname());
		}
		else {
			EbsCompanyinfo company = ebsCompanyinfoDao
					.getCompanyByMemberIdAndSessionId(member.getMemberId(),
							member.getMemberSessionId());
			model.addAttribute("title", company.getChinesename());
		}
		Map<String, Object> params = new HashMap<String,Object>();
		
		params.put("oldsession", member.getMemberSessionId());
		params.put("memberid", member.getMemberId());
		
		
		List<MemberSession> memberSessions = memberSessionService.GetMemberSession(params);
		if(memberSessions.size()>0) {
			model.addAttribute("reportCode", memberSessions.get(0).getMiddleid());
		}
		//2.获取可办证件类型

		List<CmCertificateType> certificateTypes = memberService.getMemberCertificateType(member, member.getMemberSessionId().toString());
		List<Map<String,Object>> cards = new ArrayList<Map<String,Object>>();
		//遍历所有证件类型
		for(CmCertificateType cardType : certificateTypes) {
			//组装查询条件
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("index",0);
			map.put("size", 10000);
			map.put("agent", member.getMemberId());
			map.put("cardtype",cardType.getId());
			map.put("session", cardType.getSession());
			map.put("isplastic", "1");
			map.put("isforensics", "0");
			Map<String,Object> item = new HashMap<String,Object>();
			///车辆证件
			if(cardType.getType().equals(1)) {
				List<EbsVehiclecard> list = ebsVehiclecardDao.list(map);
				if(list.size()>0) {
					item.put("cardType", cardType);
					item.put("cards", list);
				}
				cards.add(item);
			}
			else {
				List<EbsPersonnelcard> list = ebsPersonnelcardDao.list(map);
				if(list.size()>0) {
					item.put("cardType", cardType);
					item.put("cards", list);
				}
				cards.add(item);
			}
		}
		model.addAttribute("cards", cards);
		//获取取证时间
		int num = member.getMemberId() % 2;
		String time = "上午";
		if(num==0) {
			time = "下午";
		}
		model.addAttribute("time", time);
		setCommonContent(model);
		model.addAttribute("language", language);
		model.addAttribute("type", type);
		model.addAttribute("typeId", getMemberTypeId(type));
		model.addAttribute("typeName", getMemberTypeName(type, language));
		model.addAttribute("phone", phone);
		model.addAttribute("member", member);
		String templateName = "weui/list";
		templateName = "router/"+language+"/"+templateName;
		return templateName;
	}

	/**	 * 以下是暂时不用的方法	 */
	@RequestMapping(value = { "/{language}/{type}-resetpassword.html" })
	public String findPassword(@PathVariable String language,
			@PathVariable String type, Model model, HttpSession session) {
		setCommonContent(model);
		model.addAttribute("language", language);
		model.addAttribute("type", type);
		model.addAttribute("pageName", "login");
		model.addAttribute("typeId", getMemberTypeId(type));
		model.addAttribute("typeName", getMemberTypeName(type, language));

		return "resetpassword";
	}

	@RequestMapping(value = { "/{language}/{type}-regist.html" })
	public String regist(@PathVariable String language,
			@PathVariable String type, Model model, HttpSession session) {
		setCommonContent(model);

		// 公司类型和行业类型
		Map<String, Object> map = new HashMap<>();
		map.put("index", 0);
		map.put("size", 10000);
		List<SysCompanyType> companytypes = sysCompanyTypeService.list(map);
		List<SysIndustry> industries = sysIndustryService.list(map);
		model.addAttribute("companytypes", companytypes);
		model.addAttribute("industries", industries);

		model.addAttribute("pageName", "regist");
		model.addAttribute("language", language);
		model.addAttribute("type", type);
		model.addAttribute("typeId", getMemberTypeId(type));
		model.addAttribute("typeName", getMemberTypeName(type, language));

		// 展区列表
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("session", exhibitionInfo.get("sessionId").toString());

		List<Map<String, Object>> exhibitionArea = commonService
				.GetExhibitionArea(filter);
		model.addAttribute("exhibitionArea", exhibitionArea);
		model.addAttribute("token", session.getId());
		String templateName = "router";
		templateName = templateName + "/" + language;
		templateName = templateName + "/regist";
		return templateName;
	}

	/*
	 * @RequestMapping(value= {"/{language}/{type}-online.html"}) public String
	 * online(@PathVariable String language,@PathVariable String type,Model
	 * model,HttpSession session,HttpServletRequest request, HttpServletResponse
	 * response) { safeCheck(language,type,session,request,response);
	 * setCommonContent(model); model.addAttribute("pageName", "regist");
	 * model.addAttribute("language", language); model.addAttribute("type",
	 * type); model.addAttribute("typeId", getMemberTypeId(type)); Member member
	 * = (Member) session.getAttribute("member"); member.setMemberPassword("");
	 * try{ if(member!=null) {
	 * model.addAttribute("member",mapper.writeValueAsString(member));
	 * model.addAttribute("memberPojo",member); } } catch(Exception e) {
	 * model.addAttribute("memberPojo",null); model.addAttribute("member","{}");
	 * } String sessionId = exhibitionInfo.get("sessionId").toString();
	 * List<CmCertificateType> certificateTypes =
	 * memberService.getMemberCertificateType(member, sessionId);
	 * model.addAttribute("certificateTypes", certificateTypes); try {
	 * model.addAttribute("certificateTypesPojo",
	 * mapper.writeValueAsString(certificateTypes)); } catch
	 * (JsonProcessingException e) { model.addAttribute("certificateTypesPojo",
	 * "{}"); } //获取是否有权限办理室内外参展证 if(type.equals("exhibitor")) {
	 * //获取当前信息填报和展位分配情况 model.addAttribute("applyInfo",
	 * memberService.getExhibitorApplyInfo(session,
	 * Integer.parseInt(sessionId)));
	 * 
	 * } String templateName = "online"; if(!"cn".equals(language)) templateName
	 * = language+"/"+templateName; return templateName;
	 * 
	 * }
	 * 
	 * @RequestMapping(value= {"/{language}/{type}-apply.html"}) public String
	 * apply(@PathVariable String language,@PathVariable String type,Model
	 * model,HttpSession session,HttpServletRequest request, HttpServletResponse
	 * response) { setCommonContent(model);
	 * safeCheck(language,type,session,request,response);
	 * model.addAttribute("pageName", "regist"); model.addAttribute("language",
	 * language); model.addAttribute("type", type); model.addAttribute("typeId",
	 * getMemberTypeId(type)); ResultModel resultModel =
	 * tradinggroupboothallocationService
	 * .selectAreaAndShowroomType(Integer.parseInt
	 * (exhibitionInfo.get("sessionId").toString())); Map<String,Object> result
	 * = (Map<String, Object>) resultModel.getResult();
	 * if(resultModel.getStatus()==WConst.SUCCESS) { model.addAttribute("areas",
	 * result.get("areas")); model.addAttribute("inputInfo",
	 * result.get("inputInfo")); } else { model.addAttribute("areas", new
	 * ArrayList<Map<String,Object>>()); model.addAttribute("inputInfo", new
	 * ArrayList<Map<String,Object>>()); } Member member = (Member)
	 * session.getAttribute("member"); Integer memberId = member.getMemberId();
	 * Integer sessionId = (Integer) this.exhibitionInfo.get("sessionId");
	 * Map<String,Object> boothProcess = memberService.getBoothProcess(memberId,
	 * sessionId); Integer exhibitionAuditStatus = (Integer)
	 * boothProcess.get("exhibitionAuditStatus");
	 * if(exhibitionAuditStatus!=null&&exhibitionAuditStatus==1) {//已审核通过
	 * model.addAttribute("applyInfo",
	 * applyService.getApply(memberId,sessionId).getResult()); return
	 * "apply-result"; } else { Map<String,Object> company = (Map<String,
	 * Object>) companyService.getMemberCompany(session).getResult();
	 * if(company.get("company") instanceof EbsCompanyinfo) {
	 * model.addAttribute("company", company.get("company")); } return "apply";
	 * } }
	 * 
	 * @RequestMapping(value= {"/{language}/{type}-product.html"}) public String
	 * product(@PathVariable String language,@PathVariable String type,Model
	 * model,HttpSession session,HttpServletRequest request, HttpServletResponse
	 * response) { setCommonContent(model);
	 * safeCheck(language,type,session,request,response);
	 * model.addAttribute("pageName", "product"); model.addAttribute("language",
	 * language); model.addAttribute("type", type);
	 * 
	 * //1.获取产品类型 Map<String,Object> filter = new HashMap<String,Object>();
	 * filter.put("dicParentid", 90); filter.put("useable", 1);
	 * List<SystemDictionaries> productMenu =
	 * systemDictionariesService.findByMap(filter);
	 * model.addAttribute("productMenu", productMenu);
	 * 
	 * //1.获取产品单位 Map<String,Object> filter1 = new HashMap<String,Object>();
	 * filter.put("dicParentid", 105); filter.put("useable", 1);
	 * List<SystemDictionaries> productMenu1 =
	 * systemDictionariesService.findByMap(filter);
	 * model.addAttribute("productMenu1", productMenu1);
	 * 
	 * //1.获取产品单位 Map<String,Object> filter2 = new HashMap<String,Object>();
	 * filter.put("dicParentid", 100); filter.put("useable", 1);
	 * List<SystemDictionaries> productMenu2 =
	 * systemDictionariesService.findByMap(filter);
	 * model.addAttribute("productMenu2", productMenu2);
	 * 
	 * //2.获取公司列表 if(type.equals("exhibitor")) { Member member = (Member)
	 * session.getAttribute("member"); EbsCompanyinfo company =
	 * ebsCompanyinfoDao.getLastCompanyByMemberId(member.getMemberId());
	 * model.addAttribute("company",company); } else { Map<String,Object> map =
	 * new HashMap<String,Object>(); map.put("start",0); map.put("limit",
	 * 10000); map.put("sessionIds",(Integer)
	 * this.exhibitionInfo.get("sessionId")); Member member = (Member)
	 * session.getAttribute("member"); map.put("memberId",member.getMemberId());
	 * List<Map<String, Object>> companys =
	 * ebsCompanyinfoDao.getTraddingGroupCompanys(map);
	 * model.addAttribute("companys",companys); } return "product"; }
	 * 
	 * @RequestMapping(value=
	 * {"/{language}/{type}-personpapers-{paperType}.html"}) public String
	 * personpapers(@PathVariable String language,@PathVariable String
	 * type,@PathVariable String paperType,Model model,HttpSession
	 * session,HttpServletRequest request, HttpServletResponse response,Integer
	 * companyId,String companyName) { setCommonContent(model);
	 * safeCheck(language,type,session,request,response);
	 * 
	 * Member member = (Member) session.getAttribute("member"); //2.公司类型和行业类型
	 * Map<String, Object> map = new HashMap<>(); map.put("index", 0);
	 * map.put("size", 10000); List<SysCompanyType> companytypes =
	 * sysCompanyTypeService.list(map); List<SysIndustry> industries =
	 * sysIndustryService.list(map); model.addAttribute("companytypes",
	 * companytypes); model.addAttribute("industries", industries);
	 * model.addAttribute("pageName", "personpapers");
	 * model.addAttribute("language", language); model.addAttribute("type",
	 * type); model.addAttribute("typeName", getMemberTypeName(type,language));
	 * Integer intPaperType = 0; if(NumberUtil.isNumber(paperType)) {
	 * intPaperType = Integer.parseInt(paperType); }else { intPaperType =
	 * Integer.parseInt(paperType.substring(0, paperType.length()-1));
	 * if(paperType.substring(paperType.length()-1,
	 * paperType.length()).equals("o")) { model.addAttribute("isOut", 1); } else
	 * { model.addAttribute("isOut", 0); } } model.addAttribute("paperType",
	 * intPaperType); CmCertificateType certificateModal =
	 * certificateType.findById(intPaperType);
	 * model.addAttribute("certificateModal",certificateModal);
	 * model.addAttribute("isExhibitor",certificateModal.getIsexhibitor());
	 * //3.设置此证件类型输入数量限制
	 * 
	 * List<Map<String,Object>> cardProcess =
	 * memberService.getCardProcess(session
	 * ,Integer.parseInt(exhibitionInfo.get("sessionId").toString())); Long
	 * limit = 0L; for(Map<String,Object> card : cardProcess) {
	 * if(card.get("cardTypeId").equals(intPaperType)) {//获取当前证件类型
	 * if(member.getMemberType().equals(Member.MEMBER_TYPE_PURCHASER_CODE)||
	 * //采购商的采购商证是要加限制的 certificateModal.getIsexhibitor().equals(1)||//是参展证
	 * member.getMemberType().equals(Member.MEMBER_TYPE_TRADE_CODE)||//或者是交易团
	 * member.getMemberType().equals(Member.MEMBER_TYPE_REPORT_CODE)) {//记者类型
	 * Long addedCount = Long.parseLong(card.get("cardCount").toString());
	 * model.addAttribute("addedCount",addedCount);
	 * 
	 * Long canCount = 0L; if(card.get("inputCount")!=null) { canCount =
	 * Long.parseLong(card.get("inputCount").toString()); } limit = canCount +
	 * addedCount; model.addAttribute("limit",limit);
	 * model.addAttribute("canCount",canCount); } } }
	 * 
	 * //4.证件办理截止日期 String cardStopDate = "";
	 * if(type.equals("exhibitor")&&certificateModal
	 * .getChinesename().equals("参展证")) {//零散参展商的参展证 cardStopDate =
	 * this.exhibitionInfo.get("certificatesExhibitorsEndDate").toString(); }
	 * else if(type.equals("delegation")||type.equals("reporter")){ PimAgent
	 * agent = agentSerivce.getAgentByMemberIdAndSessionId(member.getMemberId(),
	 * Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
	 * if(agent.getOnsitecertification().equals(1)) {//现场制证 cardStopDate =
	 * null;//不设截止日期 } } if(cardStopDate!=null &&
	 * cardStopDate.equals("")&&(certificateModal
	 * .getChinesename().contains("参展证B")||
	 * certificateModal.getChinesename().contains("布撤展证")||
	 * certificateModal.getChinesename().contains("采购商证")||
	 * certificateModal.getChinesename().contains("布撤展车证"))) { cardStopDate =
	 * this.exhibitionInfo.get("certificatesFprEndDate").toString(); } else
	 * if(cardStopDate!=null && cardStopDate.equals("")){ cardStopDate =
	 * this.exhibitionInfo.get("certificatesEndDate").toString(); }
	 * model.addAttribute("cardStopDate",cardStopDate); //5.是否超过限制时间 Boolean
	 * isTimeout = false; if(cardStopDate!=null) { try { long now =
	 * System.currentTimeMillis(); SimpleDateFormat simpleDateFormat = new
	 * SimpleDateFormat("yyyy-MM-dd");//注意月份是MM Date date =
	 * simpleDateFormat.parse(cardStopDate); Calendar c =
	 * Calendar.getInstance(); c.setTime(date); c.add(Calendar.DATE, 1); if((now
	 * - c.getTimeInMillis()) > 0) { isTimeout = true; } } catch (ParseException
	 * e) { e.printStackTrace(); } } model.addAttribute("isTimeout",isTimeout);
	 * //6.设置非交易团和记者证的公司名称
	 * if(!type.equals("delegation")&&!type.equals("reporter")){ EbsCompanyinfo
	 * company =
	 * ebsCompanyinfoDao.getCompanyByMemberIdAndSessionId(member.getMemberId(),
	 * Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
	 * model.addAttribute("company",company); } else { if(companyId!=null)
	 * {//有默认值 EbsCompanyinfo company = ebsCompanyinfoDao.findById(companyId);
	 * model.addAttribute("company", company); } //读取交易团所添加企业列表
	 * Map<String,Object> filter = new HashMap<String,Object>();
	 * filter.put("memberId", member.getMemberId()); filter.put("sessionIds",
	 * this.exhibitionInfo.get("sessionId").toString()); filter.put("start", 0);
	 * filter.put("limit", 5000); List<Map<String,Object>> companys =
	 * ebsCompanyinfoDao.getTraddingGroupCompanys(filter);
	 * model.addAttribute("companys", companys); }
	 * 
	 * return "personpapers"; }
	 * 
	 * @RequestMapping(value= {"/{language}/{type}-historypersoncard.html"})
	 * public String historyPersoncard(@PathVariable String
	 * language,@PathVariable String type,Model model,HttpSession
	 * session,HttpServletRequest request, HttpServletResponse response) {
	 * setCommonContent(model);
	 * safeCheck(language,type,session,request,response); //公司类型和行业类型
	 * Map<String, Object> map = new HashMap<>(); map.put("index", 0);
	 * map.put("size", 10000); List<SysCompanyType> companytypes =
	 * sysCompanyTypeService.list(map); List<SysIndustry> industries =
	 * sysIndustryService.list(map); model.addAttribute("companytypes",
	 * companytypes); model.addAttribute("industries", industries);
	 * model.addAttribute("pageName", "historypersoncard");
	 * model.addAttribute("language", language); model.addAttribute("type",
	 * type); return "person-history"; }
	 * 
	 * @RequestMapping(value= {"/{language}/{type}-historycarcard.html"}) public
	 * String historyCarcard(@PathVariable String language,@PathVariable String
	 * type,Model model,HttpSession session,HttpServletRequest request,
	 * HttpServletResponse response) { setCommonContent(model);
	 * safeCheck(language,type,session,request,response); //公司类型和行业类型
	 * Map<String, Object> map = new HashMap<>(); map.put("index", 0);
	 * map.put("size", 10000); List<SysCompanyType> companytypes =
	 * sysCompanyTypeService.list(map); List<SysIndustry> industries =
	 * sysIndustryService.list(map); model.addAttribute("companytypes",
	 * companytypes); model.addAttribute("industries", industries);
	 * model.addAttribute("pageName", "historycarcard");
	 * model.addAttribute("language", language); model.addAttribute("type",
	 * type); return "car-history"; }
	 * 
	 * @RequestMapping(value= {"/{language}/{type}-historyenterprise.html"})
	 * public String historyEnterprise(@PathVariable String
	 * language,@PathVariable String type,Model model,HttpSession
	 * session,HttpServletRequest request, HttpServletResponse response) {
	 * setCommonContent(model);
	 * safeCheck(language,type,session,request,response); //公司类型和行业类型
	 * Map<String, Object> map = new HashMap<>(); map.put("index", 0);
	 * map.put("size", 10000); List<SysCompanyType> companytypes =
	 * sysCompanyTypeService.list(map); List<SysIndustry> industries =
	 * sysIndustryService.list(map); model.addAttribute("companytypes",
	 * companytypes); model.addAttribute("industries", industries);
	 * model.addAttribute("pageName", "historyenterprise");
	 * model.addAttribute("language", language); model.addAttribute("type",
	 * type); return "enterprise-history"; }
	 * 
	 * @RequestMapping(value=
	 * {"/{language}/{type}-vehiclecard-{paperType}.html"}) public String
	 * vehiclecard(@PathVariable String language,@PathVariable String
	 * type,@PathVariable String paperType,Model model,HttpSession
	 * session,HttpServletRequest request, HttpServletResponse response) {
	 * setCommonContent(model);
	 * safeCheck(language,type,session,request,response);
	 * model.addAttribute("pageName", "vehiclecard");
	 * model.addAttribute("language", language); model.addAttribute("type",
	 * type); //0.是否必须填报车辆行驶证 Boolean needCarPicture = false; //1.证件类型id Integer
	 * intPaperType = 0; if(NumberUtil.isNumber(paperType)) { intPaperType =
	 * Integer.parseInt(paperType); }else { intPaperType =
	 * Integer.parseInt(paperType.substring(0, paperType.length()-1));
	 * if(paperType.substring(paperType.length()-1,
	 * paperType.length()).equals("o")) { model.addAttribute("isOut", 1); } else
	 * { model.addAttribute("isOut", 0); } } model.addAttribute("paperType",
	 * intPaperType); //2.设置此证件类型输入数量限制
	 * 
	 * List<Map<String,Object>> cardProcess =
	 * memberService.getCardProcess(session
	 * ,Integer.parseInt(exhibitionInfo.get("sessionId").toString())); Long
	 * limit = 0L; for(Map<String,Object> card : cardProcess) {
	 * if(card.get("cardTypeId").equals(intPaperType)) {//获取当前证件类型
	 * if(type.equals("delegation")||type.equals("reporter")) {//只有交易团和代办员才限制数量
	 * Long addedCount = Long.parseLong(card.get("cardCount").toString()); Long
	 * canCount = 0L; if(card.get("inputCount")!=null) { canCount =
	 * Long.parseLong(card.get("inputCount").toString()); } limit = canCount +
	 * addedCount; model.addAttribute("limit",limit);
	 * model.addAttribute("canCount",canCount);
	 * model.addAttribute("addedCount",addedCount); } } } //3.证件类型
	 * CmCertificateType certificateModal =
	 * certificateType.findById(intPaperType);
	 * model.addAttribute("certificateModal",certificateModal); //4.是否是参展证
	 * model.addAttribute("isExhibitor",certificateModal.getIsexhibitor());
	 * //5.证件办理截止日期 String cardStopDate = "";
	 * if(type.equals("delegation")||type.equals("reporter")){ Member member =
	 * (Member) session.getAttribute("member"); PimAgent agent =
	 * agentSerivce.getAgentByMemberIdAndSessionId(member.getMemberId(),
	 * Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
	 * if(agent.getOnsitecertification().equals(1)) {//现场制证 cardStopDate =
	 * null;//不设截止日期 } else { cardStopDate = (String)
	 * exhibitionInfo.get("certificatesEndDate"); } //是否需要填报车辆行驶证 needCarPicture
	 * = agent.getUploadvehiclelicense()==0?false:true; }
	 * if(certificateModal.getChinesename().contains("布撤展车证")) { cardStopDate =
	 * this.exhibitionInfo.get("certificatesFprEndDate").toString(); }
	 * model.addAttribute("cardStopDate",cardStopDate);
	 * 
	 * //6.是否超过限制时间 Boolean isTimeout = false; if(cardStopDate!=null) { try {
	 * long now = System.currentTimeMillis(); SimpleDateFormat simpleDateFormat
	 * = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM Date date =
	 * simpleDateFormat.parse(cardStopDate); Calendar c =
	 * Calendar.getInstance(); c.setTime(date); c.add(Calendar.DATE, 1); if((now
	 * - c.getTimeInMillis()) > 0) { isTimeout = true; } } catch (ParseException
	 * e) { e.printStackTrace(); } } model.addAttribute("isTimeout",isTimeout);
	 * model.addAttribute("needCarPicture",needCarPicture);
	 * 
	 * return "vehiclecard"; }
	 * 
	 * @RequestMapping(value= {"/{language}/{type}-enterprise.html"}) public
	 * String enterprise(@PathVariable String language,@PathVariable String
	 * type,Model model,HttpSession session,HttpServletRequest request,
	 * HttpServletResponse response) { setCommonContent(model);
	 * safeCheck(language,type,session,request,response); //所有展厅 Map<String,
	 * Object> filter = new HashMap<>(); filter.put("session",
	 * exhibitionInfo.get("sessionId").toString()); List<String> rooms =
	 * ebsBoothService.queryAllShowRoom(filter); model.addAttribute("rooms",
	 * rooms); //公司类型和行业类型 Map<String, Object> map = new HashMap<>();
	 * map.put("index", 0); map.put("size", 10000); List<SysCompanyType>
	 * companytypes = sysCompanyTypeService.list(map); List<SysIndustry>
	 * industries = sysIndustryService.list(map);
	 * model.addAttribute("companytypes", companytypes);
	 * model.addAttribute("industries", industries);
	 * model.addAttribute("pageName", "enterprise");
	 * model.addAttribute("language", language); model.addAttribute("type",
	 * type);
	 * 
	 * int memberId = 0; Member member = (Member)
	 * session.getAttribute("member"); if(member!=null) { memberId =
	 * member.getMemberId(); }
	 * 
	 * //此交易团下展位 ResultModel resultModel =
	 * tradinggroupboothallocationService.selectAreaAndShowroomTypeAndBooth
	 * (memberId
	 * ,0,Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
	 * Map<String,Object> result = resultModel.getResult()!=null?(Map<String,
	 * Object>) resultModel.getResult():null;
	 * if(result!=null&&resultModel.getStatus()==WConst.SUCCESS) {
	 * model.addAttribute("areas", result.get("areas"));
	 * model.addAttribute("inputInfo", result.get("inputInfo")); } else {
	 * model.addAttribute("areas", new ArrayList<Map<String,Object>>());
	 * model.addAttribute("inputInfo", new ArrayList<Map<String,Object>>()); }
	 * //此交易团下展厅 model.addAttribute("rooms", roomService.groupRooms(memberId,
	 * Integer.parseInt(exhibitionInfo.get("sessionId").toString())));
	 * //企业添加截止日期
	 * 
	 * //5.证件办理截止日期 String cardStopDate = "";
	 * if(type.equals("delegation")||type.equals("reporter")){ PimAgent agent =
	 * agentSerivce.getAgentByMemberIdAndSessionId(member.getMemberId(),
	 * Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
	 * if(agent.getOnsitecertification().equals(1)) {//现场制证 cardStopDate =
	 * null;//不设截止日期 } else { cardStopDate = (String)
	 * exhibitionInfo.get("delegationAddCompanyEndDate"); }
	 * model.addAttribute("cardStopDate", cardStopDate);
	 * 
	 * //6.是否超过限制时间 Boolean isTimeout = false; if(cardStopDate!=null) { try {
	 * long now = System.currentTimeMillis(); SimpleDateFormat simpleDateFormat
	 * = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM Date date =
	 * simpleDateFormat.parse(cardStopDate); Calendar c =
	 * Calendar.getInstance(); c.setTime(date); c.add(Calendar.DATE, 1); if((now
	 * - c.getTimeInMillis()) > 0) { isTimeout = true; } } catch (ParseException
	 * e) { e.printStackTrace(); } } model.addAttribute("isTimeout",isTimeout);
	 * } return "enterprise"; }
	 * 
	 * @RequestMapping(value= {"/{language}/roomAndBooth-{companyId}.html"})
	 * public String roomAndBooth(@PathVariable String language,@PathVariable
	 * Integer companyId,Model model,HttpSession session,HttpServletRequest
	 * request, HttpServletResponse response) { setCommonContent(model);
	 * safeCheck(language,"delegation",session,request,response);
	 * model.addAttribute("pageName", "roomAndBooth");
	 * model.addAttribute("language", language);
	 * 
	 * int memberId = 0; Member member = (Member)
	 * session.getAttribute("member"); if(member!=null) { memberId =
	 * member.getMemberId(); }
	 * 
	 * //此交易团下展位 ResultModel resultModel =
	 * tradinggroupboothallocationService.selectAreaAndShowroomTypeAndBooth
	 * (memberId
	 * ,companyId,Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
	 * Map<String,Object> result = (Map<String, Object>)
	 * resultModel.getResult(); if(resultModel.getStatus()==WConst.SUCCESS) {
	 * model.addAttribute("areas", result.get("areas"));
	 * model.addAttribute("inputInfo", result.get("inputInfo")); } else {
	 * model.addAttribute("areas", new ArrayList<Map<String,Object>>());
	 * model.addAttribute("inputInfo", new ArrayList<Map<String,Object>>()); }
	 * return "roomAndBooth"; }
	 * 
	 * @RequestMapping(value= {"/{language}/{type}-report.html"}) public String
	 * report(@PathVariable String language,@PathVariable String type,Model
	 * model,HttpSession session,HttpServletRequest request, HttpServletResponse
	 * response) { setCommonContent(model);
	 * safeCheck(language,type,session,request,response);
	 * model.addAttribute("language", language); model.addAttribute("type",
	 * type); model.addAttribute("pageName", "report");
	 * model.addAttribute("typeId", getMemberTypeId(type));
	 * model.addAttribute("typeName",getMemberTypeName(type,language));
	 * 
	 * return "report"; }
	 * 
	 * @RequestMapping(value= {"/{language}/{type}-password.html"}) public
	 * String password(@PathVariable String language,@PathVariable String
	 * type,Model model,HttpSession session,HttpServletRequest request,
	 * HttpServletResponse response) { setCommonContent(model);
	 * safeCheck(language,type,session,request,response);
	 * model.addAttribute("language", language); model.addAttribute("type",
	 * type); model.addAttribute("pageName", "login");
	 * model.addAttribute("typeId", getMemberTypeId(type));
	 * model.addAttribute("typeName",getMemberTypeName(type,language));
	 * 
	 * return "password"; }
	 * 
	 * @RequestMapping(value= {"/{language}/{type}-company.html"}) public String
	 * company(@PathVariable String language,@PathVariable String type,Model
	 * model,HttpSession session,HttpServletRequest request, HttpServletResponse
	 * response) { setCommonContent(model);
	 * safeCheck(language,type,session,request,response);
	 * model.addAttribute("language", language); model.addAttribute("type",
	 * type); model.addAttribute("pageName", "login");
	 * model.addAttribute("typeId", getMemberTypeId(type));
	 * model.addAttribute("typeName",getMemberTypeName(type,language));
	 * 
	 * //获取member和展会届次 int memberId = 0; Member member = (Member)
	 * session.getAttribute("member"); if(member!=null) { memberId =
	 * member.getMemberId(); }
	 * 
	 * Integer sessionId = (Integer) exhibitionInfo.get("sessionId");
	 * 
	 * //1.公司类型和行业类型 Map<String, Object> map = new HashMap<>(); map.put("index",
	 * 0); map.put("size", 10000); List<SysCompanyType> companytypes =
	 * sysCompanyTypeService.list(map); List<SysIndustry> industries =
	 * sysIndustryService.list(map); //2.展位申请状态 model.addAttribute("applyInfo",
	 * memberService.getExhibitorApplyInfo(session, sessionId));
	 * model.addAttribute("companytypes", companytypes);
	 * model.addAttribute("industries", industries);
	 * 
	 * return "company"; }
	 * 
	 * @RequestMapping(value= {"/{language}/{type}-info.html"}) public String
	 * info(@PathVariable String language,@PathVariable String type,Model
	 * model,HttpSession session,HttpServletRequest request, HttpServletResponse
	 * response) { SimpleDateFormat df = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	 * System.out.println("进入方法时间："+df.format(new Date()));// new
	 * Date()为获取当前系统时间 setCommonContent(model);
	 * safeCheck(language,type,session,request,response);
	 * model.addAttribute("language", language); model.addAttribute("type",
	 * type); model.addAttribute("pageName", "info");
	 * model.addAttribute("typeId", getMemberTypeId(type));
	 * model.addAttribute("typeName",getMemberTypeName(type,language));
	 * 
	 * int memberId = 0; Member member = (Member)
	 * session.getAttribute("member"); if(member!=null) { memberId =
	 * member.getMemberId(); }
	 * 
	 * Integer sessionId = (Integer) exhibitionInfo.get("sessionId");
	 * 
	 * System.out.println("展位申请开始时间："+df.format(new Date()));// new
	 * Date()为获取当前系统时间 //添加展位申请进度信息 if(type.equals("exhibitor")) {
	 * model.addAttribute("boothProcess",
	 * memberService.getBoothProcess(memberId, sessionId)); }
	 * List<Map<String,Object>> cardProcess =
	 * memberService.getCardProcess(session
	 * ,Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
	 * model.addAttribute("cardProcess",cardProcess);
	 * 
	 * 
	 * System.out.println("审核失败开始时间："+df.format(new Date()));// new
	 * Date()为获取当前系统时间 //添加证件审核失败信息 Map<String,Object> faildCards =
	 * memberService.getFaildCards(session);
	 * 
	 * model.addAttribute("persons",faildCards.get("person"));
	 * model.addAttribute("cars",faildCards.get("car"));
	 * System.out.println("方法将近离开时间："+df.format(new Date()));// new
	 * Date()为获取当前系统时间 return "info"; }
	 * 
	 * @RequestMapping(value= {"/{language}/{type}-agent.html"}) public String
	 * agent(@PathVariable String language,@PathVariable String type,Model
	 * model,HttpSession session,HttpServletRequest request, HttpServletResponse
	 * response) { setCommonContent(model);
	 * safeCheck(language,type,session,request,response);
	 * model.addAttribute("language", language); model.addAttribute("type",
	 * type); model.addAttribute("pageName", "login");
	 * model.addAttribute("typeId", getMemberTypeId(type));
	 * model.addAttribute("typeName",getMemberTypeName(type,language));
	 * 
	 * int memberId = 0; Member member = (Member)
	 * session.getAttribute("member"); if(member!=null) { memberId =
	 * member.getMemberId(); }
	 * 
	 * model.addAttribute("agent",agentSerivce.getAgentByMemberIdAndSessionId(
	 * memberId, Integer.parseInt(exhibitionInfo.get("sessionId").toString())));
	 * 
	 * return "agent"; }
	 */
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

	/**
	 * 安全登陆验证，需登陆后访问的都加此验证
	 */
	public Member safeCheck(String language, HttpSession session) {
		if (session.getAttribute("member") == null) {
			return null;
		}
		return (Member) session.getAttribute("member");
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

	/**
	 * 以下是接口部分
	 */

	@ResponseBody
	@RequestMapping(value = { "/{language}/{type}-apply-update" })
	public ResultModel applyUpdate(@PathVariable String language,
			@PathVariable String type, HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			String parameter) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		return applyService.updateApply(parameter);
	}

	@ResponseBody
	@RequestMapping(value = { "/{language}/{type}-apply-get" })
	public ResultModel applyGet(@PathVariable String language,
			@PathVariable String type, HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			Integer memberId, Integer sessionId) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		return applyService.getApply(memberId, sessionId);
	}

	@ResponseBody
	@RequestMapping(value = { "/{language}/activateHistoryAccount" })
	public ResultModel activateHistoryAccount(@PathVariable String language,
			Integer memberId, Integer companyId, String sessionId) {
		setCommonContent();
		return memberService.activateHistoryAccount(companyId, memberId,
				sessionId);
	}

	@ResponseBody
	@RequestMapping(value = { "/{language}/sendPhoneCode" })
	public ResultModel sendPhoneCode(@PathVariable String language,
			String phone, String companyName, HttpSession session) {
		setCommonContent();
		return memberService.sendPhoneCode(phone, companyName,
				this.exhibitionInfo.get("sessionId").toString(), session);
	}

	@ResponseBody
	@RequestMapping(value = { "/{language}/getPhoneCode" })
	public ResultModel getPhoneCode(@PathVariable String language,
			String phone, HttpSession session) {
		setCommonContent();
		
		boolean sendResult = false;
		try {
			sendResult = smsService.sendRegistValidateSMS(phone, "", Integer.parseInt(exhibitionInfo.get("sessionId").toString()) , session);
		}  catch (Exception e) {
			e.printStackTrace();
			sendResult = false;
		}
		if(sendResult) {
			ResultModel result = new ResultModel(WConst.SUCCESS,"短信发送成功，请查收。",null);
			return result;
		}
		else {
			ResultModel result = new ResultModel(WConst.ERROR,"短信发送失败，请稍后重试。",null);
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = { "/{language}/validatePhoneCode" })
	public ResultModel validatePhoneCode(@PathVariable String language,
			String phoneCode, HttpSession session) {
		setCommonContent();
		Object storageCode = session.getAttribute(SMSUtil.REGIST_CODEKEY);
		if(phoneCode.equals(storageCode)) {
			ResultModel result = new ResultModel(WConst.SUCCESS,"验证成功",null);
			return result;
		}
		else {
			ResultModel result = new ResultModel(WConst.ERROR,"验证码不正确，请重试。",null);
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = { "/{language}/api/common/forgot" })
	public ResultModel forgot(@PathVariable String language,
			String companyName, String email, String phone, String resetType) {
		setCommonContent();
		return memberService.forgot(companyName, email, phone, resetType,
				exhibitionInfo.get("sessionId").toString());
	}

	/**
	 * 生成验证码
	 */
	@RequestMapping(value = "/{language}/getVerifyCode")
	public void getVerify(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
			response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expire", 0);
			RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
			randomValidateCode.getRandcode(request, response);// 输出验证码图片方法
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	/**
	 * 获取所有国家地区数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{language}/getCountryArea")
	@ResponseBody
	public List<SysCountryArea> getCountryArea() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", 0);
		map.put("size", 100000);
		return sysCountryAreaService.list(map);
	}

	/**
	 * 添加参展商管理-人员证件管理
	 */
	@PostMapping("/{language}/personcard/save")
	@ResponseBody
	public ResultModel personCardSave(@PathVariable String language,
			EbsPersonnelcard ebsPersonnelcard, HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		return ebsPersonnelcardService.addOrUpdate(ebsPersonnelcard, member,
				this.exhibitionInfo);
	}

	/**
	 * 获取人员证件列表
	 * 
	 * @param page
	 *            分页及搜索实体
	 * @return
	 */
	@RequestMapping(value = "/{language}/personcard/list")
	@ResponseBody
	public ResultModel personCards(@PathVariable String language,
			EbsPersonnelcard ebsPersonnelcard, PageModel page,
			HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}

		int memberId = 0;
		memberId = member.getMemberId();
		ebsPersonnelcard.setAgent(memberId);
		ebsPersonnelcard.setSession(exhibitionInfo.get("sessionId").toString());
		return ebsPersonnelcardService.getPersonCards(ebsPersonnelcard, page);
	}

	/**
	 * 批量删除
	 * 
	 * @param articleIdList主键列表
	 * @return
	 */
	@RequestMapping(value = "/{language}/personcard/deletes")
	@ResponseBody
	public ResultModel personcardDeletes(@PathVariable String language,
			HttpSession session,
			@RequestParam(value = "idList[]") Integer[] idList) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		return ebsPersonnelcardService.delete(idList, memberId);
	}

	/**
	 * 批量删除
	 * 
	 * @param articleIdList主键列表
	 * @return
	 */
	@RequestMapping(value = "/{language}/personcard/delete")
	@ResponseBody
	public ResultModel personcardDelete(@PathVariable String language,
			HttpSession session, Integer id) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		Integer[] idList = { id };
		return ebsPersonnelcardService.delete(idList, memberId);
	}

	/**
	 * 添加参展商管理-人员证件管理
	 */
	@PostMapping("/{language}/vehiclecard/save")
	@ResponseBody
	public ResultModel vehiclecardSave(@PathVariable String language,
			EbsVehiclecard vehiclecard, HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		vehiclecard.setAgent(memberId);
		return vehiclecardService.addOrUpdate(vehiclecard);
	}

	/**
	 * 获取人员证件列表
	 * 
	 * @param page
	 *            分页及搜索实体
	 * @return
	 */
	@RequestMapping(value = "/{language}/vehiclecard/list")
	@ResponseBody
	public ResultModel vehicleCards(@PathVariable String language,
			EbsVehiclecard vehiclecard, PageModel page, HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		vehiclecard.setAgent(memberId);
		vehiclecard.setAgent(memberId);
		vehiclecard.setSession(exhibitionInfo.get("sessionId").toString());
		return vehiclecardService.getVehicleCards(vehiclecard, page);
	}

	/**
	 * 批量删除
	 * 
	 * @param articleIdList主键列表
	 * @return
	 */
	@RequestMapping(value = "/{language}/vehiclecard/deletes")
	@ResponseBody
	public ResultModel vehiclecardDeletes(@PathVariable String language,
			HttpSession session,
			@RequestParam(value = "idList[]") Integer[] idList) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		return vehiclecardService.delete(idList, memberId);
	}

	/**
	 * 批量删除
	 * 
	 * @param articleIdList主键列表
	 * @return
	 */
	@RequestMapping(value = "/{language}/vehiclecard/delete")
	@ResponseBody
	public ResultModel vehiclecardDelete(@PathVariable String language,
			HttpSession session, Integer id) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		Integer[] idList = { id };
		return vehiclecardService.delete(idList, memberId);
	}

	/**
	 * 参展商添加企业
	 * 
	 * @param language
	 * @param type
	 * @param session
	 * @param request
	 * @param response
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = { "/{language}/enterprise/save" })
	@ResponseBody
	public ResultModel enterpriseUpdate(@PathVariable String language,
			HttpSession session, String parameter, EbsCompanyinfo company) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		return applyService.updateEnterpriseApply(parameter, company);
	}

	/**
	 * 批量删除参展企业
	 * 
	 * @param language
	 * @param type
	 * @param session
	 * @param request
	 * @param response
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = { "/{language}/enterprise/deletes" })
	@ResponseBody
	public ResultModel enterpriseDeletes(@PathVariable String language,
			HttpSession session,
			@RequestParam(value = "idList[]") Integer[] idList) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		return companyService.delete(idList, memberId,
				Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
	}

	/**
	 * 参展商添加企业
	 * 
	 * @param language
	 * @param type
	 * @param session
	 * @param request
	 * @param response
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = { "/{language}/enterprise/delete" })
	@ResponseBody
	public ResultModel enterpriseDelete(@PathVariable String language,
			HttpSession session, Integer id) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		Integer[] idList = { id };
		return companyService.delete(idList, memberId,
				Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
	}

	/**
	 * 参展商列表
	 * 
	 * @param language
	 * @param type
	 * @param session
	 * @param request
	 * @param response
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = { "/{language}/enterprise/list" })
	@ResponseBody
	public ResultModel enterpriseList(@PathVariable String language,
			Integer roomId, Integer status, PageModel page, HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		List<CmCertificateType> certificateTypes = memberService
				.getMemberCertificateType(member,
						this.exhibitionInfo.get("sessionId").toString());
		Integer attendCardId = 0;
		for (CmCertificateType card : certificateTypes) {
			if (card.getIsexhibitor().equals(1)) {
				attendCardId = card.getId();
			}
		}
		result = companyService.getTraddingGroupCompanys(roomId, status,
				exhibitionInfo.get("sessionId").toString(), page, session);
		Object data = result.getResult();
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("data", data);
		res.put("cardtype", attendCardId);
		result.setResult(res);
		return result;
	}

	/**
	 * 获取会员关联企业信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/company/get" })
	@ResponseBody
	public ResultModel getCompanyByMember(@PathVariable String language,
			HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		return companyService.getMemberCompany(session);
	}

	/**
	 * 获取会员关联企业信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/company/update" })
	@ResponseBody
	public ResultModel updateMemberCompany(@PathVariable String language,
			EbsCompanyinfo company, HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		return companyService.updateMemberCompany(company, session);
	}

	/**
	 * 代办员自我更新
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/agent/update" })
	@ResponseBody
	public ResultModel updateagent(@PathVariable String language,
			PimAgent agent, HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		try {
			result = new ResultModel(WConst.SUCCESS, WConst.SAVED,
					agentSerivce.update(agent));
		} catch (Exception e) {
			result = new ResultModel(WConst.ERROR, WConst.SAVEDERROR, e);
		}
		return result;
	}

	/**
	 * 获取会员关联企业信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/history/personcard" })
	@ResponseBody
	public ResultModel historyPersonCard(@PathVariable String language,
			String cardname, PageModel page, HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		return ebsPersonnelcardService.getHistoryPersonCard(memberId,
				Integer.parseInt(exhibitionInfo.get("sessionId").toString()),
				cardname, page);
	}

	/**
	 * 获取会员关联企业信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/history/carcard" })
	@ResponseBody
	public ResultModel historyCarCard(@PathVariable String language,
			String cardname, PageModel page, HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		return vehiclecardService.getHistoryCarCard(memberId,
				Integer.parseInt(exhibitionInfo.get("sessionId").toString()),
				cardname, page);
	}

	/**
	 * 获取会员关联企业信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/history/enterprise" })
	@ResponseBody
	public ResultModel historyEnterprise(@PathVariable String language,
			PageModel page, HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();

		return companyService.getTraddingGroupHistoryCompanys(
				Integer.parseInt(exhibitionInfo.get("sessionId").toString()),
				memberId, page);
	}

	/**
	 * 激活历届人员证件信息到本届
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/personcard/rejoins" })
	@ResponseBody
	public ResultModel personRejoins(@PathVariable String language,
			HttpSession session,
			@RequestParam(value = "idList[]") Integer[] idList) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		return ebsPersonnelcardService.rejoin(idList,0, this.exhibitionInfo,
				member);
	}

	/**
	 * 激活历届人员证件信息到本届
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/personcard/rejoin" })
	@ResponseBody
	public ResultModel personRejoin(@PathVariable String language,
			HttpSession session, Integer id) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		Integer[] idList = { id };
		return ebsPersonnelcardService.rejoin(idList,0, this.exhibitionInfo,
				member);
	}

	/**
	 * 获取会员关联企业信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/carcard/rejoins" })
	@ResponseBody
	public ResultModel carRejoins(@PathVariable String language,
			HttpSession session,
			@RequestParam(value = "idList[]") Integer[] idList) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		return vehiclecardService.rejoin(idList,
				Integer.parseInt(exhibitionInfo.get("sessionId").toString()),
				memberId);
	}

	/**
	 * 获取会员关联企业信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/carcard/rejoin" })
	@ResponseBody
	public ResultModel carRejoin(@PathVariable String language,
			HttpSession session, Integer id) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		Integer[] idList = { id };
		return vehiclecardService.rejoin(idList,
				Integer.parseInt(exhibitionInfo.get("sessionId").toString()),
				memberId);
	}

	/**
	 * 获取会员关联企业信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/enterprise/rejoins" })
	@ResponseBody
	public ResultModel enterpriseRejoins(@PathVariable String language,
			HttpSession session,
			@RequestParam(value = "idList[]") Integer[] idList) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		return companyService.rejoin(idList,
				Integer.parseInt(exhibitionInfo.get("sessionId").toString()),
				memberId);
	}

	/**
	 * 获取会员关联企业信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/enterprise/rejoin" })
	@ResponseBody
	public ResultModel enterpriseRejoin(@PathVariable String language,
			HttpSession session, Integer id) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		Integer[] idList = { id };
		return companyService.rejoin(idList,
				Integer.parseInt(exhibitionInfo.get("sessionId").toString()),
				memberId);
	}

	/**
	 * 获取人员取证报表
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/report/person" })
	@ResponseBody
	public ResultModel personReport(@PathVariable String language,
			HttpSession session, Integer cardTypeId, Integer status) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		Integer evidenceDelay = 0;
		if (exhibitionInfo.get("evidenceDelay") != null) {
			evidenceDelay = Integer.parseInt(exhibitionInfo
					.get("evidenceDelay").toString());
		}
		return ebsPersonnelcardService.takeAwayReport(evidenceDelay, memberId,
				Integer.parseInt(exhibitionInfo.get("sessionId").toString()),
				status, cardTypeId);
	}

	/**
	 * 获取车辆取证报表
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/report/car" })
	@ResponseBody
	public ResultModel carReport(@PathVariable String language,
			HttpSession session, Integer cardTypeId, Integer status) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		Integer evidenceDelay = 0;
		if (exhibitionInfo.get("evidenceDelay") != null) {
			evidenceDelay = Integer.parseInt(exhibitionInfo
					.get("evidenceDelay").toString());
		}
		return vehiclecardService.takeAwayReport(evidenceDelay, memberId,
				Integer.parseInt(exhibitionInfo.get("sessionId").toString()),
				status, cardTypeId);
	}

	/**
	 * 获取产品列表
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/product/list" })
	@ResponseBody
	public ResultModel productList(@PathVariable String language,
			HttpSession session, @RequestParam Map<String, Object> filter,
			Integer status) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		try {
			String strSessionid = exhibitionInfo.get("sessionId").toString();
			filter.put("session", strSessionid);
			filter.put("memberid", memberId);
			if (!filter.get("status").toString().equals("")) {
				filter.put("productStatus", filter.get("status"));
			}
			filter = ResultVOUtil.TiaoZhengFenYe(filter);
			List<WebProduct> list = webProductService.list(filter);
			int count = webProductService.listcount(filter);
			result = new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, list);
			result.setCount(count);
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResultModel(WConst.ERROR, WConst.QUERYFAILD, e);
		}
	}

	/**
	 * 获取产品列表
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/{language}/api/product/update" })
	@ResponseBody
	public ResultModel productUpdate(@PathVariable String language,
			HttpSession session, WebProduct product) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		try {
			if (product.getProductId() == null
					|| product.getProductId().equals(0)) {
				webProductService.save(product);
			} else {
				webProductService.update(product);
			}
			return new ResultModel(WConst.SUCCESS, WConst.SAVED, product);
		} catch (Exception e) {
			return new ResultModel(WConst.ERROR, e.getMessage(), e);
		}
	}

	/**
	 * 批量删除
	 */
	@RequestMapping("/{language}/api/product/deletes")
	@ResponseBody
	public ResultModel productDeletes(@PathVariable String language,
			HttpSession session,
			@RequestParam(value = "idList[]") Integer[] idList) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		for (Integer id : idList) {
			webProductService.deleteById(id);
		}
		return new ResultModel(WConst.SUCCESS, WConst.DELETED, null);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/{language}/api/product/delete")
	@ResponseBody
	public ResultModel productDelete(@PathVariable String language,
			HttpSession session, Integer id) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		webProductService.deleteById(id);
		return new ResultModel(WConst.SUCCESS, WConst.DELETED, null);
	}

	@RequestMapping("/getBarCode")
	public void getBarCode(String code, HttpServletResponse response)
			throws Exception {
		BufferedImage image = BarCodeUtil.insertWords(
				BarCodeUtil.getBarCode(code), code);
		ImageIO.write(image, "jpg", response.getOutputStream());
	}

	/**
	 * 验证公司名称
	 * 
	 * @param companyName
	 * @param memberType
	 * @throws Exception
	 */
	@RequestMapping("/{language}/api/common/company/validate")
	@ResponseBody
	public ResultModel validateCompany(@PathVariable String language,
			String companyName, String memberType) {
		setCommonContent();
		Integer memberTypeId = 0;
		switch (memberType) {
		case "exhibitor":
			memberTypeId = Member.MEMBER_TYPE_EXHIBITOR_CODE;
			break;
		case "decorator":
			memberTypeId = Member.MEMBER_TYPE_DECORATOR_CODE;
			break;
		case "foreign":
			memberTypeId = Member.MEMBER_TYPE_FOREIGN_CODE;
			break;
		case "purchaser":
			memberTypeId = Member.MEMBER_TYPE_PURCHASER_CODE;
			break;
		}
		Integer sessionId = Integer.parseInt(exhibitionInfo.get("sessionId")
				.toString());
		return companyService.validateCompany(companyName, memberTypeId,
				sessionId);

	}

	/**
	 * 验证公司名称
	 * 
	 * @param companyName
	 * @param memberType
	 * @throws Exception
	 */
	@RequestMapping("/{language}/api/common/company/history/validate")
	@ResponseBody
	public ResultModel validateHistoryCompany(@PathVariable String language,
			EbsCompanyinfo company, HttpSession session) {
		setCommonContent();
		ResultModel result = null;
		Member member = safeCheck(language, session);
		if (member == null) {// 未登录活登录超时，直接返回要求重新弄登录
			result = WConst.RELOGINJSON;
			return result;
		}
		int memberId = 0;
		memberId = member.getMemberId();
		Integer sessionId = Integer.parseInt(exhibitionInfo.get("sessionId")
				.toString());
		company.setSession(sessionId.toString());
		return companyService.validateHistoryCompany(company, session);

	}

	/**
	 * 注册会员
	 * 
	 * @param Member
	 * @return
	 */
	@RequestMapping(value = "/{language}/regist")
	@ResponseBody
	public ResultModel regist(@PathVariable String language, Integer isActive,
			HttpSession session, Member member, EbsCompanyinfo company) {
		setCommonContent();
		Object openId = session.getAttribute("openId");
		if (openId == null || openId.equals("")) {
			return WConst.RELOGINJSON;
		}
		Integer sessionId = Integer.parseInt(exhibitionInfo.get("sessionId")
				.toString());
		company.setSession(sessionId.toString());
		return memberService.routerRegist(isActive, member, company, session);
	}
}