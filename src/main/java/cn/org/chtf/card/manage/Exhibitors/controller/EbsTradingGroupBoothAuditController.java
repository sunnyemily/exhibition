package cn.org.chtf.card.manage.Exhibitors.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.org.chtf.card.common.utils.ExcelUtils;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsCompanyinfoService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsBoothAuditService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsManageService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsboothallocationService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsTradingGroupBoothAuditService;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgent;
import cn.org.chtf.card.manage.PreviousInformation.service.PimAgentService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.system.service.SysSmsTemplateService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;

/**
 * 参展商管理-交易团展位审核Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsTradingGroupBoothAuditController")
public class EbsTradingGroupBoothAuditController {
	
	@Autowired
	private PimAgentService pimAgentService;

    @Autowired
    private EbsTradingGroupBoothAuditService ebsTradingGroupBoothAuditService;
    
    @Autowired
    private EbsCompanyinfoService ebsCompanyinfoService;
    
    @Autowired
    private EbsBoothService ebsBoothService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private EbsScatteredExhibitorsboothallocationService ebsScatteredExhibitorsboothallocationService;
   
    @Autowired
    private EbsScatteredExhibitorsManageService ebsScatteredExhibitorsManageService;
    
    @Autowired
    private SysSmsTemplateService sysSmsTemplateService;
   
    @Autowired
    private EbsScatteredExhibitorsBoothAuditService ebsScatteredExhibitorsBoothAuditService;
   
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    /**查询交易团企业信息
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/getTradingGroupCompanyList")
    public R getTradingGroupCompanyList(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			User user = (User) session.getAttribute("user");
			map.put("userId",user.getId());
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = ebsTradingGroupBoothAuditService.getTradingGroupCompanyList(map);			
			list = StringUtil.calculateBoothRegion(list);
			int count = ebsTradingGroupBoothAuditService.getTradingGroupCompanyListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
    }
    
    /**查询单个企业信息
     * @param map
     * @param request
     * @return
     */
    @GetMapping("/selectCompanyInfo")
    public R selectCompanyInfo(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		Map<String,Object> companyInfo = ebsTradingGroupBoothAuditService.selectCompanyInfo(map);
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", companyInfo);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    } 
    
    /**查询交易团列表
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/selectTradingGroup")
    public R selectTradingGroup(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
			User user = (User) session.getAttribute("user");
			map.put("userId",user.getId());
    		List<Map<String,Object>> list = ebsTradingGroupBoothAuditService.selectTradingGroup(map);			
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED).put("data", list);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
    }
    
    /**
     * @Description: 获取人员信息列表
     * @date: 2020年8月13日 上午9:26:50
     * @author: ggwudivs
     * @param map
     * @param request
     * @return: R
     */
    @RequestMapping("/getPersonnelCardList")
    public R getPersonnelCardList(@RequestParam Map<String,Object> map, HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map = ResultVOUtil.TiaoZhengFenYe(map);
    		List<Map<String,Object>> list = ebsTradingGroupBoothAuditService.getPersonnelCardList(map);			
    		int count = ebsTradingGroupBoothAuditService.getPersonnelCardListCount(map);
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    @RequestMapping(value = {"/exhibitionOfficeAudit","/financeOfficeAudit"})
    @Transactional(rollbackFor = Exception.class)
    public R audit(@RequestParam Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("sessionId",strSessionid);
    		String act="";
    		if("exhibitionOfficeAudit".equals(map.get("type"))) {
    			if("2".equals(map.get("status"))) {//审核未通过时，释放已分配的展位
    				map.put("session",strSessionid);
    				ebsScatteredExhibitorsboothallocationService.cancleAllocationBooth(map);
    				act="交易团规划展览部审核未通过";
    			}else if("1".equals(map.get("status"))) {
    				Map<String,Object> map2 = new HashMap<>();
    				map2.put("id",map.get("companyId"));
    				map2.put("buslicensepath",map.get("buslicensepath"));
    				map2.put("relateddocumentspath",map.get("relateddocumentspath"));
					ebsScatteredExhibitorsManageService.updateCompanyInfo(map2);
					//根据企业id查询展位信息
					Map<String,Object> map3 = new HashMap<>();
					map3.put("session",strSessionid);
					map3.put("useable",1);
					map3.put("companyId",map.get("companyId"));
					List<Map<String,Object>> list = ebsBoothService.GetBoothByMap(map3);
					//if(list.size()==0) return R.error().put("code", WConst.ERROR).put("msg", "当前没有展位号，请添加展位号后再审核通过");
					//修改企业表审核状态
	    			EbsCompanyinfo ebsCompanyinfo = new EbsCompanyinfo();
	    			ebsCompanyinfo.setId(Integer.valueOf(map.get("companyId").toString()));
	    			ebsCompanyinfo.setSession(strSessionid);
	    			ebsCompanyinfo.setAuditStatus(2);
	    			ebsCompanyinfo.setAuditRemark("");
	    			ebsCompanyinfoService.update(ebsCompanyinfo);
	    			//发送展位分配短信
		    		String boothNO = "";
		    		for (Map<String,Object> ebsBooth : list) {
		    			boothNO += ","+ebsBooth.get("name");
		    		}
		    		if(boothNO.length()>0) boothNO = boothNO.substring(1);
		    		EbsCompanyinfo ebsCompanyinfo2 = ebsCompanyinfoService.findById(Integer.valueOf(map.get("companyId").toString()));
		    		Map<String,Object> pimAgentMap = new HashMap<>();
		    		pimAgentMap.put("tradinggroupid", ebsCompanyinfo2.getTradinggroupid());
		    		pimAgentMap.put("session", strSessionid);
		    		List<PimAgent> pimAgentList = pimAgentService.findByMap(pimAgentMap);
		    		String phoneNumber = "";
		    		for (PimAgent pimAgent : pimAgentList) {
		    			phoneNumber += pimAgent.getPhone();
					}
		    		if("".equals(phoneNumber)) return R.error().put("code", WConst.ERROR).put("msg", "该企业代办员手机号为空，请先确认代办员信息");
		    		sysSmsTemplateService.sendBoothConfirmSMS(phoneNumber, ebsCompanyinfo2.getChinesename(), boothNO, Integer.valueOf(strSessionid), "交易团");
		    		act="交易团规划展览部审核通过";
    			}
    			map.put("exhibitionOfficeAuditor", user.getId());
    			map.put("exhibitionOfficeAuditStatus", map.get("status"));
    			map.put("exhibitionOfficeAuditRemark", map.get("remark"));
    		}else if("financeOfficeAudit".equals(map.get("type"))) {
    			map.put("financeOfficeAuditor", user.getId());
    			map.put("financeOfficeAuditStatus", map.get("status"));
    			map.put("financeOfficeAuditRemark", map.get("remark"));
    			act="交易团财务部审核通过";
    		}
    		ebsScatteredExhibitorsBoothAuditService.audit(map);
    		sysOperationLogService.CreateEntity(act, strSessionid, 0, user.getId(), Integer.valueOf(map.get("companyId").toString()), map.toString());
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    	} catch (Exception e) {
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
    }
    
    /**
     * 撤回
     * @param map
     * @param request
     * @return
     */
    @GetMapping("/returnInfo")
    @Transactional(rollbackFor = Exception.class)
    public R returnInfo(@RequestParam Map<String,Object> map,HttpServletRequest request,HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("companyId", map.get("id"));
    		map.put("sessionId", strSessionid);
    		ebsScatteredExhibitorsBoothAuditService.returnInfo(map);
    		EbsCompanyinfo ebsCompanyinfo = new EbsCompanyinfo();
    		ebsCompanyinfo.setId(Integer.valueOf(map.get("companyId").toString()));
    		ebsCompanyinfo.setSession(strSessionid);
    		ebsCompanyinfo.setAuditStatus(1);
    		ebsCompanyinfo.setAuditRemark("");
    		ebsCompanyinfoService.update(ebsCompanyinfo);
    		sysOperationLogService.CreateEntity("交易团展位撤回", strSessionid, 0, user.getId(), 0, JSONObject.toJSONString(map));
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    	} catch (Exception e) {
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    /**证件统计
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
    		Map<String,Object> data = ebsTradingGroupBoothAuditService.loadCount(map);
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", data);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }

    /**
     * @Description: 导出excel功能
     * @date: 2020年9月27日 下午4:35:43
     * @author: ggwudivs
     * @param map
     * @param request
     * @param session
     * @return: R
     */
    @RequestMapping("/exportExcel")
    public R exportExcel(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
			User user = (User) session.getAttribute("user");
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			map.put("userId", user.getId());
			List<Map<String, Object>> list = ebsTradingGroupBoothAuditService.queryExportInfo(map);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("JYTZWFP");
			createTitle(workbook, sheet, list.size());
			for (int j = 0; j < list.size(); j++) {
				HSSFRow row = sheet.createRow(1 + j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j + 1);
				row.createCell(1).setCellValue(String.valueOf(list.get(j).get("chinesename")));
				row.createCell(2).setCellValue(String.valueOf(list.get(j).get("tradingGroupName")));
				row.createCell(3).setCellValue(String.valueOf(list.get(j).get("apply_products")));
				row.createCell(4).setCellValue(String.valueOf(list.get(j).get("industryName")));
				HSSFCell cell5 = row.createCell(5);
				cell5.setCellType(CellType.STRING);
				cell5.setCellValue(String.valueOf(list.get(j).get("boothApplyNumber")));
				row.createCell(6).setCellValue(String.valueOf(list.get(j).get("booths")));
				row.createCell(7).setCellValue(String.valueOf(list.get(j).get("contactperson")));
				row.createCell(8).setCellValue(String.valueOf(list.get(j).get("phone")));
				row.createCell(9).setCellValue(String.valueOf(list.get(j).get("tel")));
				row.createCell(10).setCellValue(String.valueOf(list.get(j).get("fax")));
				row.createCell(11).setCellValue(String.valueOf(list.get(j).get("province")));
				row.createCell(12).setCellValue(String.valueOf(list.get(j).get("purchaseintention")));
				row.createCell(13).setCellValue(String.valueOf(list.get(j).get("remark")));
			}
			String fileName = "JYTZWFP.xls";

			// 生成excel文件
			ExcelUtils.buildExcelFile(fileName, workbook);

			sysOperationLogService.CreateEntity("交易团展位审核-导出excel", strSessionid, 0, user.getId(), 0, JSONObject.toJSONString(map));

			// 浏览器下载excel
			// buildExcelDocument(fileName, workbook, response);
			return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("src", "/excel/" + fileName);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
    }

	private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet, int totalRow) {
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
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("企业名称");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("所属交易团");
		cell.setCellStyle(style);
		cell = row.createCell(3);
		cell.setCellValue("参展展品");
		cell.setCellStyle(style);
		cell = row.createCell(4);
		cell.setCellValue("展品类别");
		cell.setCellStyle(style);
		cell = row.createCell(5);
		cell.setCellValue("申请数");
		cell.setCellStyle(style);
		cell = row.createCell(6);
		cell.setCellValue("分配展位");
		cell.setCellStyle(style);
		cell = row.createCell(7);
		cell.setCellValue("联系人");
		cell.setCellStyle(style);
		cell = row.createCell(8);
		cell.setCellValue("手机");
		cell.setCellStyle(style);
		cell = row.createCell(9);
		cell.setCellValue("联系电话");
		cell.setCellStyle(style);
		cell = row.createCell(10);
		cell.setCellValue("联系传真");
		cell.setCellStyle(style);
		cell = row.createCell(11);
		cell.setCellValue("省别");
		cell.setCellStyle(style);
		cell = row.createCell(12);
		cell.setCellValue("采购意向");
		cell.setCellStyle(style);
		cell = row.createCell(13);
		cell.setCellValue("备注");
		cell.setCellStyle(style);
	}
}