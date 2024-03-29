package cn.org.chtf.card.web;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.org.chtf.card.common.constant.RequestConstant;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.Decorators.service.DecoratorEbsNoticeService;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsCompanyinfoMapper;
import cn.org.chtf.card.manage.Exhibitors.model.*;
import cn.org.chtf.card.manage.Exhibitors.service.*;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.MakeEvidence.service.CmCertificateTypeService;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgent;
import cn.org.chtf.card.manage.PreviousInformation.service.PimAgentService;
import cn.org.chtf.card.manage.article.pojo.Article;
import cn.org.chtf.card.manage.article.pojo.ArticlePageModel;
import cn.org.chtf.card.manage.article.service.ArticleService;
import cn.org.chtf.card.manage.basic.service.BasicService;
import cn.org.chtf.card.manage.basicsetting.service.BasicSettingService;
import cn.org.chtf.card.manage.common.service.CommonService;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.member.service.MemberService;
import cn.org.chtf.card.manage.menu.service.MenuService;
import cn.org.chtf.card.manage.product.model.WebProduct;
import cn.org.chtf.card.manage.product.service.WebProductService;
import cn.org.chtf.card.manage.system.model.SysCompanyType;
import cn.org.chtf.card.manage.system.model.SysCountryArea;
import cn.org.chtf.card.manage.system.model.SysIndustry;
import cn.org.chtf.card.manage.system.model.SystemDictionaries;
import cn.org.chtf.card.manage.system.service.*;
import cn.org.chtf.card.support.util.*;
import cn.org.chtf.card.support.util.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
public class SiteController {

    private Logger logger = LoggerFactory.getLogger(SiteController.class);

    Map<String, Object> exhibitionInfo;

    SiteController() {
    }

    @Resource(name = "MenuServiceImpl")
    MenuService menuService;
    @Resource(name = "MemberServiceImpl")
    MemberService memberService;
    @Resource
    HttpUtil httpUtil;

    @Autowired
    SysSessionService sessionService;

    @Autowired
    private SysExhibitionService exhibitionService;

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
    @Autowired
    private EbsStadiumService stadiumService;
    @Autowired
    private DecoratorEbsNoticeService decoratorEbsNoticeService;
    @Resource
    private CmCertificateTypeService certificateType;
    @Autowired
    private EbsBoothService ebsBoothService;
    @Autowired
    private EbsShowroomService roomService;
    @Autowired
    private PimAgentService agentSerivce;

    @Autowired
    private EbsCompanyinfoMapper ebsCompanyinfoDao;

    @Autowired
    private CommonService commonService;

    @Resource
    private SMSUtil sMSUtil;

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
    @Resource
    private ResourceLoader resourceLoader;


