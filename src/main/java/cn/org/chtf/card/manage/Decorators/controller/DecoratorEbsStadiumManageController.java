package cn.org.chtf.card.manage.Decorators.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.org.chtf.card.common.constant.RequestConstant;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.Decorators.service.DecoratorEbsStadiumManageService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.support.util.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 搭建商报馆信息Controller
 *
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Decorators/ebsStadiumManage")
@Slf4j
public class DecoratorEbsStadiumManageController {

    @Autowired
    private DecoratorEbsStadiumManageService decoratorEbsStadiumManageService;

    @Autowired
    private SysSessionService sysSessionService;

    @Autowired
    private SysOperationLogService sysOperationLogService;

    @Resource
    private HttpUtil httpUtil;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        try {
            String strSessionid = sysSessionService.getSessionID(request);
            map.put("session", strSessionid);
            User user = (User) session.getAttribute("user");
            map.put("userId", user.getId());
            map = ResultVOUtil.TiaoZhengFenYe(map);
            List<Map<String, Object>> list = decoratorEbsStadiumManageService.list(map);
            int count = decoratorEbsStadiumManageService.listcount(map);
            // 获取报馆申请截止时间，判断是否已经超出范围
            boolean stadiumFlag = getStadiumFlag(request);
            if (CollectionUtil.isNotEmpty(list)) {
                final String stadiumFlagStr = stadiumFlag + "";
                list.stream().forEach(item -> item.put("stadiumFlag", stadiumFlagStr));
            }
            return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
        }
    }

    /**
     * 获取报馆申请截止时间，判断是否已经超出范围
     * @param request
     * @return
     */
    private boolean getStadiumFlag(HttpServletRequest request) {
        // 获取报馆申请截止时间，判断是否已经超出范围
        boolean stadiumFlag = true;
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
                    // 比较当前系统时间和报馆申请截止时间
                    String nowDate = DateUtil.today();
                    int result = nowDate.compareTo(endate.toString());
                    if (result > 0) {
                        stadiumFlag = false;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("获取报馆申请截止时间异常", ex.getMessage());
        }
        return stadiumFlag;
    }

    @RequestMapping(value = {"/updateStadiumInfo", "/agreeStadiumInfo", "/rejectStadiumInfo"})
    public R updateStadiumInfo(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        try {
            String strSessionid = sysSessionService.getSessionID(request);
            User user = (User) session.getAttribute("user");
            if (map.get("auditType") != null
                    && ("auditAgree".equals(map.get("auditType")) || "auditReject".equals(map.get("auditType")))) {
                map.put("audittime", new java.sql.Timestamp(System.currentTimeMillis()));
                map.put("auditername", user.getUsername());
            }
            //修改企业信息
            decoratorEbsStadiumManageService.updateStadiumInfo(map);
            // auditType：reAudit-重审；auditAgree-审核通过；auditReject-审核不通过
            String act = "修改报馆信息";
            Object auditType = map.get("auditType");
            if (auditType != null) {
                if ("reAudit".equals(auditType.toString())) {
                    act = "搭建商报馆重审";
                } else if ("auditAgree".equals(auditType.toString())) {
                    act = "搭建商报馆审核通过";
                } else if ("auditReject".equals(auditType.toString())) {
                    act = "搭建商报馆审核不通过";
                }
            }
            sysOperationLogService.CreateEntity(act, strSessionid, 0, user.getId(),
                    Integer.valueOf(map.get("id").toString()), JSONObject.toJSONString(map));

            return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.SAVEDERROR);
        }
    }

    @GetMapping("/selectStadiumInfo")
    public R selectStadiumInfo(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        try {
            String strSessionid = sysSessionService.getSessionID(request);
            map.put("session", strSessionid);
            Map<String, Object> stadiumInfo = decoratorEbsStadiumManageService.selectStadiumInfo(map);
            return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", stadiumInfo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
        }
    }

    @RequestMapping("/downloadAttachment")
    public R downloadAttachment(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
        try {
            String strSessionid = sysSessionService.getSessionID(request);
            map.put("session", strSessionid);
            String companyZipPath = decoratorEbsStadiumManageService.downloadAttachment(map);
            if (companyZipPath != null && !"".equals(companyZipPath)) {
                return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("path", companyZipPath);
            } else {
                return R.ok().put("code", WConst.ERROR).put("msg", "没有找到附件");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
        }
    }

}