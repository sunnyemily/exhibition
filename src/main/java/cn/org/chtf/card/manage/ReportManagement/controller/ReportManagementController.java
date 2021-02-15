package cn.org.chtf.card.manage.ReportManagement.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.ReportManagement.model.AudienceSurvey;
import cn.org.chtf.card.manage.ReportManagement.model.PurchaseIntentionStatistics;
import cn.org.chtf.card.manage.ReportManagement.model.ReportManagement;
import cn.org.chtf.card.manage.ReportManagement.service.ReportManagementService;
import cn.org.chtf.card.manage.system.model.SysSurvey;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.system.service.SysSurveyService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;

/**
 * 报表管理Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/ReportManagement/Common")
public class ReportManagementController {

    @Autowired
    private ReportManagementService peportManagementService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private SysSurveyService sysSurveyService; 
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
   
    @RequestMapping("/getEnterpriseList")
    public R getEnterpriseList(@RequestParam Map<String,Object> map, HttpServletRequest request){
    	try {
    		List<Map<String,Object>> list = peportManagementService.getEnterpriseList(map);
    		String showRoomIds = "";
    		for (Map<String, Object> map2 : list) {
				if(map2 != null && !"".equals(map2.get("showRoomIds"))) showRoomIds+=(map2.get("showRoomIds")+",");
			}
    		if(showRoomIds.length()>1) showRoomIds = showRoomIds.substring(0, showRoomIds.length()-1);
    		map.put("showRoomIds", showRoomIds);
    		List<Map<String, Object>> showRoomsList = peportManagementService.getShowRoomList(map);
    		list = StringUtil.calculateBoothRegion(list);
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("showRoomsList", showRoomsList);
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    @RequestMapping("/NationalDocumentStatisticsList")
	public R NationalDocumentStatisticsList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = peportManagementService.NationalDocumentStatisticsList(map);			
			int count = peportManagementService.NationalDocumentStatisticsListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}   
    
    @RequestMapping("/NationalDocumentStatisticsCardList")
	public R NationalDocumentStatisticsCardList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<ReportManagement> list = peportManagementService.NationalDocumentStatisticsCardList(map);			
			int count = peportManagementService.NationalDocumentStatisticsCardListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}   
    
    @RequestMapping("/AudienceSurveyList")
	public R AudienceSurveyList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<AudienceSurvey> list = peportManagementService.AudienceSurveyList(map);	
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", list.size());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}   
    
    @RequestMapping("/AudienceSurveyViewList")
	public R AudienceSurveyViewList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = peportManagementService.AudienceSurveyViewList(map);	
			int count = peportManagementService.AudienceSurveyViewListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}   
    
    @RequestMapping("/RegistrationCertificateList")
	public R RegistrationCertificateList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = peportManagementService.RegistrationCertificateList(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}   
    
    @RequestMapping("/PurchaseIntentionStatisticsList")
	public R PurchaseIntentionStatisticsList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<PurchaseIntentionStatistics> list = peportManagementService.PurchaseIntentionStatisticsList(map);	
			int count = peportManagementService.PurchaseIntentionStatisticsListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}   
    
    @RequestMapping("/PlaceofAttributionList")
	public R PlaceofAttributionList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = peportManagementService.PlaceofAttributionList(map);
			StringUtil.calculateBoothRegion(list);
			int count = peportManagementService.PlaceofAttributionListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}  
    
    @RequestMapping("/ExhibitorInformationStatisticsList")
	public R ExhibitorInformationStatisticsList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = peportManagementService.ExhibitorInformationStatisticsList(map);	
			int count = peportManagementService.ExhibitorInformationStatisticsListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}   
    
    @RequestMapping("/QuestionnaireManagementList")
	public R QuestionnaireManagementList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			SysSurvey ss = sysSurveyService.findByOneMap(map);
			if(ss==null){
				List<Map<String,Object>> lit = new ArrayList<Map<String,Object>>();
				return R.ok().put("data", lit).put("code", WConst.SUCCESS).put("msg", "无调查表信息").put("count", 0);
				//return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
			}
			map.put("surveyid", ss.getId());
			List<Map<String,Object>> list = peportManagementService.QuestionnaireManagementList(map);	
			int count = peportManagementService.QuestionnaireManagementListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}  
    
    @RequestMapping("/HeadofTradingGroupList")
	public R HeadofTradingGroupList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = peportManagementService.HeadofTradingGroupList(map);	
			int count = peportManagementService.HeadofTradingGroupListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}  
    
    @RequestMapping("/ExportForeignGuestsList")
	public R ExportForeignGuestsList(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = peportManagementService.ExportForeignGuestsList(map);	
			int count = peportManagementService.ExportForeignGuestsListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}  
    
    @RequestMapping("/ESDownLoad")
	public R ESDownLoad(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			User user = (User)session.getAttribute("user");	
			map.put("session", strSessionid);
			List<Map<String,Object>> list = peportManagementService.ExhibitorInformationStatisticsList(map);
			//List<Map<String, Object>> list = peportManagementService.GetDownLoadInfo(map);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("CZSXXTJ");
			ExhibitorInformationStatisticscreateTitle(workbook, sheet, list.size());
			for(int j=0;j<list.size();j++){
				HSSFRow row = sheet.createRow(1+j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j+1);
				row.createCell(1).setCellValue(String.valueOf(list.get(j).get("booths")));
				row.createCell(2).setCellValue(String.valueOf(list.get(j).get("mianji")));
				row.createCell(3).setCellValue(String.valueOf(list.get(j).get("chinesename")));
				row.createCell(4).setCellValue(String.valueOf(list.get(j).get("engname")));
				row.createCell(5).setCellValue(String.valueOf(list.get(j).get("name")));
				row.createCell(6).setCellValue(String.valueOf(list.get(j).get("contactperson")));
				row.createCell(7).setCellValue(String.valueOf(list.get(j).get("phone")));
				row.createCell(8).setCellValue(String.valueOf(list.get(j).get("httype")));
				row.createCell(9).setCellValue(String.valueOf(list.get(j).get("czstype")));
				row.createCell(10).setCellValue(String.valueOf(list.get(j).get("htnumber")));
			}
			String fileName = "CZSXXTJ.xls";

			// 生成excel文件
			buildExcelFile(fileName, workbook);

			sysOperationLogService.CreateEntity("导出参展商信息统计", strSessionid, 0, user.getId(), 
					0, JSONObject.toJSONString(map));
			// 浏览器下载excel
			//buildExcelDocument(fileName, workbook, response);
			return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("src", "/excel/"+fileName);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}

	// 创建表头
	private void ExhibitorInformationStatisticscreateTitle(HSSFWorkbook workbook, HSSFSheet sheet, int totalRow) {
		// 合并单元格
		// 参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
		//CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 0);
		//sheet.addMergedRegion(region1);
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
		cell = row.createCell(0);cell.setCellValue("序号");cell.setCellStyle(style);
		cell = row.createCell(1);cell.setCellValue("展位号");cell.setCellStyle(style);
		cell = row.createCell(2);cell.setCellValue("展台面积");cell.setCellStyle(style);
		cell = row.createCell(3);cell.setCellValue("参展商中文名称");cell.setCellStyle(style);
		cell = row.createCell(4);cell.setCellValue("参展商英文名称");cell.setCellStyle(style);
		cell = row.createCell(5);cell.setCellValue("所属国家或地区");cell.setCellStyle(style);
		cell = row.createCell(6);cell.setCellValue("联系人名称");cell.setCellStyle(style);
		cell = row.createCell(7);cell.setCellValue("联系方式电话");cell.setCellStyle(style);
		cell = row.createCell(8);cell.setCellValue("合同方式");cell.setCellStyle(style);
		cell = row.createCell(9);cell.setCellValue("参展商类型");cell.setCellStyle(style);
		cell = row.createCell(10);cell.setCellValue("合同编号");cell.setCellStyle(style);
	}
	
	@RequestMapping("/PurchaseIntentionStatisticsDownLoad")
	public R PurchaseIntentionStatisticsDownLoad(@RequestParam Map<String, Object> map, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session) {
		try {
			User user = (User)session.getAttribute("user");	
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			List<Map<String,Object>> list = peportManagementService.PurchaseIntentionStatisticsListForDownLoad(map);
			//List<Map<String, Object>> list = peportManagementService.GetDownLoadInfo(map);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("CGYXTJ");
			PurchaseIntentionStatisticscreateTitle(workbook, sheet, list.size());
			for(int j=0;j<list.size();j++){
				HSSFRow row = sheet.createRow(1+j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j+1);
				row.createCell(1).setCellValue(String.valueOf(list.get(j).get("name")));
				row.createCell(2).setCellValue(String.valueOf(list.get(j).get("shuliang")));
				row.createCell(3).setCellValue(String.valueOf(list.get(j).get("percent")));
			}
			String fileName = "CGYXTJ.xls";

			// 生成excel文件
			buildExcelFile(fileName, workbook);

			sysOperationLogService.CreateEntity("导出采购意向统计", strSessionid, 0, user.getId(), 
					0, JSONObject.toJSONString(map));
			
			// 浏览器下载excel
			//buildExcelDocument(fileName, workbook, response);
			return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("src", "/excel/"+fileName);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}

	// 创建表头
	private void PurchaseIntentionStatisticscreateTitle(HSSFWorkbook workbook, HSSFSheet sheet, int totalRow) {
		// 合并单元格
		// 参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
		//CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 0);
		//sheet.addMergedRegion(region1);
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
		cell = row.createCell(0);cell.setCellValue("序号");cell.setCellStyle(style);
		cell = row.createCell(1);cell.setCellValue("采购名称");cell.setCellStyle(style);
		cell = row.createCell(2);cell.setCellValue("数量");cell.setCellStyle(style);
		cell = row.createCell(3);cell.setCellValue("百分比");cell.setCellStyle(style);
	}
	
	@RequestMapping("/AudienceSurveyDownLoad")
	public R AudienceSurveyDownLoad(@RequestParam Map<String, Object> map, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session) {
		try {
			User user = (User)session.getAttribute("user");	
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			List<AudienceSurvey> list = peportManagementService.AudienceSurveyList(map);
			//List<Map<String, Object>> list = peportManagementService.GetDownLoadInfo(map);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("GZHDC");
			AudienceSurveycreateTitle(workbook, sheet, list.size());
			for(int j=0;j<list.size();j++){
				HSSFRow row = sheet.createRow(1+j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j+1);
				row.createCell(1).setCellValue(String.valueOf(list.get(j).getTypename()));
				row.createCell(2).setCellValue(String.valueOf(list.get(j).getAnswername()));
				row.createCell(3).setCellValue(String.valueOf(list.get(j).getTotal()));
				row.createCell(4).setCellValue(list.get(j).getPercent().equals("0") ? "0" : list.get(j).getPercent()+"%");
			}
			String fileName = "GZHDC.xls";

			// 生成excel文件
			buildExcelFile(fileName, workbook);

			sysOperationLogService.CreateEntity("导出观众调查", strSessionid, 0, user.getId(), 
					0, JSONObject.toJSONString(map));
			
			// 浏览器下载excel
			//buildExcelDocument(fileName, workbook, response);
			return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("src", "/excel/"+fileName);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}

	// 创建表头
	private void AudienceSurveycreateTitle(HSSFWorkbook workbook, HSSFSheet sheet, int totalRow) {
		// 合并单元格
		// 参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
		//CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 0);
		//sheet.addMergedRegion(region1);
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
		cell = row.createCell(0);cell.setCellValue("序号");cell.setCellStyle(style);
		cell = row.createCell(1);cell.setCellValue("问题类型");cell.setCellStyle(style);
		cell = row.createCell(2);cell.setCellValue("答案选项");cell.setCellStyle(style);
		cell = row.createCell(3);cell.setCellValue("总人数");cell.setCellStyle(style);
		cell = row.createCell(4);cell.setCellValue("百分比");cell.setCellStyle(style);
	}
	
	@RequestMapping("/PlaceofAttributionDownLoad")
	public R PlaceofAttributionDownLoad(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			List<Map<String,Object>> list = peportManagementService.PlaceofAttributionList(map);
			StringUtil.calculateBoothRegion(list);
			//List<Map<String, Object>> list = peportManagementService.GetDownLoadInfo(map);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("JYTCZHZGSD");
			PlaceofAttributioncreateTitle(workbook, sheet, list.size());
			for(int j=0;j<list.size();j++){
				HSSFRow row = sheet.createRow(1+j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j+1);
				row.createCell(1).setCellValue(String.valueOf(list.get(j).get("name")));
				row.createCell(2).setCellValue(String.valueOf(list.get(j).get("country")));
				row.createCell(3).setCellValue(String.valueOf(list.get(j).get("province")));
				row.createCell(4).setCellValue(String.valueOf(list.get(j).get("companyNumber")));
				row.createCell(5).setCellValue(String.valueOf(list.get(j).get("cardNumber")));
				row.createCell(6).setCellValue(String.valueOf(list.get(j).get("booths")));
			}
			String fileName = "JYTCZHZGSD.xls";

			// 生成excel文件
			buildExcelFile(fileName, workbook);
			User user = (User)session.getAttribute("user");	
			sysOperationLogService.CreateEntity("导出交易团参展证归属地", strSessionid, 0, user.getId(), 0, JSONObject.toJSONString(map));
			
			// 浏览器下载excel
			//buildExcelDocument(fileName, workbook, response);
			return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("src", "/excel/"+fileName);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}

	// 创建表头
	private void PlaceofAttributioncreateTitle(HSSFWorkbook workbook, HSSFSheet sheet, int totalRow) {
		// 合并单元格
		// 参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
		//CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 0);
		//sheet.addMergedRegion(region1);
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
		cell = row.createCell(0);cell.setCellValue("序号");cell.setCellStyle(style);
		cell = row.createCell(1);cell.setCellValue("交易团名");cell.setCellStyle(style);
		cell = row.createCell(2);cell.setCellValue("国家");cell.setCellStyle(style);
		cell = row.createCell(3);cell.setCellValue("省份");cell.setCellStyle(style);
		cell = row.createCell(4);cell.setCellValue("企业数量");cell.setCellStyle(style);
		cell = row.createCell(5);cell.setCellValue("参展证数量");cell.setCellStyle(style);
		cell = row.createCell(6);cell.setCellValue("展位号");cell.setCellStyle(style);
	}
	
	@RequestMapping("/HeadofTradingGroupDownLoad")
	public R HeadofTradingGroupDownLoad(@RequestParam Map<String, Object> map, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			List<Map<String,Object>> list = peportManagementService.HeadofTradingGroupList(map);
			//List<Map<String, Object>> list = peportManagementService.GetDownLoadInfo(map);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("JYTFZRTJ");
			HeadofTradingGroupcreateTitle(workbook, sheet, list.size());
			for(int j=0;j<list.size();j++){
				HSSFRow row = sheet.createRow(1+j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j+1);
				row.createCell(1).setCellValue(String.valueOf(list.get(j).get("jytname")));
				row.createCell(2).setCellValue(String.valueOf(list.get(j).get("dbyname")));
				row.createCell(3).setCellValue(String.valueOf(list.get(j).get("tel")));
				row.createCell(4).setCellValue(String.valueOf(list.get(j).get("phone")));
				row.createCell(5).setCellValue(String.valueOf(list.get(j).get("companyname")));
				row.createCell(6).setCellValue(String.valueOf(list.get(j).get("qtzj")));
				row.createCell(7).setCellValue(String.valueOf(list.get(j).get("imagepath")));
				row.createCell(8).setCellValue(String.valueOf(list.get(j).get("loginname")));
				row.createCell(9).setCellValue(String.valueOf(list.get(j).get("zhanweihao")));
				row.createCell(10).setCellValue(String.valueOf(list.get(j).get("zhanweishu")));
			}
			String fileName = "JYTFZRTJ.xls";

			// 生成excel文件
			buildExcelFile(fileName, workbook);
			User user = (User)session.getAttribute("user");	
			sysOperationLogService.CreateEntity("导出交易团负责人统计表", strSessionid, 0, user.getId(), 
					0, JSONObject.toJSONString(map));
			
			// 浏览器下载excel
			//buildExcelDocument(fileName, workbook, response);
			return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("src", "/excel/"+fileName);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}

	// 创建表头
	private void HeadofTradingGroupcreateTitle(HSSFWorkbook workbook, HSSFSheet sheet, int totalRow) {
		// 合并单元格
		// 参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
		//CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 0);
		//sheet.addMergedRegion(region1);
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
		cell = row.createCell(0);cell.setCellValue("序号");cell.setCellStyle(style);
		cell = row.createCell(1);cell.setCellValue("交易团名称");cell.setCellStyle(style);
		cell = row.createCell(2);cell.setCellValue("联系人");cell.setCellStyle(style);
		cell = row.createCell(3);cell.setCellValue("电话");cell.setCellStyle(style);
		cell = row.createCell(4);cell.setCellValue("移动手机");cell.setCellStyle(style);
		cell = row.createCell(5);cell.setCellValue("单位名称");cell.setCellStyle(style);
		cell = row.createCell(6);cell.setCellValue("其他证件");cell.setCellStyle(style);
		cell = row.createCell(7);cell.setCellValue("证件照");cell.setCellStyle(style);
		cell = row.createCell(8);cell.setCellValue("登录名");cell.setCellStyle(style);
		cell = row.createCell(9);cell.setCellValue("展位号");cell.setCellStyle(style);
		cell = row.createCell(10);cell.setCellValue("展位数");cell.setCellStyle(style);
	}
	
	@RequestMapping("/ExportForeignGuestsDownLoad")
	public R ExportForeignGuestsDownLoad(@RequestParam Map<String, Object> map, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session) {
		try {
			User user = (User)session.getAttribute("user");	
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			List<Map<String,Object>> list = peportManagementService.ExportForeignGuestsList(map);	
			//List<Map<String, Object>> list = peportManagementService.GetDownLoadInfo(map);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("DCJBB");
			ExportForeignGuestscreateTitle(workbook, sheet, list.size());
			for(int j=0;j<list.size();j++){
				HSSFRow row = sheet.createRow(1+j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j+1);
				row.createCell(1).setCellValue(String.valueOf(list.get(j).get("name")));
				row.createCell(2).setCellValue(String.valueOf(list.get(j).get("jobtitle")));
				row.createCell(3).setCellValue(String.valueOf(list.get(j).get("sexname")));
				row.createCell(4).setCellValue(String.valueOf(list.get(j).get("countryname")));
			}
			String fileName = "DCJBB.xls";

			// 生成excel文件
			buildExcelFile(fileName, workbook);

			//sysOperationLogService.CreateEntity("导出采购意向统计", strSessionid, 0, user.getId(), 
			//		0, JSONObject.toJSONString(map));
			
			// 浏览器下载excel
			//buildExcelDocument(fileName, workbook, response);
			return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("src", "/excel/"+fileName);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}

	// 创建表头
	private void ExportForeignGuestscreateTitle(HSSFWorkbook workbook, HSSFSheet sheet, int totalRow) {
		// 合并单元格
		// 参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
		//CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 0);
		//sheet.addMergedRegion(region1);
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
		cell = row.createCell(0);cell.setCellValue("序号");cell.setCellStyle(style);
		cell = row.createCell(1);cell.setCellValue("姓名");cell.setCellStyle(style);
		cell = row.createCell(2);cell.setCellValue("职务");cell.setCellStyle(style);
		cell = row.createCell(3);cell.setCellValue("性别");cell.setCellStyle(style);
		cell = row.createCell(4);cell.setCellValue("国家");cell.setCellStyle(style);
	}
	

	// 生成excel文件
	public void buildExcelFile(String filename, HSSFWorkbook workbook)
			throws Exception {
		String filePath = "./static/excel/";
		File targetFile = new File(filePath);  
        if(!targetFile.exists()){    
        	targetFile.mkdirs();    
        }
		FileOutputStream fos = new FileOutputStream(filePath+filename);
		workbook.write(fos);
		fos.flush();
		fos.close();
	}
	
	@RequestMapping("/AudienceSurveyViewDownLoad")
	public R AudienceSurveyViewDownLoad(@RequestParam Map<String, Object> map, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			List<Map<String,Object>> list = peportManagementService.AudienceSurveyViewList(map);		
			//List<Map<String, Object>> list = peportManagementService.GetDownLoadInfo(map);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("GZDCVIEW");
			AudienceSurveyViewDownLoadcreateTitle(workbook, sheet, list.size());
			for(int j=0;j<list.size();j++){
				HSSFRow row = sheet.createRow(1+j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j+1);
				row.createCell(1).setCellValue(String.valueOf(list.get(j).get("cardtypename")));
				row.createCell(2).setCellValue(String.valueOf(list.get(j).get("tjleixing")));
				row.createCell(3).setCellValue(String.valueOf(list.get(j).get("companyname")));
				row.createCell(4).setCellValue(String.valueOf(list.get(j).get("name")));
				row.createCell(5).setCellValue(String.valueOf(list.get(j).get("imagepath")));
				row.createCell(6).setCellValue(String.valueOf(list.get(j).get("agentname")));
				row.createCell(7).setCellValue(String.valueOf(list.get(j).get("printstatusname")));
				row.createCell(8).setCellValue(String.valueOf(list.get(j).get("printnumber")));
				row.createCell(9).setCellValue(String.valueOf(list.get(j).get("iccode")));
			}
			String fileName = "GZDCVIEW.xls";

			// 生成excel文件
			buildExcelFile(fileName, workbook);
			User user = (User)session.getAttribute("user");	
			sysOperationLogService.CreateEntity("导出观众调查详细", strSessionid, 0, user.getId(), 
					0, JSONObject.toJSONString(map));
			
			// 浏览器下载excel
			//buildExcelDocument(fileName, workbook, response);
			return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("src", "/excel/"+fileName);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	}

	// 创建表头
	private void AudienceSurveyViewDownLoadcreateTitle(HSSFWorkbook workbook, HSSFSheet sheet, int totalRow) {
		// 合并单元格
		// 参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
		//CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 0);
		//sheet.addMergedRegion(region1);
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
		cell = row.createCell(0);cell.setCellValue("序号");cell.setCellStyle(style);
		cell = row.createCell(1);cell.setCellValue("证件类型");cell.setCellStyle(style);
		cell = row.createCell(2);cell.setCellValue("添加类型");cell.setCellStyle(style);
		cell = row.createCell(3);cell.setCellValue("公司名称");cell.setCellStyle(style);
		cell = row.createCell(4);cell.setCellValue("姓名");cell.setCellStyle(style);
		cell = row.createCell(5);cell.setCellValue("证件照");cell.setCellStyle(style);
		cell = row.createCell(6);cell.setCellValue("代办员");cell.setCellStyle(style);
		cell = row.createCell(7);cell.setCellValue("打印状态");cell.setCellStyle(style);
		cell = row.createCell(8);cell.setCellValue("PrintCode");cell.setCellStyle(style);
		cell = row.createCell(9);cell.setCellValue("IC卡号");cell.setCellStyle(style);
	}

}