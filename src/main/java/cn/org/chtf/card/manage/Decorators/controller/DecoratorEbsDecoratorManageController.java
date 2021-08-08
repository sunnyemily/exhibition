package cn.org.chtf.card.manage.Decorators.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.manage.Decorators.service.DecoratorEbsDecoratorManageService;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.model.EbsGuestbexhibition;
import cn.org.chtf.card.manage.Exhibitors.service.*;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.member.service.MemberService;
import cn.org.chtf.card.manage.membersession.dao.MemberSessionMapper;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.WConst;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业信息Controller
 *
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Decorators/ebsDecoratorManage")
@Slf4j
public class DecoratorEbsDecoratorManageController {

    @Autowired
    private DecoratorEbsDecoratorManageService decoratorEbsDecoratorManageService;

    @Autowired
    private EbsGuestbexhibitionService ebsGuestbexhibitionService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private EbsCompanyinfoService ebsCompanyinfoService;

    @Autowired
    private EbsBoothApplyService ebsBoothApplyService;

    @Autowired
    private EbsBoothApplyListService ebsBoothApplyListService;

    @Autowired
    private EbsBoothService ebsBoothService;

    @Autowired
    private EbsScatteredExhibitorsBoothAuditService ebsScatteredExhibitorsBoothAuditService;

    @Autowired
    private SysSessionService sysSessionService;

    @Autowired
    private SysOperationLogService sysOperationLogService;

    @Autowired
    private MemberSessionMapper memberSessionMapper;

