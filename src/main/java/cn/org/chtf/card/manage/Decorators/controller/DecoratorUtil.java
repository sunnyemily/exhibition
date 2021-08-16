package cn.org.chtf.card.manage.Decorators.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.org.chtf.card.common.constant.RequestConstant;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DecoratorUtil {

    @Resource
    private HttpUtil httpUtil;

    /**
     * 获取搭建商资质审核时间，判断是否已经超出范围
     * @param request
     * @return
     */
    public boolean getDecoratorAuditFlag(HttpServletRequest request) {
        // 获取搭建商资质审核时间，判断是否已经超出范围
        boolean auditFlag = true;
        try {
            String currentUrl = CryptographyUtil.GeCurrenttUrl(request);
            log.info("获取搭建商资质审核结束时间，当前请求地址:{}", currentUrl);
            String url = RequestConstant.getUrl(currentUrl, RequestConstant.QUALIFICATION_REVIEW_END_DATE_TYPE);
            log.info("获取搭建商资质审核结束时间，请求地址:{}", url);
            String response = httpUtil.doGet(url);
            log.info("获取搭建商资质审核结束时间，当前请求地址:{}，请求地址:{}，返回结果:{}", currentUrl, url, response);
            JSONObject jsonObject = JSON.parseObject(response);
            if (jsonObject != null) {
                Object code = jsonObject.get("code");
                Object endate = jsonObject.get("endate");
                if (code != null && StrUtil.isNotEmpty(code.toString())
                        && StrUtil.equals("200", code.toString())
                        && endate != null && StrUtil.isNotEmpty(endate.toString())) {
                    // 比较当前系统时间和资质审核时间
                    String nowDate = DateUtil.today();
                    int result = nowDate.compareTo(endate.toString());
                    if (result > 0) {
                        auditFlag = false;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("获取搭建商资质审核结束时间异常", ex.getMessage());
        }
        return auditFlag;
    }

    /**
     * 获取搭建商资质审核开始时间和结束时间
     * @param request
     * @return
     */
    public Map<String, String> getDecoratorAuditTime(HttpServletRequest request) {
        Map<String, String> auditTimeMap = new HashMap<>();
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
                Object startdate = jsonObject.get("startdate");
                Object endate = jsonObject.get("endate");
                if (code != null && StrUtil.isNotEmpty(code.toString())
                        && StrUtil.equals("200", code.toString())) {
                    if (startdate != null && StrUtil.isNotEmpty(startdate.toString())) {
                        auditTimeMap.put("decoratorAuditStartTime", startdate.toString());
                    }
                    if (endate != null && StrUtil.isNotEmpty(endate.toString())) {
                        auditTimeMap.put("decoratorAuditEndTime", endate.toString());
                    }
                }
            }
        } catch (Exception ex) {
            log.error("获取搭建商资质审核开始时间和结束时间异常", ex.getMessage());
        }
        return auditTimeMap;
    }

    /**
     * 获取搭建商资质审核开始时间
     * @param request
     * @return
     */
    public String getDecoratorAuditStartTime(HttpServletRequest request) {
        // 获取搭建商资质审核时间
        String auditStartTime = null;
        try {
            String currentUrl = CryptographyUtil.GeCurrenttUrl(request);
            log.info("获取搭建商资质审核开始时间，当前请求地址:{}", currentUrl);
            String url = RequestConstant.getUrl(currentUrl, RequestConstant.QUALIFICATION_REVIEW_END_DATE_TYPE);
            log.info("获取搭建商资质审核开始时间，请求地址:{}", url);
            String response = httpUtil.doGet(url);
            log.info("获取搭建商资质审核开始时间，当前请求地址:{}，请求地址:{}，返回结果:{}", currentUrl, url, response);
            JSONObject jsonObject = JSON.parseObject(response);
            if (jsonObject != null) {
                Object code = jsonObject.get("code");
                Object startdate = jsonObject.get("startdate");
                if (code != null && StrUtil.isNotEmpty(code.toString())
                        && StrUtil.equals("200", code.toString())
                        && startdate != null && StrUtil.isNotEmpty(startdate.toString())) {
                    auditStartTime = startdate.toString();
                }
            }
        } catch (Exception ex) {
            log.error("获取搭建商资质审核开始时间异常", ex.getMessage());
        }
        return auditStartTime;
    }

    /**
     * 获取搭建商资质审核结束时间
     * @param request
     * @return
     */
    public String getDecoratorAuditEndTime(HttpServletRequest request) {
        // 获取搭建商资质审核时间
        String auditEndTime = null;
        try {
            String currentUrl = CryptographyUtil.GeCurrenttUrl(request);
            log.info("获取搭建商资质审核结束时间，当前请求地址:{}", currentUrl);
            String url = RequestConstant.getUrl(currentUrl, RequestConstant.QUALIFICATION_REVIEW_END_DATE_TYPE);
            log.info("获取搭建商资质审核结束时间，请求地址:{}", url);
            String response = httpUtil.doGet(url);
            log.info("获取搭建商资质审核结束时间，当前请求地址:{}，请求地址:{}，返回结果:{}", currentUrl, url, response);
            JSONObject jsonObject = JSON.parseObject(response);
            if (jsonObject != null) {
                Object code = jsonObject.get("code");
                Object endate = jsonObject.get("endate");
                if (code != null && StrUtil.isNotEmpty(code.toString())
                        && StrUtil.equals("200", code.toString())
                        && endate != null && StrUtil.isNotEmpty(endate.toString())) {
                    auditEndTime = endate.toString();
                }
            }
        } catch (Exception ex) {
            log.error("获取搭建商资质审核结束时间异常", ex.getMessage());
        }
        return auditEndTime;
    }

    public String getDateStr(String value) {
        try {
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format1.parse(value);
            DateFormat format2 = new SimpleDateFormat("yyyy年MM月dd日");
            String dataStr = format2.format(date);
            return dataStr;
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
    }
}