    @RequestMapping(value = {"/{language}/default.html"})
    public String index(@PathVariable String language, Model model, HttpSession session, HttpServletRequest request) {

        setCommonContent(model);
        if (language == "index") {
            language = "cn";
        }
        Integer noticeMenuId = 234;
        Integer helpMenuId = 8;
        Integer FAQMenuId = 21;
        switch (language) {
            case "en":
                noticeMenuId = 234;
                helpMenuId = 8;
                FAQMenuId = 21;
                break;
            case "jp":
                noticeMenuId = 234;
                helpMenuId = 8;
                FAQMenuId = 21;
                break;
            case "kr":
                noticeMenuId = 234;
                helpMenuId = 8;
                FAQMenuId = 21;
                break;
            case "ru":
                noticeMenuId = 234;
                helpMenuId = 8;
                FAQMenuId = 21;
                break;
        }
        model.addAttribute("notices", articleService.getArticles(noticeMenuId, 1, request));
        model.addAttribute("helps", articleService.getArticles(helpMenuId, 3, request));
        model.addAttribute("FAQs", articleService.getArticles(FAQMenuId, 2, request));

        model.addAttribute("language", language);
        model.addAttribute("pageName", "index");

        //展会开始时间
        String startDate = exhibitionInfo.get("startDate").toString();
        int days = DateUtil.getDifferenceTotalDays(null, startDate);
        if (days < 0) {
            days = 0;
        }
        model.addAttribute("days", days);
        String templateName = "web/" + language + "/index";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/article-{menuId}-{articleId}.html"})
    public String article(@PathVariable String language, @PathVariable Integer menuId, @PathVariable Integer articleId, Model model, HttpServletRequest request) {
        setCommonContent(model);
        model.addAttribute("language", language);
        model.addAttribute("menu", menuService.getMenuByMenuId(menuId));
        ArticlePageModel page = new ArticlePageModel();
        page.setMenuId(menuId);
        page.setStart(0);
        page.setLimit(1000);
        page.setOrder("ASC");
        page.setField("articleOrder");
        String url = CryptographyUtil.GeCurrenttUrl(request);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", url);
        map.put("useable", 1);
        Integer exhibitionId = exhibitionService.findByMap(map).getId();
        page.setExhibitionId(exhibitionId);
        List<Article> articles = (List<Article>) articleService.getArticles(page).getResult();
        model.addAttribute("articles", articles);
        if (articleId.equals(0)) {
            if (articles != null && articles.size() > 0)
                model.addAttribute("article", articles.get(0));
        } else {
            model.addAttribute("article", articleService.getArticle(articleId).getResult());

        }
        String templateName = "web/" + language + "/article";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-login.html"})
    public String login(@PathVariable String language, @PathVariable String type, Model model, HttpSession session) {
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
        String templateName = "web/" + language + "/login";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-resetpassword.html"})
    public String findPassword(@PathVariable String language, @PathVariable String type, Model model, HttpSession session) {
        setCommonContent(model);
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("pageName", "login");
        model.addAttribute("typeId", getMemberTypeId(type));
        model.addAttribute("typeName", getMemberTypeName(type, language));
        String templateName = "web/" + language + "/resetpassword";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-regist.html"})
    public String regist(@PathVariable String language, @PathVariable String type, Model model, HttpSession session) {
        setCommonContent(model);

        //公司类型和行业类型
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

        //展区列表
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("session", exhibitionInfo.get("sessionId").toString());

        List<Map<String, Object>> exhibitionArea = commonService.GetExhibitionArea(filter);
        model.addAttribute("exhibitionArea", exhibitionArea);

        String templateName = "web/" + language + "/regist";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-member.html"})
    public String member(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        safeCheck(language, type, session, request, response);
        setCommonContent(model);
        model.addAttribute("pageName", "regist");
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("typeId", getMemberTypeId(type));
        model.addAttribute("typeName", getMemberTypeName(type, "cn"));
        Member member = (Member) session.getAttribute("member");
        member.setMemberPassword("");
        try {
            if (member != null) {
                model.addAttribute("member", mapper.writeValueAsString(member));
                model.addAttribute("memberPojo", member);
            }
        } catch (Exception e) {
            model.addAttribute("memberPojo", null);
            model.addAttribute("member", "{}");
        }
        String sessionId = exhibitionInfo.get("sessionId").toString();
        List<CmCertificateType> certificateTypes = memberService.getMemberCertificateType(member, sessionId);
        model.addAttribute("certificateTypes", certificateTypes);
        try {
            model.addAttribute("certificateTypesPojo", mapper.writeValueAsString(certificateTypes));
        } catch (JsonProcessingException e) {
            model.addAttribute("certificateTypesPojo", "{}");
        }
        //获取是否有权限办理室内外参展证
        if (type.equals("exhibitor")) {
            Map<String, Object> permission = memberService.getExihibitorTypePermission(member.getMemberId(), Integer.parseInt(sessionId));
            model.addAttribute("canOutCertification", permission.get("canOutCertification"));
            model.addAttribute("canInCertification", permission.get("canInCertification"));
            //获取当前信息填报和展位分配情况
            model.addAttribute("applyInfo", memberService.getExhibitorApplyInfo(session, Integer.parseInt(sessionId)));
        } else if (type.equals("decorator")) {
            //获取当前信息填报和展位分配情况
            model.addAttribute("applyInfo", memberService.getExhibitorApplyInfo(session, Integer.parseInt(sessionId)));
        }
        String templateName = "web/" + language + "/member";

        return templateName;

    }

    @RequestMapping(value = {"/{language}/{type}-online.html"})
    public String online(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        safeCheck(language, type, session, request, response);
        setCommonContent(model);
        model.addAttribute("pageName", "regist");
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("typeId", getMemberTypeId(type));
        Member member = (Member) session.getAttribute("member");
        member.setMemberPassword("");
        try {
            if (member != null) {
                model.addAttribute("member", mapper.writeValueAsString(member));
                model.addAttribute("memberPojo", member);
            }
        } catch (Exception e) {
            model.addAttribute("memberPojo", null);
            model.addAttribute("member", "{}");
        }
        String sessionId = exhibitionInfo.get("sessionId").toString();
        List<CmCertificateType> certificateTypes = memberService.getMemberCertificateType(member, sessionId);
        model.addAttribute("certificateTypes", certificateTypes);
        try {
            model.addAttribute("certificateTypesPojo", mapper.writeValueAsString(certificateTypes));
        } catch (JsonProcessingException e) {
            model.addAttribute("certificateTypesPojo", "{}");
        }
        //获取是否有权限办理室内外参展证
        if (type.equals("exhibitor")) {
            //获取当前信息填报和展位分配情况
            model.addAttribute("applyInfo", memberService.getExhibitorApplyInfo(session, Integer.parseInt(sessionId)));

        }
        String templateName = "web/" + language + "/online";

        return templateName;

    }

    @RequestMapping(value = {"/{language}/{type}-apply.html"})
    public String apply(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        model.addAttribute("pageName", "regist");
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("typeId", getMemberTypeId(type));
        ResultModel resultModel = tradinggroupboothallocationService.selectAreaAndShowroomType(Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
        Map<String, Object> result = (Map<String, Object>) resultModel.getResult();
        if (resultModel.getStatus() == WConst.SUCCESS) {
            model.addAttribute("areas", result.get("areas"));
            model.addAttribute("inputInfo", result.get("inputInfo"));
        } else {
            model.addAttribute("areas", new ArrayList<Map<String, Object>>());
            model.addAttribute("inputInfo", new ArrayList<Map<String, Object>>());
        }
        Member member = (Member) session.getAttribute("member");
        Integer memberId = member.getMemberId();
        Integer sessionId = (Integer) this.exhibitionInfo.get("sessionId");
        Map<String, Object> boothProcess = memberService.getBoothProcess(memberId, sessionId);
        Integer exhibitionAuditStatus = (Integer) boothProcess.get("exhibitionAuditStatus");
        if (exhibitionAuditStatus != null && exhibitionAuditStatus == 1) {//已审核通过
            model.addAttribute("applyInfo", applyService.getApply(memberId, sessionId).getResult());
            String templateName = "web/" + language + "/apply-result";

            return templateName;
        } else {
            Map<String, Object> company = (Map<String, Object>) companyService.getMemberCompany(session).getResult();
            if (company.get("company") instanceof EbsCompanyinfo) {
                model.addAttribute("company", company.get("company"));
            }
            String templateName = "web/" + language + "/apply";

            return templateName;
        }
    }

    @RequestMapping(value = {"/{language}/{type}-product.html"})
    public String product(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        model.addAttribute("pageName", "product");
        model.addAttribute("language", language);
        model.addAttribute("type", type);

        //1.获取产品类型
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("dicParentid", 90);
        filter.put("useable", 1);
        List<SystemDictionaries> productMenu = systemDictionariesService.findByMap(filter);
        model.addAttribute("productMenu", productMenu);

        //2.获取产品单位
        Map<String, Object> filter1 = new HashMap<String, Object>();
        filter.put("dicParentid", 105);
        filter.put("useable", 1);
        List<SystemDictionaries> productMenu1 = systemDictionariesService.findByMap(filter);
        model.addAttribute("productMenu1", productMenu1);

        //3.获取产品价格单位
        Map<String, Object> filter2 = new HashMap<String, Object>();
        filter.put("dicParentid", 100);
        filter.put("useable", 1);
        List<SystemDictionaries> productMenu2 = systemDictionariesService.findByMap(filter);
        model.addAttribute("productMenu2", productMenu2);

        //2.获取公司列表
        if (type.equals("exhibitor")) {
            Member member = (Member) session.getAttribute("member");
            EbsCompanyinfo company = ebsCompanyinfoDao.getLastCompanyByMemberId(member.getMemberId());
            model.addAttribute("company", company);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("start", 0);
            map.put("limit", 10000);
            map.put("sessionIds", (Integer) this.exhibitionInfo.get("sessionId"));
            Member member = (Member) session.getAttribute("member");
            map.put("memberId", member.getMemberId());
            List<Map<String, Object>> companys = ebsCompanyinfoDao.getTraddingGroupCompanys(map);
            model.addAttribute("companys", companys);
        }


        String templateName = "web/" + language + "/product";
        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-personpapers-{paperType}.html"})
    public String personpapers(@PathVariable String language, @PathVariable String type, @PathVariable String paperType, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer companyId, String companyName) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);

        Member member = (Member) session.getAttribute("member");
        //2.公司类型和行业类型
        Map<String, Object> map = new HashMap<>();
        map.put("index", 0);
        map.put("size", 10000);
        List<SysCompanyType> companytypes = sysCompanyTypeService.list(map);
        List<SysIndustry> industries = sysIndustryService.list(map);
        model.addAttribute("companytypes", companytypes);
        model.addAttribute("industries", industries);
        model.addAttribute("pageName", "personpapers");
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("typeName", getMemberTypeName(type, language));
        Integer intPaperType = 0;
        if (NumberUtil.isNumber(paperType)) {
            intPaperType = Integer.parseInt(paperType);
        } else {
            intPaperType = Integer.parseInt(paperType.substring(0, paperType.length() - 1));
            if (paperType.substring(paperType.length() - 1, paperType.length()).equals("o")) {
                model.addAttribute("isOut", 1);
            } else {
                model.addAttribute("isOut", 0);
            }
        }
        model.addAttribute("paperType", intPaperType);
        CmCertificateType certificateModal = certificateType.findById(intPaperType);
        model.addAttribute("certificateModal", certificateModal);
        model.addAttribute("isExhibitor", certificateModal.getIsexhibitor());
        //3.设置此证件类型输入数量限制

        List<Map<String, Object>> cardProcess = memberService.getCardProcess(session, Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
        Long limit = 0L;
        for (Map<String, Object> card : cardProcess) {
            if (card.get("cardTypeId").equals(intPaperType)) {//获取当前证件类型
                if (member.getMemberType().equals(Member.MEMBER_TYPE_PURCHASER_CODE) || //采购商的采购商证是要加限制的
                        certificateModal.getIsexhibitor().equals(1) ||//是参展证
                        member.getMemberType().equals(Member.MEMBER_TYPE_TRADE_CODE) ||//或者是交易团
                        member.getMemberType().equals(Member.MEMBER_TYPE_REPORT_CODE)) {//记者类型
                    Long addedCount = Long.parseLong(card.get("cardCount").toString());
                    model.addAttribute("addedCount", addedCount);

                    Long canCount = 0L;
                    if (card.get("inputCount") != null) {
                        canCount = Long.parseLong(card.get("inputCount").toString());
                    }
                    limit = canCount + addedCount;
                    model.addAttribute("limit", limit);
                    model.addAttribute("canCount", canCount);
                }
            }
        }

        //4.证件办理截止日期
        String cardStopDate = "";
        if (type.equals("exhibitor") && certificateModal.getChinesename().equals("参展证")) {//零散参展商的参展证
            cardStopDate = this.exhibitionInfo.get("certificatesExhibitorsEndDate").toString();
        } else if (type.equals("delegation") || type.equals("reporter")) {
            PimAgent agent = agentSerivce.getAgentByMemberIdAndSessionId(member.getMemberId(), Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
            if (agent.getOnsitecertification().equals(1)) {//现场制证
                cardStopDate = null;//不设截止日期
            }
        }
        if (cardStopDate != null && cardStopDate.equals("") && (certificateModal.getChinesename().contains("参展证B") ||
                certificateModal.getChinesename().contains("布撤展证") ||
                certificateModal.getChinesename().contains("采购商证") ||
                certificateModal.getChinesename().contains("布撤展车证") ||
                certificateModal.getChinesename().contains("嘉宾证B"))) {
            cardStopDate = this.exhibitionInfo.get("certificatesFprEndDate").toString();
        } else if (cardStopDate != null && cardStopDate.equals("")) {
            cardStopDate = this.exhibitionInfo.get("certificatesEndDate").toString();
        }
        if (type.equals("decorator")) {
            cardStopDate = getCertificateEndDate(request);
        }
        model.addAttribute("cardStopDate", cardStopDate);
        //5.是否超过限制时间
        Boolean isTimeout = false;
        if (cardStopDate != null) {
            try {
                long now = System.currentTimeMillis();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
                Date date = simpleDateFormat.parse(cardStopDate);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, 1);
                if ((now - c.getTimeInMillis()) > 0) {
                    isTimeout = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("isTimeout", isTimeout);
        //6.设置非交易团和记者证的公司名称
        if (!type.equals("delegation") && !type.equals("reporter")) {
            EbsCompanyinfo company = ebsCompanyinfoDao.getCompanyByMemberIdAndSessionId(member.getMemberId(), Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
            model.addAttribute("company", company);
        } else {
            if (companyId != null) {//有默认值
                EbsCompanyinfo company = ebsCompanyinfoDao.findById(companyId);
                model.addAttribute("company", company);
            }
            //读取交易团所添加企业列表
            Map<String, Object> filter = new HashMap<String, Object>();
            filter.put("memberId", member.getMemberId());
            filter.put("sessionIds", this.exhibitionInfo.get("sessionId").toString());
            filter.put("start", 0);
            filter.put("limit", 5000);
            List<Map<String, Object>> companys = ebsCompanyinfoDao.getTraddingGroupCompanys(filter);
            model.addAttribute("companys", companys);
        }

        String templateName = "web/" + language + "/personpapers";
//		if ("decorator".equals(type)) {
//			templateName = "web/"+language+"/personpapers-decorator";
//		}

        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-historypersoncard.html"})
    public String historyPersoncard(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        //公司类型和行业类型
        Map<String, Object> map = new HashMap<>();
        map.put("index", 0);
        map.put("size", 10000);
        List<SysCompanyType> companytypes = sysCompanyTypeService.list(map);
        List<SysIndustry> industries = sysIndustryService.list(map);
        model.addAttribute("companytypes", companytypes);
        model.addAttribute("industries", industries);
        model.addAttribute("pageName", "historypersoncard");
        model.addAttribute("language", language);
        model.addAttribute("type", type);


        String templateName = "web/" + language + "/person-history";
        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-view-{id}.html"})
    public String personview(@PathVariable String language, @PathVariable String type, @PathVariable int id, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        EbsPersonnelcard ebc = ebsPersonnelcardService.findById(id);
        model.addAttribute("info", ebc);
        String templateName = "web/" + language + "/person-view";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-historycarcard.html"})
    public String historyCarcard(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        //公司类型和行业类型
        Map<String, Object> map = new HashMap<>();
        map.put("index", 0);
        map.put("size", 10000);
        List<SysCompanyType> companytypes = sysCompanyTypeService.list(map);
        List<SysIndustry> industries = sysIndustryService.list(map);
        model.addAttribute("companytypes", companytypes);
        model.addAttribute("industries", industries);
        model.addAttribute("pageName", "historycarcard");
        model.addAttribute("language", language);
        model.addAttribute("type", type);

        String templateName = "web/" + language + "/car-history";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-historyenterprise.html"})
    public String historyEnterprise(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        //公司类型和行业类型
        Map<String, Object> map = new HashMap<>();
        map.put("index", 0);
        map.put("size", 10000);
        List<SysCompanyType> companytypes = sysCompanyTypeService.list(map);
        List<SysIndustry> industries = sysIndustryService.list(map);
        model.addAttribute("companytypes", companytypes);
        model.addAttribute("industries", industries);
        model.addAttribute("pageName", "historyenterprise");
        model.addAttribute("language", language);
        model.addAttribute("type", type);

        String templateName = "web/" + language + "/enterprise-history";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-vehiclecard-{paperType}.html"})
    public String vehiclecard(@PathVariable String language, @PathVariable String type, @PathVariable String paperType, Integer companyId, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        model.addAttribute("pageName", "vehiclecard");
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        //0.是否必须填报车辆行驶证
        Boolean needCarPicture = false;
        //1.证件类型id
        Integer intPaperType = 0;
        if (NumberUtil.isNumber(paperType)) {
            intPaperType = Integer.parseInt(paperType);
        } else {
            intPaperType = Integer.parseInt(paperType.substring(0, paperType.length() - 1));
            if (paperType.substring(paperType.length() - 1, paperType.length()).equals("o")) {
                model.addAttribute("isOut", 1);
            } else {
                model.addAttribute("isOut", 0);
            }
        }
        model.addAttribute("paperType", intPaperType);
        //2.设置此证件类型输入数量限制

        List<Map<String, Object>> cardProcess = memberService.getCardProcess(session, Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
        Long limit = 0L;
        for (Map<String, Object> card : cardProcess) {
            if (card.get("cardTypeId").equals(intPaperType)) {//获取当前证件类型
                if (type.equals("delegation") || type.equals("reporter")) {//只有交易团和代办员才限制数量
                    Long addedCount = Long.parseLong(card.get("cardCount").toString());
                    Long canCount = 0L;
                    if (card.get("inputCount") != null) {
                        canCount = Long.parseLong(card.get("inputCount").toString());
                    }
                    limit = canCount + addedCount;
                    model.addAttribute("limit", limit);
                    model.addAttribute("canCount", canCount);
                    model.addAttribute("addedCount", addedCount);
                }
            }
        }
        //3.证件类型
        CmCertificateType certificateModal = certificateType.findById(intPaperType);
        model.addAttribute("certificateModal", certificateModal);
        //4.是否是参展证
        model.addAttribute("isExhibitor", certificateModal.getIsexhibitor());
        //5.证件办理截止日期
        String cardStopDate = "";
        if (type.equals("delegation") || type.equals("reporter")) {
            Member member = (Member) session.getAttribute("member");
            PimAgent agent = agentSerivce.getAgentByMemberIdAndSessionId(member.getMemberId(), Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
            if (agent.getOnsitecertification().equals(1)) {//现场制证
                cardStopDate = null;//不设截止日期
            } else {
                cardStopDate = (String) exhibitionInfo.get("certificatesEndDate");
            }
            //是否需要填报车辆行驶证
            needCarPicture = agent.getUploadvehiclelicense() == 0 ? false : true;
        }
        if (certificateModal.getChinesename().contains("布撤展车证")) {
            cardStopDate = getCertificateEndDate(request);
            if (type.equals("decorator")) {
                needCarPicture = true;
            }
        }
        model.addAttribute("cardStopDate", cardStopDate);

        //6.是否超过限制时间
        Boolean isTimeout = false;
        if (cardStopDate != null && !"".equals(cardStopDate)) {
            try {
                long now = System.currentTimeMillis();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
                Date date = simpleDateFormat.parse(cardStopDate);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, 1);
                if ((now - c.getTimeInMillis()) > 0) {
                    isTimeout = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("isTimeout", isTimeout);
        model.addAttribute("needCarPicture", needCarPicture);

        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        }
        //6.设置非交易团和记者证的公司名称
        if (!type.equals("delegation") && !type.equals("reporter")) {
            EbsCompanyinfo company = ebsCompanyinfoDao.getCompanyByMemberIdAndSessionId(member.getMemberId(), Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
            model.addAttribute("company", company);
        } else {
            if (companyId != null) {//有默认值
                EbsCompanyinfo company = ebsCompanyinfoDao.findById(companyId);
                model.addAttribute("company", company);
            }
            //读取交易团所添加企业列表
            Map<String, Object> filter = new HashMap<String, Object>();
            filter.put("memberId", member.getMemberId());
            filter.put("sessionIds", this.exhibitionInfo.get("sessionId").toString());
            filter.put("start", 0);
            filter.put("limit", 5000);
            List<Map<String, Object>> companys = ebsCompanyinfoDao.getTraddingGroupCompanys(filter);
            model.addAttribute("companys", companys);
        }

        String templateName = "web/" + language + "/vehiclecard";

        return templateName;
    }

    /**
     * 获取搭建商办证截止时间
     * @param request
     * @return
     */
    private String getCertificateEndDate(HttpServletRequest request) {
        // 获取搭建商办证截止时间
        String endDate = null;
        try {
            String currentUrl = CryptographyUtil.GeCurrenttUrl(request);
            log.info("获取搭建商办证截止时间，当前请求地址:{}", currentUrl);
            String url = RequestConstant.getUrl(currentUrl, RequestConstant.APPLY_CERTIFICATES_END_DATE_TYPE);
            log.info("获取搭建商办证截止时间，请求地址:{}", url);
            String response = httpUtil.doGet(url);
            log.info("获取搭建商办证截止时间，当前请求地址:{}，请求地址:{}，返回结果:{}", currentUrl, url, response);
            JSONObject jsonObject = JSON.parseObject(response);
            if (jsonObject != null) {
                Object code = jsonObject.get("code");
                Object endate = jsonObject.get("endate");
                if (code != null && StrUtil.isNotEmpty(code.toString())
                        && StrUtil.equals("200", code.toString())
                        && endate != null && StrUtil.isNotEmpty(endate.toString())) {
                    endDate = endate.toString();
                }
            }
        } catch (Exception ex) {
            log.error("获取搭建商办证截止时间异常", ex.getMessage());
        }
        return endDate;
    }

    @RequestMapping(value = {"/{language}/{type}-enterprise.html"})
    public String enterprise(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        //所有展厅
        Map<String, Object> filter = new HashMap<>();
        filter.put("session", exhibitionInfo.get("sessionId").toString());
        List<String> rooms = ebsBoothService.queryAllShowRoom(filter);
        model.addAttribute("rooms", rooms);
        //公司类型和行业类型
        Map<String, Object> map = new HashMap<>();
        map.put("index", 0);
        map.put("size", 10000);
        List<SysCompanyType> companytypes = sysCompanyTypeService.list(map);
        List<SysIndustry> industries = sysIndustryService.list(map);
        model.addAttribute("companytypes", companytypes);
        model.addAttribute("industries", industries);
        model.addAttribute("pageName", "enterprise");
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("typeName", getMemberTypeName(type, language));

        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        }

        //此交易团下展位
        ResultModel resultModel = tradinggroupboothallocationService.selectAreaAndShowroomTypeAndBooth(memberId, 0, Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
        Map<String, Object> result = resultModel.getResult() != null ? (Map<String, Object>) resultModel.getResult() : null;
        if (result != null && resultModel.getStatus() == WConst.SUCCESS) {
            model.addAttribute("areas", result.get("areas"));
            model.addAttribute("inputInfo", result.get("inputInfo"));
        } else {
            model.addAttribute("areas", new ArrayList<Map<String, Object>>());
            model.addAttribute("inputInfo", new ArrayList<Map<String, Object>>());
        }
        //此交易团下展厅
        model.addAttribute("rooms", roomService.groupRooms(memberId, Integer.parseInt(exhibitionInfo.get("sessionId").toString())));
        //企业添加截止日期

        //5.证件办理截止日期
        String cardStopDate = "";
        if (type.equals("delegation") || type.equals("reporter")) {
            PimAgent agent = agentSerivce.getAgentByMemberIdAndSessionId(member.getMemberId(), Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
            if (agent.getOnsitecertification().equals(1)) {//现场制证
                cardStopDate = null;//不设截止日期
            } else {
                cardStopDate = (String) exhibitionInfo.get("delegationAddCompanyEndDate");
            }
            model.addAttribute("cardStopDate", cardStopDate);

            //6.是否超过限制时间
            Boolean isTimeout = false;
            if (cardStopDate != null) {
                try {
                    long now = System.currentTimeMillis();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
                    Date date = simpleDateFormat.parse(cardStopDate);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    c.add(Calendar.DATE, 1);
                    if ((now - c.getTimeInMillis()) > 0) {
                        isTimeout = true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            model.addAttribute("isTimeout", isTimeout);
        }

        String templateName = "web/" + language + "/enterprise";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/roomAndBooth-{companyId}.html"})
    public String roomAndBooth(@PathVariable String language, @PathVariable Integer companyId, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, "delegation", session, request, response);
        model.addAttribute("pageName", "roomAndBooth");
        model.addAttribute("language", language);

        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        }

        //此交易团下展位
        ResultModel resultModel = tradinggroupboothallocationService.selectAreaAndShowroomTypeAndBooth(memberId, companyId, Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
        Map<String, Object> result = (Map<String, Object>) resultModel.getResult();
        if (resultModel.getStatus() == WConst.SUCCESS) {
            model.addAttribute("areas", result.get("areas"));
            model.addAttribute("inputInfo", result.get("inputInfo"));
        } else {
            model.addAttribute("areas", new ArrayList<Map<String, Object>>());
            model.addAttribute("inputInfo", new ArrayList<Map<String, Object>>());
        }

        String templateName = "web/" + language + "/roomAndBooth";
        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-report.html"})
    public String report(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("pageName", "report");
        model.addAttribute("typeId", getMemberTypeId(type));
        model.addAttribute("typeName", getMemberTypeName(type, language));

        String templateName = "web/" + language + "/report";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-takecard.html"})
    public String takecard(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("pageName", "report");
        model.addAttribute("typeId", getMemberTypeId(type));
        model.addAttribute("typeName", getMemberTypeName(type, language));

        String templateName = "web/" + language + "/takecard";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-password.html"})
    public String password(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("pageName", "login");
        model.addAttribute("typeId", getMemberTypeId(type));
        model.addAttribute("typeName", getMemberTypeName(type, language));

        String templateName = "web/" + language + "/" + "password";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-company.html"})
    public String company(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("pageName", "login");
        model.addAttribute("typeId", getMemberTypeId(type));
        model.addAttribute("typeName", getMemberTypeName(type, language));

        //获取member和展会届次
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        }

        Integer sessionId = (Integer) exhibitionInfo.get("sessionId");

        //1.公司类型和行业类型
        Map<String, Object> map = new HashMap<>();
        map.put("index", 0);
        map.put("size", 10000);
        List<SysCompanyType> companytypes = sysCompanyTypeService.list(map);
        List<SysIndustry> industries = sysIndustryService.list(map);
        //2.展位申请状态
        model.addAttribute("applyInfo", memberService.getExhibitorApplyInfo(session, sessionId));
        model.addAttribute("companytypes", companytypes);
        model.addAttribute("industries", industries);
        String templateName = "web/" + language + "/" + "company";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-decorator.html"})
    public String decorator(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("pageName", "login");
        model.addAttribute("typeId", getMemberTypeId(type));
        model.addAttribute("typeName", getMemberTypeName(type, language));

        //获取member和展会届次
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        }

        Integer sessionId = (Integer) exhibitionInfo.get("sessionId");

        //1.公司类型和行业类型
        Map<String, Object> map = new HashMap<>();
        map.put("index", 0);
        map.put("size", 10000);
        List<SysCompanyType> companytypes = sysCompanyTypeService.list(map);
        List<SysIndustry> industries = sysIndustryService.list(map);
        //2.展位申请状态
        model.addAttribute("applyInfo", memberService.getExhibitorApplyInfo(session, sessionId));
        model.addAttribute("companytypes", companytypes);
        model.addAttribute("industries", industries);
        //3.判断搭建商审核时间是否超过范围
        boolean auditFlag = getAuditFlag(request);
        model.addAttribute("auditFlag", auditFlag + "");
        String templateName = "web/" + language + "/" + "decorator";

        return templateName;
    }

    /**
     * 获取搭建商资质审核时间，判断是否已经超出范围
     * @param request
     * @return
     */
    private boolean getAuditFlag(HttpServletRequest request) {
        // 获取搭建商资质审核时间，判断是否已经超出范围
        boolean auditFlag = true;
        try {
            String currentUrl = CryptographyUtil.GeCurrenttUrl(request);
            log.info("获取搭建商资质审核时间，当前请求地址:{}", currentUrl);
            String url = RequestConstant.getUrl(currentUrl, RequestConstant.QUALIFICATION_REVIEW_END_DATE_TYPE);
            log.info("获取搭建商资质审核时间，请求地址:{}", url);
            String response = httpUtil.doGet(url);
            log.info("获取搭建商资质审核时间，当前请求地址:{}，请求地址:{}，返回结果:{}", currentUrl, url, response);
            JSONObject jsonObject = JSON.parseObject(response);
            if (jsonObject != null) {
                Object code = jsonObject.get("code");
                Object endate = jsonObject.get("endate");
                if (code != null && StrUtil.isNotEmpty(code.toString())
                        && StrUtil.equals("200", code.toString())
                        && endate != null && StrUtil.isNotEmpty(endate.toString())) {
                    // 比较当前系统时间和资质审核时间
                    String nowDate = cn.hutool.core.date.DateUtil.today();
                    int result = nowDate.compareTo(endate.toString());
                    if (result > 0) {
                        auditFlag = false;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("获取搭建商资质审核时间异常", ex.getMessage());
        }
        return auditFlag;
    }

    @RequestMapping(value = {"/{language}/{type}-notice.html"})
    public String article(@PathVariable String language, @PathVariable String type, Model model, HttpServletRequest request) {
        setCommonContent(model);
        String sessionId = sessionService.getSessionID(request);
        model.addAttribute("language", language);
        PageModel page = new PageModel();
        page.setStart(0);
        page.setLimit(1000);
        page.setSession(sessionId);
        page.setOrder("DESC");
        page.setField("updateTime");
        page.setStatus(1);
        List<EbsNotice> notices = (List<EbsNotice>) decoratorEbsNoticeService.getNotices(page).getResult();
        model.addAttribute("notices", notices);
        if (notices != null && notices.size() > 0) {
            model.addAttribute("notice", notices.get(0));
        } else {
            model.addAttribute("notice", null);
        }
        String templateName = "web/" + language + "/notice";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/notice-{noticeId}.html"})
    public String article(@PathVariable String language, @PathVariable Integer noticeId, Model model, HttpServletRequest request) {
        setCommonContent(model);
        String sessionId = sessionService.getSessionID(request);
        model.addAttribute("language", language);
        PageModel page = new PageModel();
        page.setStart(0);
        page.setLimit(1000);
        page.setSession(sessionId);
        page.setOrder("DESC");
        page.setField("updateTime");
        page.setStatus(1);
        List<EbsNotice> notices = (List<EbsNotice>) decoratorEbsNoticeService.getNotices(page).getResult();
        model.addAttribute("notices", notices);
        if (noticeId == null || noticeId.intValue() == 0) {
            if (notices != null && notices.size() > 0) {
                model.addAttribute("notice", notices.get(0));
            }
        } else {
            model.addAttribute("notice", decoratorEbsNoticeService.getNotice(noticeId).getResult());

        }
        String templateName = "web/" + language + "/notice";

        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-stadium.html"})
    public String stadium(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        model.addAttribute("pageName", "vehiclecard");
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("typeId", getMemberTypeId(type));
        model.addAttribute("typeName", getMemberTypeName(type, language));
        //5.报馆申请截止日期
        String stadiumStopDate = "";
        if (type.equals("decorator")) {
            Object stadiumDecoratorEndDate = getStadiumEndDate(request);
            if (stadiumDecoratorEndDate != null) {
                stadiumStopDate = (String) stadiumDecoratorEndDate;
            } else {
                stadiumStopDate = null;//不设截止日期
            }
        }
        model.addAttribute("stadiumStopDate", stadiumStopDate);

        //6.是否超过限制时间
        Boolean isTimeout = false;
        if (stadiumStopDate != null && !"".equals(stadiumStopDate)) {
            try {
                long now = System.currentTimeMillis();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
                Date date = simpleDateFormat.parse(stadiumStopDate);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, 1);
                if ((now - c.getTimeInMillis()) > 0) {
                    isTimeout = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("isTimeout", isTimeout);

        String listData = getExhibitorList(request);
        List<Map<String, Object>> companys = new ArrayList<>();
        if (StrUtil.isNotEmpty(listData)) {
            List<Object> objectList =JSON.parseArray(listData);
            for (Object object : objectList){
                //取出list里面的值转为map
                Map<String,Object> ret = (Map<String, Object>) object;
                companys.add(ret);
            }
        }
        model.addAttribute("companys", companys);

        Member member = (Member) session.getAttribute("member");
        EbsCompanyinfo company = ebsCompanyinfoDao.getCompanyByMemberIdAndSessionId(
                member.getMemberId(),
                Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
        model.addAttribute("company", company);
        boolean isAuditAgree = true;
        if (company.getAuditStatus() != 2) {
            isAuditAgree = false;
        }
        model.addAttribute("isAuditAgree", isAuditAgree);

        String templateName = "web/" + language + "/stadium";

        return templateName;
    }

    /**
     * 获取展商列表
     * @param request
     * @return
     */
    private String getExhibitorList(HttpServletRequest request) {
        // 获取展商列表
        String listData = null;
        try {
            String currentUrl = CryptographyUtil.GeCurrenttUrl(request);
            log.info("获取展商列表，当前请求地址:{}", currentUrl);
            String url = RequestConstant.getUrl(currentUrl, RequestConstant.EXHIBITOR_LIST_TYPE);
            log.info("获取展商列表，请求地址:{}", url);
            String response = httpUtil.doGet(url);
            log.info("获取展商列表，当前请求地址:{}，请求地址:{}，返回结果:{}", currentUrl, url, response);
            JSONObject jsonObject = JSON.parseObject(response);
            if (jsonObject != null) {
                Object code = jsonObject.get("code");
                Object data = jsonObject.get("data");
                if (code != null && StrUtil.isNotEmpty(code.toString())
                        && StrUtil.equals("200", code.toString())
                        && data != null && StrUtil.isNotEmpty(data.toString())) {
                    listData = data.toString();
                }
            }
        } catch (Exception ex) {
            log.error("获取展商列表异常", ex.getMessage());
        }
        return listData;
    }

    /**
     * 获取报馆申请截止时间
     * @param request
     * @return
     */
    private String getStadiumEndDate(HttpServletRequest request) {
        // 获取报馆申请截止时间
        String endDate = null;
        try {
            String currentUrl = CryptographyUtil.GeCurrenttUrl(request);
            log.info("获取报馆申请截止时间，当前请求地址:{}", currentUrl);
            String url = RequestConstant.getUrl(currentUrl, RequestConstant.STADIUM_DECORATOR_END_DATE_TYPE);
            log.info("获取报馆申请截止时间，请求地址:{}", url);
            String response = httpUtil.doGet(url);
            log.info("获取报馆申请截止时间，当前请求地址:{}，请求地址:{}，返回结果:{}", currentUrl, url, response);
            JSONObject jsonObject = JSON.parseObject(response);
            if (jsonObject != null) {
                Object code = jsonObject.get("code");
                Object endate = jsonObject.get("endate");
                if (code != null && StrUtil.isNotEmpty(code.toString())
                        && StrUtil.equals("200", code.toString())
                        && endate != null && StrUtil.isNotEmpty(endate.toString())) {
                    endDate = endate.toString();
                }
            }
        } catch (Exception ex) {
            log.error("获取报馆申请截止时间异常", ex.getMessage());
        }
        return endDate;
    }

    /**
     * 获取指定条件的搭建商报馆展商列表
     *
     * @return
     */
    @GetMapping(value = "/listStadiumCompany")
    @ResponseBody
    public List<Map<String, Object>> listStadiumCompany(@RequestParam String companyName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("index", 0);
        map.put("size", 100000);
        map.put("companyName", companyName);
        List<Map<String, Object>> companyList = ebsCompanyinfoDao.getStadiumCompanys(map);
        return companyList;
    }

    /**
     * 添加报馆申请
     */
    @PostMapping("/stadium/save")
    @ResponseBody
    public ResultModel stadiumSave(EbsStadium stadium, HttpSession session) {
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        stadium.setAgent(memberId);
        EbsCompanyinfo company = ebsCompanyinfoDao.getCompanyByMemberIdAndSessionId(
                member.getMemberId(),
                Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
        if (company != null) {
            stadium.setCompanyid(company.getId());
            stadium.setCompanyname(company.getChinesename());
        }
        return stadiumService.addOrUpdate(stadium);
    }

    /**
     * 获取报馆申请列表
     *
     * @param page 分页及搜索实体
     * @return
     */
    @RequestMapping(value = "/stadium/list")
    @ResponseBody
    public ResultModel stadiums(EbsStadium stadium, PageModel page, HttpSession session) {
        setCommonContent();

        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        stadium.setAgent(memberId);
        stadium.setSession(exhibitionInfo.get("sessionId").toString());
        return stadiumService.getStadiums(stadium, page);
    }

    /**
     * 批量删除
     *
     * @param session
     * @param idList
     * @return
     */
    @RequestMapping(value = "/stadium/delete")
    @ResponseBody
    public ResultModel stadiumDelete(HttpSession session, @RequestParam(value = "idList[]") Integer[] idList) {
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        return stadiumService.delete(idList, memberId);
    }

    @RequestMapping(value = "/decorator-files/downloadFitmentApplicationTemplate")
    public void downloadFitmentApplicationTemplate(HttpServletResponse response, HttpServletRequest request) {
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            String filename = "附件2.特装展位装修工程申请表.doc";
            String path = "file/附件2.特装展位装修工程申请表.doc";
            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:"+path);
            response.setContentType("application/x-msdownload;");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("charset", "utf-8");
            response.addHeader("Pragma", "no-cache");
            String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);

            inputStream = resource.getInputStream();
            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/decorator-files/downloadSafetyResponsibilityCommitTemplate")
    public void downloadSafetyResponsibilityCommitTemplate(HttpServletResponse response, HttpServletRequest request) {
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            String filename = "附件3.特装展位搭建和用电安全责任承诺书.doc";
            String path = "file/附件3.特装展位搭建和用电安全责任承诺书.doc";
            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:"+path);
            response.setContentType("application/x-msdownload;");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("charset", "utf-8");
            response.addHeader("Pragma", "no-cache");
            String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);

            inputStream = resource.getInputStream();
            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = {"/{language}/{type}-info.html"})
    public String info(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("pageName", "info");
        model.addAttribute("typeId", getMemberTypeId(type));
        model.addAttribute("typeName", getMemberTypeName(type, language));

        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        }

        Integer sessionId = (Integer) exhibitionInfo.get("sessionId");

        //添加展位申请进度信息
        if (type.equals("exhibitor")) {
            model.addAttribute("boothProcess", memberService.getBoothProcess(memberId, sessionId));
        }
        List<Map<String, Object>> cardProcess = memberService.getCardProcess(session, Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
        model.addAttribute("cardProcess", cardProcess);


        //添加证件审核失败信息
        Map<String, Object> faildCards = memberService.getFaildCards(session);

        model.addAttribute("persons", faildCards.get("person"));
        model.addAttribute("cars", faildCards.get("car"));
        String templateName = "web/" + language + "/" + "info";
        return templateName;
    }

    @RequestMapping(value = {"/{language}/{type}-agent.html"})
    public String agent(@PathVariable String language, @PathVariable String type, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        setCommonContent(model);
        safeCheck(language, type, session, request, response);
        model.addAttribute("language", language);
        model.addAttribute("type", type);
        model.addAttribute("pageName", "login");
        model.addAttribute("typeId", getMemberTypeId(type));
        model.addAttribute("typeName", getMemberTypeName(type, language));

        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        }

        model.addAttribute("agent", agentSerivce.getAgentByMemberIdAndSessionId(memberId, Integer.parseInt(exhibitionInfo.get("sessionId").toString())));
        String templateName = "web/" + language + "/" + "agent";
        return templateName;
    }
    /*** 设置公共部分**/

    /**
     * 设置公共参数
     *
     * @param model
     */
    public void setCommonContent(Model model) {
        //1.设置当前届次id
        setCommonContent();
        model.addAttribute("exhibitionSessionId", exhibitionInfo.get("sessionId"));
        model.addAttribute("exhibitionInfo", exhibitionInfo);
    }

    public void setCommonContent() {
        exhibitionInfo = sessionService.getExhibitionInfo(request);
    }

    /**
     * 安全登陆验证，需登陆后访问的都加此验证
     */
    public Boolean safeCheck(String language, String type, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        if (session.getAttribute("member") == null) {
            try {
                request.getRequestDispatcher("/" + language + "/" + type + "-login.html").forward(request, response);
//				response.sendRedirect("/"+language + "/"+type+"-login.html");
//				response.flushBuffer();
//				response.end
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private Integer getMemberTypeId(String type) {
        Integer typeId = 0;
        switch (type) {//会员类型，
            case "exhibitor"://2：零散展商，
                typeId = Member.MEMBER_TYPE_EXHIBITOR_CODE;
                break;
            case "delegation"://0：交易团，
                typeId = Member.MEMBER_TYPE_TRADE_CODE;
                break;
            case "decorator"://3：布撤展商，
                typeId = Member.MEMBER_TYPE_DECORATOR_CODE;
                break;
            case "foreign"://4：外宾，
                typeId = Member.MEMBER_TYPE_FOREIGN_CODE;
                break;
            case "reporter"://1：记者，
                typeId = Member.MEMBER_TYPE_REPORT_CODE;
                break;
            case "purchaser"://5：采购商，
                typeId = Member.MEMBER_TYPE_PURCHASER_CODE;
                break;

        }
        return typeId;
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
                        typeName = "individual exhibitor";
                        break;
                    case "ru":
                        typeName = "участников";
                        break;
                    case "jp":
                        typeName = "個別出展商";
                        break;
                    case "kr":
                        typeName = "개별업체";
                        break;
                }
                break;
            case "delegation":
                switch (language) {
                    case "cn":
                        typeName = "交易团/省直厅局";
                        break;
                    case "en":
                        typeName = " trade delegation/departments and bureaus under province";
                        break;
                    case "ru":
                        typeName = "группы / Вход для департаментов пров.Хэйлунзян";
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
                        typeName = "booth builders";
                        break;
                    case "ru":
                        typeName = "застройщиков";
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
                        typeName = "forgein guests";
                        break;
                    case "ru":
                        typeName = " инностранных гостей";
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
                        typeName = "journalists";
                        break;
                    case "ru":
                        typeName = "Прессы";
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
                        typeName = "purchasers";
                        break;
                    case "ru":
                        typeName = "покупателей";
                        break;
                    case "jp":
                        typeName = "バイヤー";
                        break;
                    case "kr":
                        typeName = "바이어 ";
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
    @RequestMapping(value = {"/{language}/{type}-apply-update"})
    public ResultModel applyUpdate(@PathVariable String language, @PathVariable String type, HttpSession session, HttpServletRequest request, HttpServletResponse response, String parameter) {
        safeCheck(language, type, session, request, response);
        return applyService.updateApply(parameter);
    }

    @ResponseBody
    @RequestMapping(value = {"/{language}/{type}-apply-get"})
    public ResultModel applyGet(@PathVariable String language, @PathVariable String type, HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer memberId, Integer sessionId) {
        safeCheck(language, type, session, request, response);
        return applyService.getApply(memberId, sessionId);
    }

    @ResponseBody
    @RequestMapping(value = {"/activateHistoryAccount"})
    public ResultModel activateHistoryAccount(Integer memberId, Integer companyId, String sessionId) {
        setCommonContent();
        return memberService.activateHistoryAccount(companyId, memberId, sessionId);
    }

    @ResponseBody
    @RequestMapping(value = {"/sendPhoneCode"})
    public ResultModel sendPhoneCode(String phone, String companyName, HttpSession session) {
        setCommonContent();
        return memberService.sendPhoneCode(phone, companyName, this.exhibitionInfo.get("sessionId").toString(), session);
    }

    @ResponseBody
    @RequestMapping(value = {"/sendEmailCode"})
    public ResultModel sendEmailCode(String email, String companyName, HttpSession session) {
        setCommonContent();
        return memberService.sendEmailCode(email, companyName, this.exhibitionInfo.get("sessionId").toString(), session);
    }

    @ResponseBody
    @RequestMapping(value = {"/api/common/forgot"})
    public ResultModel forgot(String companyName, String email, String phone, String resetType) {
        setCommonContent();
        return memberService.forgot(companyName, email, phone, resetType, exhibitionInfo.get("sessionId").toString());
    }

    /**
     * 生成验证码
     */
    @RequestMapping(value = "/getVerifyCode")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
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
    @RequestMapping(value = "/getCountryArea")
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
    @PostMapping("/personcard/save")
    @ResponseBody
    public ResultModel personCardSave(EbsPersonnelcard ebsPersonnelcard, HttpSession session) {
        setCommonContent();
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        return ebsPersonnelcardService.addOrUpdate(ebsPersonnelcard, member, this.exhibitionInfo);
    }

    /**
     * 获取人员证件列表
     *
     * @param page 分页及搜索实体
     * @return
     */
    @RequestMapping(value = "/personcard/list")
    @ResponseBody
    public ResultModel personCards(EbsPersonnelcard ebsPersonnelcard, PageModel page, HttpSession session) {
        setCommonContent();

        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
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
    @RequestMapping(value = "/personcard/delete")
    @ResponseBody
    public ResultModel personcardDelete(HttpSession session, @RequestParam(value = "idList[]") Integer[] idList) {
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        return ebsPersonnelcardService.delete(idList, memberId);
    }

    /**
     * 获取人员证件列表
     *
     * @param map 分页及搜索实体
     * @return
     */
    @GetMapping(value = {"/personcard/download", "/vehiclecard/download"})
    @ResponseBody
    public void personCardDownload(@RequestParam String path, @RequestParam String downloadType, HttpServletRequest request, HttpServletResponse pipeline) {
//        String path = map.get("path").toString();
//        String url = request.getScheme()+"://"+request.getServerName() + ":" + request.getLocalPort() + path;
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//
//        try {
//
//            HttpGet httpGet = new HttpGet(url);
//            httpGet.setHeader("Accept", "*/*");
//            httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
//
//            CloseableHttpResponse response = httpClient.execute(httpGet);
//            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                pipeline.setCharacterEncoding("utf-8");
//                if (response.getEntity().getContentType().getValue().contains("text/html")) {
//                    pipeline.setContentType("text/html;charset=utf-8");
//                    pipeline.getOutputStream().write("文件不存在".getBytes("utf-8"));
//                } else {
//                    Header[] headers = response.getAllHeaders();
//                    for (Header header : headers) {
//                        if ("Server".equalsIgnoreCase(header.getName())
//                                || "Via".equalsIgnoreCase(header.getName())
//                                || "X-Kong-Upstream-Latency".equalsIgnoreCase(header.getName())
//                                || "X-Kong-Proxy-Latency".equalsIgnoreCase(header.getName())
//                                || "Access-Control-Allow-Credentials".equalsIgnoreCase(header.getName())
//                                || "Access-Control-Allow-Methods".equalsIgnoreCase(header.getName())
//                                || "Access-Control-Allow-Headers".equalsIgnoreCase(header.getName())
//                                || "Connection".equalsIgnoreCase(header.getName())) {
//                            continue;
//                        }
//
//                        pipeline.setHeader(header.getName(), header.getValue());
//                    }
//
//                    String downloadFileName = "人员证书";
//                    if ("vehiclecard".equals(map.get("downloadType"))) {
//                        downloadFileName = "车辆证书";
//                    }
//                    response.addHeader( "Content-Type", "application/octet-stream");
//                    response.addHeader( "Content-Disposition", "attachment; filename="
//                            + new String(downloadFileName.getBytes( "UTF-8"), "ISO-8859-1")
//                            + ".jpg;filename*=UTF-8''" + new String(downloadFileName.getBytes( "UTF-8"), "ISO-8859-1"));
//
////                    pipeline.setContentType("application/force-download;charset=utf-8");
//                    response.getEntity().writeTo(pipeline.getOutputStream());
//                }
//            }
//
//            //释放链接
//            if (null != response) {
//                response.close();
//            }
//            if (null != pipeline.getOutputStream()) {
//                pipeline.getOutputStream().close();
//            }
//        } catch (Exception e) {
//            try {
//                pipeline.setContentType("text/html;charset=utf-8");
//                pipeline.getOutputStream().write("下载失败".getBytes("utf-8"));
//                if (null != pipeline.getOutputStream()) {
//                    pipeline.getOutputStream().close();
//                }
//            } catch (IOException e1) {
//                e1.printStackTrace();;
//                System.out.println("图片下载已经捕获异常，关闭连接时异常，原因：" + e.getMessage());
//            }
//            e.printStackTrace();
//            System.out.println("图片下载异常，原因：" + e.getMessage());
//        } finally {
//            try {
//                httpClient.close();
////                pipeline = null;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        String path = map.get("path").toString();
        if (path == null || "".equals(path)) {
            path = request.getParameter("path");
        }
        if (downloadType == null || "".equals(downloadType)) {
            downloadType = request.getParameter("downloadType");
        }
        logger.info("证书下载，path：" + path + ",downloadType:" + downloadType);
        String filename = "人员证书";
        if ("vehiclecard".equals(downloadType)) {
            filename = "车辆证书";
        }
        File file = new File("./static" + path);

        try {
            pipeline.reset();// 清空输出流

            String resultFileName = filename + System.currentTimeMillis() + ".jpg";
            resultFileName = URLEncoder.encode(resultFileName, "UTF-8");
            pipeline.setCharacterEncoding("UTF-8");
            pipeline.setHeader("Content-disposition", "attachment; filename=" + resultFileName);// 设定输出文件头
            pipeline.setContentType("image/jpeg;charset=UTF-8");// 定义输出类型
            //输入流：本地文件路径
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            //输出流
            OutputStream out = pipeline.getOutputStream();
            //输出文件
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            pipeline.reset();
            try {
                OutputStreamWriter writer = new OutputStreamWriter(pipeline.getOutputStream(), "UTF-8");
                String data = "<script language='javascript'>alert(\"\\u64cd\\u4f5c\\u5f02\\u5e38\\uff01\");</script>";
                writer.write(data);
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 添加参展商管理-人员证件管理
     */
    @PostMapping("/vehiclecard/save")
    @ResponseBody
    public ResultModel vehiclecardSave(EbsVehiclecard vehiclecard, HttpSession session) {
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        vehiclecard.setAgent(memberId);
        return vehiclecardService.addOrUpdate(vehiclecard);
    }

    /**
     * 获取人员证件列表
     *
     * @param page 分页及搜索实体
     * @return
     */
    @RequestMapping(value = "/vehiclecard/list")
    @ResponseBody
    public ResultModel vehicleCards(EbsVehiclecard vehiclecard, PageModel page, HttpSession session) {
        setCommonContent();

        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
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
    @RequestMapping(value = "/vehiclecard/delete")
    @ResponseBody
    public ResultModel vehiclecardDelete(HttpSession session, @RequestParam(value = "idList[]") Integer[] idList) {
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
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
    @RequestMapping(value = {"/enterprise/save"})
    @ResponseBody
    public ResultModel enterpriseUpdate(HttpSession session, String parameter, EbsCompanyinfo company) {
        return applyService.updateEnterpriseApply(parameter, company);
    }

    /**
     * 参展商删除企业
     *
     * @param language
     * @param type
     * @param session
     * @param request
     * @param response
     * @param parameter
     * @return
     */
    @RequestMapping(value = {"/enterprise/delete"})
    @ResponseBody
    public ResultModel enterpriseDelete(HttpSession session, @RequestParam(value = "idList[]") Integer[] idList) {
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        return companyService.delete(idList, memberId, Integer.parseInt(exhibitionInfo.get("sessionId").toString()));
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
    @RequestMapping(value = {"/enterprise/list"})
    @ResponseBody
    public ResultModel enterpriseList(Integer roomId, Integer status, PageModel page, HttpSession session) {
        setCommonContent();
        return companyService.getTraddingGroupCompanys(roomId, status, exhibitionInfo.get("sessionId").toString(), page, session);
    }

    /**
     * 获取会员关联企业信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/company/get"})
    @ResponseBody
    public ResultModel getCompanyByMember(HttpSession session, HttpServletRequest request) {
        return companyService.getMemberCompany(session, request);
    }

    /**
     * 获取会员关联企业信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/decorator/get"})
    @ResponseBody
    public ResultModel getDecoratorByMember(HttpSession session, HttpServletRequest request) {
        return companyService.getMemberDecorator(session, request);
    }

    /**
     * 获取会员关联企业信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/company/update"})
    @ResponseBody
    public ResultModel updateMemberCompany(EbsCompanyinfo company, HttpSession session) {
        return companyService.updateMemberCompany(company, session);
    }

    /**
     * 代办员自我更新
     *
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/agent/update"})
    @ResponseBody
    public ResultModel updateagent(PimAgent agent, HttpSession session) {
        ResultModel result;
        try {
            result = new ResultModel(WConst.SUCCESS, WConst.SAVED, agentSerivce.update(agent));
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
    @RequestMapping(value = {"/api/history/personcard"})
    @ResponseBody
    public ResultModel historyPersonCard(String cardname, PageModel page, HttpSession session) {
        setCommonContent();
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        return ebsPersonnelcardService.getHistoryPersonCard(memberId, Integer.parseInt(exhibitionInfo.get("sessionId").toString()), cardname, page);
    }

    /**
     * 获取会员关联企业信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/history/carcard"})
    @ResponseBody
    public ResultModel historyCarCard(String cardname, PageModel page, HttpSession session) {
        setCommonContent();
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        return vehiclecardService.getHistoryCarCard(memberId, Integer.parseInt(exhibitionInfo.get("sessionId").toString()), cardname, page);
    }

    /**
     * 获取会员关联企业信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/history/enterprise"})
    @ResponseBody
    public ResultModel historyEnterprise(PageModel page, HttpSession session) {
        setCommonContent();
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }

        return companyService.getTraddingGroupHistoryCompanys(Integer.parseInt(exhibitionInfo.get("sessionId").toString()), memberId, page);
    }

    /**
     * 获取会员关联企业信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/personcard/rejoin"})
    @ResponseBody
    public ResultModel personRejoin(HttpSession session, @RequestParam(value = "idList[]") Integer[] idList, @RequestParam(value = "repeat") Integer repeat) {
        setCommonContent();
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        return ebsPersonnelcardService.rejoin(idList, repeat, this.exhibitionInfo, member);
    }

    /**
     * 获取会员关联企业信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/carcard/rejoin"})
    @ResponseBody
    public ResultModel carRejoin(HttpSession session, @RequestParam(value = "idList[]") Integer[] idList) {
        setCommonContent();
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        return vehiclecardService.rejoin(idList, Integer.parseInt(exhibitionInfo.get("sessionId").toString()), memberId);
    }

    /**
     * 获取会员关联企业信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/enterprise/rejoin"})
    @ResponseBody
    public ResultModel enterpriseRejoin(HttpSession session, @RequestParam(value = "idList[]") Integer[] idList) {
        setCommonContent();
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        return companyService.rejoin(idList, Integer.parseInt(exhibitionInfo.get("sessionId").toString()), memberId);
    }

    /**
     * 获取人员取证报表
     *
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/report/person"})
    @ResponseBody
    public ResultModel personReport(HttpSession session, Integer cardTypeId, Integer status) {
        setCommonContent();
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        Integer evidenceDelay = 0;
        if (exhibitionInfo.get("evidenceDelay") != null) {
            evidenceDelay = Integer.parseInt(exhibitionInfo.get("evidenceDelay").toString());
        }
        return ebsPersonnelcardService.takeAwayReport(evidenceDelay, memberId, Integer.parseInt(exhibitionInfo.get("sessionId").toString()), status, cardTypeId);
    }

    /**
     * 获取车辆取证报表
     *
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/report/car"})
    @ResponseBody
    public ResultModel carReport(HttpSession session, Integer cardTypeId, Integer status) {
        setCommonContent();
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        Integer evidenceDelay = 0;
        if (exhibitionInfo.get("evidenceDelay") != null) {
            evidenceDelay = Integer.parseInt(exhibitionInfo.get("evidenceDelay").toString());
        }
        return vehiclecardService.takeAwayReport(evidenceDelay, memberId, Integer.parseInt(exhibitionInfo.get("sessionId").toString()), status, cardTypeId);
    }

    /**
     * 获取产品列表
     *
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/product/list"})
    @ResponseBody
    public ResultModel productList(HttpSession session, PageModel page, String productName, String companyName, String status) {
        setCommonContent();
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
        } else {
            return new ResultModel(WConst.LOGINOVERTIME, WConst.LOGINOVERTIMEMSG, null);
        }
        String sessionId = exhibitionInfo.get("sessionId").toString();
        Integer intStatus = null;
        if (!status.equals("")) {
            intStatus = Integer.parseInt(status);
        }
        return webProductService.webList(sessionId, memberId, intStatus, productName, companyName, page);
    }

    /**
     * 获取产品列表
     *
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/product/update"})
    @ResponseBody
    public R productUpdate(HttpSession session, WebProduct product) {
        setCommonContent();
        int memberId = 0;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            memberId = member.getMemberId();
            product.setMemberid(memberId);
        } else {
            return R.error().put("status", WConst.LOGINOVERTIME).put("msg", WConst.LOGINOVERTIMEMSG);
        }
        try {
            if (product.getProductId() == null || product.getProductId().equals(0)) {
                webProductService.save(product);
            } else {
                webProductService.update(product);
            }
            return R.ok().put("result", product).put("status", WConst.SUCCESS).put("msg", WConst.SAVED);
        } catch (Exception e) {
            return R.error().put("status", WConst.ERROR).put("msg", WConst.SAVEDERROR);
        }
    }

    /**
     * 批量删除
     */
    @RequestMapping("/api/product/delete")
    @ResponseBody
    public R productDelete(HttpSession session, @RequestParam(value = "idList[]") Integer[] idList) {
        for (Integer id : idList) {
            webProductService.deleteById(id);
        }
        return R.ok().put("status", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

    @RequestMapping("/getBarCode")
    public void getBarCode(String code, HttpServletResponse response) throws Exception {
        BufferedImage image = BarCodeUtil.insertWords(BarCodeUtil.getBarCode(code), code);
        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    /**
     * 验证公司名称
     *
     * @param companyName
     * @param memberType
     * @throws Exception
     */
    @RequestMapping("/api/common/company/validate")
    @ResponseBody
    public ResultModel validateCompany(String companyName, String memberType) {
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
        Integer sessionId = Integer.parseInt(exhibitionInfo.get("sessionId").toString());
        return companyService.validateCompany(companyName, memberTypeId, sessionId);

    }

    /**
     * 验证公司名称
     *
     * @param companyName
     * @param memberType
     * @throws Exception
     */
    @RequestMapping("/api/common/company/history/validate")
    @ResponseBody
    public ResultModel validateHistoryCompany(EbsCompanyinfo company, HttpSession session) {
        setCommonContent();
        Integer sessionId = Integer.parseInt(exhibitionInfo.get("sessionId").toString());
        company.setSession(sessionId.toString());
        return companyService.validateHistoryCompany(company, session);

    }

    /**
     * 注册会员
     *
     * @param Member
     * @return
     */
    @RequestMapping(value = "/api/regist")
    @ResponseBody
    public ResultModel regist(Integer isActive, HttpSession session, Member member, EbsCompanyinfo company) {
        setCommonContent();

        Integer sessionId = Integer.parseInt(exhibitionInfo.get("sessionId").toString());
        company.setSession(sessionId.toString());
        return memberService.regist(isActive, member, company, session);
    }

    @RequestMapping(value = "/decorator-files/downloadAdmissionApplicationFormTemplate")
    public void downloadAdmissionApplicationFormTemplate(HttpServletResponse response, HttpServletRequest request) {
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            String filename = "附件1.特装搭建服务商资质认证申请表.doc";
            String path = "file/附件1.特装搭建服务商资质认证申请表.doc";
            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:"+path);
            response.setContentType("application/x-msdownload;");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("charset", "utf-8");
            response.addHeader("Pragma", "no-cache");
            String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);

            inputStream = resource.getInputStream();
            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}