    @Resource
    private DecoratorUtil decoratorUtil;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        try {
            String strSessionid = sysSessionService.getSessionID(request);
            map.put("session", strSessionid);
            User user = (User) session.getAttribute("user");
            map.put("userId", user.getId());
            map = ResultVOUtil.TiaoZhengFenYe(map);
            List<Map<String, Object>> list = decoratorEbsDecoratorManageService.list(map);
            int count = decoratorEbsDecoratorManageService.listcount(map);
            // 获取搭建商资质审核时间，判断是否已经超出范围
            boolean auditFlag = decoratorUtil.getDecoratorAuditFlag(request);
            if (CollectionUtil.isNotEmpty(list)) {
                final String auditFlagStr = auditFlag + "";
                list.stream().forEach(item -> item.put("auditFlag", auditFlagStr));
            }

            return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
        }
    }

    @RequestMapping("/Previouslist")
    public R Previouslist(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        try {
            String strSessionid = sysSessionService.getSessionID(request);
            map.put("nowsession", strSessionid);
            map = ResultVOUtil.TiaoZhengFenYe(map);
            List<Map<String, Object>> list = decoratorEbsDecoratorManageService.Previouslist(map);
            int count = decoratorEbsDecoratorManageService.Previouslistcount(map);
            return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
        }
    }

    @RequestMapping(value = {"/updateCompanyInfo", "/joinBlackList", "/outBlackList"})
    public R updateCompanyInfo(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        try {
            String strSessionid = sysSessionService.getSessionID(request);
            User user = (User) session.getAttribute("user");
            if (map.get("phone") != null && !"".equals(map.get("phone"))) {
                //校验手机号是否存在
                Map<String, Object> map2 = new HashMap<>(3);
                map2.put("session", strSessionid);
                map2.put("phone", map.get("phone"));
                Map<String, Object> selectCompanyInfo1 = decoratorEbsDecoratorManageService.selectCompanyInfo(map2);
                if (selectCompanyInfo1 != null
                        && !Integer.valueOf(selectCompanyInfo1.get("companyId").toString()).equals(Integer.valueOf(map.get("id").toString()))) {
                    return R.error(WConst.ERROR, "您填写的手机号已存在，请重新填写");
                }
                //校验邮箱是否存在
                map2.put("phone", null);
                map2.put("email", map.get("email"));
                Map<String, Object> selectCompanyInfo2 = decoratorEbsDecoratorManageService.selectCompanyInfo(map2);
                if (selectCompanyInfo2 != null
                        && !Integer.valueOf(selectCompanyInfo2.get("companyId").toString()).equals(Integer.valueOf(map.get("id").toString()))) {
                    return R.error(WConst.ERROR, "您填写的邮箱已存在，请重新填写");
                }
            }
            if (map.get("auditType") != null
                    && ("auditAgree".equals(map.get("auditType")) || "auditReject".equals(map.get("auditType")))) {
                map.put("audittime", new java.sql.Timestamp(System.currentTimeMillis()));
                map.put("auditername", user.getUsername());
            }
            //修改企业信息
            decoratorEbsDecoratorManageService.updateCompanyInfo(map);

            sysOperationLogService.CreateEntity("修改企业信息", strSessionid, 0, user.getId(),
                    Integer.valueOf(map.get("id").toString()), JSONObject.toJSONString(map));

            return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.SAVEDERROR);
        }
    }

    @RequestMapping(value = {"/auditCompanyInfo", "/agreeCompanyInfo", "/rejectCompanyInfo"})
    public R auditCompanyInfo(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        try {
            // 首先验证是否可以进行资质审核
            boolean auditFlag = decoratorUtil.getDecoratorAuditFlag(request);
            if (!auditFlag) {
                return R.error(WConst.ERROR, "已超过资质审核时间");
            }

            String strSessionid = sysSessionService.getSessionID(request);
            User user = (User) session.getAttribute("user");
            if (map.get("phone") != null && !"".equals(map.get("phone"))) {
                //校验手机号是否存在
                Map<String, Object> map2 = new HashMap<>(3);
                map2.put("session", strSessionid);
                map2.put("phone", map.get("phone"));
                Map<String, Object> selectCompanyInfo1 = decoratorEbsDecoratorManageService.selectCompanyInfo(map2);
                if (selectCompanyInfo1 != null
                        && !Integer.valueOf(selectCompanyInfo1.get("companyId").toString()).equals(Integer.valueOf(map.get("id").toString()))) {
                    return R.error(WConst.ERROR, "您填写的手机号已存在，请重新填写");
                }
                //校验邮箱是否存在
                map2.put("phone", null);
                map2.put("email", map.get("email"));
                Map<String, Object> selectCompanyInfo2 = decoratorEbsDecoratorManageService.selectCompanyInfo(map2);
                if (selectCompanyInfo2 != null
                        && !Integer.valueOf(selectCompanyInfo2.get("companyId").toString()).equals(Integer.valueOf(map.get("id").toString()))) {
                    return R.error(WConst.ERROR, "您填写的邮箱已存在，请重新填写");
                }
            }
            if (map.get("auditType") != null
                    && ("auditAgree".equals(map.get("auditType")) || "auditReject".equals(map.get("auditType")))) {
                map.put("audittime", new java.sql.Timestamp(System.currentTimeMillis()));
                map.put("auditername", user.getUsername());
            }
            //修改企业信息
            decoratorEbsDecoratorManageService.updateCompanyInfo(map);
            // auditType：reAudit-重审；auditAgree-审核通过；auditReject-审核不通过
            String act = "修改企业信息";
            Object auditType = map.get("auditType");
            if (auditType != null) {
                if ("reAudit".equals(auditType.toString())) {
                    act = "搭建商重审";
                } else if ("auditAgree".equals(auditType.toString())) {
                    act = "搭建商审核通过";
                } else if ("auditReject".equals(auditType.toString())) {
                    act = "搭建商审核不通过";
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

    @RequestMapping("/deleteCompanyInfo")
    @Transactional(rollbackFor = Exception.class)
    public R deleteCompanyInfo(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        try {
            String strSessionid = sysSessionService.getSessionID(request);
            User user = (User) session.getAttribute("user");
            int companyId = Integer.valueOf(map.get("id").toString());

            EbsCompanyinfo eci = ebsCompanyinfoService.findById(companyId);
            ebsCompanyinfoService.deleteById(companyId);//删除企业信息
            sysOperationLogService.CreateEntity("删除企业信息", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(eci));

            //零散参展商,线上展商,外宾和布撤展企业,登录账号未关联其他届次企业时,删除该账号
            Integer memberId = Integer.valueOf(map.get("memberId").toString());
            Member member = memberService.findById(memberId);
            if ((member.getMemberType() == 2 && member.getMemberStatus() == 0) || member.getMemberType() == 3 || member.getMemberType() == 4 || member.getMemberType() == 5 || member.getMemberType() == 7) {
                List<Map<String, Object>> memberSessionsByMemberId = memberSessionMapper.getMemberSessionsByMemId(memberId);
                if (memberSessionsByMemberId.size() == 1) {
                    memberService.delete(memberId);//删除零散参展商的登录账号
                    sysOperationLogService.CreateEntity("删除企业登录账号", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(member));
                }
            }

            //零散参展商,线上展商,外宾和布撤展企业,删除当前届次关系
            if ((member.getMemberType() == 2 && member.getMemberStatus() == 0) || member.getMemberType() == 3 || member.getMemberType() == 4 || member.getMemberType() == 5 || member.getMemberType() == 7) {
                Map<String, Object> par = new HashMap<String, Object>();
                par.put("session", strSessionid);
                par.put("memberid", memberId);
                par.put("agentid", companyId);
                memberSessionMapper.delete(par);
                sysOperationLogService.CreateEntity("删除当前届次关系", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(par));
            }

            //交易团企业和零散参展商，删除企业展位申请信息
            if (member.getMemberType() == 0 || member.getMemberType() == 2) {
                Map<String, Object> applyIdMap = ebsBoothApplyService.findByCompanyId(companyId, Integer.valueOf(strSessionid));
                if (applyIdMap != null && applyIdMap.get("applyId") != null) {
                    ebsBoothApplyService.deleteById(Integer.valueOf(applyIdMap.get("applyId").toString()));
                    sysOperationLogService.CreateEntity("删除企业展位申请信息", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(map));
                    ebsBoothApplyListService.deleteById(Integer.valueOf(applyIdMap.get("applyId").toString()));
                    sysOperationLogService.CreateEntity("删除企业展位申请详情", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(map));
                }
                // TODO 释放企业展位
                ebsBoothService.releaseCompanyBooth(companyId, Integer.valueOf(strSessionid));
                sysOperationLogService.CreateEntity("释放企业展位", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(map));
                // TODO 删除企业展位审核信息
                ebsScatteredExhibitorsBoothAuditService.deleteBoothAuditInfo(companyId, Integer.valueOf(strSessionid));
                sysOperationLogService.CreateEntity("删除企业展位审核信息", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(map));
            }

            return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
        }
    }

    @GetMapping("/selectCompanyInfo")
    public R selectCompanyInfo(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        try {
            String strSessionid = sysSessionService.getSessionID(request);
            map.put("session", strSessionid);
            Map<String, Object> companyInfo = decoratorEbsDecoratorManageService.selectCompanyInfo(map);
            companyInfo.put("auditStartTime", decoratorUtil.getDecoratorAuditStartTime(request));
            companyInfo.put("auditEndTime", decoratorUtil.getDecoratorAuditEndTime(request));
            return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", companyInfo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
        }
    }

    @GetMapping("/getDecoratorAuditTimeText")
    public R selectCompanyInfo(HttpServletRequest request) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("认证通过：");
            String decoratorAuditStartTime = decoratorUtil.getDecoratorAuditStartTime(request);
            if (StrUtil.isNotEmpty(decoratorAuditStartTime)) {
                sb.append(decoratorUtil.getDateStr(decoratorAuditStartTime));
            }
            sb.append("至");
            String decoratorAuditEndTime = decoratorUtil.getDecoratorAuditEndTime(request);
            if (StrUtil.isNotEmpty(decoratorAuditEndTime)) {
                sb.append(decoratorUtil.getDateStr(decoratorAuditEndTime));
            }
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("auditTimeText", sb.toString());
            return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", dataMap);
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
            String companyZipPath = decoratorEbsDecoratorManageService.downloadAttachment(map);
            if (companyZipPath != null && !"".equals(companyZipPath)) {
                return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DOWNLOAD_SUCCESS).put("path", companyZipPath);
            } else {
                return R.ok().put("code", WConst.ERROR).put("msg", "没有找到附件");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.DOWNLOAD_FAILED);
        }
    }

    @RequestMapping("/UseScatteredExhibitors")
    public R UseScatteredExhibitors(@RequestBody Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        String strSessionid = sysSessionService.getSessionID(request);
        User user = (User) session.getAttribute("user");
        map.put("newsession", strSessionid);

        R r = decoratorEbsDecoratorManageService.UseScatteredExhibitors(map);
        if (r.get("code").equals("1")) {
            sysOperationLogService.CreateEntity("提取历届零散参展商", strSessionid, 0, user.getId(),
                    0, JSONObject.toJSONString(map));
        }
        return r;
        //return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    }

    /**
     * 重置密码
     *
     * @param map
     * @return
     */
    @RequestMapping("/ResetPasswordlingsan")
    public ResultVO ResetPasswordlingsan(@RequestBody Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        String strSessionid = sysSessionService.getSessionID(request);
        User user = (User) session.getAttribute("user");
        String pass = CryptographyUtil.md5(WConst.DEFAULT_PASSWORD, String.valueOf(map.get("loginname")));
        EbsGuestbexhibition ebsGuestbexhibition = new EbsGuestbexhibition();
        ebsGuestbexhibition.setLoginpass(pass);
        ebsGuestbexhibition.setMemberid(Integer.valueOf(String.valueOf(map.get("memberid"))));
        ebsGuestbexhibitionService.ResetPassword(ebsGuestbexhibition);
        sysOperationLogService.CreateEntity("初始化登录密码", strSessionid, 0, user.getId(),
                0, JSONObject.toJSONString(map));
        return ResultVOUtil.success();
    }

    /**
     * 重置密码
     *
     * @param map
     * @return
     */
    @RequestMapping("/ResetPasswordcaigou")
    public ResultVO ResetPasswordcaigou(@RequestBody Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        String strSessionid = sysSessionService.getSessionID(request);
        User user = (User) session.getAttribute("user");
        String pass = CryptographyUtil.md5(WConst.DEFAULT_PASSWORD, String.valueOf(map.get("loginname")));
        EbsGuestbexhibition ebsGuestbexhibition = new EbsGuestbexhibition();
        ebsGuestbexhibition.setLoginpass(pass);
        ebsGuestbexhibition.setMemberid(Integer.valueOf(String.valueOf(map.get("memberid"))));
        ebsGuestbexhibitionService.ResetPassword(ebsGuestbexhibition);
        sysOperationLogService.CreateEntity("初始化登录密码", strSessionid, 0, user.getId(),
                0, JSONObject.toJSONString(map));
        return ResultVOUtil.success();
    }

    /**
     * 采购商管理证件统计
     *
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/loadCount")
    public R loadCount(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            String strSessionid = sysSessionService.getSessionID(request);
            map.put("session", strSessionid);
            map.put("userId", user.getId());
            map.put("type", 5);
            Map<String, Object> data = decoratorEbsDecoratorManageService.loadCount(map);
            return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
        }
    }

    @RequestMapping("/DownLoadFile")
    public R DownLoadFile(@RequestParam Map<String, Object> map,
                          HttpServletRequest request, HttpServletResponse response,
                          HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            String strSessionid = sysSessionService.getSessionID(request);
            map.put("session", strSessionid);
            List<Map<String, Object>> list = decoratorEbsDecoratorManageService.GetDownLoadInfo(map);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("搭建商");
            createTitle(workbook, sheet, list.size());
            for (int j = 0; j < list.size(); j++) {
                HSSFRow row = sheet.createRow(1 + j);
                row.setHeightInPoints(18);
                row.createCell(0).setCellValue(
                        String.valueOf(list.get(j).get("companyId")));
                row.createCell(1).setCellValue(
                        String.valueOf(list.get(j).get("chinesename")));
                row.createCell(2).setCellValue(
                        String.valueOf(list.get(j).get("organizationcode")));
                row.createCell(3).setCellValue(
                        String.valueOf(list.get(j).get("companyprofile")));
                row.createCell(4).setCellValue(
                        String.valueOf(list.get(j).get("legalpersonname")));
                row.createCell(5).setCellValue(
                        String.valueOf(list.get(j).get("legalpersoncardnumber")));
                row.createCell(6).setCellValue(
                        String.valueOf(list.get(j).get("contactperson")));
                row.createCell(7).setCellValue(
                        String.valueOf(list.get(j).get("phone")));
                row.createCell(8).setCellValue(
                        String.valueOf(list.get(j).get("email")));
                row.createCell(9).setCellValue(
                        String.valueOf(list.get(j).get("addtime")));
                row.createCell(10).setCellValue(
                        String.valueOf(list.get(j).get("auditStatusName")));
                row.createCell(11).setCellValue(
                        String.valueOf(list.get(j).get("audittime")));
                row.createCell(12).setCellValue(
                        String.valueOf(list.get(j).get("auditRemark")));
                row.createCell(13).setCellValue(
                        String.valueOf(list.get(j).get("auditername")));

            }
            String fileName = "搭建商.xls";

            // 生成excel文件
            buildExcelFile(fileName, workbook);

            sysOperationLogService.CreateEntity("搭建商信息", strSessionid, 0,
                    user.getId(), 0, JSONObject.toJSONString(map));

            // 浏览器下载excel
            // buildExcelDocument(fileName, workbook, response);
            return R.ok().put("code", WConst.SUCCESS)
                    .put("msg", WConst.QUERYSUCCESS)
                    .put("src", "/excel/" + fileName);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR)
                    .put("msg", WConst.QUERYFAILD);
        }
    }

    // 创建表头
    private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet,
                             int totalRow) {
        // 合并单元格
        // 参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
        // CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 0);
        // sheet.addMergedRegion(region1);
        HSSFRow row = sheet.createRow(0);
        // 设置行高
        row.setHeightInPoints(18);
        // 设置列宽度
        sheet.setColumnWidth(0, 30 * 256);
        // 设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("ID");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("企业全称");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("企业统一社会信用代码");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("单位情况简介");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("法人姓名");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("法人身份证号码");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("联系人姓名");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("联系人电话");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("电子邮箱");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("最近一次提审时间");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("审核状态");
        cell.setCellStyle(style);
        cell = row.createCell(11);
        cell.setCellValue("审核时间");
        cell.setCellStyle(style);
        cell = row.createCell(12);
        cell.setCellValue("驳回原因");
        cell.setCellStyle(style);
        cell = row.createCell(13);
        cell.setCellValue("审核账号");
        cell.setCellStyle(style);
    }

    // 生成excel文件
    public void buildExcelFile(String filename, HSSFWorkbook workbook)
            throws Exception {
        String filePath = "./static/excel/";
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(filePath + filename);
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

    // 浏览器下载excel
    public void buildExcelDocument(String filename, HSSFWorkbook workbook,
                                   HttpServletResponse response) throws Exception {
        response.reset();
        response.setContentType("octets/stream");
        // response.setHeader("Content-Disposition","attachment;filename="+exportFileName);
        response.setHeader("Content-Disposition", "attachment;filename=\""
                + new String(filename.getBytes("UTF-8"), "iso8859-1") + "\"");
    }

}